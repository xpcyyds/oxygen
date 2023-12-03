package com.oxygen.wechat.dto;

public class AccessTokenDto {
    private String accessToken;
    private long expireTime;//超时时间

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireIn) {
        this.expireTime = System.currentTimeMillis() + expireIn * 1000;
        System.out.println("this.expireTime1==="+this.expireTime);
    }

    /**
     * 判断accessToken是否超时
     * @return
     */
    public boolean isExpired(){
        System.out.println("this.expireTime2==="+System.currentTimeMillis());
        return System.currentTimeMillis()>this.expireTime;
    }
}
