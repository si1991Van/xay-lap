package com.viettel.construction.screens.menu_updateconstruction;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

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
public class TabHandOverFragment extends BaseCameraFragment implements ImageInProcessAdapter.OnClickDelete {

    @BindView(R.id.imgCapture)
    ImageView imgCapture;

    @BindView(R.id.rcvImage)
    RecyclerView recyclerView;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.rdoDien)
    RadioButton rdoDien;

    @BindView(R.id.rdoXayDung)
    RadioButton rdoXayDung;

    @BindView(R.id.edtNote)
    EditText edtNote;

    private Date currentDate = new Date();

    private ImageInProcessAdapter adapterElectricity;
    private ImageInProcessAdapter adapterBuilding;
    private ArrayList<ConstructionImageInfo> listImageElectricity;
    private ArrayList<ConstructionImageInfo> listImageUploadElectricity;
    private ArrayList<ConstructionImageInfo> listImageBuilding;
    private ArrayList<ConstructionImageInfo> listImageUploadBuilding;

    private boolean isLoadedImage_Electricity = false;
    private boolean isLoadedImage_Building = false;


    private ConstructionExtraDTORequest currentConstruction = new ConstructionExtraDTORequest();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_hand_over, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //
        listImageElectricity = new ArrayList<>();
        listImageUploadElectricity = new ArrayList<>();
        listImageBuilding = new ArrayList<>();
        listImageUploadBuilding = new ArrayList<>();
        //
        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (rdoDien.isChecked()) {
                    currentConstruction.setHandover_note_electric(editable.toString());
                } else {
                    currentConstruction.setHandover_note_build(editable.toString());
                }
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        adapterElectricity = new ImageInProcessAdapter(listImageElectricity, getContext(), true, this);
        adapterBuilding = new ImageInProcessAdapter(listImageBuilding, getContext(), true, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Mặc định là Electricity
        recyclerView.setAdapter(adapterElectricity);
    }

    @OnClick(R.id.rdoDien)
    public void chkElectricity() {
        setElectricityInfo();
    }

    @OnClick(R.id.rdoXayDung)
    public void chkBuilding() {
        setBuildingInfo();
    }


    private void setElectricityInfo() {
        if (currentConstruction.getHandover_note_electric() != null)
            edtNote.setText(currentConstruction.getHandover_note_electric());
        else {
            edtNote.setText(null);
        }

        if (currentConstruction.getHandover_date_electric() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date _date = currentConstruction.getHandover_date_electric();
            if (_date != null) {
                currentDate = _date;
                tvTime.setText(format.format(_date));
            }
        } else {
            currentDate = new Date();
            tvTime.setText(null);
        }


        if (!isLoadedImage_Electricity) {
            //Load Image
            ApiManager.getInstance().getImagesByConstructionExtraIDType(
                    new ConstructionExtraDTOImageRequest("53", currentConstruction.getConstruction_id()),
                    ConstructionExtraDTOImageResponse.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                ConstructionExtraDTOImageResponse resultApi = ConstructionExtraDTOImageResponse.class.cast(result);
                                if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                    isLoadedImage_Electricity = true;
                                    if (resultApi.getListImage() != null && resultApi.getListImage().size() > 0) {
                                        // add image to list
                                        listImageElectricity.addAll(resultApi.getListImage());

                                    }
                                    //
                                    adapterElectricity.setmList(listImageElectricity);
                                    recyclerView.setAdapter(adapterElectricity);
                                    adapterElectricity.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {

                        }
                    });
        }else{
            recyclerView.setAdapter(adapterElectricity);
            adapterElectricity.notifyDataSetChanged();
        }

    }

    private void setBuildingInfo() {
        if (currentConstruction.getHandover_note_build() != null)
            edtNote.setText(currentConstruction.getHandover_note_build());
        else {
            edtNote.setText(null);
        }

        if (currentConstruction.getHandover_date_build() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date _date = currentConstruction.getHandover_date_build();
            if (_date != null) {
                currentDate = _date;
                tvTime.setText(format.format(_date));
            }
        } else {
            currentDate = new Date();
            tvTime.setText(null);
        }

        if (!isLoadedImage_Building) {
            //Load Image
            ApiManager.getInstance().getImagesByConstructionExtraIDType(
                    new ConstructionExtraDTOImageRequest("41", currentConstruction.getConstruction_id()),
                    ConstructionExtraDTOImageResponse.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                ConstructionExtraDTOImageResponse resultApi = ConstructionExtraDTOImageResponse.class.cast(result);
                                if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                    isLoadedImage_Building = true;
                                    if (resultApi.getListImage() != null && resultApi.getListImage().size() > 0) {
                                        // add image to list
                                        listImageBuilding.addAll(resultApi.getListImage());

                                    }
                                    adapterBuilding.setmList(listImageBuilding);
                                    recyclerView.setAdapter(adapterBuilding);
                                    adapterBuilding.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {

                        }
                    });
        }else{
            recyclerView.setAdapter(adapterBuilding);
            adapterBuilding.notifyDataSetChanged();
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
        //  imageInfo.setLongtitude(longitude);
        if (rdoDien.isChecked()) {
            listImageElectricity.add(0, imageInfo);

            adapterElectricity.notifyItemInserted(0);
        }else{
            listImageBuilding.add(0, imageInfo);
            adapterBuilding.notifyItemInserted(0);
        }
    }

    public void reloadData(ConstructionExtraDTORequest data) {
        isLoadedImage_Electricity = false;
        isLoadedImage_Building = false;
        if (data != null) {

            listImageElectricity = new ArrayList<>();
            listImageUploadElectricity = new ArrayList<>();
            listImageBuilding = new ArrayList<>();
            listImageUploadBuilding = new ArrayList<>();
            adapterBuilding.setmList(listImageBuilding);
            adapterElectricity.setmList(listImageElectricity);
            currentConstruction = data;
            //Time and note
            if (rdoDien.isChecked()) {
                setElectricityInfo();
            } else {
                setBuildingInfo();
            }
        }
    }


    @OnClick(R.id.imgCapture)
    public void imageCapture() {
        onLaunchCamera();
    }

    @Override
    public void onDelete(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa ảnh");
        builder.setMessage("Bạn có muốn xóa ảnh ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (rdoDien.isChecked()) {
                listImageElectricity.get(pos).setStatus(-1);
                listImageElectricity.get(pos).setBase64String(null);
                if (listImageElectricity.get(pos).getUtilAttachDocumentId() > 0)
                    listImageUploadElectricity.add(listImageElectricity.get(pos));
                listImageElectricity.remove(pos);
                adapterElectricity.notifyItemRemoved(pos);
            } else {
                listImageBuilding.get(pos).setStatus(-1);
                listImageBuilding.get(pos).setBase64String(null);
                if (listImageBuilding.get(pos).getUtilAttachDocumentId() > 0)
                    listImageUploadBuilding.add(listImageBuilding.get(pos));
                listImageBuilding.remove(pos);
                adapterBuilding.notifyItemRemoved(pos);
            }
        });
        builder.setNegativeButton("Không", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.c5));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.c5));

    }

    public void updateNewImage() {

        for (int i = 0; i < listImageElectricity.size(); i++) {
            if (listImageElectricity.get(i).getStatus() == 0) {
                //Lấy thêm những image mới
                listImageUploadElectricity.add(listImageElectricity.get(i));
            }
        }
        currentConstruction.setLstHandOverElectricityImages(listImageUploadElectricity);

        //
        for (int i = 0; i < listImageBuilding.size(); i++) {
            if (listImageBuilding.get(i).getStatus() == 0) {
                //Lấy thêm những image mới
                listImageUploadBuilding.add(listImageBuilding.get(i));
            }
        }
        currentConstruction.setLstHandOverBuildingImages(listImageUploadBuilding);

    }

    @OnClick(R.id.tvTime)
    public void selectTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(), (datePicker, year, month, day) ->
                {
                    cal.set(year, month, day);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    tvTime.setText(format.format(cal.getTime()));
                    currentDate = cal.getTime();

                    if (rdoDien.isChecked()) {
                        currentConstruction.setHandover_date_electric(currentDate);
                        currentConstruction.setHandover_note_electric(edtNote.getText().toString());
                    } else {
                        currentConstruction.setHandover_date_build(currentDate);
                        currentConstruction.setHandover_note_build(edtNote.getText().toString());
                    }

                }, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
