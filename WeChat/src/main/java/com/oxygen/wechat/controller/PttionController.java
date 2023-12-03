package com.oxygen.wechat.controller;

import com.oxygen.wechat.dto.Result;
import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.OxyMachineEntity;
import com.oxygen.wechat.entity.OxyMachineEntity2;
import com.oxygen.wechat.entity.PttionEntity;
import com.oxygen.wechat.mapper.BusinessMapper;
import com.oxygen.wechat.mapper.OxymachineMapper;
import com.oxygen.wechat.mapper.PttionMapper;
import com.oxygen.wechat.service.*;
import com.oxygen.wechat.util.*;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping("api/v2/pttion")
public class PttionController {
    @Autowired
    FSService fsService;

    @Autowired
    PttionService pttionService;

    @Autowired
    PttionMapper pttionMapper;

    @Autowired
    BusinessService businessService;

    @Autowired
    OxymachineService oxymachineService;

    @Autowired
    OxymachineMapper oxymachineMapper;

    @Autowired
    BusinessMapper businessMapper;

    @Autowired
    TokenService tokenService;

    /**
     * 1.按设定规则找到最佳归还的驿站。
     * @param
     * @return
     */
    @RequestMapping(value = "/selectFSghstation",method = RequestMethod.GET)
    public @ResponseBody
    Result FSghstation(@RequestParam("pageNum")Integer pageNum,
                       @RequestParam("pageSize")Integer pageSize,
                       @RequestParam("busiReturnArea")String busiReturnArea){
        Integer scanNum = pageSize*(pageNum-1);
        String busiArea = busiReturnArea.replace("、","");
        List<PttionEntity> pttionList = fsService.FSghstation(scanNum,pageSize,busiArea);
        System.out.println("pttionId==="+pttionList);
        List<PttionEntity> pttionList1 = pttionService.selectPttionIdByBusiArea(busiArea);
        Integer total = pttionList1.size();
        List list = pttionList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }

    /**
     * 2.通过BusiArea查找PttionId。
     * @param busiArea
     * @return
     */
    @RequestMapping(value = "/selectPttionId",method = RequestMethod.GET)
    public @ResponseBody
    Result FSghstation1(@RequestParam("pageNum")Integer pageNum,
                        @RequestParam("pageSize")Integer pageSize,
                        @RequestParam("busiArea")String busiArea){
        busiArea = busiArea.replace("、", "");
        Integer scanNum = pageSize*(pageNum-1);
        List<PttionEntity> pttionList = pttionService.selectPttionIdByBusiAreaAll(scanNum,pageSize,busiArea);
        System.out.println("pttionId==="+pttionList);
        Integer total = pttionList.size();
        List list = pttionList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }

    /**
     * 3.查询所有驿站信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectAllPttion",method = RequestMethod.GET)
    public @ResponseBody
    Result FSghstation2(@RequestParam("pageNum")Integer pageNum,
                                    @RequestParam("pageSize")Integer pageSize){
        Integer scanNum = pageSize*(pageNum-1);
        List<PttionEntity> pttionList = pttionService.selectPttion(scanNum,pageSize);
        System.out.println("pttionId==="+pttionList);
        Integer total = pttionService.selectPttion();
        List list = pttionList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }


    /**
     *
     * 测试接口
     */
    /**
     * 4.从订单信息表business里查询是否有未完成派送确认的订单
     */
    @RequestMapping(value = "/FSdaipaisong",method = RequestMethod.GET)
    public @ResponseBody
    Result FSghstation3(@RequestParam("pttionId")String pttionId){
        Integer code;
        Integer num = pttionService.FSdaipaisong(pttionId, BusiStatusUtil.Scan_Code_Bus);
        if(num==1)code = 200;
        else code = 201;
        return new Result(code,"获取成功");
    }

