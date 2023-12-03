package com.oxygen.wechat.entity;

import java.util.Date;

public class WtRecordEntity {
    private String wrId;//记录编号

    private String wrOwner;//记录发起者

    private String wrAction;//行为动作

    private String wrResult;//动作结果

    private Date wrDate;//动作时间

    public WtRecordEntity(String wrId, String wrOwner, String wrAction, String wrResult, Date wrDate) {
        this.wrId = wrId;
        this.wrOwner = wrOwner;
        this.wrAction = wrAction;
        this.wrResult = wrResult;
        this.wrDate = wrDate;
    }

    @Override
    public String toString() {
        return "WtRecordEntity{" +
                "wrId='" + wrId + '\'' +
                ", wrOwner='" + wrOwner + '\'' +
                ", wrAction='" + wrAction + '\'' +
                ", wrResult='" + wrResult + '\'' +
                ", wrDate=" + wrDate +
                '}';
    }

    public String getWrId() {
        return wrId;
    }

    public void setWrId(String wrId) {
        this.wrId = wrId;
    }

    public String getWrOwner() {
        return wrOwner;
    }

    public void setWrOwner(String wrOwner) {
        this.wrOwner = wrOwner;
    }

    public String getWrAction() {
        return wrAction;
    }

    public void setWrAction(String wrAction) {
        this.wrAction = wrAction;
    }

    public String getWrResult() {
        return wrResult;
    }

    public void setWrResult(String wrResult) {
        this.wrResult = wrResult;
    }

    public Date getWrDate() {
        return wrDate;
    }

    public void setWrDate(Date wrDate) {
        this.wrDate = wrDate;
    }
}
