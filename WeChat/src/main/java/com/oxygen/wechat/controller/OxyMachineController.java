package com.oxygen.wechat.controller;

import com.oxygen.wechat.dto.Result;
import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.OxyMachineEntity;
import com.oxygen.wechat.entity.OxyMachineEntity2;
import com.oxygen.wechat.service.OxymachineService;
import com.oxygen.wechat.util.BusiStatusUtil;
import com.oxygen.wechat.util.OxymachineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/oxyMachineController")
public class OxyMachineController {

    @Autowired
    OxymachineService oxymachineService;

    /**
     * 1.所有制氧机设备信息展示
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getAllMachineStatus",method = RequestMethod.GET)
    public @ResponseBody
    Result FSsentmachine(@RequestParam("pageNum")Integer pageNum,
                         @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<OxyMachineEntity> oxyMachineEntities = oxymachineService.selectAllOxymachine(scanNum,pageSize);
        Integer total = oxymachineService.selectAllOxymachineNum();
        List list = oxyMachineEntities;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }

    /**
     * 2.获取特定设备信息
     * @param omId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getMachineMessage",method = RequestMethod.GET)
    public @ResponseBody
    Result FSsentmachine(@RequestParam("omId")String omId,
                         @RequestParam("pageNum")Integer pageNum,
                         @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<OxyMachineEntity2> oxyMachineEntity2s = oxymachineService.selectOxymachineMsg(omId,scanNum,pageSize);
        Integer total = oxymachineService.selectOxymachineMsgNum(omId);
        List list = oxyMachineEntity2s;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }
}
