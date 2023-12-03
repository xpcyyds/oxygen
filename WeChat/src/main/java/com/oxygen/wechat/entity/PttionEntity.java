package com.oxygen.wechat.entity;

public class PttionEntity {

    private String pttionId;//驿站编号

    private String pttionName;//驿站名称

    private String pttionPro;//所在省份

    private String pttionCity;//所在城市

    private String pttionArea;//所在区县

    private String pttionTown;//所在镇

    private String pttionAdd;//具体地址

    private String pttionTel;//联系电话

    private Integer pttionSFee;//服务费标准 每发出一件多少钱

    private Integer pttionRFee;//服务费标准 每回收一件多少钱

    private Integer pttionExtend1;//库存数量

    private Integer pttionExtend2;//配送数量

    private Integer pttionExtend3;//等待处理数

    private Integer pttionExtend4;//库存比正常为0，不正常为1

    @Override
    public String toString() {
        return "PttionEntity{" +
                "pttionId='" + pttionId + '\'' +
                ", pttionName='" + pttionName + '\'' +
                ", pttionPro='" + pttionPro + '\'' +
                ", pttionCity='" + pttionCity + '\'' +
                ", pttionArea='" + pttionArea + '\'' +
                ", pttionTown='" + pttionTown + '\'' +
                ", pttionAdd='" + pttionAdd + '\'' +
                ", pttionTel='" + pttionTel + '\'' +
                ", pttionSFee=" + pttionSFee +
                ", pttionRFee=" + pttionRFee +
                ", pttionExtend1=" + pttionExtend1 +
                ", pttionExtend2=" + pttionExtend2 +
                ", pttionExtend3=" + pttionExtend3 +
                ", pttionExtend4=" + pttionExtend4 +
                '}';
    }

    public Integer getPttionExtend3() {
        return pttionExtend3;
    }

    public void setPttionExtend3(Integer pttionExtend3) {
        this.pttionExtend3 = pttionExtend3;
    }

    public Integer getPttionExtend4() {
        return pttionExtend4;
    }

    public void setPttionExtend4(Integer pttionExtend4) {
        this.pttionExtend4 = pttionExtend4;
    }

    public Integer getPttionExtend1() {
        return pttionExtend1;
    }

    public void setPttionExtend1(Integer pttionExtend1) {
        this.pttionExtend1 = pttionExtend1;
    }

    public Integer getPttionExtend2() {
        return pttionExtend2;
    }

    public void setPttionExtend2(Integer pttionExtend2) {
        this.pttionExtend2 = pttionExtend2;
    }

    public String getPttionId() {
        return pttionId;
    }

    public void setPttionId(String pttionId) {
        this.pttionId = pttionId;
    }

    public String getPttionName() {
        return pttionName;
    }

    public void setPttionName(String pttionName) {
        this.pttionName = pttionName;
    }

    public String getPttionPro() {
        return pttionPro;
    }

    public void setPttionPro(String pttionPro) {
        this.pttionPro = pttionPro;
    }

    public String getPttionCity() {
        return pttionCity;
    }

    public void setPttionCity(String pttionCity) {
        this.pttionCity = pttionCity;
    }

    public String getPttionArea() {
        return pttionArea;
    }

    public void setPttionArea(String pttionArea) {
        this.pttionArea = pttionArea;
    }

    public String getPttionTown() {
        return pttionTown;
    }

    public void setPttionTown(String pttionTown) {
        this.pttionTown = pttionTown;
    }

    public String getPttionAdd() {
        return pttionAdd;
    }

    public void setPttionAdd(String pttionAdd) {
        this.pttionAdd = pttionAdd;
    }

    public String getPttionTel() {
        return pttionTel;
    }

    public void setPttionTel(String pttionTel) {
        this.pttionTel = pttionTel;
    }

    public Integer getPttionSFee() {
        return pttionSFee;
    }

    public void setPttionSFee(Integer pttionSFee) {
        this.pttionSFee = pttionSFee;
    }

    public Integer getPttionRFee() {
        return pttionRFee;
    }

    public void setPttionRFee(Integer pttionRFee) {
        this.pttionRFee = pttionRFee;
    }
}
