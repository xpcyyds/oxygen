package com.oxygen.wechat.controller;

import com.oxygen.wechat.service.FSService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/wtrecord")
public class WtRecordController {

    @Autowired
    FSService fsService;

    @RequestMapping(value = "/insertWtRecord",method = RequestMethod.POST)
    public @ResponseBody
    boolean insertBusiness(@RequestParam("wrOwner")String wrOwner,
                           @RequestParam("wrAction")String wrAction,
                           @RequestParam("wrResult")String wrResult){
        boolean bl = fsService.insertFSRecord(wrOwner,wrAction,wrResult);
        return bl;
    }
}
