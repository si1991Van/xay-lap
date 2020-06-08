package com.viettel.construction.screens.menu_history_vttb;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.history.HandOverHistoryDTORespone;
import com.viettel.construction.model.api.history.StTransactionDTO;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class TabItemReceiver_HandOverFragment
        extends FragmentListBase<StTransactionDTO, HandOverHistoryDTORespone> {


    private boolean isReceiver;

    public void setReceiver(boolean receiver) {
        isReceiver = receiver;
    }
    
    @Override
    public int getLayoutID() {
        return R.layout.fragment_tiep_nhan_vttb;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        TabItemReceiver_HandOverAdapter adapter = new TabItemReceiver_HandOverAdapter(getContext(), listData);
        adapter.setReceiver(isReceiver);
        return adapter;
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<StTransactionDTO> dataSearch(String keyWord) {

        String input = StringUtil.removeAccentStr(keyWord).toLowerCase().trim();
        List<StTransactionDTO> dataSearch = new ArrayList<>();
        for (StTransactionDTO dto : listData) {
            String billCode, constructionCode;
            if (dto.getStockTransCode() != null) {
                billCode = dto.getStockTransCode().toLowerCase();
            } else {
                billCode = "";
            }
            if (dto.getStockTransConstructionCode() != null) {
                constructionCode = dto.getStockTransConstructionCode().toLowerCase();
            } else {
                constructionCode = "";
            }
            if (billCode.contains(input) || constructionCode.contains(input))
                dataSearch.add(dto);
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, StTransactionDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        if (isReceiver) {
            return getString(R.string.ReceiverTitle);
        }
        return getString(R.string.HandOverTitle);
    }

    @Override
    public int getMenuID() {
        return R.menu.menu_history;
    }

    @Override
    public List<StTransactionDTO> menuItemClick(int menuItem) {
        List<StTransactionDTO> data;
        switch (menuItem) {
            default://case R.id.all_history:
                data = listData;
                break;
            case R.id.wait_history:
                data = filterByStatus("0");
                break;
            case R.id.received_history:
                data = filterByStatus("1");
                break;
            case R.id.refuse_history:
                data = filterByStatus("2");
                break;
        }
        return data;
    }

    private List<StTransactionDTO> filterByStatus(String status) {
        List<StTransactionDTO> dataSearch =
                Observable.from(listData).filter(x -> x.getConfirm() != null && x.getConfirm().contains(status))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, StTransactionDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<StTransactionDTO> getResponseData(HandOverHistoryDTORespone result) {
        HandOverHistoryDTORespone respone = HandOverHistoryDTORespone.class.cast(result);
        List<StTransactionDTO> data = new ArrayList<>();
        if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (isReceiver)
                data = respone.getListStTransactionReceivePagesDTO();
            else
                data = respone.getListStTransactionHandoverPagesDTO();
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_HANDOVER_HISTORY;
    }

    @Override
    public Class<HandOverHistoryDTORespone> responseEntityClass() {
        return HandOverHistoryDTORespone.class;
    }

    @Override
    public void onItemRecyclerViewclick(StTransactionDTO item) {
        Fragment frag = new HandOver_Receiver_Detail_Level1Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("StTransactionDTO", item);
        frag.setArguments(bundle);
        commitChange(frag, true);
    }

    @Override
    public void onItemRecyclerViewLongclick(StTransactionDTO item) {

    }
}
