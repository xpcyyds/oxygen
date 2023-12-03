package com.oxygen.wechat.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "urlutils")
public class UrlUtils {
    private static String weChatUrl;//token刷新时间，单位：秒
    private static String APP_ID;//token过期时间，单位：秒
    private static String APP_SECRET;//token过期时间，单位：秒

    @Value("${urlutils.weChatUrl}")
    public static void setWeChatUrl(String weChatUrl) {
        UrlUtils.weChatUrl = weChatUrl;
    }
    @Value("${urlutils.appId}")
    public static void setAppId(String appId) {
        APP_ID = appId;
    }
    @Value("${urlutils.appSecret}")
    public static void setAppSecret(String appSecret) {
        APP_SECRET = appSecret;
    }
}
