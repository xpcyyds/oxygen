package com.oxygen.wechat.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxygen.wechat.config.WxConfig;
import com.oxygen.wechat.dto.MsgDto;
import com.oxygen.wechat.dto.Result;
import com.oxygen.wechat.dto.WxMpXmlMessage;
import com.oxygen.wechat.entity.WxUserEntity;
import com.oxygen.wechat.mapper.ManagerMapper;
import com.oxygen.wechat.mapper.OxymachineMapper;
import com.oxygen.wechat.service.FSService;
import com.oxygen.wechat.service.WxUserService;
import com.oxygen.wechat.util.HttpRequestUtil;
import com.oxygen.wechat.util.SecurityUtil;
import com.oxygen.wechat.util.TokenUtil;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@Controller
@RequestMapping("weChat")
public class WeChatController {

    @Autowired
    WxUserService wxUserService;

    @Autowired
    WxConfig wxConfig;

    @Autowired
    FSService fsService;

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    OxymachineMapper oxymachineMapper;

    private static final String weChatUrl = "http://www.qcopen.cn:8081";
    
    //private static final String weChatUrl = "http://3cf06dfb.r10.cpolar.top";

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public @ResponseBody
    void weChatInit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        signature	微信加密签名，signature结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce参数。
//        timestamp	时间戳
//        nonce	随机数
//        echostr	随机字符串

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        String[] arrs = {wxConfig.getToken(),timestamp,nonce};
        //字符串排序
        Arrays.sort(arrs);
        StringBuffer sb = new StringBuffer();
        for(String a:arrs){
             sb.append(a);
        }
        String sha1 = SecurityUtil.SHA1(sb.toString());
        System.out.println(sha1.equals(signature));
        if(sha1.equals(signature)){
            resp.getWriter().println(echostr);
        }
        System.out.println("echostr=="+echostr);
        System.out.println("signature=="+signature);
        System.out.println("nonce=="+nonce);
    }

    @RequestMapping(value = "/getSignature",method = RequestMethod.GET)
    public @ResponseBody
    Result getSignature(@RequestParam("url")String url) throws IOException {

//        String timestamp = req.getParameter("timestamp");
//        String nonce = req.getParameter("nonce");
//        String echostr = req.getParameter("echostr");
        System.out.println("调用接口");
        Map<String, Object> data = TokenUtil.getSignature(url);
        System.out.println("url============================="+url);

//        System.out.println("echostr=="+echostr);
//        System.out.println("signature=="+signature);
//        System.out.println("nonce=="+nonce);
        return new Result(200,"获取成功",data);
    }

    /**
     * 获取openid我要借
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenIdByBorrow")
    @ResponseBody
    public void getOpenIdByBorrow(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        //String borrowUrl = URLEncoder.encode("/weChat/getWxgzhUserByBorrow", "utf-8");
        String borrowUrl = "pay.qcopen.cn/pay/weixin/openid?client_id=2000&redirect_uri=http://75962e95.r10.cpolar.top/weChat/getWxgzhUserByBorrow";
        String getCodeUrl = "http://pay.qcopen.cn/pay/weixin/openid?client_id=2000&redirect_uri=http://75962e95.r10.cpolar.top/weChat/getWxgzhUserByBorrow";

        HttpRequestUtil.sendGet(getCodeUrl);
//        //回调地址
//        try {
//            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
//            response.sendRedirect(getCodeUrl);
//
//            System.out.println("redirect:"+getCodeUrl);
//            //String json3 = new ObjectMapper().writeValueAsString(map);
//            //response.setContentType("application/json;charset=UTF-8");
//            //response.getWriter().println(json3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }


    /**
     * 获取code的重定向方法
     * 我要借
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserByBorrow")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiByBorrow(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String code=request.getParameter("code");
//        String errcode=request.getParameter("errcode");
//        String state= request.getParameter("state");
        String openid=request.getParameter("openid");
        //System.out.println("来了来了s="+code+",errcode"+errcode+"state="+state);

        //String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);
        //String param = "appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        //String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
        //JSONObject json2 = JSONObject.fromObject(jsonString);
       // String openid = json2.getString("openid");
        System.out.println("###############"+openid+"用户点击我要借");

        boolean bl = fsService.selectFSLease(openid);
        String redirectUri = "http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Findex%2FtwoModel";
        if(bl){
            //如果有订单，直接进入订单信息
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Fmessage%2FhavedOrder&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");
        }else {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+redirectUri+"&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);



        System.out.println(map);

//        HashMap<String,String> hm = new HashMap<>();//       hm.put("code",code);
//        if(ToolUtil.isEmpty(code)){
//            return Result.failure("code为空");
//        }
//        Map<String,Object> openidMap = new HashMap<>();
//        openidMap = WxGzhUtils.getOpenId(weixin4jConfig.getAppid(),weixin4jConfig.getSecret(),code);
//
//        String openid = openidMap.get("openid")== null?null:openidMap.get("openid").toString();
//        String access_token = openidMap.get("access_token")== null?null:openidMap.get("access_token").toString();
//
//        if(ToolUtil.isEmpty(openid) || ToolUtil.isEmpty(access_token)){
//            return Result.failure("openid或access_token参数为空");
//        }
//        Map<String,Object> map = new HashMap<>();
//        map = WxGzhUtils.getUserInfo(openid,access_token);

        return Result.ok(map);
    }

    /**
     * 获取openid我要还
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenIdByReturn")
    @ResponseBody
    public String getOpenIdByReturn(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        String returnUrl = URLEncoder.encode("/weChat/getWxgzhUserByReturn", "utf-8");

        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+weChatEncoderUrl+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";


        //回调地址
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(getCodeUrl);
            System.out.println("redirect:"+getCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }

    /**
     * 获取code的重定向方法
     * 我要还
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserByReturn")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiByReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code=request.getParameter("code");
        String errcode=request.getParameter("errcode");
        String state= request.getParameter("state");
        System.out.println("来了来了s="+code+",errcode"+errcode+"state="+state);

        String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);

        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
        JSONObject json2 = JSONObject.fromObject(jsonString);
        String openid = json2.getString("openid");
        System.out.println("###############"+openid);
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Freturn%2Freturn&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");

        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);

        System.out.println(map);

        return Result.ok(map);
    }

    /**
     * 获取openid
     * 详细信息
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenId")
    @ResponseBody
    public String getOpenId(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        String detailUrl = URLEncoder.encode("/weChat/getWxgzhUserDetail", "utf-8");

        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+weChatEncoderUrl+detailUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";


        //回调地址
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(getCodeUrl);
            System.out.println("redirect:"+getCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }

    /**
     * 获取code的重定向方法
     * 详细信息
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserDetail")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String code=request.getParameter("code");
//        String errcode=request.getParameter("errcode");
//        String state= request.getParameter("state");
//        System.out.println("来了来了s="+code+",errcode"+errcode+"state="+state);
        String openid=request.getParameter("openid");
        System.out.println("###############"+openid+"用户点击订单详情");
//        String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);
//        //String param = "appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
//        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
//        JSONObject json2 = JSONObject.fromObject(jsonString);
//        String openid = json2.getString("openid");
//        System.out.println("###############"+openid);
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Fvx%2ForderDetail&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");


        //String openid = "123456";
        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);

        System.out.println(map);

        return Result.ok(map);
    }

    /**
     * 获取openid
     * 我要买
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenIdByPurchase")
    @ResponseBody
    public String getOpenIdByPurchase(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        String purchaseUrl = URLEncoder.encode("/weChat/getWxgzhUserByPurchase", "utf-8");

        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+weChatEncoderUrl+purchaseUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";


        //回调地址
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(getCodeUrl);

            System.out.println("redirect:"+getCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }

    /**
     * 获取code的重定向方法
     * 我要买
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserByPurchase")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiByPurchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code=request.getParameter("code");
        String errcode=request.getParameter("errcode");
        String state= request.getParameter("state");
        System.out.println("来了来了s="+code+",errcode"+errcode+"state="+state);

        String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);
        //String param = "appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
        JSONObject json2 = JSONObject.fromObject(jsonString);
        String openid = json2.getString("openid");
        System.out.println("###############"+openid);
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Fbuy%2Fbuy&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");


        //String openid = "123456";
        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);

        System.out.println(map);

        return Result.ok(map);
    }

    /**
     * 获取openid驿站人员登录
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenIdByPttion")
    @ResponseBody
    public String getOpenIdByPttion(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        String returnUrl = URLEncoder.encode("/weChat/getWxgzhUserByPttion", "utf-8");

        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+weChatEncoderUrl+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";


        //回调地址
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(getCodeUrl);
            System.out.println("redirect:"+getCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }

    /**
     * 获取code的重定向方法
     * 驿站管理人员登录
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserByPttion")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiByPttion(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String code=request.getParameter("code");
//        String errcode=request.getParameter("errcode");
//        String state= request.getParameter("state");
//        System.out.println("来了来了s="+code+",errcode"+errcode+"state="+state);
        String openid=request.getParameter("openid");
        System.out.println("###############"+openid+"用户点击我的订单");
//        String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);
//
//        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
//        JSONObject json2 = JSONObject.fromObject(jsonString);
//        String openid = json2.getString("openid");
//        System.out.println("###############"+openid);
        if(managerMapper.selectFSLoginPttionManager(openid)==1){
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Femployee%2Forders&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");

        }else {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Femployee%2Finfo%2FnotEmployee&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);

        System.out.println(map);

        return Result.ok(map);
    }

    /**
     * 获取openid驿站人员登录
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenIdByPttionScan")
    @ResponseBody
    public String getOpenIdByPttionScan(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        String returnUrl = URLEncoder.encode("/weChat/getWxgzhUserByPttionScan", "utf-8");

        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+weChatEncoderUrl+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";


        //回调地址
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(getCodeUrl);
            System.out.println("redirect:"+getCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }

    /**
     * 获取code的重定向方法
     * 驿站管理人员登录
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserByPttionScan")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiByPttionScan(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String code=request.getParameter("code");
//        String errcode=request.getParameter("errcode");
//        String state= request.getParameter("state");
//        System.out.println("来了来了s="+co de+",errcode"+errcode+"state="+state);
        String openid=request.getParameter("openid");
        System.out.println("###############"+openid+"用户点击制氧机入库");
//        String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);
//
//        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
//        JSONObject json2 = JSONObject.fromObject(jsonString);
//        String openid = json2.getString("openid");
//        System.out.println("###############"+openid);
        if(managerMapper.selectFSLoginPttionManager(openid)==1){
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Femployee%2FoneReturn&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");

        }else {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Femployee%2Finfo%2FnotEmployee&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);

        System.out.println(map);

        return Result.ok(map);
    }

    /**
     * 获取扫描二维码用户的openid
     * @param
     * @param
     * @return
     */
    @GetMapping("/getOpenIdByQRCode")
    @ResponseBody
    public String getOpenIdByQRCode(@RequestParam("omId")String omId, HttpServletResponse response) throws UnsupportedEncodingException {

        String weChatEncoderUrl = URLEncoder.encode(weChatUrl, "utf-8");
        String returnUrl = URLEncoder.encode("/weChat/getWxgzhUserByQRCode?omId="+omId, "utf-8");

        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri="+weChatEncoderUrl+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        System.out.println("二维码测试");

        //回调地址
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(getCodeUrl);
            System.out.println("redirect:"+getCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+getCodeUrl;//必须重定向，否则不能成功
    }

    /**
     * 获取code的重定向方法
     * 驿站管理人员登录
     * @param request
     * @return
     */
    @GetMapping("/getWxgzhUserByQRCode")
    @CrossOrigin//(origins = "https://open.weixin.qq.com", maxAge = 3600)
    public @ResponseBody Result getWxgzhApiByPttionScan(@RequestParam("omId")String omId,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) throws IOException {
//        String code=request.getParameter("code");
//        String errcode=request.getParameter("errcode");
//        String state= request.getParameter("state");
//        System.out.println("来了来了s="+code+",errcode"+errcode+"state="+state);
        String openid=request.getParameter("openid");
        System.out.println("###############"+openid+"用户扫码");

        System.out.println("获取omid成功"+omId);

//        String wxLoginUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wxConfig.getAppId(),wxConfig.getAppSecret(),code);
//
//        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl);
//        JSONObject json2 = JSONObject.fromObject(jsonString);
//        String openid = json2.getString("openid");
//        System.out.println("###############"+openid);

        //判断制氧机状态是否为1驿站或6归还入库或8或2
        if((oxymachineMapper.selectOxymachineIsCanUse1(omId))==1){

            System.out.println("1");
            //判断是不是驿站管理员
            if(managerMapper.selectFSLoginPttionManager(openid)==1){
                System.out.println("2");
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Femployee%2Forders&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");
                //System.out.println("b");
            }else {
                System.out.println("3");
                response.sendRedirect("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAxMDM0NjIxOA==&scene=124#wechat_redirect");
            }
        }else if((oxymachineMapper.selectOxymachineIsReturn(omId))==1){//判断制氧机状态是否为0出厂或5归还途中
            System.out.println("4");
            //判断是不是驿站管理员
            if(managerMapper.selectFSLoginPttionManager(openid)==1){
                System.out.println("5");
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Femployee%2FoneReturn&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");

            }else {
                response.sendRedirect("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAxMDM0NjIxOA==&scene=124#wechat_redirect");
            }

        }else {
            //判断是不是用户
            if(oxymachineMapper.selectWeChatByBusiMid(omId).equals(openid)){
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c6f55dbb72b0b81&redirect_uri=http%3A%2F%2Fwww.qcopen.cn%2Fywyl-zyj-h5%2F%23%2Fpages%2Fvx%2ForderDetail&response_type=code&scope=snsapi_base&state="+openid+"#wechat_redirect");

            }else {
                response.sendRedirect("http://www.qcopen.cn/ywyl-zyj-h5/#/pages/message/unauthorized");
            }
        }

        //判断是不是新用户
        if(!fsService.selectFSWetChat(openid)){
            //添加用户
            fsService.insertFSPerson(openid);
        }


        Map<String,Object> map = new HashMap<>();
        map.put("openId",openid);

        System.out.println(map);

        return Result.ok(map);
    }

    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public @ResponseBody
    String weChatReceiveMsg(HttpServletRequest req) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            //读取输入流
            Document document = reader.read(inputStream);
            //获得root节点
            Element root = document.getRootElement();
            //获得所有的子节点
            List<Element> elements = root.elements();

            for (Element element : elements){
                map.put(element.getName(),element.getStringValue());
            }
        }catch (DocumentException e){
            e.printStackTrace();
        }


