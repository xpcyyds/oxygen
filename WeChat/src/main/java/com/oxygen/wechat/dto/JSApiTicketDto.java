package com.oxygen.wechat.dto;

public class JSApiTicketDto {
    private String jsApiTicket;
    private long expireTime;//超时时间

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = System.currentTimeMillis() + expireTime * 1000;
    }

    /**
     * 判断jsApiTicket是否超时
     * @return
     */
    public boolean isExpired(){
        return System.currentTimeMillis()>this.expireTime;
    }
}
