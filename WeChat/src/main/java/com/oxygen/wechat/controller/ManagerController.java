package com.oxygen.wechat.controller;

import com.oxygen.wechat.dto.Result;
import com.oxygen.wechat.service.FSService;
import com.oxygen.wechat.service.ManagerService;
import com.oxygen.wechat.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("api/v1/manager")
public class ManagerController {

    @Autowired
    FSService fsService;

    @Autowired
    ManagerService managerService;

    /**
     * 1. 管理人员登录
     * @param mgName
     * @param mgPass
     * @return
     */
    @RequestMapping(value = "/selectManager",method = RequestMethod.GET)
    public @ResponseBody
    Result getFSlogin(@RequestParam("mgName")String mgName,
                      @RequestParam("mgPass")String mgPass){
        boolean flag = fsService.selectFSLogin(mgName,mgPass);
        boolean flagName = managerService.selectFSLoginByMgName(mgName);
        String msg = "登陆成功";
        if(flag == false){
            msg = flagName ? "密码错误" : "用户名不存在，请注册";
        }else {
            Map<String,String> payload = new HashMap<>();
            payload.put("mgName",mgName);
            //生成JWT令牌
            String token = JWTUtils.getToken(payload);
//            String refreshToken = JWTUtils.getReFreshToken(payload);
            //logger.info("用户获取令牌成功,token==="+token);
            System.out.println("用户获取令牌成功,token==="+token);
//            System.out.println(refreshToken);
            /*long expireTime = JWT.decode(token)
                    .getExpiresAt().getTime();
            System.out.println("diff0====expireTime"+expireTime/1000);*/
            Object data = 0;
            //user.setLoginTime(System.currentTimeMillis()/1000);
            //System.out.println(user.getLoginTime());
            //logger.info("用户登录成功");
            return new Result(200,msg,token,10000000+System.currentTimeMillis());
        }
        //Object data = flag ? 0 : -1;
        return new Result(201,msg);
    }
    /**
     * 2. 获取登录人员权限
     * @param mgName
     * @param mgPass
     * @param mgMobile
     * @return
     */
    @RequestMapping(value = "/selectManagerQuanx",method = RequestMethod.GET)
    public @ResponseBody
    Integer getFSQuanx(@RequestParam("mgName")String mgName,
                      @RequestParam("mgPass")String mgPass,
                      @RequestParam("mgMobile")String mgMobile){
        Integer num = fsService.selectFSQuanx(mgName,mgPass,mgMobile);
        return num ;
    }
}
