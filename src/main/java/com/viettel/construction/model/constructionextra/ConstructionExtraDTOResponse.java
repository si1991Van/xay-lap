package com.viettel.construction.model.constructionextra;

import com.viettel.construction.model.api.ResultInfo;

import java.util.List;



public class ConstructionExtraDTOResponse {

	private ResultInfo resultInfo;
	private ConstructionExtraDTO data;

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	public ConstructionExtraDTO getData() {
		return data;
	}

	public void setData(ConstructionExtraDTO data) {
		this.data = data;
	}
}
