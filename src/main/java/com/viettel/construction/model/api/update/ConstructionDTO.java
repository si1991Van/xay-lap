package com.viettel.construction.model.api.update;

/**
 * Created by manro on 3/19/2018.
 */

public class ConstructionDTO {
    private long constructionTaskId;
    private double completePercent;
    private String description;
    private long catTaskId;
    private long performerId;
    private String startDate;
    private String endDate;
    private String taskName;

    public ConstructionDTO(long constructionTaskId, double completePercent, String description, long catTaskId, long performerId, String startDate, String endDate, String taskName) {
        this.constructionTaskId = constructionTaskId;
        this.completePercent = completePercent;
        this.description = description;
        this.catTaskId = catTaskId;
        this.performerId = performerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskName = taskName;
    }
}
