package com.viettel.construction.model.api.issue;

import java.util.List;

import com.viettel.construction.model.api.ResultInfo;

public class IssueDTOResponse {
    private List<IssueWorkItemDTO> listIssueEntityDTO;
    private ResultInfo resultInfo;
    private long type;

    public List<IssueWorkItemDTO> getListIssueEntityDTO() {
        return listIssueEntityDTO;
    }

    public void setListIssueEntityDTO(List<IssueWorkItemDTO> listIssueEntityDTO) {
        this.listIssueEntityDTO = listIssueEntityDTO;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