    /**
     * 5.扫码枪扫码将busiStatus改为8用于驿站按订单编号匹配制氧机
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/FSpipeimachine",method = RequestMethod.GET)
    public @ResponseBody
    Result FSpipeimachine(@RequestParam("busiId")String busiId,
                              @RequestParam("omId")String omId,
                              @RequestParam("busiPstation1")String busiPstation1
                          ){
        omId=omId.replace("http://pay.qcopen.cn/pay/weixin/openid?client_id=","");
        //从表oxymachine里按条件omStatus=1 or omStatus=6查询对应参数(制氧机编号)的count()结果，
        // =1，表示该制氧机当前可以租借
        System.out.println("omId=="+omId);
        Integer num = oxymachineService.selectOxymachineIsCanUse(omId);
        System.out.println("num=="+num);
        if(num==1){//表示该制氧机当前可以租借
            System.out.println("该制氧机当前可以租借");
            //查询订单状态busiStatus是否为7
            Integer num1 = businessService.selectBusByBusiStatus(busiId,BusiStatusUtil.Matching_Pttion);
            if(num1==1) {
                System.out.println("查询订单状态busiStatus为7");
                //更改订单状态busiStatus改为8
                boolean bl = businessService.updateBus(busiId, BusiStatusUtil.Scan_Code_Bus);
                //更改制氧机状态busiStatus改为8
                boolean bl1 = oxymachineService.updateOxymachineOmStatus(omId, OxymachineUtil.Successfully_Matched);
                //订单与制氧机编号匹配
                boolean bl2 = businessService.updateBusiMid(busiId, omId);
                //查询订单对应的驿站ID


                //添加制氧机信息
                if (bl) {
                    return new Result(200, "处理成功");
                } else {
                    return new Result(201, "处理失败");
                }
            }else {
                System.out.println("查询订单状态busiStatus不为7");
                return new Result(202,"处理失败,当前订单状态报错");
            }
        }else {
            return new Result(203,"处理失败,当前制氧机不可以租借");
        }
    }

    /**
     * 6.用于驿站按订单编号确认制氧机送出
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/FSsentmachine",method = RequestMethod.GET)
    public @ResponseBody
    Result FSsentmachine(@RequestParam("busiId")String busiId,
                          @RequestParam("busiMid")String busiMid,
                         @RequestParam("busiPstation1")String busiPstation1
                          //@RequestParam("pttionId")String pttionId
    ){
        //首先从表oxymachine里按条件omStatus=8查询对应参数(制氧机编号)的count()结果
        Integer num = oxymachineMapper.selectOxymachineomStatus(busiMid,OxymachineUtil.Successfully_Matched);

        if(num==1){//表示该制氧机成功匹配
            System.out.println("该制氧机当前可以租借");
            //查询订单状态busiStatus是否为8，是否匹配跑腿
            Integer num1 = businessService.selectBusByBusiStatus(busiId,BusiStatusUtil.Scan_Code_Bus);
            if(num1==1) {
                System.out.println("查询订单状态busiStatus为8");
                //更改订单状态busiStatus改为9确认送出
                boolean bl = businessService.updateBus(busiId, BusiStatusUtil.Match_Runner);
                //更改制氧机状态omStatus改为2送出
                boolean bl1 = oxymachineService.updateOxymachineOmStatus(busiMid, OxymachineUtil.Send_Out);

                //pttionExtend1的值减1
                //驿站库存数减一
                boolean bl3 = pttionService.updatePttionExtend1Reduce(busiPstation1);
                //pttionExtend3的值减1
                boolean bl4 = pttionMapper.updatePttionExtend31(busiPstation1);
//                //添加订单的送达时间
//                Date date = new Date();
//                //format的格式可以任意
//                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String busiAdate = sdf.format(date);
//                boolean bl2 = businessMapper.updateBusiAdate(busiId,busiAdate);

                if (bl) {
                    return new Result(200, "处理成功");
                } else {
                    return new Result(201, "处理失败");
                }
            }else {
                System.out.println("查询订单状态busiStatus不为8");
                return new Result(202,"处理失败,当前订单状态报错");
            }
        }else {
            return new Result(203,"处理失败,当前制氧机未匹配");
        }
    }

    /**
     * 7.用于驿站按订单编号确认制氧机送达
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/FSsentsure",method = RequestMethod.GET)
    public @ResponseBody
    Result FSsentsure(@RequestParam("busiId")String busiId,
                         @RequestParam("busiMid")String busiMid
                         //@RequestParam("pttionId")String pttionId
    ){
        //首先从表oxymachine里按条件omStatus=2查询对应参数(制氧机编号)的count()结果
        Integer num = oxymachineMapper.selectOxymachineomStatus(busiMid,OxymachineUtil.Send_Out);

        if(num==1){//表示该制氧机成功送出
            System.out.println("该制氧机当前可以确认送达");
            //查询订单状态busiStatus是否为9，是否送出
            Integer num1 = businessService.selectBusByBusiStatus(busiId,BusiStatusUtil.Match_Runner);
            if(num1==1) {
                System.out.println("查询订单状态busiStatus为9");
                //更改订单状态busiStatus改为1送达
                boolean bl = businessService.updateBus(busiId, BusiStatusUtil.Service);
                //更改制氧机状态omStatus改为3送达
                boolean bl1 = oxymachineService.updateOxymachineOmStatus(busiMid, OxymachineUtil.Send_Over);


                //添加订单的送达时间
                Date date = new Date();
                //format的格式可以任意
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String busiAdate = sdf.format(date);
                boolean bl2 = businessMapper.updateBusiAdate(busiId,busiAdate);

                if (bl) {
                    return new Result(200, "处理成功");
                } else {
                    return new Result(201, "处理失败");
                }
            }else {
                System.out.println("查询订单状态busiStatus不为9");
                return new Result(202,"处理失败,当前订单状态报错");
            }
        }else {
            return new Result(203,"处理失败,当前制氧机未送出");
        }
    }

    /**
     * 8.用于驿站入库制氧机
     * @return
     */
    @RequestMapping(value = "/FSruku",method = RequestMethod.GET)
    public @ResponseBody
    Result FSruku(@RequestParam("openId")String openId,
                  @RequestParam("busiMid")String busiMid) {

        System.out.println(busiMid);
        //首先判断是不是制氧机
        if (busiMid.indexOf("qczltoken") != -1) {
            System.out.println("字符串中包含指定的字符串");
        }else {
            return new Result(204, "处理失败,非制氧机二维码");
        }

        //判断是否为库中的制氧机
        if(oxymachineMapper.selectOxymachineIsExist(busiMid) != 1){
            String recordId= UUID.randomUUID().toString();
            recordId=recordId.replace("-", "");
            System.out.println(recordId);
            Date date = new Date();
            //format的格式可以任意
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String omDate = sdf.format(date);
            System.out.println(omDate);

            //新增制氧机
            oxymachineMapper.insertOxymachine(recordId,busiMid,omDate);
            return new Result(205, "新增制氧机"+busiMid+"成功");
        }

        //从表oxymachine里按条件omStatus=0 or omStatus=5查询对应参数(制氧机编号)的count()结果
        Integer num = oxymachineService.selectOxymachineIsReturn(busiMid);
        if (num == 1) {//表示该制氧机已经归还
            System.out.println("该制氧机当前可以入库");
            //查询订单状态busiStatus是否为13，是否归还快递已经派送成功
            Integer num1 = businessMapper.selectBusByBusiMid(busiMid, BusiStatusUtil.Return_Delivered_Succeeded);
            String busiId = businessMapper.selectBusByBusiMid1(busiMid, BusiStatusUtil.Return_Delivered_Succeeded);
            if (num1 == 1) {

                long timestamp = System.currentTimeMillis();
                String url = "http://pay.qcopen.cn/refund/apply?client_id=2000&client_trade_no=YJ"+busiId+ "&total_fee=1&timestamp="+timestamp+"&sign=1";
                String result = HttpRequestUtil.sendGet(url);
                System.out.println("result"+result);
                JSONObject json = JSONObject.fromObject(result);
                String refundstatus = json.getString("errorMessage");

                if (refundstatus.equals("500 null")) {
                    System.out.println("查询订单状态busiStatus为13");
                    //更改订单状态busiStatus改为4归还送达
                    boolean bl = businessService.updateBus(busiId, BusiStatusUtil.Return_Succeeded);
                    //更改制氧机状态omStatus改为6归还入库
                    boolean bl1 = oxymachineService.updateOxymachineOmStatus(busiMid, OxymachineUtil.Return_Over);
                    //更改用户订单显示状态0
                    businessService.updateBusSta(busiId, BusiIsStatusUtil.NO_STATUS);
                    String pttionId = pttionMapper.selectpttionIdByWeChat(openId);
                    //驿站库存数加一
                    boolean bl2 = pttionService.updatePttionExtend1Add(pttionId);
                    //添加订单的送达时间
                    Date date = new Date();
                    //format的格式可以任意
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String busiDdate = sdf.format(date);

                    boolean bl3 = businessMapper.updateBusiDdate(busiId, busiDdate);

                    if (bl) {
                        return new Result(200, "处理成功");
                    } else {
                        return new Result(201, "处理失败");
                    }
                } else {
                    System.out.println("退款状态为===" + refundstatus);
                    return new Result(201, "处理失败");
                }

            } else {
                System.out.println("查询订单状态busiStatus不为13");
                return new Result(202, "处理失败,当前订单状态报错");
            }
        } else {
            return new Result(203, "处理失败,当前制氧机不可以入库");
        }
    }

