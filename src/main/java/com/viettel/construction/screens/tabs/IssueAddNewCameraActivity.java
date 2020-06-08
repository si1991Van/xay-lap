package com.viettel.construction.screens.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.screens.commons.SelectConstructionCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.issue.IssueDTO;
import com.viettel.construction.model.api.issue.IssueDTOResponse;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.commons.SelectConsCategoryCameraActivity;


/***
 * Tạo phản ánh
 */
public class IssueAddNewCameraActivity extends BaseCameraActivity {


    @BindView(R.id.tv_construction)
    TextView tvConstruction;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.edt_content)
    EditText edtContent;

    @BindView(R.id.txtHeader)
    TextView txtHeader;
    private ConstructionStationWorkItem construction;
    private ConstructionStationWorkItem construction1;
    private long constructionId;
    private long categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content_creat_reflect);
        ButterKnife.bind(this);
        txtHeader.setText("Tạo phản ánh");
    }


    @OnClick(R.id.tv_construction)
    public void OnClickContruction() {
        Intent intent = new Intent(this, SelectConstructionCameraActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.tv_category)
    public void OnClickCategory() {
        if (constructionId > 0) {
            Intent intent = new Intent(this, SelectConsCategoryCameraActivity.class);
            intent.putExtra("id_construction", constructionId);
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(this, "Vui lòng chọn công trình.", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.txtCancel)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.txtSave)
    public void onSave() {

        if (validateSave())
            saveData();

    }

    private void saveData() {
        long idUser = VConstant.getUser().getSysUserId();
        long idGroup = VConstant.getUser().getSysGroupId();
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setContent(edtContent.getText().toString());
        issueDTO.setConstructionId(constructionId);
        issueDTO.setWorkItemId(categoryId);
        issueDTO.setStatus("1");
        issueDTO.setCreatedUserId(idUser);
        issueDTO.setCreatedGroupId(idGroup);
        if (!edtContent.getText().toString().trim().isEmpty()) {
            if (!tvConstruction.getText().toString().trim().isEmpty()) {
                ApiManager.getInstance().registerIssue(issueDTO, IssueDTOResponse.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        IssueDTOResponse response = IssueDTOResponse.class.cast(result);
                        ResultInfo resultInfo = response.getResultInfo();
                        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            Toast.makeText(IssueAddNewCameraActivity.this, "Tạo mới thành công.", Toast.LENGTH_SHORT).show();
                            edtContent.setText("");
                            categoryId = 0;
                            tvCategory.setText("");

                            App.getInstance().setNeedUpdateIssue(true);
                            finish();
                        } else {
                            if (resultInfo.getMessage() != null)
                                Toast.makeText(IssueAddNewCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(IssueAddNewCameraActivity.this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(IssueAddNewCameraActivity.this,
                        getString(R.string.warning_user_have_not_choose_construction), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(IssueAddNewCameraActivity.this,
                    getString(R.string.warning_user_have_not_fill_edit_text_reflect), Toast.LENGTH_SHORT).show();
        }

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
                                constructionId = construction.getConstructionId();
                                tvConstruction.setText(construction.getConstructionCode());
                                // reset gia tri hang muc
                                categoryId = 0;
                                tvCategory.setText("");
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
                                    categoryId = construction1.getWorkItemId();
                                    tvCategory.setText(construction1.getName());
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

    private boolean validateSave() {
        boolean isSave = false;
        if (edtContent.getText().toString().trim().length() == 0 || edtContent.getText().toString().trim().isEmpty()) {
            Toast.makeText(IssueAddNewCameraActivity.this, getString(R.string.please_input_content_reflect), Toast.LENGTH_SHORT).show();
            isSave = false;
        } else if (constructionId == 0) {
            Toast.makeText(IssueAddNewCameraActivity.this, getString(R.string.please_input_construction), Toast.LENGTH_SHORT).show();
            isSave = false;
        } else
            isSave = true;

        return isSave;
    }
}