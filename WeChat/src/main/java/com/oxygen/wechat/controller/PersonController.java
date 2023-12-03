package com.oxygen.wechat.controller;

import com.oxygen.wechat.entity.PersonEntity;
import com.oxygen.wechat.service.FSService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/count")
public class PersonController {

    @Autowired
    FSService fsService;

    /**
     * 1.查询该用户是否已经绑定公众号
     * @param personWeChat
     * @param personName
     * @param personMobile
     * @return
     */
    @RequestMapping(value = "/selectById",method = RequestMethod.GET)
    public @ResponseBody boolean getStatus(@RequestParam("personWeChat")String personWeChat,
                      @RequestParam("personName")String personName,
                      @RequestParam("personMobile")String personMobile){
        boolean bl = fsService.selectFSWetChat(personWeChat);
        return bl;
    }

    /**
     * 2.添加一条新的游客记录
     * @param personWeChat
     * @return
     */
    @RequestMapping(value = "/insertPerson",method = RequestMethod.POST)
    public @ResponseBody boolean insertPerson(@RequestParam("personWeChat")String personWeChat){
        boolean bl = fsService.insertFSPerson(personWeChat);
        return bl;
    }


}
