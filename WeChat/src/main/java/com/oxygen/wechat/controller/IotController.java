package com.oxygen.wechat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.oxygen.wechat.dto.Result;
//import com.oxygen.wechat.service.IotService;
import com.oxygen.wechat.mapper.BusinessMapper;
import com.oxygen.wechat.mapper.OxymachineMapper;
import com.oxygen.wechat.service.OxymachineService;
import com.oxygen.wechat.util.HttpRequestUtil;
import com.oxygen.wechat.util.OxymachineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/iot")
public class IotController {
    private final static Logger logger = LoggerFactory.getLogger(IotController.class);


    @Autowired
    OxymachineService oxymachineService;

    @Autowired
    BusinessMapper businessMapper;

//    @Autowired
//    DeviceService deviceService;
//
//    @Autowired
//    UrlConfigBean urlConfigBean;
//    @Autowired
//    CommandService commandService;

//    @Value("${jwt.token}")
//    String token;

    @Value("${mqtt.server}")
    private String mqttServer;
    @Value("${mqtt.token}")
    private String mqttServerToken;

    @GetMapping("server")
    public @ResponseBody
    Result getServer(HttpServletRequest httpServletRequest,
                     @RequestParam("token")String token) throws Exception {
        if(!mqttServerToken.equals(token)){
//            throw new Exception("token invalid");
            System.out.println("连接失败");
            return new Result(201,"连接失败");
        }
        //return toJson(mqttServer,httpServletRequest);
        System.out.println("连接成功");
        return new Result(200,"连接成功");
    }

//    @GetMapping("getToken")
//    public @ResponseBody
//    String getToken(HttpServletRequest httpServletRequest) throws Exception {
//        return toJson(iotService.getToken(),httpServletRequest);
//    }

