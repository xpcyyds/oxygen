package com.oxygen.wechat.service;

import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.ParameterEntity;
import com.oxygen.wechat.entity.PersonEntity;
import com.oxygen.wechat.entity.PttionEntity;
import com.oxygen.wechat.mapper.*;
import com.oxygen.wechat.util.BusiIsStatusUtil;
import com.oxygen.wechat.util.BusiStatusUtil;
import com.oxygen.wechat.util.ParaFeeUtil;
import com.oxygen.wechat.util.PersonStatusUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FSService {


    @Autowired
    PersonMapper personMapper;

    @Autowired
    BusinessMapper businessMapper;

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    ParameterMapper parameterMapper;

    @Autowired
    WtRecordMapper wtRecordMapper;

    @Autowired
    BusinessService businessService;

    @Autowired
    PttionService pttionService;

    @Autowired
    PttionMapper pttionMapper;



    /**
     * 1. 查询是否已经绑定公众号
     * @param personWeChat
     * @return
     */
    public boolean selectFSWetChat(String personWeChat){
        Integer num = personMapper.selectFSWetChat(personWeChat);
        return num == 1 ? true : false;
    }

    /**
     * 2. 添加一条新的游客记录
     * @param personWeChat
     * @return
     */
    public boolean insertFSPerson(String personWeChat){
        //生成唯一idpersonWeChat, String personName, String personMobile
        String id= UUID.randomUUID().toString();
        //替换uuid中的"-"
        id=id.replace("-", "");
        System.out.println(id);
        Date time = new Date();
        System.out.println(time);
        boolean bl = personMapper.insertFSPerson(id,personWeChat, PersonStatusUtil.NO_STATUS,time);
        return bl;
    }



    /**
     * 3. 查询当前游客是否正在租借的信息String personName, String personMobile
     * @param personWeChat
     * @return
     */
    public boolean selectFSLease(String personWeChat){
        //boolean bl = businessService.selectFSLeaseByWeChat(personWeChat);
        Integer busiIsStatus = BusiIsStatusUtil.IS_STATUS;
        Integer num = businessMapper.selectFSLease(personWeChat,busiIsStatus);
        return num == 1 ? true : false;
    }

    /**
     * 4. 管理人员登录
     * @param mgName
     * @param mgPass
     * @return
     */
    public boolean selectFSLogin(String mgName,String mgPass){
        Integer num = managerMapper.selectFSLogin(mgName,mgPass);
        return num == 1 ? true : false;
    }

    /**
     * 5. 获取登录人员权限
     * @param mgName
     * @param mgPass
     * @param mgMobile
     * @return
     */
    public Integer selectFSQuanx(String mgName,String mgPass,String mgMobile){
        Integer num = managerMapper.selectFSQuanx(mgName,mgPass,mgMobile);
        return num;
    }

    /**
     * 6. 计费函数
     * @return
     */
    public Integer selectFSFee(String busi_Adate, String busi_Qdate)throws ParseException{
        String pmItem = "FEE";
        Integer busiFee = 0;

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date busiAdate = sdf.parse(busi_Adate);

        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date busiQdate = sdf1.parse(busi_Qdate);

        ParameterEntity parameterEntity = parameterMapper.selectFSQuery(pmItem);
        System.out.println(parameterEntity);
        if(Integer.valueOf(parameterEntity.getPmValueO()).intValue()==1){//以天数计费
//            long usageTime = busiQdate.getTime()-busiBdate.getTime();
//            if(usageTime <= ParaFeeUtil.Twenty_Four_Hour){
//                busiFee = Integer.parseInt(parameterEntity.getPmValueT());
//            } else {
//                long overtime = usageTime - ParaFeeUtil.Twenty_Four_Hour;
//                int count = 0;
//                while(overtime > ParaFeeUtil.Twelve_Hour){
//                    count++;
//                    System.out.println("overtime===="+overtime);
//                    overtime = overtime - ParaFeeUtil.Twelve_Hour;
//                }
//                count++;
//                System.out.println(count);
//                busiFee = count * Integer.parseInt(parameterEntity.getPmValueT())/2 + Integer.parseInt(parameterEntity.getPmValueT());
//            }

            long usageTime = busiQdate.getTime()-busiAdate.getTime();
            if(usageTime <= ParaFeeUtil.Twenty_Four_Hour){//24小时以内
                busiFee = Integer.parseInt(parameterEntity.getPmValueT());
            } else if((usageTime <= ParaFeeUtil.Forty_Eight_Hour) & (usageTime > ParaFeeUtil.Twenty_Four_Hour)){//48小时以内
                busiFee = Integer.parseInt(parameterEntity.getPmValueS()) + Integer.parseInt(parameterEntity.getPmValueT());
            }else if(usageTime > ParaFeeUtil.Forty_Eight_Hour){//48小时以后
                long overtime = usageTime - ParaFeeUtil.Forty_Eight_Hour;
                int count = 1;
                while(overtime > ParaFeeUtil.Twenty_Four_Hour){
                    count++;
                    System.out.println("overtime===="+overtime);
                    overtime = overtime - ParaFeeUtil.Twenty_Four_Hour;
                }
                System.out.println(count);
                busiFee = count * Integer.parseInt(parameterEntity.getPmValueF()) + Integer.parseInt(parameterEntity.getPmValueS()) + Integer.parseInt(parameterEntity.getPmValueT());
            }
        }else if(Integer.valueOf(parameterEntity.getPmValueO()).intValue()==2){
            long usageTime = busiQdate.getTime()-busiAdate.getTime();
            if(usageTime <= ParaFeeUtil.Two_Hour){
                busiFee = Integer.parseInt(parameterEntity.getPmValueT());
            }else {
                long overtime = usageTime - ParaFeeUtil.Two_Hour;
                int count = 0;
                while(overtime > ParaFeeUtil.One_Hour){
                    count++;
                    overtime = overtime - ParaFeeUtil.One_Hour;
                }
                count++;
                System.out.println(count);
                busiFee = Integer.parseInt(parameterEntity.getPmValueT()) + count * Integer.parseInt(parameterEntity.getPmValueS());
            }
        }
        System.out.println("所需费用:"+busiFee);
        return busiFee;
    }

    /**
     * 7. 分情况判断游客是否需要交押金的查询函数
     * @param personWeChat
     * @param wxFen
     * @return
     */
    public Integer selectFSQuery(String personWeChat,Integer wxFen){
        String pmItem = "wxfen";
        int deposit = 0;//押金
        ParameterEntity parameterEntity = parameterMapper.selectFSQuery(pmItem);
        if(wxFen<Integer.parseInt(parameterEntity.getPmValueO())){
            deposit = Integer.parseInt(parameterEntity.getPmValueT());
        }else if(wxFen>=Integer.parseInt(parameterEntity.getPmValueO())&&wxFen<Integer.parseInt(parameterEntity.getPmValueS())){
            deposit = Integer.parseInt(parameterEntity.getPmValueF());
        }else if(wxFen>=Integer.parseInt(parameterEntity.getPmValueS())&&wxFen<Integer.parseInt(parameterEntity.getPmValueI())){
            deposit = Integer.parseInt(parameterEntity.getPmValueX());
        }
        return deposit;
    }

    /**
     * 8. 查询当前订单详情
     * @param personWeChat
     * @return
     */
    public Map selectFSSearch(String personWeChat) throws ParseException ,NullPointerException{
        //Map<String,Object> map = new HashMap<>();
        Map<String,Object> map= businessMapper.selectFSSearch(personWeChat,BusiIsStatusUtil.IS_STATUS);
        if(map.isEmpty()){
            return map;
        }else {
            if(map.get("busi_Adate")==null){

            //判断送达时间是否为空

                return map;
            }else {
                //获取送达时间
                String busiAdate = (String)map.get("busi_Adate");
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = sdf.parse(busiAdate);
                //将date转成long型时间戳,获取送达时间
                long timestamp1 = date1.getTime();
                System.out.println("timestamp1==="+timestamp1);

                //获取当前时间
                Date date2 = new Date();
                //将date转成long型时间戳,获取送达时间
                long timestamp2 = date2.getTime();
                System.out.println("timestamp2==="+timestamp2);

                //时间差
                long timeDif = (timestamp2 - timestamp1)/1000/60/60 + 1;
                System.out.println("timeDif==="+timeDif);
                map.put("timeDif",timeDif);
                //format的格式可以任意
                DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String busiQdate = sdf1.format(date2);
                System.out.println(busiQdate);

                Integer busiFee = selectFSFee(busiAdate,busiQdate);
                map.put("busiFee",busiFee);
                System.out.println("当前map"+map);
                return map;
            }
        }


        //计算费用
        //map.put("busiMid",businessEntity.getBusiMid());
//        map.put("busiAdate",businessEntity.getBusiAdate());
//        map.put("busiName",businessEntity.getBusiName());
//        map.put("busiNumber",businessEntity.getBusiNumber());
//        map.put("busiAdd",businessEntity.getBusiAdd());
        //String orderMsg = businessEntity.getBusiMid()+"$"+businessEntity.getBusiPdate()+"$"+businessEntity.getBusiAdate()
                //+"$"+businessEntity.getBusiRdate()+"$"+businessEntity.getBusiStatus()+"$"+businessEntity.getBusiPstation1()+"$";

    }

    /**
     * 9. 登记新的订单（下单模式）
     * @param personWeChat
     * @return
     */
    public String insertFSInbusi(String personWeChat, String personName, String personMobile, String busiArea,String busiAdd){
        //生成唯一id
//        String busiMid= UUID.randomUUID().toString();
        String busiId= UUID.randomUUID().toString();
        //替换uuid中的"-"
        busiId=busiId.replace("-", "");
        System.out.println(busiId);
        Date date = new Date();
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String busiPdate = sdf.format(date);
        System.out.println(busiPdate);
        boolean bl = businessMapper.insertFSInbusi2(busiId,personWeChat,personName,personMobile,busiArea,busiAdd,busiPdate,1);

        return busiId;
    }

    /**
     * 9. 登记新的订单（自提模式）
     * @param personWeChat
     * @return
     */
    public boolean insertFSInbusi0(String personWeChat, String personName, String personMobile,String busiPstation1){
//        String busiArea="";
//        String busiAdd="";
        //生成唯一id
//        String busiMid= UUID.randomUUID().toString();
        String busiId= UUID.randomUUID().toString();
        //替换uuid中的"-"
        busiId=busiId.replace("-", "");
        System.out.println(busiId);
        Date date = new Date();
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String busiPdate = sdf.format(date);
        System.out.println(busiPdate);
        boolean bl = businessMapper.insertFSInbusi0(busiId,personWeChat,personName,personMobile,busiPdate,busiPstation1,0);
        return bl;
    }

    /**
     * 10. 游客归还制氧机下单
     * @param personWeChat
     * @return
     */
    public boolean updateFSGuihuan(String personWeChat, String busiReturnArea,String busiReturnAdd,String busiRdate,Integer busiFee){
        String busiId = businessService.selectFSLeaseByIsStatus(personWeChat);
        Integer busiStatus = BusiStatusUtil.Return_Order;
        boolean bl = businessMapper.updateFSGuihuan(busiId,busiStatus,busiReturnArea,busiReturnAdd,busiRdate,busiFee);
        return bl;
    }

    /**
     * 11. 游客直接购买
     * @param personWeChat
     * @return
     */
    public boolean insertFSSail(String personWeChat){
        Integer busiStatus = BusiStatusUtil.Using;
        Integer busiStatus1 = BusiStatusUtil.Tourists_Buy;
        //Integer busiIsStatus = BusiIsStatusUtil.NO_STATUS;
        Integer num = businessMapper.selectFSLease(personWeChat,busiStatus);
        boolean bl;
        //if(num==1){
            String busiId = businessService.selectFSLeaseByIsStatus(personWeChat);

            bl = businessMapper.updateFSSail(busiId,busiStatus1,BusiIsStatusUtil.NO_STATUS);

        return bl;
    }

    /**
     * 12. 操作流水记录函数
     * @param wrOwner
     * @param wrAction
     * @param wrResult
     * @return
     */
    public boolean insertFSRecord(String wrOwner, String wrAction, String wrResult){
        //生成唯一id
        String id= UUID.randomUUID().toString();
        //替换uuid中的"-"
        id=id.replace("-", "");
        System.out.println(id);
        Date time = new Date();
        System.out.println(time);
        boolean bl = wtRecordMapper.insertFSRecord(id,wrOwner,wrAction,wrResult,time);
        return bl;
    }

    /**
     * 13. 按设定规则找到最佳归还的驿站。
     * @param busiArea
     * @return
     */
    public List FSghstation(Integer pageNum, Integer pageSize,String busiArea){

        String pmItem="guihuanline";
        //Map<String,Object> map = new HashMap<>();

        //判断是否查找到同城驿站
        Integer count = pttionService.selectIsEntityByBusiArea(busiArea);
        System.out.println("count======"+count);
        if(count<=0){
            return null;
        }else {
            //获取pmValueO值
            String pmValueO = pttionService.FSghstationSelectPmvalueo(pmItem);
            //获取比值
            float ratio = Float.valueOf(pmValueO).floatValue();
            System.out.println("ratio=="+ratio);
            List<PttionEntity> list = pttionService.selectPttionIdByBusiArea(busiArea);
            System.out.println(list);
            //取得大于ratio的值为min
            String pttionId = list.get(0).getPttionId();
            System.out.println("pttionId==="+pttionId);
            float num=0;
            for(int i = 0 ;i < list.size() ;++i){
                num = (float)list.get(i).getPttionExtend1()/(float)list.get(i).getPttionExtend2();
                System.out.println("num===="+num);
                pttionId = list.get(i).getPttionId();
                if((num-ratio)>0){
                    System.out.println("num===="+num);
                    System.out.println("库存标配比超标");
                    pttionMapper.updatePttionExtend4(pttionId,1);
                }else {
                    pttionMapper.updatePttionExtend4(pttionId,0);
                }
            }
            List<PttionEntity> pttionList = pttionMapper.selectPttionIdByBusiArea(pageNum,pageSize,busiArea);
            System.out.println(list);
            return pttionList;
        }
    }
}