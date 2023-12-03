package com.oxygen.wechat.service;

import com.oxygen.wechat.entity.OxyMachineEntity;
import com.oxygen.wechat.entity.OxyMachineEntity2;
import com.oxygen.wechat.mapper.OxymachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OxymachineService {
    @Autowired
    OxymachineMapper oxymachineMapper;
    /**
     * 1.从表oxymachine里按条件omStatus=1 or omStatus=6查询对应参数(制氧机编号)的count()结果
     * @return
     */
    public Integer selectOxymachineIsCanUse(String omId){
        Integer total= oxymachineMapper.selectOxymachineIsCanUse(omId);
        return total;
    }

    /**
     * 1.从表oxymachine里按条件omStatus=1 or omStatus=6查询对应参数(制氧机编号)的count()结果
     * @return
     */
    public Integer selectOxymachineIsRep(String omId){
        Integer total= oxymachineMapper.selectOxymachineIsCanUse(omId);
        return total;
    }

    /**
     * 2.更改Oxymachine的omStatus
     * @return
     */
    public boolean updateOxymachineOmStatus(String omId,Integer omStatus){
        boolean bl= oxymachineMapper.updateOxymachineOmStatus(omId,omStatus);
        return bl;
    }

    /**
     * 1.从表oxymachine里按条件omStatus=0 or omStatus=5查询对应参数(制氧机编号)的count()结果
     * @return
     */
    public Integer selectOxymachineIsReturn(String omId){
        Integer total= oxymachineMapper.selectOxymachineIsReturn(omId);
        return total;
    }

    public boolean insertOxymachine2(String omId,Double Latitude,Double Longitude,String nowIotDate,Integer omStatus){
        String recordId= UUID.randomUUID().toString();
        //替换uuid中的"-"
        recordId=recordId.replace("-", "");
        System.out.println(recordId);
        boolean bl = oxymachineMapper.insertOxymachine2(recordId,omId,Latitude,Longitude,nowIotDate,omStatus);
        return bl;
    }

    public boolean insertOxymachine3(String omId,Double Latitude,Double Longitude,String nowIotDate,Integer omStatus,String omUser){
        String recordId= UUID.randomUUID().toString();
        //替换uuid中的"-"
        recordId=recordId.replace("-", "");
        System.out.println(recordId);
        boolean bl = oxymachineMapper.insertOxymachine3(recordId,omId,Latitude,Longitude,nowIotDate,omStatus,omUser);
        return bl;
    }

    public Integer selectOxymachineomStatus(String omId){
        Integer num = oxymachineMapper.selectOxymachineomStatus1(omId);
        return num;
    }

    public Integer selectOxymachine2(Integer omId){
        Integer num = oxymachineMapper.selectOxymachine2(omId);
        return num;
    }

    //1.所有制氧机设备信息展示
    public List<OxyMachineEntity> selectAllOxymachine(Integer pageNum, Integer pageSize){
        List<OxyMachineEntity> oxyMachineEntities= oxymachineMapper.selectAllOxymachine(pageNum,pageSize);
        return oxyMachineEntities;
    }

    //2.特定制氧机设备信息展示
    public List<OxyMachineEntity2> selectOxymachineMsg(String omId,Integer pageNum, Integer pageSize){
        List<OxyMachineEntity2> oxyMachineEntities= oxymachineMapper.selectOxymachineMsg(omId,pageNum,pageSize);
        return oxyMachineEntities;
    }

    public Integer selectAllOxymachineNum(){
        Integer num = oxymachineMapper.selectAllOxymachineNum();
        return num;
    }

    //2.特定制氧机设备信息数量
    public Integer selectOxymachineMsgNum(String omId){
        Integer num = oxymachineMapper.selectOxymachineMsgNum(omId);
        return num;
    }
    //判断制氧机状态是否异常
    public Integer selectOxymachineStatus(String omId){
        Integer num = oxymachineMapper.selectOxymachineStatus(omId);
        return num;
    }
}
