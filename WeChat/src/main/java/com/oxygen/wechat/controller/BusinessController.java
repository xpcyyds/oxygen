package com.oxygen.wechat.controller;

import com.oxygen.wechat.dto.Result;
import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.PersonEntity;
import com.oxygen.wechat.entity.PttionEntity;
import com.oxygen.wechat.mapper.BusinessMapper;
import com.oxygen.wechat.mapper.PttionMapper;
import com.oxygen.wechat.service.BusinessService;
import com.oxygen.wechat.service.FSService;
import com.oxygen.wechat.service.OxymachineService;
import com.oxygen.wechat.service.PttionService;
import com.oxygen.wechat.util.BusiStatusUtil;
import com.oxygen.wechat.util.OxymachineUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
@RequestMapping("api/v1/business")
public class BusinessController {
    @Autowired
    FSService fsService;

    @Autowired
    BusinessService businessService;

    @Autowired
    BusinessMapper businessMapper;

    @Autowired
    PttionMapper pttionMapper;

    @Autowired
    OxymachineService oxymachineService;

    @Autowired
    PttionService pttionService;

    /**
     * 1.查询当前游客有没有下过单
     * @param personWeChat
     * @return
     */
    @RequestMapping(value = "/selectCountByWeChat",method = RequestMethod.GET)
    public @ResponseBody
    boolean getStatus1(@RequestParam("personWeChat")String personWeChat){
        boolean bl = businessService.selectFSLeaseByWeChat(personWeChat);
        return bl;
    }

    /**
     * 2.查询当前游客是否正在租借的信息
     * @param personWeChat
     * //@param personName
     * //@param personMobile
     * @return
     */
    @RequestMapping(value = "/selectByWeChat",method = RequestMethod.GET)
    public @ResponseBody
    Result getStatus(@RequestParam("personWeChat")String personWeChat){
        boolean bl = fsService. selectFSLease(personWeChat);
        if(bl){
            return new Result(200,"当前有租借信息");
        }else {
            return new Result(201,"当前没有租借信息");
        }
    }

    /**
     * 3. 查询当前订单详情
     * @param personWeChat
     * @return
     */
    @RequestMapping(value = "/selectBusMsgByWeChat",method = RequestMethod.GET)
    public @ResponseBody
    Result getBusinessMsg(@RequestParam("personWeChat")String personWeChat)throws ParseException,NullPointerException{

        //
        boolean bl = fsService.selectFSLease(personWeChat);
        if(bl){
            Map data = fsService.selectFSSearch(personWeChat);
            System.out.println(data);
            return new Result(200,"查询成功",data);
        }else {
            return new Result(201,"当前无订单");
        }

    }


    /**
     * 4.登记新的订单(下单模式)
     * @param personWeChat
     * @param personName
     * @param personMobile
     * @param busiAdd
     * @return
     */
    @RequestMapping(value = "/insertBusiness1",method = RequestMethod.GET)
    public @ResponseBody Result insertBusiness(@RequestParam("personWeChat")String personWeChat,
                                              @RequestParam("personName")String personName,
                                              @RequestParam("personMobile")String personMobile,
                                               @RequestParam("busiArea")String busiArea,
                                                @RequestParam("busiAdd")String busiAdd){

//        DateTime time =new DateTime(); //获取当前时间
//        System.out.println(time);  //输出当前时间
//查询当前用户是否有缓存订单
        Integer num = businessMapper.selectFSInbusi2Num1(personWeChat);
        if(num>=1){
            //删除缓存
            businessMapper.deleteFSInbusi2ByWeChat(personWeChat);
        }
        boolean bl = fsService. selectFSLease(personWeChat);
        if(bl){
            return new Result(200,"当前有租借信息");
        }else{
            System.out.println("进入");
            //查询驿站是否有库存
            String area = busiArea.replace("、","");
            List<PttionEntity> list = pttionService.selectPttionIdByBusiArea(area);
            System.out.println(list);
            //判断是否有驿站
            if(list.isEmpty()){
                return new Result(202,busiArea+"区域没有驿站");
            }
            String pttionId = list.get(0).getPttionId();
            System.out.println("pttionId==="+pttionId);
            Integer num1=0;
            boolean bl1 = false;
            for(int i = 0 ;i < list.size() ;++i){
                num1 = list.get(i).getPttionExtend1();
                System.out.println("num===="+num);
                if(num1>=1){
                    bl1 = true;
                    break;
                }
            }
            if(!bl1){
                return new Result(202,busiArea+"区域驿站没有库存");
            }

            //添加缓存，获取订单号
            String busiId = fsService.insertFSInbusi(personWeChat,personName,personMobile,busiArea,busiAdd);
            if(busiId.isEmpty()){
                /*
                添加操作记录（下单）
                 */
                return new Result(201,"处理失败");
            }else {
                // 创建一个HashMap对象
                Map<String, Object> data = new HashMap<>();
                // 添加键值对
                data.put("busiId", busiId);

                return new Result(200,"处理成功",data);
            }
        }

    }

