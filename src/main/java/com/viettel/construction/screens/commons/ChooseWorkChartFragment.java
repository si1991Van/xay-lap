package com.viettel.construction.screens.commons;



import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;

/**
 * Màn hình lựa chọn công việc được tạo
 */
public class ChooseWorkChartFragment extends BaseChartFragment {
    @BindView(R.id.imgBack)
    ImageView btnCancel;
    @BindView(R.id.btn_1)
    TextView btn1;
    @BindView(R.id.btn_2)
    TextView btn2;
    @BindView(R.id.btn_3)
    TextView btn3;
    @BindView(R.id.btn_4)
    TextView btn4;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_work, container, false);
        ButterKnife.bind(this, view);
        txtHeader.setText("Chọn loại công việc");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick(R.id.imgBack)
    public void onClickCancel() {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.btn_1)
    public void changeScreen1() {
//        commitChange(new CreateNewCvCameraActivity());
        startActivity(new Intent(getActivity(), CreateNewCvCameraActivity.class));
    }

    @OnClick(R.id.btn_2)
    public void changeScreen2() {
//        commitChange(new CreateNewHSHCCameraActivity());
        startActivity(new Intent(getActivity(), CreateNewHSHCCameraActivity.class));

    }

    @OnClick(R.id.btn_3)
    public void changeScreen3() {
//        commitChange(new CreateNewRevenueCameraActivity());
        startActivity(new Intent(getActivity(), CreateNewRevenueCameraActivity.class));

    }

    @OnClick(R.id.btn_4)
    public void changeScreen4() {
        //commitChange(new CreateNewHSHCCameraActivity());
        startActivity(new Intent(getActivity(), CreateNewCvOther2CameraActivity.class));

    }
}
