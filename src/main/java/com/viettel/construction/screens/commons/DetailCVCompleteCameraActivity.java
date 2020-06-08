package com.viettel.construction.screens.commons;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.ImageCompleteAdapter;
import com.viettel.construction.server.service.IOnRequestListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

/**
 * Chi tiết công việc đã hoàn thành
 */
public class DetailCVCompleteCameraActivity extends BaseCameraActivity {
    @BindView(R.id.btn_camera)
    ImageView mBtnCamera;

    @BindView(R.id.rv_list_image_complete)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_content_cv_complete)
    TextView mTVContentCV;
    @BindView(R.id.tv_construction_name_complete)
    TextView mTVConstructionName;
    @BindView(R.id.tv_category_name_complete)
    TextView mTVCategoryName;
    @BindView(R.id.tv_start_time_complete)
    TextView mTVStartTime;
    @BindView(R.id.tv_performer)
    TextView mTVPerformer;

    @BindView(R.id.edt_process)
    EditText edtProcess;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    @BindView(R.id.prg_loading)
    CustomProgress progress;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private File photoFile;
    private ImageCompleteAdapter mAdapter;
    public static List<ConstructionImageInfo> mListUrl;
    private ConstructionTaskDTO mObjWork;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv_complete);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.detail);
        progress.setLoading(true);
        initData();
        initListImageFromServer();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mObjWork = (ConstructionTaskDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV);
            mTVContentCV.setText(mObjWork.getTaskName());
            mTVConstructionName.setText(mObjWork.getConstructionCode());
            mTVCategoryName.setText(mObjWork.getWorkItemName());
            //mTVStartTime.setText(mObjWork.getStartDate() + " - " + mObjWork.getEndDate());
            mTVStartTime.setText(getString(R.string.start_date_end_date, mObjWork.getStartDate(), mObjWork.getEndDate()));
            if (mObjWork.getDescription() != null) {
                edtDescription.setText(mObjWork.getDescription().toString().trim());
            }
            if (mObjWork.getCompletePercent() != null) {
                Double percent = Double.parseDouble(mObjWork.getCompletePercent());
                edtProcess.setText(percent.intValue() + "");
            } else {
                edtProcess.setText(getString(R.string.zero));
            }
            if (mObjWork.getPerformerName() != null) {
                mTVPerformer.setText(mObjWork.getPerformerName());
            }
        }
        mListUrl = new ArrayList<>();
        mListUrl.clear();
//        mListUrl.addAll(FakeData.ListUrlImage());

        disableEditText(edtDescription);
        disableEditText(edtProcess);
    }

    private void setupRecyclerView() {
        mAdapter = new ImageCompleteAdapter(mListUrl, this, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

//    public void onLaunchCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference to access to future access
//        String fileName = FileUtils.getFileName();
//        photoFile = FileUtils.getInstance().getPhotoFileUri(this, fileName);
//        // required for API >= 24
//        Uri fileProvider = FileProvider.getUriForFile(this,
//                getResources().getString(R.string.camera_provider), photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//        // check app can handle
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, VConstant.REQUEST_CODE_CAMERA);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {

                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {

                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        /** Picture wasn't taken*/
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @OnClick(R.id.imgBack)
    public void onClickCancel() {
        finish();
    }


    private void initListImageFromServer() {
        ApiManager.getInstance().loadImage(mObjWork, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (resultApi.getListImage() != null && resultApi.getListImage().size() > 0) {
                            // add image to list
                            mListUrl.addAll(resultApi.getListImage());
                            setupRecyclerView();
                            progress.setLoading(false);
                        } else {
                            progress.setLoading(false);
                            if (resultApi.getResultInfo().getMessage() != null && resultApi.getResultInfo().getMessage().length() > 0) {
                                Toast.makeText(DetailCVCompleteCameraActivity.this, resultApi.getResultInfo().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(DetailCVCompleteCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

