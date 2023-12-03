package com.oxygen.wechat.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class WxMpXmlMessage {

    private String toUserName;
    private String fromUserName;
    private long createTime;
    private String msgType;
    private String msgEvent;
    private String content;
    private String eventKey;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgEvent() {
        return msgEvent;
    }

    public void setMsgEvent(String msgEvent) {
        this.msgEvent = msgEvent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
