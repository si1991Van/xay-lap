package com.viettel.construction.screens.menu_updateconstruction;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraFragment;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTOImageRequest;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTOImageResponse;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTORequest;
import com.viettel.construction.screens.atemp.adapter.ImageInProcessAdapter;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabLicenseFragment extends BaseCameraFragment implements ImageInProcessAdapter.OnClickDelete {

    @BindView(R.id.imgCapture)
    ImageView imgCapture;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.rcvImage)
    RecyclerView recyclerView;

    private Date currentDate = new Date();
    private ImageInProcessAdapter mAdapter;
    private ArrayList<ConstructionImageInfo> listImage;
    private ArrayList<ConstructionImageInfo> listImageUpdate;


    private ConstructionExtraDTORequest currentConstruction = new ConstructionExtraDTORequest();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_license, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        listImage = new ArrayList<>();
        listImageUpdate = new ArrayList<>();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new ImageInProcessAdapter(listImage, getContext(), true, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void reloadData(ConstructionExtraDTORequest data) {
        if (data != null) {
            listImage = new ArrayList<>();
            listImageUpdate = new ArrayList<>();
            mAdapter.setmList(listImage);
            currentConstruction = data;
            if (currentConstruction.getIs_building_permit() != null && currentConstruction.getIs_building_permit().equalsIgnoreCase("1")) {
                imgCapture.setVisibility(View.VISIBLE);
            } else {
                imgCapture.setVisibility(View.INVISIBLE);
            }


            if (data.getBuilding_permit_date() != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                Date _date = data.getBuilding_permit_date();
                if (_date != null) {
                    currentDate = _date;
                    tvTime.setText(format.format(_date));
                }


            }else{
                currentDate = new Date();
                tvTime.setText(null);
            }

            //Load Image
            ApiManager.getInstance().getImagesByConstructionExtraIDType(
                    new ConstructionExtraDTOImageRequest("54", currentConstruction.getConstruction_id()),
                    ConstructionExtraDTOImageResponse.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                ConstructionExtraDTOImageResponse resultApi = ConstructionExtraDTOImageResponse.class.cast(result);
                                if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                    if (resultApi.getListImage() != null && resultApi.getListImage().size() > 0) {
                                        // add image to list
                                        listImage.addAll(resultApi.getListImage());


                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {

                        }
                    });
        }

    }

    public void getCameraCapturePath() {
        String filePath = mPhotoFile.getPath();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String pictureName = "IMG_" + timeStamp + ".jpg";
        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);

        ConstructionImageInfo imageInfo = new ConstructionImageInfo();
        imageInfo.setStatus(0);//
        imageInfo.setBase64String(StringUtil.getStringImage(bitmap));
        imageInfo.setImageName(pictureName);
        // imageInfo.setLatitude(latitude);
        // imageInfo.setLongtitude(longitude);
        listImage.add(0, imageInfo);
        mAdapter.notifyItemInserted(0);
    }

    public void updateNewImage() {
        for (int i = 0; i < listImage.size(); i++) {
            if (listImage.get(i).getStatus() == 0) {
                //Lấy thêm những image mới
                listImageUpdate.add(listImage.get(i));
            }
        }
        currentConstruction.setLstLicenseImages(listImageUpdate);
    }

    @OnClick(R.id.imgCapture)
    public void imageCapture() {
        onLaunchCamera();
    }

    @OnClick(R.id.tvTime)
    public void selectTime() {
        if (currentConstruction.getIs_building_permit() != null && currentConstruction.getIs_building_permit().equalsIgnoreCase("1")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(getContext(), (datePicker, year, month, day) ->
                    {
                        cal.set(year, month, day);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        tvTime.setText(format.format(cal.getTime()));
                        currentDate = cal.getTime();

                        currentConstruction.setBuilding_permit_date(currentDate);
                    }, cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else {
            Toast.makeText(getContext(), "Không được phép cập nhật giấy phép xây dựng.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDelete(int pos) {
        if (currentConstruction.getIs_building_permit() != null && currentConstruction.getIs_building_permit().equalsIgnoreCase("1")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Xóa ảnh");
            builder.setMessage("Bạn có muốn xóa ảnh ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Xóa", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                listImage.get(pos).setStatus(-1);
                listImage.get(pos).setBase64String(null);
                if (listImage.get(pos).getUtilAttachDocumentId() > 0)
                    listImageUpdate.add(listImage.get(pos));
                listImage.remove(pos);
                mAdapter.notifyItemRemoved(pos);
            });
            builder.setNegativeButton("Không", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.c5));
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.c5));
        } else {
            Toast.makeText(getContext(), "Không được phép cập nhật giấy phép xây dựng.", Toast.LENGTH_SHORT).show();
        }
    }
}
