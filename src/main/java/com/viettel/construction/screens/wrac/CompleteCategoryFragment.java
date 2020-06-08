package com.viettel.construction.screens.wrac;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.viettel.construction.common.App;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.model.api.WorkItemResult;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Hiển thị danh sách hạng mục hoàn thành
 */

public class CompleteCategoryFragment extends Fragment {


    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rcvData)
    RecyclerView rcvCategory;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.txtHeader)
    TextView txtTitle;
    @BindView(R.id.progressBar)
    CustomProgress customProgress;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;
    private boolean allowSearch = true;

    private List<WorkItemDetailDTO> listData = new ArrayList<>();


    private CompleteCategoryAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_complete, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtTitle.setText(getActivity().getString(R.string.complete_category1, "0"));
        setupEdt();
        initRecyclerView();
        loadData();
    }

    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        edtSearch.setText("");
        allowSearch = true;
    }

    private void changeHeader() {
        txtTitle.setText(getString(R.string.complete_category1,
                adapter.getItemCount() + ""));
        if (adapter.getItemCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateCompleteCategory()) {
            App.getInstance().setNeedUpdateCompleteCategory(false);
            loadData();
        }
    }

    private void initRecyclerView() {
        adapter = new CompleteCategoryAdapter(getActivity(), listData);
        rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvCategory.setAdapter(adapter);
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        edtSearch.setText("");
    }

    private void setupEdt() {
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (allowSearch) {
                        if (editable.toString().trim().length() == 0) {
                            adapter.setListData(listData);
                        } else {
                            if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);
                            String input = StringUtil.removeAccentStr(editable.toString()).toUpperCase().trim();
                            List<WorkItemDetailDTO> dataSearch = new ArrayList<>();
                            for (WorkItemDetailDTO workItemDetailDTO : listData) {
                                if (workItemDetailDTO.getConstructionCode() != null && workItemDetailDTO.getName() != null) {
                                    String constructionCode = StringUtil.removeAccentStr(workItemDetailDTO.getConstructionCode()).toUpperCase();
                                    String name = StringUtil.removeAccentStr(workItemDetailDTO.getName()).toString().toUpperCase();
                                    if (constructionCode.contains(input) || name.contains(input)) {
                                        dataSearch.add(workItemDetailDTO);
                                    }
                                }
                            }
                            adapter.setListData(dataSearch);
                        }
                        adapter.notifyDataSetChanged();
                        //
                        changeHeader();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListWorkComplete(WorkItemResult.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                WorkItemResult itemResult = WorkItemResult.class.cast(result);
                if (itemResult.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (itemResult.getListWorkItemDTO() != null) {

                        listData = itemResult.getListWorkItemDTO();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                    }
                }
                customProgress.setLoading(false);

                //
                changeHeader();
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);

                //
                changeHeader();
            }
        });
    }


    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_complete_category, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            cleartSearch();
            switch (menuItem.getItemId()) {
                case R.id.all:
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();
                    changeHeader();
                    break;
                case R.id.confirm:
                    if (listData.size() > 0) {
                        filterByStatus("3");
                    }
                    break;
                case R.id.dont_confirm:
                    if (listData.size() > 0) {
                        filterByStatus("2");
                    }
                    break;

            }
            return true;
        });
        popup.show();
    }


    private void filterByStatus(String status) {

        List<WorkItemDetailDTO> dataSearch = rx.Observable.from(listData)
                .filter(x -> x.getStatus().contains(status))
                .toList().toBlocking().singleOrDefault(new ArrayList<>());

        adapter.setListData(dataSearch);
        adapter.notifyDataSetChanged();

        //
        changeHeader();
    }
}
