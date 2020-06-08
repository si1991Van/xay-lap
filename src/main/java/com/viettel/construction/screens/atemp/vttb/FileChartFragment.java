package com.viettel.construction.screens.atemp.vttb;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.viettel.construction.screens.atemp.adapter.FileAdapter;
import com.viettel.construction.server.util.MeasUtils;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;


public class FileChartFragment extends BaseChartFragment {

    @BindView(R.id.recycler_view)
    RecyclerView rcvFile;

    public FileChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FileAdapter fileAdapter = new FileAdapter(getActivity());
        final GreedoLayoutManager layoutManager = new GreedoLayoutManager(fileAdapter);
        layoutManager.setMaxRowHeight(MeasUtils.dpToPx(150, getActivity()));
        rcvFile.setLayoutManager(layoutManager);
        rcvFile.setAdapter(fileAdapter);
        int spacing = MeasUtils.dpToPx(4, getActivity());
        rcvFile.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        layoutManager.setFixedHeight(true);
        layoutManager.requestLayout();
    }

}
