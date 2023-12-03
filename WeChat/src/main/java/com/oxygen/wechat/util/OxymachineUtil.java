package com.oxygen.wechat.util;

public class OxymachineUtil {
    //制氧机当前状态0出厂、1驿站、2送出、3送达、4正使用、5归还途中、6归还入库、7被游客购买、8驿站匹配成功
    public static final Integer Leave_Factory = 0;//出厂

    public static final Integer Stage = 1;//驿站

    public static final Integer Send_Out = 2;//送出

    public static final Integer Send_Over = 3;//送达

    public static final Integer Using = 4;//正使用

    public static final Integer Back_Way = 5;//归还途中

    public static final Integer Return_Over = 6;//归还入库

    public static final Integer Purchased = 7;//被游客购买

    public static final Integer Successfully_Matched = 8;//驿站匹配成功

    public static final Integer Abnormal = 9;//异常状态
}
