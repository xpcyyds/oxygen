package com.oxygen.wechat.service;

import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.ManagerEntity;
import com.oxygen.wechat.mapper.BusinessMapper;
import com.oxygen.wechat.util.BusiIsStatusUtil;
import com.oxygen.wechat.util.BusiStatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BusinessService {
    @Autowired
    BusinessMapper businessMapper;

    public String selectFSLeaseByIsStatus(String personWeChat){
        String orderId = businessMapper.selectFSLeaseByIsStatus(personWeChat, BusiIsStatusUtil.IS_STATUS);
        return orderId;
    }

    //查询用户之前有没有下过单
    public boolean selectFSLeaseByWeChat(String personWeChat){
        Integer count  = businessMapper.selectFSLeaseByWeChat(personWeChat);
        boolean bl = count>=1?true:false;
        return bl;
    }

    //查询所有用户下单信息busiType==null
    public List<BusinessEntity> selectBus(Integer scanNum, Integer pageSize, Integer busiStatus){
        List<BusinessEntity> list= businessMapper.getAll(scanNum,pageSize,busiStatus);
        return list;
    }

    //查询所有用户下单信息busiType!=null
    public List<BusinessEntity> selectBus1(Integer scanNum, Integer pageSize, Integer busiStatus,Integer busiType){
        List<BusinessEntity> list= businessMapper.getAll1(scanNum,pageSize,busiStatus,busiType);
        return list;
    }

    //驿站管理人员查询所有用户下单信息busiType!=null
    public List<BusinessEntity> selectBus2(Integer scanNum, Integer pageSize, Integer busiStatus,Integer busiType,String pttionId){
        List<BusinessEntity> list= businessMapper.getAll2(scanNum,pageSize,busiStatus,busiType,pttionId);
        return list;
    }

    //驿站管理人员查询所有用户下单信息busiType!=null
    public List<BusinessEntity> selectBus3(Integer scanNum, Integer pageSize, Integer busiStatus,String pttionId){
        List<BusinessEntity> list= businessMapper.getAll3(scanNum,pageSize,busiStatus,pttionId);
        return list;
    }

    //查询所有用户下单总数busiType==null
    public Integer selectCount(Integer busiStatus){
        Integer count = businessMapper.count(busiStatus);
        return count;
    }

    //查询所有用户下单总数busiType!=null
    public Integer selectCount1(Integer busiStatus,Integer busiType){
        Integer count = businessMapper.count1(busiStatus,busiType);
        return count;
    }

    //驿站人员查询所有用户下单总数busiType!=null
    public Integer selectCount2(Integer busiStatus,Integer busiType,String pttionId){
        Integer count = businessMapper.count2(busiStatus,busiType,pttionId);
        return count;
    }

    //驿站人员查询所有用户下单总数busiType==null
    public Integer selectCount2(Integer busiStatus,String pttionId){
        Integer count = businessMapper.count3(busiStatus,pttionId);
        return count;
    }

    //更新订单状态
    public boolean updateBus(String busId,Integer busiStatus){
        boolean bl = businessMapper.updateBus(busId,busiStatus);
        return bl;
    }

    //更新订单状态
    public boolean updateBusSta(String busId,Integer busiIsStatus){
        boolean bl = businessMapper.updateBusSta(busId,busiIsStatus);
        return bl;
    }

    //订单与制氧机编号匹配
    public boolean updateBusiMid(String busId,String omId){
        boolean bl = businessMapper.updateBusiMid(busId,omId);
        return bl;
    }

    //查询当前订单状态是否为设定值
    public Integer selectBusByBusiStatus(String busiId,Integer busiStatus){
        Integer num  = businessMapper.selectBusByBusiStatus(busiId,busiStatus);
        return num;
    }



    //更新发出驿站信息
    public boolean updateBusiPstation1(String busId,String busiPstation1){
        boolean bl = businessMapper.updateBusiPstation1(busId,busiPstation1);
        return bl;
    }

    //更新发出驿站信息
    public boolean updateBusiPstation2(String busId,String busiPstation2){
        boolean bl = businessMapper.updateBusiPstation2(busId,busiPstation2);
        return bl;
    }
}
