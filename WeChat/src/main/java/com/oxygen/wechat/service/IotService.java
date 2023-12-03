//package com.oxygen.wechat.service;
//
//import com.google.gson.Gson;
//import com.ywyl.sh.mqtt.modules.mqtt.config.CmdType;
//import com.ywyl.sh.mqtt.modules.mqtt.config.MyUrlList;
//import com.ywyl.sh.mqtt.modules.mqtt.config.UrlConfigBean;
//import com.ywyl.sh.mqtt.modules.mqtt.controller.IotController;
//import com.ywyl.sh.mqtt.modules.mqtt.dto.*;
//import com.ywyl.sh.mqtt.modules.mqtt.utils.HttpRequestUtil;
//import org.mc.springcloudext.core.CommResponse;
//import org.mc.springcloudext.core.ScExtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
///**
// * Created by baimao
// * Time:2020/6/28
// */
//
//@Service
//public class IotService {
//    private final static Logger logger = LoggerFactory.getLogger(IotService.class);
//
//    @Value("${hwUser.IAMname}")
//    private String IAMname;
//
//    @Value("${hwUser.IAMpassword}")
//    private String IAMpassword;
//
//    @Value("${hwUser.url}")
//    private String tokenUrl;
//
//    @Value("${hwUser.domainName}")
//    private String domainName;
//
//    @Autowired
//    DeviceService deviceService;
//
//    @Autowired
//    UrlConfigBean urlConfigBean;
//
//
//    @Value("${jwt.token}")
//    String token;
//
//    @Autowired
//    MyRedisService myRedisService;
//
//    @Autowired
//    CommandService commandService;
//
//    @Autowired
//    IotCommandService iotCommandService;
//
//    private Integer count = 0;
//
//    private static final  String CHANNEL = "http";
//
//    private static final String CALL_BACK_URL = "http://www.qcopen.cn/yz-mqtt/api/v1/getDeviceData";
//
////    private static final String CALL_BACK_URL = "http://bd9f-183-221-76-141.ngrok.io/api/v1/iot/getDeviceStatusDate";
//
//    //   private static final String RESOURCE = "device.data";
//
//    private static final String RESOURCE = "device.property";
//
//    private static final String EVENT = "report";
//
//    private static final String APP_ID = "c78f51e1321e4641bf5c4272faf2f740";
//
//    private static final String serviceId = "Command";
//
//    public String getToken() throws ScExtException {
//        TokenRequestJsonDto tokenRequestJsonDto = new TokenRequestJsonDto(this.domainName,this.IAMname,this.IAMpassword);
//        String result = null;
//        try {
//            result = HttpRequestUtil.sendPost(tokenUrl,tokenRequestJsonDto.toString(),null,2);
//        } catch (Exception e) {
//            throw new ScExtException("获取令牌失败");
//        }
//        logger.debug(result);
//        return result;
//    }
//
//
//    /**
//     * 创建订阅
//     * @return
//     */
//    public String createSub() throws ScExtException {
//        String token = this.getToken();
//        Gson gson = new Gson();
//        SubDto subDto  = new SubDto();
//        subDto.setApp_id(APP_ID);
//        subDto.setCallbackurl(CALL_BACK_URL);
//        subDto.setChannel(CHANNEL);
//        SubjectDto subjectDto = new SubjectDto(
//                RESOURCE,EVENT
//        );
//        subDto.setSubject(subjectDto);
//        String url = MyUrlList.CREATE_SUBSCRIPTION;
//        String json = gson.toJson(subDto);
//        logger.debug(json);
//        String result = null;
//        try {
//            result = HttpRequestUtil.sendPost(url,json,token,2);
//        } catch (Exception e) {
//            throw new ScExtException("订阅失败");
//        }
//        logger.debug(result);
//        return "success";
//    }
//
//    /**
//     * 创建消息
//     */
//    public String createMessage(String deviceId, CreateMessageDto createMessageDto) throws ScExtException {
//        String token = this.getToken();
//        String result = null;
//        String url = MyUrlList.CREATE_MESSAGE+deviceId+"/messages";
//        Gson gson = new Gson();
//        try {
//            result = HttpRequestUtil.sendPost(url,gson.toJson(new MessageDto("work")),token,2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        logger.debug(result);
//        return "success";
//
//    }
//
//    /**
//     * 获取消息
//     */
//
////    public void getDeviceData(Map map) throws Exception {
////        Gson gson = new Gson();
////        String json = gson.toJson(map).replace("=",":");
////        logger.debug("[DEVICE RECEIVE] :::>>> {}",json);
////        System.out.println("[DEVICE RECEIVE] :::>>>"+json);
////        ReceiveDto receiveDto = gson.fromJson(json,ReceiveDto.class);
////        ReceiveDto.Paras result = receiveDto.getParas();
////        if (result == null){
////            return;
////        }
//
////        DeviceData deviceData = gson.fromJson(json,DeviceData.class);
//    //       String cmd = deviceData.getNotify_data().getServices().get(0).getData().getCmd();
//    //       Integer code = deviceData.getNotify_data().getServices().get(0).getData().getCode();
//    //       String deviceId = deviceData.getNotify_data().getDevice_id();
//    //       ActualData actualData = deviceData.getNotify_data().getServices().get(0).getData().getData();
//    //       String orderId = myRedisService.get(deviceId);
//    //       if (CmdType.GET_STATES.equals(cmd)){
////            CreateMessageDto createMessageDto = new CreateMessageDto("states","work");
////            createMessage(deviceId,createMessageDto);
//    //如果redis中存在状态则
////            if (orderId != null){
////                work(deviceId,orderId);
////            }else {
////                rest(deviceId,actualData.getOrderid());
//    //           }
////        }else if(CmdType.REST.equals(cmd)){
////        }else if (CmdType.WORK.equals(cmd)){
////        }
////        deviceService.insertMachineLog(deviceId,actualData);
////    }
//    /**
//     * 新加功能
//     * 获取华为云的数据
//     * 接收到后返回数据
//     * time:2022/6/17 update
//     * 临时改动
//     */
//    public void getDeviceStatusDate(Map map) throws Exception{
//        Gson gson = new Gson();
//        String json = gson.toJson(map).replace("=",":");
//        logger.info("receive data from iot platform: {}",json);
//        DeviceStatusDate deviceStatusDate = gson.fromJson(json,DeviceStatusDate.class);
//        String deviceId = deviceStatusDate.getNotify_data().getHeader().getDevice_id();
//        DeviceProperties deviceProperty = deviceStatusDate.getNotify_data().getBody().getServices().get(0).getProperty();
//
//        //以下用于收到IOT数据后，插入数据库之前的一些前置处理。
//        try {
//            AlarmCfg alarmCfg = new AlarmCfg();
//            if (deviceProperty.getMode() == 3)//mode为3时出现了一键报警
//            {
//                alarmCfg.setExecute(4);
//                alarmCfg.setConfirm(1);
//                iotCommandService.AlarmService(deviceId, alarmCfg);
//            }
//            if (this.selectBraceletStatus(deviceId).equals("101"))//开启设备
//            {
//                SysCfg sysCfg = new SysCfg();
//                sysCfg.setSysmode(2);
//                sysCfg.setSyslplimit(33);
//                sysCfg.setSyswakeuplimit(35);
//                iotCommandService.SysService(deviceId, sysCfg);
//                this.updateBraceletStatus(deviceId,200);
//            }
//
//            if (this.selectBraceletStatus(deviceId).equals("102"))//关闭设备
//            {
//                SysCfg sysCfg = new SysCfg();
//                sysCfg.setSysmode(1);
//                sysCfg.setSyslplimit(33);
//                sysCfg.setSyswakeuplimit(35);
//                iotCommandService.SysService(deviceId, sysCfg);
//                this.updateBraceletStatus(deviceId,200);
//            }
//        }catch(Exception e){
//            logger.error("",e);
//        }
//        //设备开始连接网络。  deviceProperty.getMode()是Integer，还是可能为空。
//        if(deviceProperty.getMode()!=null&&deviceProperty.getMode() == 4){
//            //等待见bindwxuserywyl
//        }
//        else
//        {
//            String result = deviceService.insertMachineLog(deviceId,deviceProperty);
//            logger.info("iot data persistence status: {}",result);
//        }
//
//
//        // commandService.commandSmart(deviceId,"req");//commandSmart(deviceId,"RedGreen");//(deviceId,"GetInfoSuccess");
//    }
///*    *//**
//     * 下发命令
//     *//*
//    public String createCommand(String deviceId,String cmd,String data) throws ScExtException {
//        Gson gson = new Gson();
//        String time = String.valueOf(System.currentTimeMillis());
//        CommandParasDto commandParasDto = new CommandParasDto(cmd,time,data);
//        CommandDto commandDto = new CommandDto(commandParasDto);
//        String json = gson.toJson(commandDto);
//        String url = MyUrlList.CREATE_COMMAND+deviceId+"/commands";
//        try {
//            String result = HttpRequestUtil.sendPost(url,json,getToken(),2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "success";
//    }
//
//    *//**
//     * 激活手环
//     *//*
//    public String work(String deviceId,String orderId) throws ScExtException {
//        String data = CommandParasDataDto.ORDER_ID+orderId;
//        return createCommand(deviceId,CmdType.WORK,data);
//    }
//
//    *//**
//     * 手动查询手环
//     *//*
//    public String read(String deviceId,String orderId) throws ScExtException {
//        String data = CommandParasDataDto.ORDER_ID+orderId;
//        return createCommand(deviceId,CmdType.READ,data);
//    }
//
//    *//**
//     * 冷启动获取GPS
//     *//*
//    public String cold(String deviceId,String orderId) throws ScExtException {
//        String data = CommandParasDataDto.ORDER_ID+orderId;
//        return createCommand(deviceId,CmdType.COLD,data);
//    }
//
//    *//**
//     * 结束手环
//     *//*
//    public String rest(String deviceId,String orderId) throws ScExtException {
//        String data = CommandParasDataDto.ORDER_ID+orderId;
//        return createCommand(deviceId,CmdType.REST,data);
//    }
//
//    *//**
//     * 通过orderId查order的状态
//     *//*
//    public String selectStatusByOrderId(String orderId) throws ScExtException {
//        Gson gson = new Gson();
//        String url = urlConfigBean.getBackUrlHead() + urlConfigBean.getCrud() + "api/v1/machine/insertMachineLog";
//        String param = "orderId=" + orderId;
//        String result = HttpRequestUtil.sendGet(url, param, this.token, 1);
//        CommResponse<Object> commResponse = gson.fromJson(result, CommResponse.class);
//        if (commResponse.getStatus() == 1) {
//            throw new ScExtException(commResponse.getErrorMessage());
//        }
//        return String.valueOf(commResponse.getBody());
//    }
//
//    *//**
//     * 查看下发命令的状态
//     *//*
//
//    public String selectBraceletStatus(String machineId) throws ScExtException {
//        Gson gson = new Gson();
//        String url = urlConfigBean.getBackUrlHead() + urlConfigBean.getCrud() + "api/v1/product/selectBraceletStatus";
//        String param = "machineId=" + machineId;
//        String result = HttpRequestUtil.sendGet(url, param, this.token, 1);
//        CommResponse<Object> commResponse = gson.fromJson(result, CommResponse.class);
//        if (commResponse.getStatus() == 1) {
//            throw new ScExtException(commResponse.getErrorMessage());
//        }
//        return String.valueOf(commResponse.getBody());
//    }
//
//
//    *//**
//     * 更新下发命令的状态
//     *//*
//
//    public String updateBraceletStatus(String machineId,Integer status) throws ScExtException {
//        Gson gson = new Gson();
//        String url = urlConfigBean.getBackUrlHead() + urlConfigBean.getCrud() + "api/v1/product/updateBraceletStatus";
//        String param = "machineId=" + machineId + "&status="+status;
//        String result = HttpRequestUtil.sendGet(url, param, this.token, 1);
//        CommResponse<Object> commResponse = gson.fromJson(result, CommResponse.class);
//        if (commResponse.getStatus() == 1) {
//            throw new ScExtException(commResponse.getErrorMessage());
//        }
//        return String.valueOf(commResponse.getBody());
//    }*/
//
//    /**
//     * name:zhangYilin
//     * time:2022/9/2
//     * 得知设备是否在线
//     */
//    /*public void getDeviceOnline(Map map) throws Exception {
//        Gson gson = new Gson();
//
//        String json = gson.toJson(map).replace("=", ":");
//        logger.info("receive data from iot platform: {}", json);
//        DeviceStatusOnlineDate deviceStatusDate = gson.fromJson(json, DeviceStatusOnlineDate.class);
//        String deviceId = deviceStatusDate.getNotify_data().getHeader().getDevice_id();
//        DeviceOnlineBody deviceOnlineBody = deviceStatusDate.getNotify_data().getBody();
//        if (deviceOnlineBody.getStatus().equals("ONLINE"))
//        {
//            // 2022-09-08 注释掉测试
////            //联网后做开启和关闭处理。
////            try {
////                AlarmCfg alarmCfg = new AlarmCfg();
////                if (this.selectBraceletStatus(deviceId).equals("101"))//开启设备
////                {
////                    SysCfg sysCfg = new SysCfg();
////                    sysCfg.setSysmode(2);
////                    sysCfg.setSyslplimit(33);
////                    sysCfg.setSyswakeuplimit(35);
////                    iotCommandService.SysService(deviceId, sysCfg);
////                    this.updateBraceletStatus(deviceId,200);
////                }
////
////                if (this.selectBraceletStatus(deviceId).equals("102"))//关闭设备
////                {
////                    SysCfg sysCfg = new SysCfg();
////                    sysCfg.setSysmode(1);
////                    sysCfg.setSyslplimit(33);
////                    sysCfg.setSyswakeuplimit(35);
////                    iotCommandService.SysService(deviceId, sysCfg,"IOTDA.014111");
////                    this.updateBraceletStatus(deviceId,200);
////                }
////            }catch(Exception e){
////                logger.error("",e);
////            }
//
//            logger.info(deviceId + "设备在线");
//
//        }*/
//    }
//
//}
//
