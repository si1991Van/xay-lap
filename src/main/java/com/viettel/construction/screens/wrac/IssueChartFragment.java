package com.viettel.construction.screens.wrac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.screens.tabs.IssueAddNewCameraActivity;
import com.viettel.construction.screens.tabs.IssueDetailCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.issue.IssueDTOResponse;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.appbase.BaseChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Hiển thị danh sách phản ánh
 */
public class IssueChartFragment extends BaseChartFragment implements ListReflectAdapter.OnClickItem {
    @BindView(R.id.edtSearch)
    EditText mEdtSearch;
    @BindView(R.id.rcvData)
    RecyclerView rcvFlect;
    @BindView(R.id.btnAddItem)
    FloatingActionButton imgAdd;

    @BindView(R.id.imgFilter)
    ImageView btnFilter;

    @BindView(R.id.progressBar)
    CustomProgress customProgress;

    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private List<IssueWorkItemDTO> listData;

    private ListReflectAdapter adapter;
    private IssueDTOResponse response;

    private boolean allowSearch = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_reflect, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listData = new ArrayList<>();
        txtHeader.setText(getString(R.string.list_reflect, "0"));
        setupEdt();
        initData();
        loadData();
    }

    private void setupEdt() {
        mEdtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
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
                        if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                            imgClearTextSearch.setVisibility(View.VISIBLE);
                        if (editable.toString().trim().length() == 0) {
                            adapter.setListData(listData);
                        } else {
                            List<IssueWorkItemDTO> dataSearch = new ArrayList<>();

                            String input = StringUtil.removeAccentStr(editable.toString() + "").toLowerCase().trim();
                            if (input.length() != 0) {
                                for (IssueWorkItemDTO dto : listData) {
                                    String code, content;
                                    if (dto.getCode() != null) {
                                        code = dto.getCode().toLowerCase();
                                    } else {
                                        code = "";
                                    }
                                    if (dto.getContent() != null) {
                                        content = StringUtil.removeAccentStr(dto.getContent()).toLowerCase();
                                    } else {
                                        content = "";
                                    }
                                    if (code.contains(input) || content.contains(input)) {
                                        dataSearch.add(dto);
                                    }
                                }
                            }
                            adapter.setListData(dataSearch);
                        }
                        adapter.notifyDataSetChanged();
                        //
                        if (adapter.getItemCount() == 0) {
                            txtNoData.setVisibility(View.VISIBLE);
                        } else
                            txtNoData.setVisibility(View.INVISIBLE);

                        txtHeader.setText(getString(R.string.list_reflect, "" + adapter.getItemCount()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void clearSearch() {
        allowSearch = false;
        mEdtSearch.setText("");
        allowSearch = true;
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        try {
            imgClearTextSearch.setVisibility(View.INVISIBLE);
            mEdtSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListReflect(IssueDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    response = IssueDTOResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listData = response.getListIssueEntityDTO();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                    }
                    //
                    if (adapter.getItemCount() == 0) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                    customProgress.setLoading(false);
                    txtHeader.setText(getString(R.string.list_reflect, "" + adapter.getItemCount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                //
                if (adapter.getItemCount() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else
                    txtNoData.setVisibility(View.INVISIBLE);

                customProgress.setLoading(false);
            }
        });
    }


    private void initData() {
        adapter = new ListReflectAdapter(getActivity(), listData, this);
        rcvFlect.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvFlect.setAdapter(adapter);
    }

    @OnClick(R.id.btnAddItem)
    public void add() {
        Intent addIssue = new Intent(getContext(),IssueAddNewCameraActivity.class);
        startActivity(addIssue);
    }


    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), btnFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_reflect, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                clearSearch();
                switch (menuItem.getItemId()) {
                    case R.id.all:
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        txtHeader.setText(getString(R.string.list_reflect, "" + adapter.getItemCount()));
                        break;
                    case R.id.open:
                        if (listData.size() != 0)
                            filterByStatus("1");
                        break;
                    case R.id.close:
                        if (listData.size() != 0)
                            filterByStatus("0");
                        break;

                }
                return true;
            }
        });
        popup.show();
    }

    private void filterByStatus(String status) {
        List<IssueWorkItemDTO> dataSearch = Observable.from(listData).filter(x -> x.getStatus() != null && x.getStatus().contains(status))
                .toList().toBlocking().singleOrDefault(new ArrayList<>());


        adapter.setListData(dataSearch);
        adapter.notifyDataSetChanged();

        txtHeader.setText(getString(R.string.list_reflect, "" + adapter.getItemCount()));

        //
        if (adapter.getItemCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);
    }


    // Nếu là tỉnh trưởng getType == 1 , 0 là nhân viên , ngược lại là ban điều hành
    @Override
    public void onClickReflect(IssueWorkItemDTO data) {
        try {
            App.getInstance().setNeedUpdateIssue(false);

            Intent intent = new Intent(getContext(), IssueDetailCameraActivity.class);
            intent.putExtra(ParramConstant.IssueDTOType, response.getType());
            intent.putExtra("reflect", data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateIssue()) {
            loadData();
        }
    }
}
