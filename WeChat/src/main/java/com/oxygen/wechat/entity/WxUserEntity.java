package com.oxygen.wechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("wx_user")
public class WxUserEntity {

    @TableId("wx_user_id")
    private String wxUserId;//唯一编号

    private String openId;//游客微信名

    public WxUserEntity() {
    }

    public WxUserEntity(String wxUserId, String openId) {
        this.wxUserId = wxUserId;
        this.openId = openId;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
