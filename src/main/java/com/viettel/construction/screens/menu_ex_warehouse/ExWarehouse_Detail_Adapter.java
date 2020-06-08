package com.viettel.construction.screens.menu_ex_warehouse;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.common.OnLoadDataListener;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.model.api.SysUserRequest;

import java.util.List;

public class ExWarehouse_Detail_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //for load more
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadDataListener onLoadDataListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private Activity activity;
    private List<SynStockTransDetailDTO> listData;
    private OnClickDetailImplement onClickDetailImplement;
    private LinearLayoutManager linearLayoutManager;
    private SynStockTransDTO synStockTransDTO;
    private boolean isABill;

    public List<SynStockTransDetailDTO> getListData() {
        return listData;
    }

    public void setListData(List<SynStockTransDetailDTO> listData) {
        this.listData = listData;
    }

    public ExWarehouse_Detail_Adapter(RecyclerView recyclerView, Activity activity, List<SynStockTransDetailDTO> list, OnClickDetailImplement onClickDetailImplement) {
        this.activity = activity;
        this.listData = list;
        this.onClickDetailImplement = onClickDetailImplement;

        linearLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (onLoadDataListener != null) {
//                        onLoadDataListener.onLoadMore();
//                    }
//                    isLoading = true;
//                }
                checkLoadMore();
            }
        });
    }

    public ExWarehouse_Detail_Adapter(RecyclerView recyclerView, Activity activity, List<SynStockTransDetailDTO> list, OnClickDetailImplement onClickDetailImplement, SynStockTransDTO synStockTransDTO, boolean isABill) {
        this.activity = activity;
        this.listData = list;
        this.onClickDetailImplement = onClickDetailImplement;
        this.synStockTransDTO = synStockTransDTO;
        this.isABill = isABill;
        linearLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (onLoadDataListener != null) {
//                        onLoadDataListener.onLoadMore();
//                    }
//                    isLoading = true;
//                }
                checkLoadMore();
            }
        });
    }

    private void checkLoadMore(){
        totalItemCount = linearLayoutManager.getItemCount();
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            if (onLoadDataListener != null) {
                onLoadDataListener.onLoadMore();
            }
            isLoading = true;
        }
    }

    public void setLoadMoreListener(OnLoadDataListener loadMoreListener) {
        this.onLoadDataListener = loadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_implement, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.row_load, parent, false);
            return new LoadHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            SynStockTransDetailDTO supplies = listData.get(position);
            ViewHolder h = (ViewHolder) holder;
            h.txt_postion.setText("" + (position + 1) + ".");
            h.txt_name.setText(supplies.getGoodsName() != null ? supplies.getGoodsName() : "");
            h.txt_mavattu.setText(supplies.getGoodsCode() != null ? supplies.getGoodsCode() : "");
            h.txt_donvitinh.setText(supplies.getGoodsUnitName() != null ? supplies.getGoodsUnitName() : "");
            h.txt_soluong.setText(supplies.getAmountOrder() != null ? supplies.getAmountOrder().intValue() + "" : "");
