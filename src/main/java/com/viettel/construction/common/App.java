package com.viettel.construction.common;


import android.content.Context;
import androidx.multidex.MultiDexApplication;
import android.util.Log;

import com.viettel.construction.server.util.NetworkUtil;

import java.io.File;

public class App extends MultiDexApplication {
    private static App instance;
    public static Context mContext;
    public String author = "";
    private boolean needUpdate;
    private boolean needUpdateAfterContinue;
    private boolean needUpdateAfterCreateNewWork;
    private boolean needUpdateBtn;
    private boolean needUpdateBtnSupplier;
    private boolean needUpdateCompleteCategory;
    private boolean needUpdateAfterSaveDetail;
    private boolean needUpdateAfterRejectedBill;
    private boolean needUpdateAfterConfirmBill;
    private boolean needCallGetDataFromDeliveryBill;
    private boolean needUpdateIssue;
    private boolean needUpdateEntangle;
    private boolean needUpdateAcceptance;
    private boolean needUpdateRefund;
    private boolean needUpdateHandOverReceiver;
    private int tabIndexConstructionManagement;
    private int tabIndexHistoryHandOver;
    private boolean needUpdateBGMB;

    public boolean isNeedUpdateBGMB() {
        return needUpdateBGMB;
    }

    public void setNeedUpdateBGMB(boolean needUpdateBGMB) {
        this.needUpdateBGMB = needUpdateBGMB;
    }

    private boolean isTested;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }

    public boolean isNeedUpdateHandOverReceiver() {
        return needUpdateHandOverReceiver;
    }

    public void setNeedUpdateHandOverReceiver(boolean needUpdateHandOverReceiver) {
        this.needUpdateHandOverReceiver = needUpdateHandOverReceiver;
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public boolean isTested() {
        return isTested;
    }

    public void setTested(boolean tested) {
        isTested = tested;
    }

    public boolean isNeedUpdateRefund() {
        return needUpdateRefund;
    }

    public void setNeedUpdateRefund(boolean needUpdateRefund) {
        this.needUpdateRefund = needUpdateRefund;
    }

    public boolean isNeedUpdateAcceptance() {
        return needUpdateAcceptance;
    }

    public void setNeedUpdateAcceptance(boolean needUpdateAcceptance) {
        this.needUpdateAcceptance = needUpdateAcceptance;
    }

    public void setNeedUpdateAfterContinue(boolean needUpdateAfterContinue) {
        this.needUpdateAfterContinue = needUpdateAfterContinue;
    }

    public boolean isNeedUpdateAfterContinue() {
        return needUpdateAfterContinue;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public int returnTabIndexHistoryHandOver() {
        return tabIndexHistoryHandOver;
    }

    public void setTabIndexHistoryHandOver(int index) {
        this.tabIndexHistoryHandOver = index;
    }

    public int getTabIndexConstructionManagement() {
        return tabIndexConstructionManagement;
    }

    public void setIndextTabConstructionManagement(int index) {
        this.tabIndexConstructionManagement = index;
    }

    public boolean isNeedUpdateIssue() {
        return needUpdateIssue;
    }

    public void setNeedUpdateIssue(boolean needUpdateIssue) {
        this.needUpdateIssue = needUpdateIssue;
    }

    public boolean isNeedUpdateEntangle() {
        return needUpdateEntangle;
    }

    public void setNeedUpdateEntangle(boolean needUpdateEntangle) {
        this.needUpdateEntangle = needUpdateEntangle;
    }

    public boolean isNeedCallGetDataFromDeliveryBill() {
        return needCallGetDataFromDeliveryBill;
    }

    public void setNeedCallGetDataFromDeliveryBill(boolean needCallGetDataFromDeliveryBill) {
        this.needCallGetDataFromDeliveryBill = needCallGetDataFromDeliveryBill;
    }

    public boolean isNeedUpdateAfterConfirmBill() {
        return needUpdateAfterConfirmBill;
    }

    public void setNeedUpdateAfterConfirmBill(boolean needUpdateAfterConfirmBill) {
        this.needUpdateAfterConfirmBill = needUpdateAfterConfirmBill;
    }

    public boolean isNeedUpdateAfterRejectedBill() {
        return needUpdateAfterRejectedBill;
    }

    public void  setNeedUpdateAfterRejectedBill(boolean needUpdateAfterRejectedBill) {
        this.needUpdateAfterRejectedBill = needUpdateAfterRejectedBill;
    }

    public boolean isNeedUpdateBtnSupplier() {
        return needUpdateBtnSupplier;
    }

    public void setNeedUpdateBtnSupplier(boolean needUpdateBtnSupplier) {
        this.needUpdateBtnSupplier = needUpdateBtnSupplier;
    }

    public boolean isNeedUpdateAfterSaveDetail() {
        return needUpdateAfterSaveDetail;
    }

    public void setNeedUpdateAfterSaveDetail(boolean needUpdateAfterSaveDetail) {
        this.needUpdateAfterSaveDetail = needUpdateAfterSaveDetail;
    }

    public boolean isNeedUpdateCompleteCategory() {
        return needUpdateCompleteCategory;
    }

    public void setNeedUpdateCompleteCategory(boolean needUpdateCompleteCategory) {
        this.needUpdateCompleteCategory = needUpdateCompleteCategory;
    }

    public boolean isNeedUpdateAfterCreateNewWork() {
        return needUpdateAfterCreateNewWork;
    }

    public void setNeedUpdateAfterCreateNewWork(boolean needUpdateAfterCreateNewWork) {
        this.needUpdateAfterCreateNewWork = needUpdateAfterCreateNewWork;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean isNeedUpdateBtn() {
        return needUpdateBtn;
    }

    public void setNeedUpdateBtn(boolean needUpdateBtn) {
        this.needUpdateBtn = needUpdateBtn;
    }


    public static boolean hasNetwork() {
        //error         ========================
        return NetworkUtil.isNetworkAvailable(mContext);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        SugarContext.terminate();
    }
}
