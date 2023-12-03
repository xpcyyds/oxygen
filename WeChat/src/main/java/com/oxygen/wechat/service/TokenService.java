package com.oxygen.wechat.service;

import com.oxygen.wechat.util.TokenUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService {

    private static final String APP_ID = "wx94539f586dbad103";
    /**
     * 获取签名
     * @param url
     * @return
     */
    public Map<String, String> sign(String url) {
        Map<String, String> resultMap = new HashMap<>(16);
        //这里的jsapi_ticket是获取的jsapi_ticket。
        String jsapiTicket = TokenUtil.getJSApiTicket();
        //这里签名中的nonceStr要与前端页面config中的nonceStr保持一致，所以这里获取并生成签名之后，还要将其原值传到前端
//        if(ToolUtil.isEmpty(jsapiTicket)){
//            return resultMap;
//        }
        String nonceStr = createNonceStr();
        //nonceStr
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println("string1:"+string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        resultMap.put("url", url);
        resultMap.put("jsapi_ticket", jsapiTicket);
        resultMap.put("nonceStr", nonceStr);
        resultMap.put("timestamp", timestamp);
        resultMap.put("signature", signature);
        resultMap.put("appId", APP_ID);
        return resultMap;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
