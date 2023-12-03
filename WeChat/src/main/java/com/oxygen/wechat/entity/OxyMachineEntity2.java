package com.oxygen.wechat.entity;

import java.util.Date;

public class OxyMachineEntity2 {
    private String recordId;//记录标号

    private String omId;//制氧机编号

    //private String omDate;//制氧机出厂时间

    private Integer omStatus;//制氧机当前状态0出厂、1驿站、2送出、3送达、4正使用、5归还途中、6归还入库、7被游客购买、8驿站匹配成功，等待送出。

    private Double Latitude;

    private Double Longitude;

    private String nowTime;//制氧机上传信息时间

    private String omUser;//制氧机的用户


    public OxyMachineEntity2(String recordId, String omId, Integer omStatus, Double latitude, Double longitude, String nowTime, String omUser) {
        this.recordId = recordId;
        this.omId = omId;
        this.omStatus = omStatus;
        Latitude = latitude;
        Longitude = longitude;
        this.nowTime = nowTime;
        this.omUser = omUser;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getOmUser() {
        return omUser;
    }

    public void setOmUser(String omUser) {
        this.omUser = omUser;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOmId() {
        return omId;
    }

    public void setOmId(String omId) {
        this.omId = omId;
    }

    public Integer getOmStatus() {
        return omStatus;
    }

    public void setOmStatus(Integer omStatus) {
        this.omStatus = omStatus;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
