package com.viettel.construction.screens.tabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.common.App;
import com.viettel.construction.common.PrefManager;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.screens.atemp.other.WatchPersonalDebtChartFragment;
import com.viettel.construction.screens.home.LoginCameraActivity;
import com.viettel.construction.screens.menu_entangle.EntangleFragment;
import com.viettel.construction.screens.menu_history_vttb.TabPageHistoryVTTBFragment;
import com.viettel.construction.screens.menu_updateconstruction.UpdateConstructionDetailCameraActivity;
import com.viettel.construction.server.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TabMenuChartFragment extends BaseChartFragment {


    @BindView(R.id.manage_problem_with_cdt)
    LinearLayout mManageProblemWithCDT;
    @BindView(R.id.sign_out)
    LinearLayout mSignOut;
    @BindView(R.id.ln_for_debt_user)
    LinearLayout lnDept;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.ln_history)
    LinearLayout lnHistory;
    @BindView(R.id.construction_management)
    LinearLayout lnConstructionManagement;
    @BindView(R.id.txtVersion)
    TextView txtVersion;
//    @BindView(R.id.ln_vol_tio)
//    LinearLayout mLnVolTio;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SysUserDTO dto = VConstant.getDTO();
        String phoneNumber = dto.getPhoneNumber();
        if (phoneNumber != null) {
            if (phoneNumber.length() == 10) {
                String newNumber = StringUtil.printPhone(phoneNumber).substring(1);
                txtPhone.setText(newNumber);
                txtName.setText(dto.getFullName());
            } else {
                txtPhone.setText(StringUtil.printPhone(phoneNumber));
                txtName.setText(dto.getFullName());
            }
        }

        String versionApp = StringUtil.getVersionApp(getContext());
        txtVersion.setText("Version " + versionApp);
    }




    @OnClick(R.id.construction_management)
    public void onClickConstructionManagement() {
//        App.getInstance().setIndextTabConstructionManagement(0);
//        commitChange(new TabPageConstructionFragment(), true);
        Intent constructionInfo = new Intent(getContext(),
                UpdateConstructionDetailCameraActivity.class);
        startActivity(constructionInfo);
    }
    /**
     * Vướng
     */
    @OnClick(R.id.manage_problem_with_cdt)
    public void onClickManageProblemWithCDT() {
//        commitChange(new ListConstructionEntangleChartFragment(), true);
        commitChange(new EntangleFragment(), true);
    }
    /*
    @OnClick(R.id.ln_handover)
    public void onClickHandover() {
        //startActivity(new Intent(getActivity(), TransferMatetialActivity.class));
    }
    @Optional
    @OnClick(R.id.confirm_delivery_bill)
    public void onClickConfirmDeliveryBill() {
        commitChange(new TabPageExWareHouseFragment(), true);
    }

    @OnClick(R.id.for_control_supplies)
    public void onClickForControlSupplies() {
//        commitChange(new ListRefundLever1ChartFragment(), true);
        commitChange(new RefundInventoryLevel1Fragment(), true);
    }
    @OnClick(R.id.confirm_complete_catessgory)
    public void onClickCompleteCategory() {
        commitChange(new WAcceptanceLevel2ChartFragment(), true);
        bus.post(new UpdateEvent(0));
    }
    */

    /***
     * Lịch sử bàn giao vật tư thiết bị
     */
    @OnClick(R.id.ln_history)
    public void onClickHistory() {
        App.getInstance().setTabIndexHistoryHandOver(0);
//        commitChange(new HandOver_Receiver_Detail_Level2Fragment(),true);
//        commitChange(new LichSuVTTBFragment(), true);

        commitChange(new TabPageHistoryVTTBFragment(), true);

    }

    @OnClick(R.id.ln_for_debt_user)
    public void onClickDept() {
        commitChange(new WatchPersonalDebtChartFragment(), true);
    }

    private AlertDialog alertDialogLogout;

    @OnClick(R.id.sign_out)
    public void onClickSignOut() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.AskLogout);
            builder.setNegativeButton(R.string.No, null);
            builder.setPositiveButton(R.string.Yes, (dialogInterface, i) -> {
                try {
                    alertDialogLogout.dismiss();
                    PrefManager.getInstance().put(VConstant.KEY_REMEMBER_LOGIN, null);
                    PrefManager.getInstance().put(VConstant.KEY_LOGIN_USER, null);
                    startActivity(new Intent(getActivity(), LoginCameraActivity.class));
                    getActivity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            alertDialogLogout = builder.create();
            alertDialogLogout.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public interface HighlightBottomBar {
        void highLight();
    }
}
