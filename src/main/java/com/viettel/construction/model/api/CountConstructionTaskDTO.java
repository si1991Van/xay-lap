package com.viettel.construction.model.api;

/**
 * Created by manro on 3/9/2018.
 */

public class CountConstructionTaskDTO {
    private int slowProcess;
    private int fastProcess;
    private int per_total;
    private int per_didn_perform;
    private int per_executable;
    private int per_complition;
    private int per_stop;
    private int sup_total;
    private int sup_didn_perform;
    private int sup_executable;
    private int sup_complition;
    private int sup_stop;

    private int totalStock;
    private int chotiepnhan;
    private int datiepnhan;
    private int datuchoi;

    public int getBgmbReceived() {
        return bgmbReceived;
    }

    public void setBgmbReceived(int bgmbReceived) {
        this.bgmbReceived = bgmbReceived;
    }

    public int getBgmbNotReceived() {
        return bgmbNotReceived;
    }

    public void setBgmbNotReceived(int bgmbNotReceived) {
        this.bgmbNotReceived = bgmbNotReceived;
    }

    private int bgmbReceived;
    private int bgmbNotReceived;

    public CountConstructionTaskDTO(int slowProcess, int fastProcess, int per_total, int per_didn_perform, int per_executable, int per_complition, int per_stop, int sup_total, int sup_didn_perform, int sup_executable, int sup_complition, int sup_stop) {
        this.slowProcess = slowProcess;
        this.fastProcess = fastProcess;
        this.per_total = per_total;
        this.per_didn_perform = per_didn_perform;
        this.per_executable = per_executable;
        this.per_complition = per_complition;
        this.per_stop = per_stop;
        this.sup_total = sup_total;
        this.sup_didn_perform = sup_didn_perform;
        this.sup_executable = sup_executable;
        this.sup_complition = sup_complition;
        this.sup_stop = sup_stop;
    }

    public int getSlowProcess() {
        return slowProcess;
    }

    public void setSlowProcess(int slowProcess) {
        this.slowProcess = slowProcess;
    }

    public int getFastProcess() {
        return fastProcess;
    }

    public void setFastProcess(int fastProcess) {
        this.fastProcess = fastProcess;
    }

    public int getPer_total() {
        return per_total;
    }

    public void setPer_total(int per_total) {
        this.per_total = per_total;
    }

    public int getPer_didn_perform() {
        return per_didn_perform;
    }

    public void setPer_didn_perform(int per_didn_perform) {
        this.per_didn_perform = per_didn_perform;
    }

    public int getPer_executable() {
        return per_executable;
    }

    public void setPer_executable(int per_executable) {
        this.per_executable = per_executable;
    }

    public int getPer_complition() {
        return per_complition;
    }

    public void setPer_complition(int per_complition) {
        this.per_complition = per_complition;
    }

    public int getPer_stop() {
        return per_stop;
    }

    public void setPer_stop(int per_stop) {
        this.per_stop = per_stop;
    }

    public int getSup_total() {
        return sup_total;
    }

    public void setSup_total(int sup_total) {
        this.sup_total = sup_total;
    }

    public int getSup_didn_perform() {
        return sup_didn_perform;
    }

    public void setSup_didn_perform(int sup_didn_perform) {
        this.sup_didn_perform = sup_didn_perform;
    }

    public int getSup_executable() {
        return sup_executable;
    }

    public void setSup_executable(int sup_executable) {
        this.sup_executable = sup_executable;
    }

    public int getSup_complition() {
        return sup_complition;
    }

    public void setSup_complition(int sup_complition) {
        this.sup_complition = sup_complition;
    }

    public int getSup_stop() {
        return sup_stop;
    }

    public void setSup_stop(int sup_stop) {
        this.sup_stop = sup_stop;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getChotiepnhan() {
        return chotiepnhan;
    }

    public void setChotiepnhan(int chotiepnhan) {
        this.chotiepnhan = chotiepnhan;
    }

    public int getDatiepnhan() {
        return datiepnhan;
    }

    public void setDatiepnhan(int datiepnhan) {
        this.datiepnhan = datiepnhan;
    }

    public int getDatuchoi() {
        return datuchoi;
    }

    public void setDatuchoi(int datuchoi) {
        this.datuchoi = datuchoi;
    }
}