    /**
     * 4.登记新的订单(自提模式)
     * @param personWeChat
     * @param personName
     * @return
     */
    @RequestMapping(value = "/insertBusiness2",method = RequestMethod.GET)
    public @ResponseBody Result insertBusiness2(@RequestParam("personWeChat")String personWeChat,
                                               @RequestParam("personName")String personName,
                                               @RequestParam("personMobile")String personMobile,
                                                @RequestParam("busiPstation1")String busiPstation1
//                                               @RequestParam("busiArea")String busiArea,
//                                               @RequestParam("busiAdd")String busiAdd
                                           ){

//        DateTime time =new DateTime(); //获取当前时间
//        System.out.println(time);  //输出当前时间
        boolean bl = fsService. selectFSLease(personWeChat);
        if(bl){
            return new Result(200,"当前有租借信息");
        }else{
            boolean bl1 = fsService.insertFSInbusi0(personWeChat,personName,personMobile,busiPstation1);
            //驿站等待处理数加一
            boolean bl2 = pttionMapper.updatePttionExtend3(busiPstation1);
            if(bl1){
                /*
                添加操作记录（下单）
                 */
                return new Result(200,"处理成功");
            }else {
                return new Result(201,"处理失败");
            }
        }

    }

    /**
     * 3. 判断当前订单详情是否支付成功
     * @return
     */
    @RequestMapping(value = "/selectPayIsSuccess",method = RequestMethod.GET)
    public @ResponseBody
    String  getBusinessMsg(@RequestParam("tradeno")String tradeno,@RequestParam("status")String status){
        System.out.println("支付是否成功判断"+status);
        System.out.println("支付订单号"+tradeno);

        if(status.equals("success")||status.equals("1")){
            if(tradeno.startsWith("YJ")){
                String busiId = tradeno.replace("YJ","");
                //获取缓存中的数据
                BusinessEntity businessEntity = businessMapper.selectFSInbusi2(busiId);
                //更新订单状态
                businessMapper.insertFSInbusi(businessEntity.getBusiId(),businessEntity.getBusiWechat(),businessEntity.getBusiName(),
                        businessEntity.getBusiNumber(),businessEntity.getBusiArea(),businessEntity.getBusiAdd(),businessEntity.getBusiPdate(),businessEntity.getBusiType());
                //删除缓存中的数据
                businessMapper.deleteFSInbusi2(busiId);
                System.out.println("押金支付成功");
                return "ok";
            }else if(tradeno.startsWith("GH")){
                String busiId = tradeno.replace("GH","");
                //获取缓存中的数据
                BusinessEntity businessEntity = businessMapper.selectFSInbusi2(busiId);
                System.out.println("获取缓存中的数据成功");
                System.out.println(businessEntity);
                //更新订单状态
                fsService.updateFSGuihuan(businessEntity.getBusiWechat(), businessEntity.getBusiReturnArea(),businessEntity.getBusiReturnAdd(),
                        businessEntity.getBusiRdate(),businessEntity.getBusiFee());
                System.out.println("更新订单数据成功");
                //删除缓存中的数据
                businessMapper.deleteFSInbusi2(busiId);
                System.out.println("归还付款成功");
                return "ok";
            }else if(tradeno.startsWith("GM")){
                String busiId = tradeno.replace("GM","");
                //通过订单号查询用户
                String personWeChat = businessMapper.selectFSInbusiBybusiId(busiId);
                //更改订单信息
                boolean bl = fsService.insertFSSail(personWeChat);
                //查询制氧机编号
                String busiMid = businessMapper.selectBusiMidBybusiId(busiId);
                //更改制氧机状态omStatus改为7游客购买
                boolean bl1 = oxymachineService.updateOxymachineOmStatus(busiMid, OxymachineUtil.Purchased);
                System.out.println("购买付款成功");
                return "ok";
            }else {
                return "error";
            }
        }else {
            if(tradeno.startsWith("YJ")){
                String busiId = tradeno.replace("YJ","");
                System.out.println("押金支付失败");
//                //删除订单
//                businessMapper.deleteFSInbusi0(busiId);
                return "error";
            }else if(tradeno.startsWith("GH")){
                System.out.println("归还付款失败");
                return "error";
            }else if(tradeno.startsWith("GM")){
                System.out.println("购买付款失败");
                return "error";
            }else {
                return "error";
            }
        }
    }

