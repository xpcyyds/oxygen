package com.oxygen.wechat.util;

import com.oxygen.wechat.config.WxConfig;
import com.oxygen.wechat.dto.AccessTokenDto;
import com.oxygen.wechat.dto.JSApiTicketDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class TokenUtil {

    private static final String APP_ID = "wx8c6f55dbb72b0b81";
    private static final String APP_SECRET = "750a415d0cac686fbd51644c30c11bf2";
    private static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    private static AccessTokenDto accessTokenDto = new AccessTokenDto();
    private static JSApiTicketDto jsApiTicketDto = new JSApiTicketDto();

//    public static void main(String[] args){
//        //getAccessToken();
//        getJSApiTicket();
//    }

    private static void getToken(){
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",APP_ID,APP_SECRET);
        String result = HttpRequestUtil.sendGet(url);
        System.out.println(result);
        JSONObject json = JSONObject.fromObject(result);
        String token = json.getString("access_token");
        long expireTime = json.getLong("expires_in");
        accessTokenDto.setAccessToken(token);
        accessTokenDto.setExpireTime(expireTime);
    }

    private static void getTicket(){
        String url = JSAPI_TICKET
                + "?access_token=" + getAccessToken()
                + "&type=jsapi";
        String result = HttpRequestUtil.sendGet(url);
        System.out.println(result);
        JSONObject json = JSONObject.fromObject(result);
        String ticket = json.getString("ticket");
        long expireTime = json.getLong("expires_in");
        System.out.println("ticket==="+ticket);
        System.out.println("expireTime==="+expireTime);
        jsApiTicketDto.setJsApiTicket(ticket);
        jsApiTicketDto.setExpireTime(expireTime);
    }

    /**
     * 生成签名
     * @param nonceStr 随机字符串
     * @param jsapiTicket 有效的jsapi_ticket
     * @param timestamp 时间戳
     * @param url 当前网页的URL，不包含#及其后面部分
     * @return 签名
     */
    public static String generateSignature(String nonceStr, String jsapiTicket, long timestamp, String url) {
        // 对所有待签名参数按照字段名的ASCII码从小到大排序（字典序）
        Map<String, String> paramMap = new TreeMap<>();
        paramMap.put("noncestr", nonceStr);
        paramMap.put("jsapi_ticket", jsapiTicket);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("url", url);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getValue().trim().length() > 0) {
                sb.append(entry.getKey().toLowerCase()).append("=").append(entry.getValue().trim()).append("&");
            }
        }
        sb.setLength(sb.length() - 1);

        // 对string1作SHA1加密，字段名和字段值都采用原始值，不进行URL转义
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(sb.toString().getBytes());
            byte[] digest = md.digest();
            StringBuilder hexStr = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    hexStr.append("0");
                }
                hexStr.append(hex);
            }
            return hexStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // 示例使用
    public static void main(String[] args) {
//        String nonceStr = "594845264";
//        String jsapiTicket = getJSApiTicket();
//        long timestamp = System.currentTimeMillis(); //获取时间戳
//        String url = "http://www.example.com";
//        String signature = generateSignature(nonceStr, jsapiTicket, timestamp, url);
//        System.out.println(signature); // 输出签名
//        String accessToken = getAccessToken();
//        System.out.println("accessToken==="+accessToken);
    }

    /**
     * 获取signature
     * @return
     */
    public static Map getSignature(String url){
        Map<String, Object> data = new HashMap<String, Object>();
        String nonceStr = "1558474191";
        String jsapiTicket = getJSApiTicket();
        long timestamp = System.currentTimeMillis(); //获取时间戳
        //String url = "http://www.example.com";
        String signature = generateSignature(nonceStr, jsapiTicket, timestamp, url);
        data.put("appId",APP_ID);
        data.put("timestamp",timestamp);
        data.put("nonceStr",nonceStr);
        data.put("signature",signature);
//        appId: '', // 必填，公众号的唯一标识
//                timestamp: , // 必填，生成签名的时间戳
//        nonceStr: '', // 必填，生成签名的随机串
//                signature: '',// 必填，签名
        System.out.println(data); // 输出签名
        return data;
    }

    /**
     * 获取accessToken
     * @return
     */
    public static String getAccessToken(){
        if(accessTokenDto==null||accessTokenDto.isExpired()){
            getToken();
        }
        return accessTokenDto.getAccessToken();
    }

    /**
     * 获取JSApiTicket
     * @return
     */
    public static String getJSApiTicket(){
        if(jsApiTicketDto==null||jsApiTicketDto.isExpired()){
            getTicket();
        }
        return jsApiTicketDto.getJsApiTicket();
    }

    /**
     * 获取模板ID
     * @return
     */
    public static String getTemplateId(){
        if(accessTokenDto==null||accessTokenDto.isExpired()){
            getToken();
        }
        return accessTokenDto.getAccessToken();
    }

}
