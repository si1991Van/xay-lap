package com.viettel.construction.screens.tabs;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.screens.commons.SelectConsCategoryCameraActivity;
import com.viettel.construction.screens.commons.SelectConstructionCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.issue.IssueDTOResponse;
import com.viettel.construction.model.api.issue.IssueHistoryEntityDTO;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.server.api.ApiManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IssueDetailCameraActivity extends BaseCameraActivity {
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
    RadioButton rb1ChuyenBanDieuHanh;
    @BindView(R.id.rb2)
    RadioButton rb2MoPhanAnh;
    @BindView(R.id.rb3)
    RadioButton rb3DongPhanAnh;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private int issueDTOType = 0;

    private IssueWorkItemDTO issueWorkItemDTO;
    private IssueDTOResponse response;
    private ConstructionStationWorkItem construction;
    private ConstructionStationWorkItem construction1;
    private long idConstruction;
    private long idCategory;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_reflect_return);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.ComplainDetail);
        getData();
        setupPermissionView();
    }

    private void setupPermissionView() {
        //rb1ChuyenBanDieuHanh dont use in this case
        disableEditText(edtContentHanding);

        issueDTOType = getIntent().getIntExtra(ParramConstant.IssueDTOType, 0);

        /*if (issueWorkItemDTO.getCreatedUserId() == VConstant.getUser().getSysUserId()
                && issueWorkItemDTO.getContentHanding() != null) {*/
            tvConstruction.setClickable(true);
            tvCategory.setClickable(true);
            tvCategory.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_pencil), null);
            tvConstruction.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_pencil), null);
        /*} else {
            tvConstruction.setClickable(false);
            tvCategory.setClickable(false);
            tvCategory.setCompoundDrawables(null, null, null, null);
            tvConstruction.setCompoundDrawables(null, null, null, null);
        }*/

        // add permision
        switch (issueDTOType) {
            case 0://Nhan vien: Chỉ có đóng và mở phản ảnh
            {
                rb1ChuyenBanDieuHanh.setVisibility(View.GONE);
                //nhan vien
                if (issueWorkItemDTO.getStatus() != null) {
                    if (issueWorkItemDTO.getStatus().equals("1"))
                        //Phản ảnh đang mở
                        rb2MoPhanAnh.setChecked(true);
                    else
                        rb3DongPhanAnh.setChecked(true);
                }


            }
            break;
            case 1://Tinh trưởng: Đóng phản ảnh và chuyển ban điều hành
            {
                rb2MoPhanAnh.setVisibility(View.GONE);
                if (issueWorkItemDTO.getIsProcessFeedBack() != null) {
                    if (issueWorkItemDTO.getIsProcessFeedBack() != 1L) {//Chưa chuyển ban điều hành
                        rb1ChuyenBanDieuHanh.setVisibility(View.VISIBLE);
                        rb3DongPhanAnh.setVisibility(View.VISIBLE);

                        tvSave.setClickable(true);
                        tvSave.setText(R.string.save);

                    } else {
                        rb1ChuyenBanDieuHanh.setVisibility(View.GONE);
                        rb2MoPhanAnh.setVisibility(View.GONE);
                        rb3DongPhanAnh.setVisibility(View.GONE);

                        tvSave.setText("");
                        tvSave.setClickable(false);

                    }
                }
                if (issueWorkItemDTO.getStatus().equals("2")) {
                    rb1ChuyenBanDieuHanh.setChecked(true);
                } else if (issueWorkItemDTO.getStatus().equals("0")) {
                    rb3DongPhanAnh.setChecked(true);
                }
            }
            break;
            default: {
                rb2MoPhanAnh.setVisibility(View.GONE);
                rb1ChuyenBanDieuHanh.setVisibility(View.GONE);
                //Ban dieu hanh: Đóng phản ảnh
                if (issueWorkItemDTO.getChangeSysRoleCode() != null) {
                    if (issueWorkItemDTO.getChangeSysRoleCode() == 1L) {
                        rb3DongPhanAnh.setVisibility(View.VISIBLE);
                        //
                        tvSave.setClickable(true);
                        tvSave.setText(R.string.save);
                    } else {
                        rb3DongPhanAnh.setVisibility(View.GONE);
                        tvSave.setText("");
                        tvSave.setClickable(false);
                    }

                    if (issueWorkItemDTO.getStatus().equals("2")) {
                        rb1ChuyenBanDieuHanh.setChecked(true);
                    } else if (issueWorkItemDTO.getStatus().equals("0")) {
                        rb3DongPhanAnh.setChecked(true);
                    }
                }
                break;
            }
        }
    }

    public void getData() {
        SysUserDTO dto = VConstant.getDTO();
        issueWorkItemDTO = (IssueWorkItemDTO) getIntent().getExtras().get("reflect");
        if (issueWorkItemDTO != null) {
            edtContent.setText(issueWorkItemDTO.getContent());
            edtContentHanding.setText(issueWorkItemDTO.getContentHanding());

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
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void save() {
        IssueHistoryEntityDTO issueHistoryEntityDTO = new IssueHistoryEntityDTO();
        IssueHistoryEntityDTO issueHistoryContentIssueDetail = new IssueHistoryEntityDTO();

        if (issueDTOType == 0)
            issueHistoryEntityDTO.setType(0);
        else
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


        //tinh truong, ban dieu hanh
        if (rb1ChuyenBanDieuHanh.isChecked()) {
            issueWorkItemDTO.setChangeSysRoleCode(1L);//Đã chuyển cho ban điều hành
        } else if (rb3DongPhanAnh.isChecked()) {
            issueWorkItemDTO.setChangeSysRoleCode(0L);
        }

        issueWorkItemDTO.setContentHanding(edtContentHanding.getText().toString());
        issueWorkItemDTO.setContent(edtContent.getText().toString());

        ApiManager.getInstance().updateIssue(issueWorkItemDTO,
                issueHistoryContentIssueDetail, issueHistoryContentHandingDetail,
                IssueDTOResponse.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            response = IssueDTOResponse.class.cast(result);
                            ResultInfo resultInfo = response.getResultInfo();
                            if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                Toast.makeText(IssueDetailCameraActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                                App.getInstance().setNeedUpdateIssue(true);
                                finish();
                            } else {
                                Toast.makeText(IssueDetailCameraActivity.this, getString(R.string.update_fail)
                                        + (resultInfo.getMessage()!=null?": " + resultInfo.getMessage():""), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(IssueDetailCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
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


}