    /**
     * 9.获取accessToken
     */
    @RequestMapping(value = "/getAccessToken",method = RequestMethod.GET)
    public @ResponseBody
    Result getAccessToken(){
        Integer code;
        String accessToken = TokenUtil.getAccessToken();
        String Ticket=TokenUtil.getJSApiTicket();
        System.out.println("accessToken=="+accessToken);
        System.out.println("Ticket=="+Ticket);
        code = 200;
        return new Result(code,"获取成功");
    }

    /**
     * 10.获取accessToken1
     */
    @RequestMapping(value = "/getAccessToken1",method = RequestMethod.GET)
    public @ResponseBody
    Result getAccessToken1(){
        Integer code;
        String accessToken = TokenUtil.getAccessToken();
        String Ticket=TokenUtil.getJSApiTicket();
        System.out.println("accessToken=="+accessToken);
        System.out.println("Ticket=="+Ticket);
        code = 200;
        return new Result(code,"获取成功");
    }


    /**
     * 11.获取微信公众号信息
     */
    @RequestMapping(value = "/getSign",method = RequestMethod.GET)
    @ResponseBody
    public Object scanJsApi(@Param("tokenUrl") String tokenUrl) {
        Map<String, String> res = tokenService.sign(tokenUrl);
        if(res==null){
            return"获取公众号信息失败";
        }else{
            return res;
        }
    }

