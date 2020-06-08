package com.viettel.construction.screens.tabs;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.ImageCategoryAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.model.api.WorkItemDetailDTORespone;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseCameraActivity;

/**
 * Hiển thị chi tiết hạng mục đã hoàn thành
 */

public class CompleteCategoryCameraActivity extends BaseCameraActivity {

    @BindView(R.id.rcv_image_complete)
    RecyclerView rcvImage;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.btn_compelte)
    TextView btnComplete;
    @BindView(R.id.tv_construction_name_complete)
    TextView txtConstruction;

    @BindView(R.id.tv_category_name_complete)
    TextView txtNameCategory;
    @BindView(R.id.tv_finish_time_complete)
    TextView txtTime;
    @BindView(R.id.edt_description)
    EditText edtDescription;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private WorkItemDetailDTO workItemDTO;
    private ImageCategoryAdapter mAdapter;
    public static List<ConstructionImageInfo> mListUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_complete_category);
        ButterKnife.bind(this);
        txtHeader.setText("Xác nhận hoàn thành");
        // 2 chua xac nhan,3 la da xac nhan ,
        if (getIntent().getExtras() != null) {
            workItemDTO = (WorkItemDetailDTO) getIntent().getExtras().getSerializable("workDTO_data");
            if (workItemDTO.getStatus().equals("3")) {
                btnComplete.setVisibility(View.GONE);
                edtDescription.setText(workItemDTO.getApproveDescription());
                disableEditText(edtDescription);
            } else {
                btnComplete.setVisibility(View.VISIBLE);
            }
            txtConstruction.setText(workItemDTO.getConstructionCode());
            txtNameCategory.setText(workItemDTO.getName());

            String startDate = "";
            String endDate = "";

            if (workItemDTO.getStartingDate() != null) {
                startDate = changeStringFormat(workItemDTO.getStartingDate());
            } else {
                startDate = workItemDTO.getStartingDate();
            }

            if (workItemDTO.getCompleteDate() != null) {
                endDate = changeStringFormat(workItemDTO.getCompleteDate());
            } else {
                endDate = workItemDTO.getCompleteDate();
            }

            txtTime.setText(startDate + " - " + endDate);


            btnComplete.setOnClickListener((view) -> {

                workItemDTO.setApproveDescription(edtDescription.getText().toString().trim());
                ApiManager.getInstance().updateWork(workItemDTO, WorkItemDetailDTORespone.class,
                        new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            WorkItemDetailDTORespone workItemDetailDTORespone = WorkItemDetailDTORespone.class.cast(result);
                            ResultInfo resultInfo = workItemDetailDTORespone.getResultInfo();
                            if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                Toast.makeText(CompleteCategoryCameraActivity.this, "Đã cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                App.getInstance().setNeedUpdateCompleteCategory(true);
                                finish();
                            } else {
                                Toast.makeText(CompleteCategoryCameraActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(CompleteCategoryCameraActivity.this, "Lỗi kết nối mạng !", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }


        mListUrl = new ArrayList<>();
        initListImageFromServer();
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    private void initListImageFromServer() {
        ApiManager.getInstance().getListImageForCategoryComplete(workItemDTO, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (resultApi.getListImage() != null) {
                            // add image to list
                            mListUrl.addAll(resultApi.getListImage());
                            setUpView();
                        } else {
                            Toast.makeText(CompleteCategoryCameraActivity.this, "Người dùng không có ảnh !", Toast.LENGTH_SHORT).show();
                        }
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

    private void setUpView() {
        mAdapter = new ImageCategoryAdapter(mListUrl, this, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvImage.setLayoutManager(linearLayoutManager);
        rcvImage.setAdapter(mAdapter);

    }
}
