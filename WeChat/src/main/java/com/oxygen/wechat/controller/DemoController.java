package com.oxygen.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("weChat/v1/demo")
public class DemoController {

    @GetMapping("info")
    public String info(){
        return "Hello xpc SpringBoot";
    }
}
