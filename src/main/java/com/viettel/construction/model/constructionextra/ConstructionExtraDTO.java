package com.viettel.construction.model.constructionextra;

import java.util.Date;


public class ConstructionExtraDTO {

    private long construction_id;

    private Date handover_date_build;
    private String handover_note_build;

    //
    private Date handover_date_electric;

    private String handover_note_electric;
    private String is_building_permit;
    //
    private Date building_permit_date;
    //
    private Date blueprint_date;
    private String status;

    //
    private Date updated_date;
    private Long updated_user_id;
    private Long updated_group_id;

    //
    private Date starting_date;


    public long getConstruction_id() {
        return construction_id;
    }

    public void setConstruction_id(long construction_id) {
        this.construction_id = construction_id;
    }

    public Date getHandover_date_build() {
        return handover_date_build;
    }

    public void setHandover_date_build(Date handover_date_build) {
        this.handover_date_build = handover_date_build;
    }

    public String getHandover_note_build() {
        return handover_note_build;
    }

    public void setHandover_note_build(String handover_note_build) {
        this.handover_note_build = handover_note_build;
    }

    public Date getHandover_date_electric() {
        return handover_date_electric;
    }

    public void setHandover_date_electric(Date handover_date_electric) {
        this.handover_date_electric = handover_date_electric;
    }

    public String getHandover_note_electric() {
        return handover_note_electric;
    }

    public void setHandover_note_electric(String handover_note_electric) {
        this.handover_note_electric = handover_note_electric;
    }

    public String getIs_building_permit() {
        return is_building_permit;
    }

    public void setIs_building_permit(String is_building_permit) {
        this.is_building_permit = is_building_permit;
    }

    public Date getBuilding_permit_date() {
        return building_permit_date;
    }

    public void setBuilding_permit_date(Date building_permit_date) {
        this.building_permit_date = building_permit_date;
    }

    public Date getBlueprint_date() {
        return blueprint_date;
    }

    public void setBlueprint_date(Date blueprint_date) {
        this.blueprint_date = blueprint_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public Long getUpdated_user_id() {
        return updated_user_id;
    }

    public void setUpdated_user_id(Long updated_user_id) {
        this.updated_user_id = updated_user_id;
    }

    public Long getUpdated_group_id() {
        return updated_group_id;
    }

    public void setUpdated_group_id(Long updated_group_id) {
        this.updated_group_id = updated_group_id;
    }

    public Date getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(Date starting_date) {
        this.starting_date = starting_date;
    }
}
