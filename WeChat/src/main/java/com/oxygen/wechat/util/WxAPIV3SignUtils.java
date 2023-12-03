package com.oxygen.wechat.util;

import sun.misc.BASE64Decoder;


import okhttp3.HttpUrl;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

/**
 * @ClassName WxAPIV3SignUtils
 * @Description: 微信API-V3签名工具
 * @Author xiPengCheng
 * @Date 2023/5/4
 * @Version V1.0
 */

public class WxAPIV3SignUtils {
    // Authorization: <schema> <token>
    // GET - getToken("GET", httpurl, "")
    // POST - getToken("POST", httpurl, json)

    public static final String sign_type = "HMAC-SHA256";//签名类型，仅支持HMAC-SHA256。示例值：HMAC-SHA256
    private static final String schema = "WECHATPAY2-SHA256-RSA2048";
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    /**
     *
     * @param method
     * @param url
     * @param body
     * @param mchId 商户号
     * @param privateKey 商户证书私钥
     * @param serialNo 商户API证书序列号
     * @return
     * @throws Exception
     */
    public static String getToken(String method,HttpUrl url,String body,String mchId,String privateKey,String serialNo) throws Exception{
        String nonceStr = generateNonceStr();
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        PrivateKey privateKey1 = getPrivateKey(privateKey);
        String signature = sign(message.getBytes("utf-8"),privateKey1);

        return schema + " " +"mchid=\"" + mchId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + serialNo + "\","
                + "signature=\"" + signature + "\"";
    }

    /**
     *
     * @param message
     * @param privateKey 商户私钥
     * @return
     */
    private static String sign(byte[] message, PrivateKey privateKey) throws Exception{
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }

    private static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * String转私钥PrivateKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

}
