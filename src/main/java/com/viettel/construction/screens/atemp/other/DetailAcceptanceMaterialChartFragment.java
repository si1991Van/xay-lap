package com.viettel.construction.screens.atemp.other;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;


public class DetailAcceptanceMaterialChartFragment extends BaseChartFragment {
    @BindView(R.id.iv_back_detail_acceptance)
    ImageView mIVBack;
    public DetailAcceptanceMaterialChartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_acceptance_material,container,false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.iv_back_detail_acceptance)
    public void onClickBack(){
        //commitChange(new );
    }
}
