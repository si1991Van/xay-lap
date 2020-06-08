package com.viettel.construction.model.constructionextra;

public class ConstructionExtraDTOImageRequest {

	private String imageType;
	private long constructionID;

	public ConstructionExtraDTOImageRequest() {
	}

	public ConstructionExtraDTOImageRequest(String imageType, long constructionID) {
		this.imageType = imageType;
		this.constructionID = constructionID;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public long getConstructionID() {
		return constructionID;
	}

	public void setConstructionID(long constructionID) {
		this.constructionID = constructionID;
	}

}
