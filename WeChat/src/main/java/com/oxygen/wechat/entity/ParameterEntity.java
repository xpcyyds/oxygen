package com.oxygen.wechat.entity;

public class ParameterEntity {

    private String pmId;//记录编号

    private String pmItem;//类别

    private String pmValueO;//类别对应的值1

    private String pmValueT;//类别对应的值2

    private String pmValueS;//类别对应的值3

    private String pmValueF;//类别对应的值4

    private String pmValueI;//类别对应的值5

    private String pmValueX;//类别对应的值6

    @Override
    public String toString() {
        return "ParameterEntity{" +
                "pmId='" + pmId + '\'' +
                ", pmItem='" + pmItem + '\'' +
                ", pmValueO='" + pmValueO + '\'' +
                ", pmValueT='" + pmValueT + '\'' +
                ", pmValueS='" + pmValueS + '\'' +
                ", pmValueF='" + pmValueF + '\'' +
                ", pmValueI='" + pmValueI + '\'' +
                ", pmValueX='" + pmValueX + '\'' +
                '}';
    }

    public ParameterEntity(String pmValueO, String pmValueT, String pmValueS, String pmValueF, String pmValueI, String pmValueX) {
        this.pmValueO = pmValueO;
        this.pmValueT = pmValueT;
        this.pmValueS = pmValueS;
        this.pmValueF = pmValueF;
        this.pmValueI = pmValueI;
        this.pmValueX = pmValueX;
    }

    public String getPmValueF() {
        return pmValueF;
    }

    public void setPmValueF(String pmValueF) {
        this.pmValueF = pmValueF;
    }

    public String getPmValueI() {
        return pmValueI;
    }

    public void setPmValueI(String pmValueI) {
        this.pmValueI = pmValueI;
    }

    public String getPmValueX() {
        return pmValueX;
    }

    public void setPmValueX(String pmValueX) {
        this.pmValueX = pmValueX;
    }

    public ParameterEntity(String pmValueO, String pmValueT) {
        this.pmValueO = pmValueO;
        this.pmValueT = pmValueT;
    }

    public ParameterEntity(String pmValueO, String pmValueT, String pmValueS) {
        this.pmValueO = pmValueO;
        this.pmValueT = pmValueT;
        this.pmValueS = pmValueS;
    }

    public ParameterEntity(String pmId, String pmItem, String pmValueO, String pmValueT, String pmValueS) {
        this.pmId = pmId;
        this.pmItem = pmItem;
        this.pmValueO = pmValueO;
        this.pmValueT = pmValueT;
        this.pmValueS = pmValueS;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmItem() {
        return pmItem;
    }

    public void setPmItem(String pmItem) {
        this.pmItem = pmItem;
    }

    public String getPmValueO() {
        return pmValueO;
    }

    public void setPmValueO(String pmValueO) {
        this.pmValueO = pmValueO;
    }

    public String getPmValueT() {
        return pmValueT;
    }

    public void setPmValueT(String pmValueT) {
        this.pmValueT = pmValueT;
    }

    public String getPmValueS() {
        return pmValueS;
    }

    public void setPmValueS(String pmValueS) {
        this.pmValueS = pmValueS;
    }
}
