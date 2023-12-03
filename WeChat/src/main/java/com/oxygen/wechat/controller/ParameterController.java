package com.oxygen.wechat.controller;

import com.oxygen.wechat.service.FSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("api/v1/parameter")
public class ParameterController {
    @Autowired
    FSService fsService;  

    /**
     * 1. 计费函数
     * @param personWeChat
     * @param personName
     * @param personMobile
     * @param busiMid
     * @param busiBdate
     * @param busiQdate
     * @return
     */
//    @RequestMapping(value = "/selectFSFee",method = RequestMethod.GET)
//    public @ResponseBody
//    Integer selectFSQuanx(@RequestParam("personWeChat")String personWeChat,
//                          @RequestParam("personName")String personName,
//                          @RequestParam("personMobile")String personMobile,
//                          @RequestParam("busiMid") String busiMid,
//                          @RequestParam("busiBdate")Date busiBdate,
//                          @RequestParam("busiQdate")Date busiQdate)throws ParseException {
////        String  str = java.net.URLEncoder.encode("gQGd8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyalVxZHdodFllMkcxMDAwMHcwM04AAgRS2cdjAwQAAAAA");
////        System.out.println(str);
//        Integer busiFee = fsService.selectFSFee(personWeChat,personName,personMobile,busiMid,busiBdate,busiQdate);
//        return busiFee ;
//    }




    /**
     * 2. 分情况判断游客是否需要交押金的查询函数
     * @param personWeChat
     * @param wxFen
     * @return
     */
    @RequestMapping(value = "/selectFSQuery",method = RequestMethod.GET)
    public @ResponseBody
    Integer selectFSQuanx(@RequestParam("personWeChat")String personWeChat,
                          @RequestParam("wxFen")Integer wxFen){
        Integer busiFee = fsService.selectFSQuery(personWeChat,wxFen);
        return busiFee ;
    }
}