    /**
     *5. 游客归还制氧机下单
     * @param personWeChat
     * @param
     * @param
     * @param busiReturnAdd
     * @return
     */
    @RequestMapping(value = "/returnFSGuihuan",method = RequestMethod.GET)
    public @ResponseBody
    Result  updateFSGuihuan(@RequestParam("personWeChat")String personWeChat,
                            @RequestParam("busiReturnArea")String busiReturnArea,
                           @RequestParam("busiReturnAdd")String busiReturnAdd)throws ParseException,NullPointerException{

        Integer num = businessMapper.selectFSInbusi2Num2(personWeChat);
        if(num>=1){
            //删除缓存
            businessMapper.deleteFSInbusi2ByWeChat(personWeChat);
        }
        if(businessMapper.selectBusByBusiStatusWechat(personWeChat,BusiStatusUtil.Return_Order)==0&&
                (businessMapper.selectBusByBusiStatusWechat(personWeChat,BusiStatusUtil.Using)==1||
                businessMapper.selectBusByBusiStatusWechat(personWeChat,BusiStatusUtil.Service)==1)){
            //添加订单的归还下单时间
            Date date = new Date();
            //format的格式可以任意
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String busiRdate = sdf.format(date);

            //获取订单使用时间
            Map data = fsService.selectFSSearch(personWeChat);
            String busiAdate = (String)data.get("busi_Adate");
            //计算费用
            Integer busiFee = fsService.selectFSFee(busiAdate,busiRdate);
            System.out.println("当前费用为"+busiFee);
            //通过用户查询订单号
            String busiId = businessMapper.selectFSInbusiBypersonWeChat(personWeChat);
            //添加缓存订单
            businessMapper.insertFSInbusi2ById(busiId,personWeChat);
            boolean bl = businessMapper.updateFSGuihuan2(busiId, busiReturnArea,busiReturnAdd,busiRdate,busiFee);

            if(bl){
                System.out.println("归还下单成功");
                return new Result(200,"处理成功");
            }else {
                System.out.println("归还下单失败");
                return new Result(201,"处理失败");
            }
        }else {
            System.out.println("归还下单失败,已经存在订单");
            return new Result(201,"处理失败");
        }
    }

    /**
     * 6.游客直接购买
     * @param personWeChat
     * @return
     */
    @RequestMapping(value = "/insertSailBusiness",method = RequestMethod.GET)
    public @ResponseBody Result insertSailBusiness(@RequestParam("personWeChat")String personWeChat
//                                                @RequestParam("personName")String personName,
//                                                @RequestParam("personMobile")String personMobile,
//                                                @RequestParam("busiAdd")String busiAdd,
//                                                   @RequestParam("busiArea")String busiArea
                                                   ){

        boolean bl1 = fsService.selectFSLease(personWeChat);
        if(bl1==false){
            return new Result(203,"当前没有租借信息");
        }

        //if(bl){
            return new Result(200,"处理成功");
//        }else {
//            return new Result(201,"处理失败");
//        }
    }
}
