package com.viettel.construction.screens.atemp.other;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.wrac.ManageWorkAdapter;
import com.viettel.construction.appbase.BaseChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListConstructionChartFragment extends BaseChartFragment {
    private ManageWorkAdapter adapter;
    private List<ConstructionTaskDTO> mListConstruction = new ArrayList<>();
    private List<ConstructionTaskDTO> mListBackUp = new ArrayList<>();
    private List<ConstructionTaskDTO> mListBackUpForSearch = new ArrayList<>();
    private View mView;
    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private String scheduleType = "";
    private SysUserRequest sysUser;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.rcvData)
    RecyclerView rcvWork;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.txtHeader)
    TextView txtTitle;

    @BindView(R.id.edtSearch)
    EditText edtSearchWork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_construction_child_list, container, false);
        ButterKnife.bind(this, mView);
        if (getArguments() != null) {
            constructionScheduleItemDTO = (ConstructionScheduleItemDTO) getArguments().getSerializable("CONSTRUCTION_MANAGEMENT_OBJ_2");
            scheduleType = getArguments().getString("type");
            if (getArguments().getSerializable("SYS_USER_2") != null) {
                sysUser = (SysUserRequest) getArguments().getSerializable("SYS_USER_2");
            }
            txtTitle.setText(constructionScheduleItemDTO.getName());
        }
        return mView;
    }

}
