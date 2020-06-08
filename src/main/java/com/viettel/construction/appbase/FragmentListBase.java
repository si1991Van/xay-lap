package com.viettel.construction.appbase;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.home.HomeCameraActivity;
import com.viettel.construction.server.service.IServerResultListener;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/***
 * KePham
 * @param <MD>
 * @param <Res>
 */
public abstract class FragmentListBase<MD, Res> extends Fragment
        implements AdapterFragmentListBase.IItemRecyclerviewClick<MD> {

    @Nullable
    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @Nullable
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @Nullable
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Nullable
    @BindView(R.id.imgFilter)
    ImageView imgFilter;

    @Nullable
    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    @Nullable
    @BindView(R.id.expandableData)
    ExpandableListView listViewExpandableData;

    @Nullable
    @BindView(R.id.progressBar)
    public CustomProgress progressBar;

    @Nullable
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @Nullable
    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;


    //
    public abstract int getLayoutID();

    public abstract AdapterFragmentListBase getAdapterInstance();

    public abstract AdapterExpandableListBase getAdapterExpandInstance();

    public abstract List<MD> dataSearch(String keyWord);

    public abstract List<ExpandableListModel<String, MD>> dataSearchExpandableList(String keyWord);

    public abstract String getHeaderTitle();

//    public abstract String getHintSearchbox();

    public abstract int getMenuID();

    public abstract List<MD> menuItemClick(int menuItem);

    public abstract List<ExpandableListModel<String, MD>> menuItemClickExpandableList(int menuItem);

    //Load data
    public abstract List<MD> getResponseData(Res result);

    public abstract Object[] getParramLoading();

    public abstract APIType getAPIType();

    public abstract Class<Res> responseEntityClass();

    //
    protected SysUserRequest sysUser;
    //
    protected AdapterFragmentListBase adapter;
    protected AdapterExpandableListBase adapterExpand;
    protected List<MD> listData = new ArrayList<>();
    protected List<ExpandableListModel<String, MD>> listDataExpandable = new ArrayList<>();
    private boolean allowSearch = true;

    private int menuPopupItemID = -1;
    private final String TAG = "VTFragmentListBase";
    //

    /***
     * Phuc vu cho viec khoi tao
     * //Giao dien dang expandable hay la giao dien dang recyclerview
     * @return
     */
    protected boolean allowExpandableList() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initData();
        loadData();
    }

    @Optional
    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        edtSearch.setText("");
    }

    public void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        edtSearch.setText("");
        allowSearch = true;
    }

    public void initData() {
        //Adapter
        adapter = getAdapterInstance();
        adapter.itemRecyclerviewClick = this;
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rcvData.setLayoutManager(manager);
        rcvData.setAdapter(adapter);
        //
        if (allowExpandableList() && listViewExpandableData != null) {
            listViewExpandableData.setOnGroupExpandListener((groupPosition) -> {
                listViewExpandableData.smoothScrollToPosition(groupPosition);
            });
            adapterExpand = getAdapterExpandInstance();
            if (adapterExpand != null)
                adapterExpand.itemRecyclerviewClick = this;
            listViewExpandableData.setAdapter(adapterExpand);
        }
        //Editext
        if (edtSearch != null) {
            edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            edtSearch.setOnEditorActionListener((v, actionId, event) -> {
                boolean handled = false;
                try {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        // Send the user message
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                        handled = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return handled;
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
                            if (menuPopupItemID != R.id.construction) {
                                if (editable.toString().trim().length() == 0)
                                    adapter.setListData(listData);
                                else {
                                    imgClearTextSearch.setVisibility(View.VISIBLE);
                                    List<MD> dataSearch = dataSearch(editable.toString().trim());
                                    adapter.setListData(dataSearch);
                                }
                                adapter.notifyDataSetChanged();
                                rcvData.smoothScrollToPosition(0);
                                setHeader();
                            } else {
                                //TODO: Search Group
                                if (editable.toString().trim().length() == 0) {
                                    adapterExpand.setListData(listDataExpandable);
                                    adapterExpand.notifyDataSetChanged();
                                    //expand all
                                    for (int groupPosition = 0; groupPosition < adapterExpand.getGroupCount(); groupPosition++)
                                        listViewExpandableData.collapseGroup(groupPosition);

                                    listViewExpandableData.smoothScrollToPosition(0);
                                    setHeaderExpandable();
                                } else {
                                    imgClearTextSearch.setVisibility(View.VISIBLE);
                                    rebuildTree(editable.toString().trim());
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void rebuildTree(String keyword) {
        //Search
        List<ExpandableListModel<String, MD>> dataSearch =
                dataSearchExpandableList(keyword);
        adapterExpand.setListData(dataSearch);
        listViewExpandableData.setAdapter(adapterExpand);
        adapterExpand.notifyDataSetChanged();
        //expand all
        for (int groupPosition = 0; groupPosition < adapterExpand.getGroupCount(); groupPosition++)
            listViewExpandableData.expandGroup(groupPosition);

        //Set  header
        setHeaderExpandable();

    }

    @Optional
    @OnClick({R.id.imgBack, R.id.txtCancel})
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }




    public void setHeader() {
        txtHeader.setText(String.format(getHeaderTitle(),
                adapter.getItemCount() + ""));
        if (adapter.getItemCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);
    }

    public void setHeaderExpandable() {
        int total = 0;
        for (int i = 0; i < adapterExpand.getGroupCount(); i++)
            total += adapterExpand.getChildrenCount(i);
        txtHeader.setText(String.format(getHeaderTitle(),
                total + ""));
        if (adapterExpand.getGroupCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);
    }

    @Optional
    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(getMenuID(), popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            try {
                menuPopupItemID = menuItem.getItemId();
                cleartSearch();
                if (menuItem.getItemId() != R.id.construction) {
                    if (listViewExpandableData != null)
                        listViewExpandableData.setVisibility(View.INVISIBLE);
                    rcvData.setVisibility(View.VISIBLE);
                    //
                    List<MD> dataSearch = menuItemClick(menuItem.getItemId());
                    adapter.setListData(dataSearch);
                    adapter.notifyDataSetChanged();
                    rcvData.smoothScrollToPosition(0);
                    setHeader();
                } else {
                    //
                    listViewExpandableData.setVisibility(View.VISIBLE);
                    rcvData.setVisibility(View.INVISIBLE);
                    listDataExpandable = menuItemClickExpandableList(menuItem.getItemId());
                    adapterExpand.setListData(listDataExpandable);
                    adapterExpand.notifyDataSetChanged();
                    listViewExpandableData.smoothScrollToPosition(0);
                    setHeaderExpandable();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
        popup.show();
    }


    public void loadData() {
        progressBar.setLoading(true);
        Log.d(TAG,"loadData");
        ApiManager.getInstance().getDataFromServer(
                getAPIType(), responseEntityClass(),
                new IServerResultListener<Res>() {
                    @Override
                    public void onResponse(Res result) {
                        try {
                            if (result != null) {
                                listData = getResponseData(result);
                                if (edtSearch.getText().length() > 0) {
                                    adapter.setListData(dataSearch(edtSearch.getText().toString().trim()));
                                } else {
                                    adapter.setListData(listData);
                                    setHeader();
                                }
                                if (menuPopupItemID != R.id.construction)
                                    adapter.notifyDataSetChanged();
                                else {
                                    //Load lai
                                    listDataExpandable = menuItemClickExpandableList(menuPopupItemID);
                                    rebuildTree(edtSearch.getText().toString());
                                }
                            }
                            progressBar.setLoading(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(getContext() != null) {
                                Toast.makeText(getContext(), R.string.error_mes, Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setLoading(false);
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        try {
                            progressBar.setLoading(false);
                            if(getContext() != null) {
                                Toast.makeText(getContext(), R.string.error_mes, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }, getParramLoading());

    }

    public void commitChange(Fragment fragment, Boolean... isAdd) {
        if (getActivity() instanceof HomeCameraActivity)
            ((HomeCameraActivity) getActivity()).changeLayout(fragment, isAdd);
    }


}
