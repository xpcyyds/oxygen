package com.oxygen.wechat.entity;

import java.util.Date;

public class ManagerEntity {
    private Integer mgId;//记录编号

    private String mgName;//姓名

    private String mgMobile;//手机号

    private String mgPass;//密码

    private String mgStatus;//地位

    @Override
    public String toString() {
        return "ManagerEntity{" +
                "mgId=" + mgId +
                ", mgName='" + mgName + '\'' +
                ", mgMobile='" + mgMobile + '\'' +
                ", mgPass='" + mgPass + '\'' +
                ", mgStatus='" + mgStatus + '\'' +
                '}';
    }

    public ManagerEntity(Integer mgId, String mgName, String mgMobile, String mgPass, String mgStatus) {
        this.mgId = mgId;
        this.mgName = mgName;
        this.mgMobile = mgMobile;
        this.mgPass = mgPass;
        this.mgStatus = mgStatus;
    }

    public Integer getMgId() {
        return mgId;
    }

    public void setMgId(Integer mgId) {
        this.mgId = mgId;
    }

    public String getMgName() {
        return mgName;
    }

    public void setMgName(String mgName) {
        this.mgName = mgName;
    }

    public String getMgMobile() {
        return mgMobile;
    }

    public void setMgMobile(String mgMobile) {
        this.mgMobile = mgMobile;
    }

    public String getMgPass() {
        return mgPass;
    }

    public void setMgPass(String mgPass) {
        this.mgPass = mgPass;
    }

    public String getMgStatus() {
        return mgStatus;
    }

    public void setMgStatus(String mgStatus) {
        this.mgStatus = mgStatus;
    }
}
