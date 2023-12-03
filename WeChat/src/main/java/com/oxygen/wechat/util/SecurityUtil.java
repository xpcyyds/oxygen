package com.oxygen.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
    public static String SHA1(String inStr) {

        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("sha1");     //选择SHA-1，也可以选择MD5
            md.update(inStr.getBytes());       //返回的是byet[]，要转化为String存储比较方便
            byte[] msg = md.digest();
            for(byte b : msg){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
}