    /**
     * 12.驿站管理员查询订单详情
     //* @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectPttion",method = RequestMethod.GET)
    public @ResponseBody
    Result getFSQuanx(@RequestParam("personWeChat")String personWeChat,
                      @RequestParam("pageNum")Integer pageNum,
                      @RequestParam("pageSize")Integer pageSize,
                      @RequestParam(required=false,name="busiStatus")Integer busiStatus){
        //查询驿站管理人员所属驿站编号
        String pttionId = pttionMapper.selectpttionIdByWeChat(personWeChat);
        System.out.println("pttionId=="+pttionId);
        //从哪个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus3(scanNum,pageSize,busiStatus,pttionId);
        Integer total = businessService.selectCount2(busiStatus,pttionId);
        List list = businessList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }


    /**
     * 13.获取制氧机数据
     */
    @RequestMapping(value = "/getOxymachine",method = RequestMethod.GET)
    public @ResponseBody
    Result getOxymachine(@RequestParam("pageNum")Integer pageNum,
                         @RequestParam("pageSize")Integer pageSize){
        //从哪个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<OxyMachineEntity2> OxyMachineEntity2 = oxymachineMapper.selectAllOxymachine2(scanNum,pageSize);
        Integer total = oxymachineMapper.selectAllOxymachine2count3();
        List list = OxyMachineEntity2;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }
}
