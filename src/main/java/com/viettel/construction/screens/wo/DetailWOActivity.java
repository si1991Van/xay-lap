package com.viettel.construction.screens.wo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.screens.custom.dialog.DialogCancel;
import com.viettel.construction.screens.custom.dialog.DialogPleaseComment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailWOActivity extends BaseCameraActivity implements DialogCancel.OnClickDialogForCancel,
        DialogPleaseComment.OnClickDialogPleaseComment {

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.tv_code_wo)
    TextView tvCode;
    @BindView(R.id.tv_name_wo)
    TextView tvName;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_performer)
    TextView tvPerformer;


    @BindView(R.id.tv_handover)
    TextView tvHandover;
    @BindView(R.id.tv_Accept)
    TextView tvAccept;
    @BindView(R.id.tv_Reject)
    TextView tvReject;

    @BindView(R.id.tv_Report)
    TextView tvReport;
    @BindView(R.id.tv_Finish)
    TextView tvFinish;

    private ConstructionAcceptanceCertDetailDTO workItemDTO;
    private DialogCancel dialogCancel;
    private DialogPleaseComment dialogPleaseComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wo);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            workItemDTO = (ConstructionAcceptanceCertDetailDTO) getIntent().getExtras().getSerializable("Item_WO");
        }
        dialogCancel = new DialogCancel(this, this);
        dialogCancel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseComment = new DialogPleaseComment(this, this);
        dialogPleaseComment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setDataView();
    }


    private void setDataView(){
        tvCode.setText("COGTRIH_SL");
        tvName.setText("Xay mog cot duoi dat");
        tvProgress.setText("3/10");
        tvPerformer.setText("Pham thi kim heu");
        tvStatus.setText("Cho Ft tiep nhan");
        txtHeader.setText("Xay mog cot duoi dat");
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick({R.id.tv_handover, R.id.tv_Accept, R.id.tv_Process, R.id.tv_Finish})
    public void onClickBtnCancel() {
        finish();
    }


    @OnClick(R.id.tv_Reject)
    public void onReject(){
        dialogCancel.show();
    }

    @OnClick(R.id.tv_Report)
    public void onReport(){
        dialogPleaseComment.show();
    }

    @Override
    public void onClickConfirmOfCancel(String s) {
        dialogCancel.dismiss();
    }

    @Override
    public void OnClickDialogPleaseComment(String s) {
        dialogPleaseComment.dismiss();
    }
}
