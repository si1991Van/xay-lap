package com.viettel.construction.screens.wrac;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionScheduleDTO;
import com.viettel.construction.model.api.ConstructionScheduleDTOResponse;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.commons.SelectEmployeeCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomBottomSheetDialogFragment;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Menu Quản lý công trình , màn hình 2
 */

public class ConstructionLevel1ChartFragment_rac extends
        BaseChartFragment implements DetailConstructionAdapter_rac.OnClickItem {
    @BindView(R.id.rcvData)
    RecyclerView recyclerView;
    @BindView(R.id.txtHeader)
    TextView mTVTitle;
    @BindView(R.id.edtSearch)
    EditText mEdtSearch;
    @BindView(R.id.imgFilter)
    ImageView mIVFilter;
    @BindView(R.id.progressBar)
    CustomProgress customProgress;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private ConstructionScheduleDTO constructionScheduleDTO;
    private String scheduleType = "";
    private EmployeeApi employee;
    private SysUserRequest sysUser;
    private CustomBottomSheetDialogFragment bottomSheetDialogFragment;
    private DetailConstructionAdapter_rac detailConstructionAdapter;
    private List<ConstructionScheduleItemDTO> constructionList = new ArrayList<>();
    private List<ConstructionScheduleItemDTO> constructionListBackup = new ArrayList<>();
    private List<ConstructionScheduleItemDTO> constructionListBackupForSearch = new ArrayList<>();
    private ConstructionScheduleDTOResponse response;
    private Integer position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_base, container, false);
        ButterKnife.bind(this, view);
        mEdtSearch.setText("");
        setupEdt();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            constructionScheduleDTO = (ConstructionScheduleDTO) getArguments().getSerializable("CONSTRUCTION_MANAGEMENT_OBJ");
            scheduleType = getArguments().getString("type");
            if (getArguments().getSerializable("SYS_USER_1") != null) {
                sysUser = (SysUserRequest) getArguments().getSerializable("SYS_USER_1");
            }
            if (constructionScheduleDTO != null) {
                mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
            }
        }
        customProgress.setLoading(false);
        initData();
        loadData();
    }

    private void clearData() {
        constructionList.clear();
        constructionListBackup.clear();
        constructionListBackupForSearch.clear();
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
                if (constructionListBackup.size() != 0) {
                    constructionList.clear();
                    if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                        imgClearTextSearch.setVisibility(View.VISIBLE);
                    Log.e("Length:", editable.toString().length()+"");
                    String input = StringUtil.removeAccentStr(editable.toString().toString()).toLowerCase().trim();
                    if (!input.isEmpty()) {
                        for (ConstructionScheduleItemDTO itemDto: constructionListBackupForSearch) {
                            String name, performer;
                            if (itemDto.getName() != null) {
                                name = StringUtil.removeAccentStr(itemDto.getName()).toLowerCase();
                            } else {
                                name = "";
                            }
                            if (itemDto.getSyuFullName() != null) {
                                performer = StringUtil.removeAccentStr(itemDto.getSyuFullName()).toLowerCase();
                            } else {
                                performer = "";
                            }
                            if (name.contains(input) || performer.contains(input)) {
                                constructionList.add(itemDto);
                            }
                        }
                    } else {
                        constructionList.addAll(constructionListBackupForSearch);
                    }
                    detailConstructionAdapter.notifyDataSetChanged();
                    //
                    if (constructionList == null ||
                            (constructionList != null &&
                                    constructionList.size() == 0)) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                }


            }
        });
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        mEdtSearch.setText("");
    }

    private void initData() {
        constructionScheduleDTO.setScheduleType(scheduleType);


        //
        detailConstructionAdapter = new DetailConstructionAdapter_rac(scheduleType, getActivity(), constructionList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(detailConstructionAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // load lại nếu như có công việc đang thực hiện chuyển sang tạm dừng
        if (App.getInstance().isNeedUpdate()) {
            loadData();
        }
        // load lại nếu có công việc tạo mới
        if (App.getInstance().isNeedUpdateAfterCreateNewWork()) {
            loadData();
        }
        // load lại nếu có công việc thay đổi sau khi xem detail
        if (App.getInstance().isNeedUpdateAfterSaveDetail()) {
           loadData();
        }
        // load lại nếu có công việc đang tạm dừng chuyển sang tiếp tục thực hiện
        if (App.getInstance().isNeedUpdateAfterContinue()) {
           loadData();
        }
    }

    public void loadData() {
        String author;
        if (sysUser != null) {
            if (!sysUser.getAuthorities().isEmpty()) {
                author = sysUser.getAuthorities();
            } else {
                author = "";
            }
        } else {
            author = "";
        }
        customProgress.setLoading(true);
        ApiManager.getInstance().getValueConstructionManagement(author, constructionScheduleDTO, ConstructionScheduleDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T resuelt) {
                try {
                    response = ConstructionScheduleDTOResponse.class.cast(resuelt);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        clearData();
                        constructionList.addAll(response.getListConstructionScheduleItemDTO());
                        constructionListBackup.addAll(constructionList);
                        constructionListBackupForSearch.addAll(constructionList);
                        detailConstructionAdapter.notifyDataSetChanged();
                        if (getActivity() != null && isAdded()) {
                            if (constructionList.size() != 0) {
                                mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                            } else {
                                mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                            }
                        }
                        if (!mEdtSearch.getText().toString().isEmpty()) {
                            String input = mEdtSearch.getText().toString();
                            mEdtSearch.setText(input);
                            mEdtSearch.setSelection(input.length());
                        }
                        customProgress.setLoading(false);
                    } else {
                        customProgress.setLoading(false);
                        Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                    //
                    if (detailConstructionAdapter.getDetailConstructionList() == null ||
                            (detailConstructionAdapter.getDetailConstructionList() != null
                                    && detailConstructionAdapter.getDetailConstructionList().size() == 0)) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                if (getActivity() != null && isAdded()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), mIVFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.detail_construction_management_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.all:
                        mEdtSearch.setText("");
                        if (constructionListBackup.size() != 0) {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            constructionList.addAll(constructionListBackup);
                            constructionListBackupForSearch.addAll(constructionListBackup);
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                        } else {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                        }
                        break;
                    case R.id.did_not_perform:
                        mEdtSearch.setText("");
                        if (constructionListBackup.size() != 0) {
                            filterByStatus("1", false);
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                        } else {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                        }
                        break;
                    case R.id.in_process:
                        mEdtSearch.setText("");
                        if (constructionListBackup.size() != 0) {
                            filterByStatus("2", false);
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                        } else {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                        }
                        break;
                    case R.id.finished:
                        mEdtSearch.setText("");
                        if (constructionListBackup.size() != 0) {
                            filterByStatus("3", false);
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                        } else {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                        }
                        break;
                    case R.id.low_process:
                        mEdtSearch.setText("");
                        if (constructionListBackup.size() != 0) {
                            filterByStatus("2", true);
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                        } else {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                        }
                        break;
                    case R.id.on_schedule:
                        mEdtSearch.setText("");
                        if (constructionListBackup.size() != 0) {
                            filterByStatus("1", true);
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), constructionList.size() + ""));
                        } else {
                            constructionList.clear();
                            constructionListBackupForSearch.clear();
                            detailConstructionAdapter.notifyDataSetChanged();
                            mTVTitle.setText(getString(R.string.title_detail_construction_management, constructionScheduleDTO.getConstructionCode(), "0"));
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }


    @OnClick(R.id.imgBack)
    public void onClickBack() {
        //commitChange(new ConstructionManagementFragmnet());
        getFragmentManager().popBackStack();
    }

    // completePercent
    // 0 chưa thực hiện
    // 0 < < 100 đang thực hiện
    // 100 đã hoàn thành

    // 1 chưa thực hiện
    // 2 đang thực hiện
    // 3 Đã hoàn thành
    private void filterByStatus(String status, boolean isLowProcess) {
        constructionList.clear();
        constructionListBackupForSearch.clear();
        for (ConstructionScheduleItemDTO itemDTO : constructionListBackup) {
            if (isLowProcess) {
                String temp;
                if (itemDTO.getCompleteState() > 0) {
                    temp = itemDTO.getCompleteState() + "";
                } else {
                    temp = 0 + "";
                }
                if (temp.contains(status))
                    constructionList.add(itemDTO);
            } else {
                Long temp1;
                if (itemDTO.getCompletePercent() != null) {
                    temp1 = itemDTO.getCompletePercent();
                } else {
                    temp1 = null;
                }
                if (status.equals("1") && temp1 != null && temp1 == 0L) {
                    constructionList.add(itemDTO);
                } else if (status.equals("2") && temp1 != null && temp1 > 0L && temp1 < 100L) {
                    constructionList.add(itemDTO);
                } else if (status.equals("3") && temp1 != null && temp1 == 100L) {
                    constructionList.add(itemDTO);
                } else if (status.equals("1") && temp1 == null) {
                    constructionList.add(itemDTO);
                }
            }
        }
        constructionListBackupForSearch.addAll(constructionList);
        detailConstructionAdapter.notifyDataSetChanged();
        //
        if (constructionListBackupForSearch == null ||
                (constructionListBackupForSearch != null &&
                        constructionListBackupForSearch.size() == 0)) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClickDetailConstruction(int pos) {
        Fragment frag = new ConstructionLevel2ChartFragment_rac();
        ConstructionScheduleItemDTO constructionScheduleItemDTO = constructionList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putString("type", scheduleType);
        bundle.putSerializable("CONSTRUCTION_MANAGEMENT_OBJ_2", constructionScheduleItemDTO);
        bundle.putSerializable("SYS_USER_2", sysUser);
        frag.setArguments(bundle);
        commitChange(frag,true);
    }

    @Override
    public void onLongClickDetailConstruction(int pos) {

        if (sysUser != null) {
            if (sysUser.getAuthorities().equals(VConstant.MANAGE_PLAN)
//                    && ((constructionList.get(pos).getIsInternal().trim().equals("1")) || (constructionList.get(pos).getIsInternal().trim().equals("2")))
                    && scheduleType.equals("2")) {
                App.getInstance().setAuthor(VConstant.MANAGE_PLAN);
                bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION_CATEGORY_HANDOVER", pos);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), "BOTTOM_SHEET_CHANGE_PERSON");
            }
        }
    }

    public void changePerson(int pos) {
        bottomSheetDialogFragment.dismiss();
        position = pos;
        Intent intent = new Intent(getActivity(), SelectEmployeeCameraActivity.class);

        startActivityForResult(intent, 3);

    }

    public void cancelAction() {
        bottomSheetDialogFragment.dismiss();
    }

    private void handoverPerson(EmployeeApi employee) {
        if (employee != null) {
            customProgress.setLoading(true);
            SysUserRequest sysUserRequest = new SysUserRequest();
            String author = "";
            if (sysUser != null) {
                if (!sysUser.getAuthorities().isEmpty()) {
                    author = sysUser.getAuthorities();
                } else {
                    author = "";
                }
            } else {
                author = "";
            }
            sysUserRequest.setSysUserId(Long.parseLong(employee.getSysUserId()));
            ConstructionScheduleItemDTO dto = constructionList.get(position);
            dto.setIsInternal(constructionScheduleDTO.getIsInternal());
            ApiManager.getInstance().requestHandoverPersonCategory(author, sysUserRequest, dto, ConstructionScheduleDTOResponse.class, new IOnRequestListener() {
                @Override
                public <T> void onResponse(T result) {
                    try {
                        ConstructionScheduleDTOResponse Response = ConstructionScheduleDTOResponse.class.cast(result);
                        ResultInfo resultInfo = Response.getResultInfo();
                        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            customProgress.setLoading(false);
                            App.getInstance().setAuthor(null);
                            Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            customProgress.setLoading(false);
                            Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    customProgress.setLoading(false);
                    Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 3:
                    if (resultCode == 3) {
                        if (data.getExtras() != null) {
                            employee = (EmployeeApi) data.getSerializableExtra("employeeResult");
                            handoverPerson(employee);
                        }
                    }
                default:
    //                txtWork.setText(data.getStringExtra("categoryResult"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
