package com.viettel.construction.screens.wrac;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.commons.SelectConsCategoryCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.issue.IssueDTOResponse;
import com.viettel.construction.model.api.issue.IssueHistoryEntityDTO;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.commons.SelectConstructionCameraActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComplaintDetail2CameraActivity extends BaseCameraActivity {
    @BindView(R.id.edt_content_reflect)
    EditText edtContent;
    @BindView(R.id.edt_content_handing)
    EditText edtContentHanding;
    @BindView(R.id.tv_construction)
    TextView tvConstruction;
    @BindView(R.id.txtSave)
    TextView tvSave;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.txt_item)
    TextView tvCategory;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private Intent intent;
    private IssueWorkItemDTO issueWorkItemDTO;
    private IssueDTOResponse response;
    private ConstructionStationWorkItem construction;
    private ConstructionStationWorkItem construction1;
    private long idConstruction;
    private long idCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_reflect_return2);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.ComplainDetail);
        getData();
        setupPermissionView();
    }

    private void setupPermissionView() {
        //disable
        disableEditText(edtContent);
        tvConstruction.setClickable(false);
        tvCategory.setClickable(false);
        //enalble
        enableEditText(edtContentHanding);
        SysUserRequest sysUserRequest = VConstant.getUser();

        if (issueWorkItemDTO.getCreatedUserId() == sysUserRequest.getSysUserId() && edtContentHanding.getText().toString() == null) {
            tvConstruction.setClickable(true);
            tvCategory.setClickable(true);
            tvCategory.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_pencil),null);
            tvConstruction.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_pencil),null);
        }else{
            tvConstruction.setClickable(false);
            tvCategory.setClickable(false);
            tvCategory.setCompoundDrawables(null,null,null,null);
            tvConstruction.setCompoundDrawables(null,null,null,null);
        }

        if (issueWorkItemDTO.getCreatedUserId() == sysUserRequest.getSysUserId()) {
            enableEditText(edtContent);
        }

        //add permission view
        if (issueWorkItemDTO.getIsProcessFeedBack() != null) {
            if (issueWorkItemDTO.getIsProcessFeedBack() != 1L) {
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.INVISIBLE);
                rb3.setVisibility(View.VISIBLE);

                tvSave.setVisibility(View.VISIBLE);

            } else {
                rb1.setVisibility(View.INVISIBLE);
                rb2.setVisibility(View.INVISIBLE);
                rb3.setVisibility(View.INVISIBLE);

                tvSave.setVisibility(View.GONE);

            }
        }


    }

    public void getData() {
        SysUserDTO dto = VConstant.getDTO();
        issueWorkItemDTO = (IssueWorkItemDTO) getIntent().getExtras().get("reflect2");
        if (issueWorkItemDTO != null) {
            edtContent.setText(issueWorkItemDTO.getContent());
            edtContentHanding.setText(issueWorkItemDTO.getContentHanding());
//            tvConstruction.setText(issueWorkItemDTO.getConstructionId() + "");

            if (issueWorkItemDTO.getStatus().equals("2")) {
                rb1.setChecked(true);
            } else if (issueWorkItemDTO.getStatus().equals("0")) {
                rb3.setChecked(true);
            }

            tvConstruction.setText(issueWorkItemDTO.getCode());
            tvCategory.setText(issueWorkItemDTO.getWorkItemName());

            tvName.setText(dto.getFullName());

            //get constructionId
            idConstruction = issueWorkItemDTO.getConstructionId();
        }
    }

    @OnClick(R.id.txtCancel)
    public void onClickCancel() {
        finish();
    }

    @OnClick(R.id.txtSave)
    public void onClickSave() {
        //save data to server
        if (checkRadioSave())
            save();
        else
            Toast.makeText(this, "Bạn chưa lựa chọn chuyển hoặc đóng phản ánh.", Toast.LENGTH_SHORT).show();
    }

    private void save() {
        IssueHistoryEntityDTO issueHistoryEntityDTO = new IssueHistoryEntityDTO();
        IssueHistoryEntityDTO issueHistoryContentIssueDetail = new IssueHistoryEntityDTO();

        issueHistoryEntityDTO.setOldValue(issueWorkItemDTO.getContent());
        issueHistoryEntityDTO.setNewValue(edtContent.getText().toString());
        issueHistoryEntityDTO.setType(1);
        issueHistoryEntityDTO.setCreatedUserId(issueWorkItemDTO.getCreatedUserId());

        IssueHistoryEntityDTO issueHistoryContentHandingDetail = new IssueHistoryEntityDTO();
        issueHistoryContentHandingDetail.setOldValue(issueWorkItemDTO.getContentHanding());
        issueHistoryContentHandingDetail.setNewValue(edtContentHanding.getText().toString());
        issueHistoryContentHandingDetail.setCreatedUserId(issueWorkItemDTO.getCreatedUserId());
        issueHistoryContentHandingDetail.setIssueId(issueWorkItemDTO.getIssueId());

        issueHistoryContentIssueDetail.setCreatedUserId(issueWorkItemDTO.getCreatedUserId());
        issueHistoryContentIssueDetail.setOldValue(issueWorkItemDTO.getContent());
        issueHistoryContentIssueDetail.setIssueId(issueWorkItemDTO.getIssueId());
        issueHistoryContentIssueDetail.setNewValue(edtContent.getText().toString().trim());

        issueWorkItemDTO.setOldStatus(issueWorkItemDTO.getStatus());
        if (rb1.isChecked()) {
            issueWorkItemDTO.setChangeSysRoleCode(1L);
        } else if (rb3.isChecked()) {
            issueWorkItemDTO.setChangeSysRoleCode(0L);
        }
        issueWorkItemDTO.setContentHanding(edtContentHanding.getText().toString());
        issueWorkItemDTO.setContent(edtContent.getText().toString());

        ApiManager.getInstance().updateIssue(issueWorkItemDTO,
                issueHistoryContentIssueDetail, issueHistoryContentHandingDetail, IssueDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    response = IssueDTOResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(ComplaintDetail2CameraActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        App.getInstance().setNeedUpdateIssue(true);
                        finish();
                    } else {
                        Toast.makeText(ComplaintDetail2CameraActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(ComplaintDetail2CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == 38) {
                        if (data.getExtras() != null) {
                            if (data.getExtras().getSerializable("resultIntent") != null) {
                                construction = (ConstructionStationWorkItem) data.getSerializableExtra("resultIntent");
                                idConstruction = construction.getConstructionId();
                                tvConstruction.setText(construction.getConstructionCode());
                                issueWorkItemDTO.setConstructionId(idConstruction);
                            }
                        }
                    }
                    break;
                case 2:
                    if (resultCode == 2) {
                        if (data.getExtras() != null) {
                            if (data.getExtras().getSerializable("categoryResult") != null) {
                                construction1 = (ConstructionStationWorkItem) data.getExtras().getSerializable("categoryResult");
                                if (construction1 != null) {
                                    idCategory = construction1.getWorkItemId();
                                    tvCategory.setText(construction1.getName());
                                    issueWorkItemDTO.setWorkItemId(idCategory);
                                }
                            }
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_construction)
    public void OnClickChooseConstruction() {
        try {
            intent = new Intent(this, SelectConstructionCameraActivity.class);
            this.startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.txt_item)
    public void OnClickChooseCategory() {
        try {
            if (idConstruction > 0) {
                intent = new Intent(this, SelectConsCategoryCameraActivity.class);
                intent.putExtra("id_construction", idConstruction);
                startActivityForResult(intent, 2);
            } else {
                Toast.makeText(this, getResources().getString(R.string.please_choose_category), Toast.LENGTH_SHORT).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkRadioSave() {
        boolean isSave = false;
        if (issueWorkItemDTO.getChangeSysRoleCode() != 1) {
            if (!rb1.isChecked() && !rb3.isChecked()) {
                isSave = false;
            } else isSave = true;
        }
        return isSave;
    }
}