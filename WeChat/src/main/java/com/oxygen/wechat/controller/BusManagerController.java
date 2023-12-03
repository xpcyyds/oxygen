package com.oxygen.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxygen.wechat.dto.Result;
import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.mapper.BusinessMapper;
import com.oxygen.wechat.mapper.ManagerMapper;
import com.oxygen.wechat.mapper.PttionMapper;
import com.oxygen.wechat.service.BusinessService;
import com.oxygen.wechat.service.FSService;
import com.oxygen.wechat.service.ManagerService;
import com.oxygen.wechat.service.OxymachineService;
import com.oxygen.wechat.util.BusiStatusUtil;
import com.oxygen.wechat.util.HttpRequestUtil;
import com.oxygen.wechat.util.OxymachineUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("api/v2/busManager")
public class BusManagerController {
    @Autowired
    FSService fsService;

    @Autowired
    ManagerService managerService;

    @Autowired
    BusinessService businessService;

    @Autowired
    PttionMapper pttionMapper;

    @Autowired
    BusinessMapper businessMapper;

    @Autowired
    OxymachineService oxymachineService;

    @Autowired
    ManagerMapper managerMapper;
    /**
     * 1.处理新的订单busistatus=0
     //* @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectBus",method = RequestMethod.GET)
    public @ResponseBody
    Result selectFSQuanx(//@RequestParam("query")String query,
                      @RequestParam("pageNum")Integer pageNum,
                      @RequestParam("pageSize")Integer pageSize,
                        @RequestParam("busiType")Integer busiType){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        if(busiType==2){
            List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize,BusiStatusUtil.Lease_Order);
            List list = businessList;
            Integer total = businessService.selectCount(BusiStatusUtil.Lease_Order);
            return new Result(200,"获取成功",list,total);
        }else {
            List<BusinessEntity> businessList = businessService.selectBus1(scanNum,pageSize,BusiStatusUtil.Lease_Order,busiType);
            List list1 = businessList;
            Integer total1 = businessService.selectCount1(BusiStatusUtil.Lease_Order,busiType);
            return new Result(200,"获取成功",list1,total1);
        }


    }


    /**
     * 2.处理新的订单busistatus=7
     //* @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectPttion",method = RequestMethod.GET)
    public @ResponseBody
    Result getFSQuanx(//@RequestParam("query")String query,
                                    @RequestParam("pageNum")Integer pageNum,
                                    @RequestParam("pageSize")Integer pageSize){
        //从哪个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize,BusiStatusUtil.Matching_Pttion);
        Integer total = businessService.selectCount(BusiStatusUtil.Matching_Pttion);
        List list = businessList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }

    /**
     * 3.匹配驿站将busiStatus改为7
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/updateBus",method = RequestMethod.GET)
    public @ResponseBody
    Result updateFSQuanx(@RequestParam("busiId")String busiId,
                         @RequestParam("pttionId")String pttionId) throws JsonProcessingException {
        //更改订单状态
        boolean bl = businessService.updateBus(busiId,BusiStatusUtil.Matching_Pttion);

        //添加发出驿站信息
        boolean bl1 = businessService.updateBusiPstation1(busiId,pttionId);
        //驿站等待处理数加一
        boolean bl2 = pttionMapper.updatePttionExtend3(pttionId);

        //查询当前驿站对应的管理员
        String openId = managerMapper.selectPttionManagerOpenId(pttionId);
        //给驿站管理员推送消息

        //获取当前时间
        Date date = new Date();
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(date);

        Map<String,Object> properties = new HashMap<String,Object>();
        properties.put("first", "等待匹配制氧机");
        properties.put("keyword1", busiId);
        properties.put("keyword2", "您有一个新的订单，请尽快进入我的订单处理");
        properties.put("keyword3", nowDate);
        properties.put("remark", "成功");

        //转为JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(properties);

        jsonString = URLEncoder.encode(jsonString);

        String url = "http://pay.qcopen.cn/message/wechat/push";
        String param = "client_id=2000" +
                "&template_id=hZoFSZJpsZlmZL9xN6oGDfmqoy1Aci3unxwkvbglxuI" +
                "&timestamp=" +System.currentTimeMillis()+
                "&data=" +jsonString+
                "&sign=1" +
                "&url=http:www.baidu.com" +
                "&open_id="+openId;
        HttpRequestUtil.sendPost(url,param);

        if(bl&&bl1&&bl2){
            return new Result(200,"处理成功");
        }else {
            return new Result(201,"处理失败");
        }
    }

    /**
     * 4.扫码枪扫码将busiStatus改为8
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/scanCodeBus",method = RequestMethod.GET)
    public @ResponseBody
    Result scanCodeBusFSQuanx(@RequestParam("busiId")String busiId){
        //更改订单状态
        boolean bl = businessService.updateBus(busiId,BusiStatusUtil.Scan_Code_Bus);

        //添加制氧机信息
        if(bl){
            return new Result(200,"处理成功");
        }else {
            return new Result(201,"处理失败");
        }
    }

    /**
     * 5.查询所有busiStatus为8的订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectBusEight",method = RequestMethod.GET)
    public @ResponseBody
    List<BusinessEntity> selectBusEightFSQuanx(//@RequestParam("query")String query,
                                    @RequestParam("pageNum")Integer pageNum,
                                    @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize, BusiStatusUtil.Scan_Code_Bus);
        return businessList ;
    }

    /**
     * 6.匹配跑腿成功将busiStatus改为9
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/matchRunnerBus",method = RequestMethod.GET)
    public @ResponseBody
    Result matchRunnerBusFSQuanx(@RequestParam("busiId")String busiId){
        boolean bl = businessService.updateBus(busiId,BusiStatusUtil.Match_Runner);

        String pttionExtend1 = businessMapper.selectbusiPstation1BybusiId(busiId);
        System.out.println(pttionExtend1);
        //在驿站信息表 pttion里对应该驿站将将字段pttionExtend1的值减1
        pttionMapper.updatePttionExtend1(pttionExtend1);
        if(bl){
            return new Result(200,"处理成功");
        }else {
            return new Result(201,"处理失败");
        }
    }

    /**
     * 7.查询所有busiStatus为3的订单(归还单)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectBusThree",method = RequestMethod.GET)
    public @ResponseBody
    Result selectBusThreeFSQuanx(//@RequestParam("query")String query,
                                               @RequestParam("pageNum")Integer pageNum,
                                               @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize, BusiStatusUtil.Return_Order);
        Integer total = businessService.selectCount(BusiStatusUtil.Return_Order);
        List list = businessList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }

    /**
     * 8.处理归还订单将busiStatus改为11
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/processReturnBus",method = RequestMethod.GET)
    public @ResponseBody
    Result processReturnBusFSQuanx(@RequestParam("busiId")String busiId,
                                   @RequestParam("pttionId")String busiPstation2) throws JsonProcessingException {


        //查询当前订单状态是否为3归还下单
        Integer num = businessService.selectBusByBusiStatus(busiId,BusiStatusUtil.Return_Order);
        if(num==1){
            System.out.println("查询当前订单状态为3归还下单");
            //更改订单状态
            boolean bl1 = businessService.updateBus(busiId,BusiStatusUtil.Return_Order_Succeeded);
            //添加归还驿站信息
            boolean bl2 = businessService.updateBusiPstation2(busiId,busiPstation2);

            //查询当前驿站对应的管理员
            String openId = managerMapper.selectPttionManagerOpenId(busiPstation2);
            //给驿站管理员推送消息
            //获取当前时间
            Date date = new Date();
            //format的格式可以任意
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sdf.format(date);

            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put("first", "等待制氧机送达");
            properties.put("keyword1", busiId);
            properties.put("keyword2", "您有一个新的归还快递，请注意查收");
            properties.put("keyword3", nowDate);
            properties.put("remark", "成功");

            //转为JSON格式
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(properties);

            jsonString = URLEncoder.encode(jsonString);

            String url = "http://pay.qcopen.cn/message/wechat/push";
            String param = "client_id=2000" +
                    "&template_id=hZoFSZJpsZlmZL9xN6oGDfmqoy1Aci3unxwkvbglxuI" +
                    "&timestamp=" +System.currentTimeMillis()+
                    "&data=" +jsonString+
                    "&sign=1" +
                    "&url=http:www.baidu.com" +
                    "&open_id="+openId;
            HttpRequestUtil.sendPost(url,param);
            //添加制氧机信息
            if(bl1){
                return new Result(200,"处理成功");
            }else {
                return new Result(201,"处理失败");
            }
        }
        System.out.println("查询当前订单状态不为3归还下单");
        return new Result(202,"当前订单状态不为归还下单3");
    }

    /**
     * 9.处理游客直接购买单将busiStatus改为10
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/processPurchaseBus",method = RequestMethod.GET)
    public @ResponseBody
    Result processPurchaseBusFSQuanx(@RequestParam("busiId")String busiId,
                                     @RequestParam("busiMid")String busiMid) {
        long timestamp = System.currentTimeMillis();
        String url = "http://pay.qcopen.cn/refund/apply?client_id=2000&client_trade_no=YJ" + busiId + "&total_fee=1&timestamp=" + timestamp + "&sign=1";
        String result = HttpRequestUtil.sendGet(url);
        System.out.println("result" + result);
        JSONObject json = JSONObject.fromObject(result);
        String refundstatus = json.getString("errorMessage");

        if (refundstatus.equals("500 null")) {
            //更改订单状态
            boolean bl = businessService.updateBus(busiId, BusiStatusUtil.Direct_Purchase);
            System.out.println("更改订单状态成功");
            //更改制氧机状态omStatus改为7购买
            boolean bl1 = oxymachineService.updateOxymachineOmStatus(busiMid, OxymachineUtil.Purchased);
            System.out.println("购买更改制氧机状态成功");
            //添加制氧机信息
            if (bl) {
                return new Result(200, "处理成功");
            } else {
                return new Result(201, "处理失败");
            }
        }
        return new Result(201, "处理失败");
    }

    /**
     * 10.查询所有busiStatus为5的订单(购买单)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectBusFive",method = RequestMethod.GET)
    public @ResponseBody
    Result selectBusFiveFSQuanx(//@RequestParam("query")String query,
                                               @RequestParam("pageNum")Integer pageNum,
                                               @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize, BusiStatusUtil.Tourists_Buy);
        Integer total = businessService.selectCount(BusiStatusUtil.Tourists_Buy);
        List list = businessList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }

    /**
     * 11.查询所有busiStatus为11的订单(归还快递下单成功)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectBusEle",method = RequestMethod.GET)
    public @ResponseBody
    Result selectBusEleFSQuanx(//@RequestParam("query")String query,
                                @RequestParam("pageNum")Integer pageNum,
                                @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize, BusiStatusUtil.Return_Order_Succeeded);
        Integer total = businessService.selectCount(BusiStatusUtil.Return_Order_Succeeded);
        List list = businessList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }


    /**
     * 8.处理归还订单将busiStatus改为13
     * @param busiId
     * @return
     */
    @RequestMapping(value = "/returnDeliveredBus",method = RequestMethod.GET)
    public @ResponseBody
    Result processReturnDeliveredFSQuanx(@RequestParam("busiId")String busiId,
                                         @RequestParam("busiMid")String busiMid){


        //查询当前订单状态是否为3归还下单
        Integer num = businessService.selectBusByBusiStatus(busiId,BusiStatusUtil.Return_Order_Succeeded);
        if(num==1){
            System.out.println("查询当前订单状态为11归还快递下单成功");
            //更改订单状态
            boolean bl1 = businessService.updateBus(busiId,BusiStatusUtil.Return_Delivered_Succeeded);
            //更改制氧机状态omStatus改为5归还途中
            boolean bl2 = oxymachineService.updateOxymachineOmStatus(busiMid, OxymachineUtil.Back_Way);
            if(bl1){
                return new Result(200,"处理成功");
            }else {
                return new Result(201,"处理失败");
            }
        }
        System.out.println("查询当前订单状态不为11归还快递下单成功");
        return new Result(202,"当前订单状态不为归还快递下单成功11");
    }
    /**
     * 11.查询所有busiStatus为12的订单(归还快递已经取件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectBusTwl",method = RequestMethod.GET)
    public @ResponseBody
    Result selectBusTwlFSQuanx(//@RequestParam("query")String query,
                               @RequestParam("pageNum")Integer pageNum,
                               @RequestParam("pageSize")Integer pageSize){
        //从那个数开始查询
        Integer scanNum = pageSize*(pageNum-1);
        List<BusinessEntity> businessList = businessService.selectBus(scanNum,pageSize, BusiStatusUtil.Return_Package_picked);
        Integer total = businessService.selectCount(BusiStatusUtil.Return_Package_picked);
        List list = businessList;
        System.out.println("total===="+total);
        return new Result(200,"获取成功",list,total);
    }






    /**
     * 4.查询商品操作
     * @param queryPage
     * @return
     */
//    @RequestMapping(value = "/getGoods",method = RequestMethod.GET)
//    public Result getByName(QueryPage queryPage) {
//        logger.info("query==="+queryPage.getQuery()+"pageNum==="+queryPage.getPageNum()+"pageSize==="+queryPage.getPageSize());
////        Lis t<Goods> goodsList =  goodsService.getByName(goodsName);
//        //1.进行模糊查询并利用wrapper容器进行
//        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
//        wrapper.like("goods_Name",queryPage.getQuery());
//        //2.接收wrapper容器中的数据进行分页操作
//        IPage page = new Page(queryPage.getPageNum(),queryPage.getPageSize());
//        goodsDao.selectPage(page,wrapper);
//        //3.用goodList来接收分页操作后的数据
//        List<Goods> goodsList =  page.getRecords();
//        logger.info("分页查询数据成功");
//
//        Integer code = goodsList != null ? Code.GET_OK : Code.GET_ERR;
//        String msg = goodsList != null ? "" : "数据查询失败，请重试！";
//        //4.搜索进行模糊查询后的数据总数
//        Long total = goodsDao.selectCount(wrapper);
//        logger.info("数据总数==="+total);
//        return new Result(code, goodsList, msg, total);
    //}
}
