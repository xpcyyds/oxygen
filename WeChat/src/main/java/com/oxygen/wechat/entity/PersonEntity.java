package com.oxygen.wechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;


@TableName("person")
public class PersonEntity {
    @TableId("person_id")
    private String personId;//唯一编号

    private String personWeChat;//游客微信名

    private String personName;//游客姓名

    private String personMobile;//游客手机

    private String personAdd;//游客住址

    private Integer personStatus;//游客状态0,正常，1黑名单。

    private String personPost;//游客邮箱

    private Date personDate;//初次扫码时间

    public PersonEntity(String personId, String personWeChat, String personName, String personMobile, Date personDate) {
        this.personId = personId;
        this.personWeChat = personWeChat;
        this.personName = personName;
        this.personMobile = personMobile;
        this.personDate = personDate;
    }

    public PersonEntity(String personId, String personWeChat, String personName, String personMobile, String personAdd, Integer personStatus, String personPost, Date personDate) {
        this.personId = personId;
        this.personWeChat = personWeChat;
        this.personName = personName;
        this.personMobile = personMobile;
        this.personAdd = personAdd;
        this.personStatus = personStatus;
        this.personPost = personPost;
        this.personDate = personDate;
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "personId='" + personId + '\'' +
                ", personWeChat='" + personWeChat + '\'' +
                ", personName='" + personName + '\'' +
                ", personMobile='" + personMobile + '\'' +
                ", personAdd='" + personAdd + '\'' +
                ", personStatus='" + personStatus + '\'' +
                ", personPost='" + personPost + '\'' +
                ", personDate='" + personDate + '\'' +
                '}';
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonWeChat() {
        return personWeChat;
    }

    public void setPersonWeChat(String personWeChat) {
        this.personWeChat = personWeChat;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonMobile() {
        return personMobile;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    public String getPersonAdd() {
        return personAdd;
    }

    public void setPersonAdd(String personAdd) {
        this.personAdd = personAdd;
    }

    public Integer getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(Integer personStatus) {
        this.personStatus = personStatus;
    }

    public String getPersonPost() {
        return personPost;
    }

    public void setPersonPost(String personPost) {
        this.personPost = personPost;
    }

    public Date getPersonDate() {
        return personDate;
    }

    public void setPersonDate(Date personDate) {
        this.personDate = personDate;
    }
}
