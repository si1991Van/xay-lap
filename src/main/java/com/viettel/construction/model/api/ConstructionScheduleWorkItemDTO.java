package com.viettel.construction.model.api;

public class ConstructionScheduleWorkItemDTO extends ConstructionScheduleItemDTO{

    private String taskName;

    private String workItemName;
    private String description;
    private String workName;
    private String completeDate;
    private String constructionState;
    private String note;
    private Long constructionTaskId;
    private String path;
    private String type;
    private String taskOder;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }



    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getConstructionState() {
        return constructionState;
    }

    public void setConstructionState(String constructionState) {
        this.constructionState = constructionState;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getConstructionTaskId() {
        return constructionTaskId;
    }

    public void setConstructionTaskId(Long constructionTaskId) {
        this.constructionTaskId = constructionTaskId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskOder() {
        return taskOder;
    }

    public void setTaskOder(String taskOder) {
        this.taskOder = taskOder;
    }


}
