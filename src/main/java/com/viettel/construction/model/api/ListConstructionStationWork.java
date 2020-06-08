package com.viettel.construction.model.api;

import java.util.List;

/**
 * Created by manro on 3/12/2018.
 */

public class ListConstructionStationWork {
    private ResultInfo resultInfo;
    private List<ConstructionStationWorkItem> listConstructionStationWorkItem;

    public ListConstructionStationWork(ResultInfo resultInfo, List<ConstructionStationWorkItem> listConstructionStationWorkItem) {
        this.resultInfo = resultInfo;
        this.listConstructionStationWorkItem = listConstructionStationWorkItem;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<ConstructionStationWorkItem> getListConstructionStationWorkItem() {
        return listConstructionStationWorkItem;
    }

    public void setListConstructionStationWorkItem(List<ConstructionStationWorkItem> listConstructionStationWorkItem) {
        this.listConstructionStationWorkItem = listConstructionStationWorkItem;
    }
}
