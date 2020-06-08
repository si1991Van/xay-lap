package com.viettel.construction.model.api;

import java.util.List;

/**
 * Created by manro on 3/17/2018.
 */

public class ListImageResult {
    List<ListImageResult> listImageResults;

    public List<ListImageResult> getListImageResults() {
        return listImageResults;
    }

    public void setListImageResults(List<ListImageResult> listImageResults) {
        this.listImageResults = listImageResults;
    }

    public ListImageResult(List<ListImageResult> listImageResults) {

        this.listImageResults = listImageResults;
    }
}