    @PostMapping("insertMachinesTest")
    public @ResponseBody void insertMachines(@RequestBody Map map,
                                               HttpServletRequest request,HttpServletResponse response) throws Exception {
//        System.out.println(token);
//        CommResponse<IotMachineResultDto> result = new Gson().fromJson(HttpRequestUtil.sendGet(urlConfigBean.getBackUrlHead()+urlConfigBean.getCrud()+"api/v1/machine/insertMachines","machineId="+machineId,token,1),CommResponse.class);
//        if (result.getStatus()==1){
//            return toJson("添加设备失败或设备ID重复",request);
//        }
//        return result.getBody();
        System.out.println(map+"UID获取成功");
        Map notify_data = (Map)map.get("notify_data");
        System.out.println(notify_data+"notify_data获取成功");
        Map body = (Map)notify_data.get("body");
        System.out.println("body获取成功"+body);
        List services = (List) body.get("services");
        System.out.println("services获取成功"+services);
        Map properties0 = (Map)services.get(0);
        System.out.println("properties0获取成功"+properties0);
        Map properties = (Map)properties0.get("properties");
        System.out.println("properties获取成功"+properties);

        Integer UID = (Integer) properties.get("UID");
        System.out.println("UID获取成功"+UID);
        Double Latitude = (Double) properties.get("Latitude");
        System.out.println("Latitude获取成功"+Latitude);
        Double Longitude = (Double) properties.get("Longitude");
        System.out.println("Longitude获取成功"+Longitude);

        String strUID = "qczltoken"+UID.toString();

//        Integer num = oxymachineService.selectOxymachine2(UID);
//        System.out.println(num+"num");
        //判断制氧机状态是否异常
//        if(oxymachineService.selectOxymachineStatus(strUID)<=0){
//            //更新制氧机设备状态为9非正常状态
//            //oxymachineService.updateOxymachineOmStatus(strUID, OxymachineUtil.Abnormal);
//        }
        //获取当前时间
        Date date = new Date();
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowIotDate = sdf.format(date);

        //查询当前制氧机状态
        Integer omStatus = oxymachineService.selectOxymachineomStatus(strUID);

        //判断制氧机是否被使用
        if(omStatus==2 || omStatus==3 || omStatus==4 || omStatus==5){
            //查询使用用户
            String omUser = businessMapper.selectUserByBusiMid(strUID);
            boolean bl = oxymachineService.insertOxymachine3(strUID,Latitude,Longitude,nowIotDate,omStatus,omUser);
        }else {
            String omUser = "非正常使用";
            boolean bl = oxymachineService.insertOxymachine3(strUID,Latitude,Longitude,nowIotDate,omStatus,omUser);
        }

//// 设置HTTP状态码为 200
        String msg = "success";
        String json = new Gson().toJson(msg);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping(value = "/demo",method = RequestMethod.GET)
    public @ResponseBody
    String selectBusFiveFSQuanx() throws JsonProcessingException {
//        //查询当前驿站对应的管理员
//        String openId = managerMapper.selectPttionManagerOpenId(busiPstation2);
        //给驿站管理员推送消息
        System.out.println(111);
        Map<String,Object> properties = new HashMap<String,Object>();
        properties.put("first", "等待匹配制氧机");
        properties.put("keyword1", "111");
        properties.put("keyword2", "您有一个新的订单，请尽快进入‘我的订单’处理");
        properties.put("keyword3", "111");
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
                "&open_id=o6jxus0oivImaPVOm_uff_mg71MA";
        String string = HttpRequestUtil.sendPost(url,param);
        return string;
    }
//    @ApiOperation("订阅")
//    @GetMapping("subDevice")
//    public @ResponseBody String subDevice(HttpServletRequest request) throws Exception {
//        return toJson(iotService.createSub(),request);
//    }

//    @PostMapping("getDeviceData")
//    public @ResponseBody String getDeviceData(@RequestBody Map map,
    //                                             HttpServletRequest request) throws Exception {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        iotService.getDeviceData(map);
//        return toJson("insertDevice",request);
//    }

//    /**
//     * 新加功能：接受华为云的数据
//     * 更新功能：接受后返回GetInfoSuccess
//     * 就是调用command的接口
//     * name:zhangyilin
//     * time:2021/9/10
//     * @param map
//     * @param request
//     * @return
//     * @throws Exception
//     */
//
//    @PostMapping("getDeviceStatusDate")
//    public @ResponseBody String getDeviceStatusDate(@RequestBody Map map,
//                                                    HttpServletRequest request) throws  Exception {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        //将map放入阻塞队列
//        queueManager.addQueue(map);
//
//        return toJson("updateDevice",request);
//    }
//    @GetMapping("work")
//    public @ResponseBody String work(@RequestParam("deviceId")String deviceId,
//                                     HttpServletRequest request) throws ScExtException {
//        return toJson(iotService.work(deviceId,"111"),request);
//    }
//
//    @GetMapping("read")
//    public @ResponseBody String read(@RequestParam("deviceId")String deviceId,
//                                     HttpServletRequest request) throws ScExtException {
//        return toJson(iotService.read(deviceId,"111"),request);
//    }
//
//    @GetMapping("rest")
//    public @ResponseBody String rest(@RequestParam("deviceId")String deviceId,
//                                     HttpServletRequest request) throws ScExtException {
//        return toJson(iotService.rest(deviceId,"111"),request);
//    }
//
//    @GetMapping("insertDevice")
//    public @ResponseBody String insertDevice(@RequestParam("machineId")String machineId,
//                                             HttpServletRequest request) throws Exception {
//        return toJson(deviceService.insertDevice(machineId),request);
//    }
//
//    /**
//     * name:zhangyilin
//     * time:2021/9/21
//     * 用于测试与华为云的连接， 推送
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("test")
//    public @ResponseBody String test(HttpServletRequest request) throws  Exception {
//        return toJson("200", request);
//    }
//    /**
//     * name:zhangyilin
//     * time:2021/9/21
//     * 测试设备发送消息
//     */
//    @GetMapping("deviceTest")
//    public @ResponseBody String deviceTest(@RequestParam("deviceId")String deviceId,
//                                           HttpServletRequest request)throws Exception{
//        return toJson(deviceService.testDevice(deviceId),request);
//    }
//    /**
//     * name:zhangyilin
//     * time:2021/10/2
//     * 新加功能
//     * 对手环进行同步控制
//     */
//    @GetMapping("commandSmart")
//    public  @ResponseBody String commandSmart(@RequestParam("deviceId")String deviceId,
//                                              @RequestParam("command")String command,
//                                              HttpServletRequest request)throws Exception{
//        return toJson(commandService.commandSmart(deviceId,command),request);
//    }
//
//    @GetMapping("selectstatus")
//    public @ResponseBody String selectId(@RequestParam("machineId")String machineId,
//                                         @RequestParam("status")Integer status,
//                                         HttpServletRequest request) throws ScExtException {
//        return toJson(iotService.updateBraceletStatus(machineId,status),request);
//    }
//
//    /**
//     * 新加功能：接受华为云的数据
//     * 更新功能：接受后返回GetInfoSuccess
//     * 就是调用command的接口
//     * name:zhangyilin
//     * time:2022/9/2
//     * @param map
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("getDeviceOnlineStatusDate")
//    public @ResponseBody String getDeviceOnlineStatusDate(@RequestBody Map map,
//                                                          HttpServletRequest request) throws  Exception {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        iotService.getDeviceOnline(map);
//        return toJson("updateDeviceOnline",request);
//    }

}
