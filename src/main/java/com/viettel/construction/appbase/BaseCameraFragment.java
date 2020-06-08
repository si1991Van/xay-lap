package com.viettel.construction.appbase;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.viettel.construction.R;
import com.viettel.construction.common.GPSTracker;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.db.BacDzDbHelper;
import com.viettel.construction.model.db.ImageCapture;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.FileUtils;
import com.viettel.construction.util.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.OnClick;
import butterknife.Optional;

import static android.content.Context.LOCATION_SERVICE;

public class BaseCameraFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static String TAG = BaseCameraFragment.class.getSimpleName();
    private GPSTracker gps = null;
    public double latitude, longitude;
    public GoogleApiClient googleApiClient;
    private LocationManager locationManager;
    public static final int PERMISSION_REQUEST_CODE = 1003;
    private static final int REQUEST_CHECK_PERMISSION_LOCATION = 10001;
    public File mPhotoFile;


    @Optional
    @OnClick(R.id.btn_camera)
    public void onClickBtnCamera() {
        try {
            if (checkRuntimePermission()) {
                accessLocation();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public boolean checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            //Đã cấp quyền rồi
            if (gps == null)
                gps = new GPSTracker(getContext());
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Yêu cầu quyền
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            switch (requestCode) {
                case PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0) {
                        boolean accessCamera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean writeExternalStorage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean accessLocation = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        if (accessCamera && readExternalStorage && writeExternalStorage && accessLocation) {
                            if (gps == null)
                                gps = new GPSTracker(getContext());
                            //Mo quyen thanh cong
                            getLocation_CapturePhoto();
                        } else {
                            checkPermissionDontAskAgain();
                        }
                    } else {
                        checkPermissionDontAskAgain();
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AlertDialog dialogPermission = null;

    private void checkPermissionDontAskAgain() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                    || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                    || shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                    || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Neu chua tich vao don't ash again
                checkRuntimePermission();
            } else {
                try {
                    String strMessage = "";
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        strMessage = getString(R.string.DenyCamera);
                    else if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                            || !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                        strMessage = getString(R.string.DenyGPS);
                    else if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                        strMessage = getString(R.string.DenyReadMemory);
                    else if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        strMessage = getString(R.string.DenyWriteMemory);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(strMessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.setting, (dialogInterface, i) -> {
                        try {
                            dialogPermission.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    dialogPermission = builder.create();
                    dialogPermission.show();


                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case VConstant.REQUEST_CODE_CAMERA: {
                    if (resultCode == Activity.RESULT_OK) {
                        String filePath = mPhotoFile.getPath();
                        Log.v("URL", filePath);
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        String imageBase64 = StringUtil.getStringImage(bitmap);

                        ImageCapture imageCapture = new ImageCapture();
                        imageCapture.setImageData(imageBase64);
                        imageCapture.setLongtitude(longitude);
                        imageCapture.setLattitude(latitude);
                        if (longitude != 0.0 && latitude != 0.0) {
                            boolean result = BacDzDbHelper.getInstance(getActivity()).insertImage(imageCapture);
                            if (result) {
                                Log.w("SaveImageLocal","Insert DB thành công");
                                Toast.makeText(getContext(), "Lưu ảnh thành công.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        /** Picture wasn't taken*/
                    }
                }

                break;
                case REQUEST_CHECK_PERMISSION_LOCATION:
                    if (resultCode == Activity.RESULT_OK) {
                        //Mo gps thanh cong
                        getLocation_CapturePhoto();
                    } else {
                        Toast.makeText(getContext(), R.string.denyGPSWarning, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public void onLaunchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        String fileName = FileUtils.getFileName();
        mPhotoFile = FileUtils.getInstance().getPhotoFileUri(getContext(), fileName);
        // required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(getContext(),
                getResources().getString(R.string.camera_provider), mPhotoFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            intent.setClipData(ClipData.newRawUri("", fileProvider));
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // check app can handle
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, VConstant.REQUEST_CODE_CAMERA);
        }
    }


    public void accessLocation() {
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //goi chuc nang chup anh
            getLocation_CapturePhoto();

        } else {
            askForEnableGPS();
        }

    }


    private void getLocation_CapturePhoto() {
        if (gps != null && gps.getLatitude() != 0.0 && gps.getLongitude() != 0.0) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.e("Latitude", latitude + "");
            Log.e("Longitude", longitude + "");
            //
            onLaunchCamera();
        } else {
            Toast.makeText(getContext(), R.string.WaitingGetLocationWarning, Toast.LENGTH_SHORT).show();
        }
    }


    public String ConvertPointToLocation(String Latitude, String Longitude) {
        String address = "";
        Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                    Float.parseFloat(Latitude), Float.parseFloat(Longitude), 1);

            if (addresses.size() > 0) {
                for (int index = 0; index < addresses.get(0)
                        .getMaxAddressLineIndex(); index++)
                    address += addresses.get(0).getAddressLine(index) + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    private void askForEnableGPS() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback((rs) -> {
            final Status status = rs.getStatus();
            //final LocationSettingsStates state = result.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    if (ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    //Sau khi doi mo GPS thanh cong
                    //Get location
                    getLocation_CapturePhoto();


                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialogPermission.
                    try {
                        // Show the dialogPermission by calling startResolutionForResult(),
                        // and check the result in onActivityResult().

                        status.startResolutionForResult(getActivity(), REQUEST_CHECK_PERMISSION_LOCATION);

                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialogPermission.
                    break;
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