//            h.edt_soluongthucnhan.setText(supplies.getAmountReal() != null ? supplies.getAmountReal().intValue() + "" : "");
//            h.edt_ngaythucnhan.setText(supplies.getRealRecieveDate() != null ? supplies.getRealRecieveDate().toString() : "");


            if(synStockTransDTO.getStockType().contains("A")){
                h.bill_detail.setOnClickListener(view -> {
                    h.setExpand(!h.isExpand());
                    if(h.isExpand()) {
                        h.bill_detail_expand.setVisibility(View.VISIBLE);
                        h.ic_expand.setImageResource(R.drawable.ic_expand_less_24dp);
                    } else {
                        h.bill_detail_expand.setVisibility(View.GONE);
                        h.ic_expand.setImageResource(R.drawable.ic_expand_more_24dp);
                    }
                });
                SysUserRequest sysUserRequest = VConstant.getUser();
                Long loginSysUserId = sysUserRequest.getSysUserId();
                Long receiverId;
                if (synStockTransDTO.getReceiverId() != null) {
                    receiverId = synStockTransDTO.getReceiverId();
                } else {
                    receiverId = 1L;
                }
                if((synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("0") && receiverId == 1) ||
                        (synStockTransDTO.getLastShipperId().compareTo(receiverId) != 0 && receiverId.compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("0"))
                        || (synStockTransDTO.state.trim().equals("2") && synStockTransDTO.confirm.trim().equals("0") && synStockTransDTO.lastShipperId.compareTo(loginSysUserId) == 0)
                        || (synStockTransDTO.confirm.trim().equals("1") && synStockTransDTO.state.trim().equals("0")&& receiverId.compareTo(loginSysUserId) == 0)){
                    h.edt_soluongthucnhan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String amountReal = h.edt_soluongthucnhan.getText().toString();
                            if(amountReal.trim().isEmpty()){
                                supplies.setAmountReal(supplies.getAmountOrder());
                            } else supplies.setAmountReal(Double.parseDouble(amountReal));
                            if(supplies.getAmountReal() > supplies.getAmountOrder() || supplies.getAmountReal() <= 0){
                                h.edt_soluongthucnhan.setError("Số lượng thực nhận không hợp lệ");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
                else if ((synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("1") && receiverId == 1) ||
                        (receiverId != 1 && synStockTransDTO.getState().trim().equals("2") && synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0) ||
                        (synStockTransDTO.getLastShipperId().compareTo(receiverId) == 0 && synStockTransDTO.getState().trim().equals("1"))) {
                    // có bàn giao và quay lại
//                rlForTheRest.setVisibility(View.VISIBLE);
                    h.edt_soluongthucnhan.setEnabled(false);
                    h.edt_soluongthucnhan.setText(supplies.getAmountReal() != null ? supplies.getAmountReal().intValue() + "" : "");

                } else if ((synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && (synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("0") || synStockTransDTO.getConfirm().trim().equals("2"))
                        && receiverId != 1 && synStockTransDTO.getState().trim().equals("0") && receiverId.compareTo(loginSysUserId) != 0 && !synStockTransDTO.getConfirm().trim().equals("0")) ||
                        (synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("1") && receiverId.compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("2")) ||
                        (synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && receiverId == 1 && synStockTransDTO.getConfirm().trim().equals("2"))
                        || (synStockTransDTO.state.trim().equals("2") && synStockTransDTO.confirm.trim().equals("0") && receiverId.compareTo(loginSysUserId) == 0)
                        || receiverId != 1) {
                    h.edt_soluongthucnhan.setEnabled(false);
                    h.edt_soluongthucnhan.setText(supplies.getAmountReal() != null ? supplies.getAmountReal().intValue() + "" : "");
                }
            } else {
                h.llSoLuongThucNhan.setVisibility(View.GONE);
                h.ic_expand.setImageResource(R.drawable.ic_navigate_next);
                h.bill_detail.setOnClickListener(v -> {
                    onClickDetailImplement.onClickDetails(supplies);
                });
            }


        } else if (holder instanceof LoadHolder)

        {
            LoadHolder load = (LoadHolder) holder;
            load.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    public void setLoaded() {
        isLoading = false;
    }


    static class LoadHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_postion, txt_name, txt_mavattu, txt_donvitinh, txt_soluong;
        private EditText edt_soluongthucnhan;
        private LinearLayout lnSupplies, bill_detail, bill_detail_expand, llSoLuongThucNhan;
        private ImageView ic_expand;
        private boolean isExpand;

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            txt_postion = itemView.findViewById(R.id.txt_postion);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_mavattu = itemView.findViewById(R.id.txt_mavattu);
            txt_donvitinh = itemView.findViewById(R.id.txt_donvitinh);
            txt_soluong = itemView.findViewById(R.id.txt_soluong);
            edt_soluongthucnhan = itemView.findViewById(R.id.edt_soluongthucnhan);
            bill_detail = itemView.findViewById(R.id.bill_detail);
            bill_detail_expand = itemView.findViewById(R.id.bill_detail_expand);
            ic_expand = itemView.findViewById(R.id.ic_expand);
            lnSupplies = itemView.findViewById(R.id.ln_implement);
            llSoLuongThucNhan = itemView.findViewById(R.id.ll_soluongthucnhan);
        }
    }

    public interface OnClickDetailImplement {
        void onClickDetails(SynStockTransDetailDTO data);
    }

}
