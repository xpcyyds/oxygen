package com.oxygen.wechat.entity;

import java.util.Date;

 
public class BusinessEntity {
    private String busiId;//记录编号

    private String busiWechat;//使用人

    private String busiAdd;//详细地址

    private String busiMid;//制氧机编号

    private String busiPdate;//下单时间

    private String busiAdate;//送达时间

    private String busiBdate;//开始使用时间

    private String busiRdate;//归还下单时间

    private String busiQdate;//取件时间

    private String busiDdate;//到达驿站时间

    private Integer busiStatus;//商业状态0租借下单、1送达、2使用、3归还下单、4归还成功、5游客购买、6归还成功且已付费成功

    private String busiPstation1;//从哪个驿站发出的

    private Integer busiFee;//计费 该订单最后实际计费费用

    private String busiPstation2;//归还到哪个驿站

    private String busiReturnAdd;//归还详细地址

    private String busiName;//姓名

    private String busiNumber;//电话

    private Integer busiIsStatus;

    private String busiArea;//所在地区

    private String busiReturnArea;//归还所在地区

    private String busiExtend1;//记录省市区县

    private String busiExtend2;//记录详细的配送地址

    private String busiExtend5;//姓名和联系电话

    private Integer busiType;//订单类型 0自提/1下单


    public BusinessEntity(String busiId, String busiWechat, String busiAdd, String busiMid, String busiPdate, String busiAdate, String busiBdate, String busiRdate, String busiQdate, String busiDdate, Integer busiStatus, String busiPstation1, Integer busiFee, String busiPstation2, String busiReturnAdd, String busiName, String busiNumber, Integer busiIsStatus, String busiArea, String busiReturnArea, String busiExtend1, String busiExtend2, String busiExtend5,Integer busiType) {
        this.busiId = busiId;
        this.busiWechat = busiWechat;
        this.busiAdd = busiAdd;
        this.busiMid = busiMid;
        this.busiPdate = busiPdate;
        this.busiAdate = busiAdate;
        this.busiBdate = busiBdate;
        this.busiRdate = busiRdate;
        this.busiQdate = busiQdate;
        this.busiDdate = busiDdate;
        this.busiStatus = busiStatus;
        this.busiPstation1 = busiPstation1;
        this.busiFee = busiFee;
        this.busiPstation2 = busiPstation2;
        this.busiReturnAdd = busiReturnAdd;
        this.busiName = busiName;
        this.busiNumber = busiNumber;
        this.busiIsStatus = busiIsStatus;
        this.busiArea = busiArea;
        this.busiReturnArea = busiReturnArea;
        this.busiExtend1 = busiExtend1;
        this.busiExtend2 = busiExtend2;
        this.busiExtend5 = busiExtend5;
        this.busiType = busiType;
    }

    public Integer getBusiType() {
        return busiType;
    }

    public void setBusiType(Integer busiType) {
        this.busiType = busiType;
    }

    public String getBusiExtend1() {
        return busiExtend1;
    }

    public void setBusiExtend1(String busiExtend1) {
        this.busiExtend1 = busiExtend1;
    }

    public String getBusiExtend2() {
        return busiExtend2;
    }

    public void setBusiExtend2(String busiExtend2) {
        this.busiExtend2 = busiExtend2;
    }

    public String getBusiExtend5() {
        return busiExtend5;
    }

    public void setBusiExtend5(String busiExtend5) {
        this.busiExtend5 = busiExtend5;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getBusiWechat() {
        return busiWechat;
    }

    public void setBusiWechat(String busiWechat) {
        this.busiWechat = busiWechat;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public String getBusiNumber() {
        return busiNumber;
    }

    public void setBusiNumber(String busiNumber) {
        this.busiNumber = busiNumber;
    }

    public String getBusiArea() {
        return busiArea;
    }

    public void setBusiArea(String busiArea) {
        this.busiArea = busiArea;
    }

    public String getBusiAdd() {
        return busiAdd;
    }

    public void setBusiAdd(String busiAdd) {
        this.busiAdd = busiAdd;
    }

    public String getBusiReturnArea() {
        return busiReturnArea;
    }

    public void setBusiReturnArea(String busiReturnArea) {
        this.busiReturnArea = busiReturnArea;
    }

    public String getBusiReturnAdd() {
        return busiReturnAdd;
    }

    public void setBusiReturnAdd(String busiReturnAdd) {
        this.busiReturnAdd = busiReturnAdd;
    }

    public String getBusiMid() {
        return busiMid;
    }

    public void setBusiMid(String busiMid) {
        this.busiMid = busiMid;
    }

    public String getBusiPdate() {
        return busiPdate;
    }

    public void setBusiPdate(String busiPdate) {
        this.busiPdate = busiPdate;
    }

    public String getBusiAdate() {
        return busiAdate;
    }

    public void setBusiAdate(String busiAdate) {
        this.busiAdate = busiAdate;
    }

    public String getBusiBdate() {
        return busiBdate;
    }

    public void setBusiBdate(String busiBdate) {
        this.busiBdate = busiBdate;
    }

    public String getBusiRdate() {
        return busiRdate;
    }

    public void setBusiRdate(String busiRdate) {
        this.busiRdate = busiRdate;
    }

    public String getBusiQdate() {
        return busiQdate;
    }

    public void setBusiQdate(String busiQdate) {
        this.busiQdate = busiQdate;
    }

    public String getBusiDdate() {
        return busiDdate;
    }

    public void setBusiDdate(String busiDdate) {
        this.busiDdate = busiDdate;
    }

    public Integer getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(Integer busiStatus) {
        this.busiStatus = busiStatus;
    }

    public Integer getBusiIsStatus() {
        return busiIsStatus;
    }

    public void setBusiIsStatus(Integer busiIsStatus) {
        this.busiIsStatus = busiIsStatus;
    }

    public String getBusiPstation1() {
        return busiPstation1;
    }

    public void setBusiPstation1(String busiPstation1) {
        this.busiPstation1 = busiPstation1;
    }

    public Integer getBusiFee() {
        return busiFee;
    }

    public void setBusiFee(Integer busiFee) {
        this.busiFee = busiFee;
    }

    public String getBusiPstation2() {
        return busiPstation2;
    }

    public void setBusiPstation2(String busiPstation2) {
        this.busiPstation2 = busiPstation2;
    }

}
