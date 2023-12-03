package com.oxygen.wechat.entity;

import java.util.Date;

public class OxyMachineEntity {
    private String recordId;//记录标号

    private String omId;//制氧机编号

    private Date omDate;//制氧机出厂时间

    private Integer omStatus;//制氧机当前状态0出厂、1驿站、2送出、3送达、4正使用、5归还途中、6归还入库、7被游客购买、8驿站匹配成功，等待送出。

    public OxyMachineEntity(String recordId, String omId, Date omDate, Integer omStatus) {
        this.recordId = recordId;
        this.omId = omId;
    }
 
    @Override
    public String toString() {
        return "OxyMachineEntity{" +
                "recordId='" + recordId + '\'' +
                ", omId='" + omId + '\'' +
                ", omDate=" + omDate +
                ", omStatus=" + omStatus +
                '}';
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

    public Date getOmDate() {
        return omDate;
    }

    public void setOmDate(Date omDate) {
        this.omDate = omDate;
    }

    public Integer getOmStatus() {
        return omStatus;
    }

    public void setOmStatus(Integer omStatus) {
        this.omStatus = omStatus;
    }
}