//        byte[] b = new byte[1024];
//        int len = 0;
//        while ((len = inputStream.read(b))!=-1){
//            System.out.println(new String(b,0,len));
//        }

        String messageType=map.get("MsgType");								//消息类型
        String messageEvent=map.get("Event");								    //消息事件
        // openid
        String fromUser=map.get("FromUserName");									//发送者帐号
        String toUser=map.get("ToUserName");										//开发者微信号
        //String text=map.get("MsgType");										//文本消息  文本内容
        // 生成二维码时穿过的特殊参数
        String eventKey=map.get("EventKey");									//二维码参数

        String replyMsg="";

        if(messageType.equals("event")){
            //if判断，判断查询
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","200");
            //jsonObject = null;
            //先根据openid从数据库查询  => 从自己数据库中查取用户信息 => jsonObject
            //WxUserEntity wxUser = wxUserService.getByOpenId(fromUser);
            if(wxUserService.getByOpenId(fromUser)==true){
                System.out.println("存在用户");
                //jsonObject = JSONObject.fromObject(wxUser);
            }
            if(messageEvent.equals("SCAN")){

                //log.info("欢迎回来");
                //扫描二维码
                replyMsg = "欢迎回来";
            }
            if(messageEvent.equals("subscribe")){
                //关注
                replyMsg = "谢谢您的关注";
                //log.info("感谢您的关注");
                eventKey = eventKey.substring(8);
                System.out.println(eventKey);
            }
            if(messageEvent.equals("uns ubscribe")){
                //取消关注，从数据库中删除用户信息
                wxUserService.removeByOpenId(fromUser);
            }
            //没有该用户
            if(wxUserService.getByOpenId(fromUser)==false&&messageEvent.equals("subscribe")){
                //从微信上中拉取用户信息
                String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + TokenUtil.getAccessToken() +
                        "&openid=" + fromUser +
                        "&lang=zh_CN";
                String result = HttpRequestUtil.sendGet(url);
                jsonObject = JSONObject.fromObject(result);
                //获取用户信息字段
                String nickname = jsonObject.getString("nickname");
                String sex = jsonObject.getString("sex");
                String province = jsonObject.getString("province");
                String city = jsonObject.getString("city");
                String country = jsonObject.getString("country");
                //String headimgurl = jsonObject.getString("headimgurl");
                //WxUserEntity wxUser1 = new WxUserEntity();
                wxUserService.insertWxUser(fromUser);
                if(!fsService.selectFSLease(fromUser)){
                    //添加信息表
                    fsService.insertFSPerson(fromUser);
                }

                //判断数据库中是否有重复数据,没有重复数据插入数据库
                //wxUserInfoService.save(wxUser1);
            }
            // 扫码成功，存入缓存
            //loginMap.put(eventKey,new CodeLoginKey(eventKey,fromUser));
            //return jsonObject;
        }
        if(messageType.equals("text")){
            replyMsg="你好";
        }
        //return jsonObject;

        System.out.println(map);
        String msg = getReplyMsg(map,replyMsg);
        System.out.println("收到用户消 息");
        return msg;
    }

    /**
     * 获得回复的消息内容
     * @param map
     * @return xml格式的字符串
     */
    private String getReplyMsg(Map<String, String> map,String replyMsg) {
        MsgDto msgDto = new MsgDto();
        msgDto.setToUserName(map.get("FromUserName"));
        msgDto.setFromUserName(map.get("ToUserName"));
        msgDto.setMsgType("text");
        msgDto.setContent(replyMsg);
        msgDto.setCreateTime(System.currentTimeMillis()/1000);

        //XStream将java对象转为xml
        XStream xStream = new XStream();
        xStream.processAnnotations(MsgDto.class);
        String xml = xStream.toXML(msgDto);
        return xml;
    }
}
