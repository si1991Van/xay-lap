package com.viettel.construction.screens.menu_ex_warehouse;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.databinding.ABillBinding;
import com.viettel.construction.databinding.PxkItemPaging;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SysUserRequest;

import java.util.ArrayList;

import butterknife.OnTextChanged;

public class TabPageExWarehouse_ItemAdapter_Paging extends PagedListAdapter<SynStockTransDTO, TabPageExWarehouse_ItemAdapter_Paging.PxkHolder> {

    private Context mContext;

    private IItemRecyclerviewClick mItemClick;

    ArrayList<SynStockTransDTO> listSelected = new ArrayList<>();

    public void setmItemClick(IItemRecyclerviewClick mItemClick) {
        this.mItemClick = mItemClick;
    }

    public TabPageExWarehouse_ItemAdapter_Paging(Context context, DiffUtil.ItemCallback<SynStockTransDTO>  DIFF_CALLBACK){
        super(DIFF_CALLBACK);
        mContext = context;

    }

    @NonNull
    @Override
    public PxkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PxkItemPaging binding = PxkItemPaging.inflate(inflater, parent, false);
        return new PxkHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PxkHolder holder, int position) {
        holder.bindTo(getItem(position), position + 1);

        SynStockTransDTO bill = getItem(position);
        holder.binding.cbSelect.setChecked(bill.isSelected());
        holder.binding.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                SynStockTransDTO currentSelected = getItem(position);
                if(checkBox.isChecked()){
                    currentSelected.setSelected(true);
                    listSelected.add(currentSelected);

                } else if (!checkBox.isChecked()){
                    currentSelected.setSelected(false);
                    listSelected.remove(currentSelected);
                }
                mItemClick.onCheckboxItemSelected(listSelected);
            }
        });
    }

    public class PxkHolder extends RecyclerView.ViewHolder{

        public PxkItemPaging binding;

        public PxkHolder(PxkItemPaging binding){
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bindTo(SynStockTransDTO bill, int pos){
            if (bill.getStockType().contains("A") && bill.getCntContractCode() != null) {
                binding.lnContractCode.setVisibility(View.VISIBLE);
                binding.txtContractCode.setText(bill.getCntContractCode());
            } else {
                binding.lnContractCode.setVisibility(View.GONE);
            }
            if (bill.getReceiverId() == null) {
                binding.tvStatusBg.setVisibility(View.GONE);
                binding.txtCondition.setVisibility(View.GONE);
            } else {
                binding.tvStatusBg.setVisibility(View.VISIBLE);
                binding.txtCondition.setVisibility(View.VISIBLE);
            }
            binding.tvIndex.setText(mContext.getString(R.string.text_code_bill_with_index, pos));
            if (bill.getCode() != null)
                binding.txtCode.setText(bill.getCode());
            //26112018_HuyVT2_Comment_Start: Add them construction Code vao danh sach phieu xuat kho
            if (bill.getConsCode() != null)
                binding.txtConstructionCode.setText(bill.getConsCode());
//        26112018_HuyVT2_Comment_End-----------------

            if (bill.getRealIeTransDate() != null)
                binding.txtReleaseDate.setText(bill.getRealIeTransDate());

            if (bill.getConfirm() != null) {
                switch (bill.getConfirm().trim()) {
                    case "0":
                        binding.txtStatus.setText(mContext.getString(R.string.wait_for_receive));// Chờ tiếp nhận
                        binding.txtStatus.setTextColor(Color.GRAY);
                        break;
                    case "1":
                        binding.txtStatus.setText(mContext.getString(R.string.received_bill_menu));// Đã tiếp nhận
                        binding.txtStatus.setTextColor(Color.BLACK);
                        break;
                    case "2":
                        binding.txtStatus.setText(mContext.getString(R.string.rejected)); // Đã từ chối
                        binding.txtStatus.setTextColor(Color.RED);
                        break;
                    default:
                        break;
                }
            } else {
                binding.txtStatus.setText(mContext.getString(R.string.wait_for_receive));
                binding.txtStatus.setTextColor(Color.GRAY);

            }
// receiver is null -> hide state
            if (bill.getState() != null) {
                switch (bill.getState().trim()) {
                    case "0":
                        binding.txtCondition.setText(mContext.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
                        binding.txtCondition.setTextColor(Color.GRAY);
                        break;
                    case "1":
                        binding.txtCondition.setText(mContext.getString(R.string.confirmed));// Đã xác nhận
                        binding.txtCondition.setTextColor(Color.BLACK);
                        break;
                    case "2":
                        binding.txtCondition.setText(mContext.getString(R.string.deny));// Từ chối xác nhận
                        binding.txtCondition.setTextColor(Color.RED);
                        break;
                    default:
                        break;
                }
            } else {
                binding.txtCondition.setText(mContext.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
                binding.txtCondition.setTextColor(Color.GRAY);
            }
            //Start visible checkbox
            SysUserRequest sysUserRequest = VConstant.getUser();
            Long loginSysUserId = sysUserRequest.getSysUserId();
            Long receiverId;
            if (bill.getReceiverId() != null) {
                receiverId = bill.getReceiverId();
            } else {
                receiverId = 1L;
            }
            if(bill.getStockType().contains("A")){
                if((bill.getLastShipperId() != null && bill.getConfirm().trim().equals("0") && receiverId == 1) ||
                        (bill.getLastShipperId().compareTo(receiverId) != 0 && receiverId.compareTo(loginSysUserId) == 0 && bill.getState().trim().equals("0"))
                        || (bill.state.trim().equals("2") && bill.confirm.trim().equals("0") && bill.lastShipperId.compareTo(loginSysUserId) == 0)
                        || (bill.confirm.trim().equals("1") && bill.state.trim().equals("0")&& receiverId.compareTo(loginSysUserId) == 0)){
                    binding.cbSelect.setVisibility(View.VISIBLE);
                }
                else if ((bill.getLastShipperId() != null && bill.getConfirm().trim().equals("1") && receiverId == 1) ||
                        (receiverId != 1 && bill.getState().trim().equals("2") && bill.getLastShipperId().compareTo(loginSysUserId) == 0) ||
                        (bill.getLastShipperId().compareTo(receiverId) == 0 && bill.getState().trim().equals("1"))) {
                    // có bàn giao và quay lại
//                rlForTheRest.setVisibility(View.VISIBLE);
                    binding.cbSelect.setVisibility(View.GONE);

                } else if ((bill.getLastShipperId().compareTo(loginSysUserId) == 0 && (bill.getLastShipperId().compareTo(loginSysUserId) == 0 && bill.getState().trim().equals("0") || bill.getConfirm().trim().equals("2"))
                        && receiverId != 1 && bill.getState().trim().equals("0") && receiverId.compareTo(loginSysUserId) != 0 && !bill.getConfirm().trim().equals("0")) ||
                        (bill.getLastShipperId() != null && bill.getConfirm().trim().equals("1") && receiverId.compareTo(loginSysUserId) == 0 && bill.getState().trim().equals("2")) ||
                        (bill.getLastShipperId().compareTo(loginSysUserId) == 0 && receiverId == 1 && bill.getConfirm().trim().equals("2"))
                        || (bill.state.trim().equals("2") && bill.confirm.trim().equals("0") && receiverId.compareTo(loginSysUserId) == 0)
                        || receiverId != 1) {
                    binding.cbSelect.setVisibility(View.GONE);
                }
            } else binding.cbSelect.setVisibility(View.GONE);

            //End visible checkbox
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClick.onItemRecyclerViewclick(bill);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public interface IItemRecyclerviewClick {
        void onItemRecyclerViewclick(SynStockTransDTO item);
        void onCheckboxItemSelected(ArrayList listSelected);
    }
}
