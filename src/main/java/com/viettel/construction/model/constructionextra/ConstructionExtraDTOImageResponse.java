package com.viettel.construction.model.constructionextra;

import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ResultInfo;

import java.util.List;



public class ConstructionExtraDTOImageResponse {

	private List<ConstructionImageInfo> listImage;
	private ResultInfo resultInfo;

	public List<ConstructionImageInfo> getListImage() {
		return listImage;
	}

	public void setListImage(List<ConstructionImageInfo> listImage) {
		this.listImage = listImage;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

}
