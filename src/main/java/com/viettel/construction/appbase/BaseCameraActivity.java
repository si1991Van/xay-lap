package com.viettel.construction.appbase;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.viettel.construction.R;
import com.viettel.construction.common.GPSTracker;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.util.FileUtils;

import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Manroid on 18/01/2018.
 */

public class BaseCameraActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final int COMMIT_INTENT = 500;
    public static final String CHANGE_LAYOUT_2 = "COMMIT";


    private DatePickerDialog datePickerDialog;
    public File mPhotoFile;

    private GPSTracker gps = null;
    public double latitude, longitude;
    public GoogleApiClient googleApiClient;
    private LocationManager locationManager;
    public static final int REQUEST_CHECK_SETTINGS = 100;
    private static final int REQUEST_CHECK_PERMISSION_LOCATION = 10001;

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public String changeStringFormat(String s) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr;
        try {
            reformattedStr = myFormat.format(fromUser.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
            reformattedStr = "";
        }
        return reformattedStr;
    }

    public boolean checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            if (gps == null)
                gps = new GPSTracker(this);
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{
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
                                gps = new GPSTracker(this);
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseCameraActivity.this);
                    builder.setMessage(strMessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.setting, (dialogInterface, i) -> {
                        try {
                            dialogPermission.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CHECK_PERMISSION_LOCATION) {
                if (resultCode == Activity.RESULT_OK) {
                    //Mo gps thanh cong
                    getLocation_CapturePhoto();
                } else {
                    Toast.makeText(this, R.string.denyGPSWarning, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onLaunchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        String fileName = FileUtils.getFileName();
        mPhotoFile = FileUtils.getInstance().getPhotoFileUri(this, fileName);
        // required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(this,
                getResources().getString(R.string.camera_provider), mPhotoFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            intent.setClipData(ClipData.newRawUri("", fileProvider));
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // check app can handle
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, VConstant.REQUEST_CODE_CAMERA);
        }
    }

    @Optional
    @OnClick(R.id.btn_camera)
    public void onClickBtnCamera() {
        try {
            if (checkRuntimePermission()) {
                accessLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accessLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            Toast.makeText(BaseCameraActivity.this, R.string.WaitingGetLocationWarning, Toast.LENGTH_SHORT).show();
        }
    }


    public String ConvertPointToLocation(String Latitude, String Longitude) {
        String address = "";
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
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
        googleApiClient = new GoogleApiClient.Builder(this)
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
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                        status.startResolutionForResult(BaseCameraActivity.this, REQUEST_CHECK_PERMISSION_LOCATION);

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

    public void enableEditText(EditText edt) {
//        edt.setClickable(true);
//        edt.setCursorVisible(true);
//        edt.setFocusable(true);
//        edt.setFocusableInTouchMode(true);
        edt.setEnabled(true);
    }

    public void disableEditText(EditText edt) {
//        edt.setClickable(false);
//        edt.setCursorVisible(false);
//        edt.setFocusable(false);
//        edt.setFocusableInTouchMode(false);
        edt.setEnabled(false);
    }

    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public boolean checkValidate(EditText editText, String path) {
        String input = editText.getText().toString().trim();
        if (input.length() == 0) {
            return false;
        }
        Double range = Double.parseDouble(input);
        if (range < 0 || range > 100) {
            return false;
        } else if (input.length() == 0 || input == null) {
            editText.requestFocus();
            editText.setError(getString(R.string.please_input));
            return false;
        } else return true;
    }

    public boolean checkValidate(EditText editText) {
        String input = editText.getText().toString().trim();
        if (input.length() > 0 && input == null) {
            editText.requestFocus();
            editText.setError(getString(R.string.please_input));
            return false;
        }
        return true;
    }


    public String getCurretnTimeStampMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        String res = strDate.substring(5, 7);
        return res;
    }

    public String getCurretnTimeStampYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        String res = strDate.substring(0, 4);
        return res;
    }

    public void setTime(final TextView txtCalendar) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialogPermission
        datePickerDialog = new DatePickerDialog(this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                txtCalendar.setText(i2 + "/" + (i1 + 1) + "/" + i);
            }
        }, mYear, mMonth, mDay);

        //MM-dd-yyyy
        TextView tv = new TextView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setPadding(10, 10, 10, 10);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setText(getResources().getString(R.string.setting_date));
        tv.setTextColor(getResources().getColor(R.color.colorWhite));
        tv.setBackgroundColor(getResources().getColor(R.color.c5));
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "THOÁT", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "CHỌN", datePickerDialog);
        datePickerDialog.setCustomTitle(tv);
        datePickerDialog.show();

    }

    public Date ConvertStringToDate(String s) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = df.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}
