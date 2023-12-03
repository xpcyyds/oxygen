package com.oxygen.wechat.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "busistatus")
public class BusiStatusUtil {
    //商品管理
    public static final Integer Lease_Order = 0;//租借下单

    public static final Integer Service = 1;//送达

    public static final Integer Using = 2;//使用中

    public static final Integer Return_Order  = 3;//归还下单

    public static final Integer Return_Succeeded = 4;//归还成功

    public static final Integer Tourists_Buy = 5;//游客购买

    public static final Integer All_Successful = 6;//归还成功且已付费成功

    public static final Integer Matching_Pttion = 7;//匹配驿站

    public static final Integer Scan_Code_Bus = 8;//扫码枪扫码

    public static final Integer Match_Runner = 9;//匹配跑腿

    public static final Integer Direct_Purchase = 10;//直接购买

    public static final Integer Return_Order_Succeeded = 11;//归还快递下单成功

    public static final Integer Return_Package_picked = 12;//归还快递已经取件

    public static final Integer Return_Delivered_Succeeded = 13;//归还快递已经派送成功

    //public static final Integer Return_Matching_Pttion = 14;//归还匹配驿站成功
}
