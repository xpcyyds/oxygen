package com.oxygen.wechat.service;

import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.PttionEntity;
import com.oxygen.wechat.mapper.PttionMapper;
import com.oxygen.wechat.util.BusiStatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PttionService {

    @Autowired
    PttionMapper pttionMapper;

    /**
     * 1. 通过pmitem查找pmvalueo
     * @param pmItem
     * @return
     */
    public String FSghstationSelectPmvalueo(String pmItem){
        String pmValueO = pttionMapper.selectPmvalueoByPmitem(pmItem);
        return pmValueO;
    }

    /**
     * 2. 通过BusiArea查找PttionId
     * @param scanNum
     * @param pageSize
     * @param busiArea
     * @return
     */
    public List<PttionEntity> selectPttionIdByBusiAreaAll(Integer scanNum,Integer pageSize,String busiArea){
        List<PttionEntity> map = pttionMapper.selectPttionIdByBusiAreaAll(scanNum,pageSize,busiArea);
        return map;
    }

    /**
     * 3. 通过BusiArea查找PttionId
     * @param busiArea
     * @return
     */
    public List<PttionEntity> selectPttionIdByBusiArea(String busiArea){
        List<PttionEntity> map = pttionMapper.selectPttionByBusiAreaAll(busiArea);
        return map;
    }

    /**
     * 4. 通过BusiArea查找是否有对应Pttion
     * @param busiArea
     * @return
     */
    public Integer selectIsEntityByBusiArea(String busiArea){
        Integer count = pttionMapper.selectIsEmptyByBusiArea(busiArea);
        return count;
    }

    /**
     * 5. 通过pttionId查找驿站信息
     * @param pttionId
     * @return
     */
    public List<PttionEntity> selectPttionIdByPttionId(String pttionId){
        List<PttionEntity> map = pttionMapper.selectPttionIdByPttionId(pttionId);
        return map;
    }

    /**
     * 6. 查找所有驿站信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<PttionEntity> selectPttion(Integer pageNum, Integer pageSize){
        List<PttionEntity> list= pttionMapper.selectAllPttion(pageNum,pageSize);
        return list;
    }

    /**
     * 7. 查找驿站总数
     * @return
     */
    public Integer selectPttion(){
        Integer total= pttionMapper.selectAllPttionCount();
        return total;
    }

    /**
     * 8. 驿站处理数加一
     * @param pttionId
     * @return
     */
    public boolean selectIsEntityBypttionId(String pttionId){
        boolean bl = pttionMapper.updatePttionExtend3(pttionId);
        return bl;
    }

    /**
     * 9. 从订单信息表business里查询是否有未完成派送确认的订单
     * @param pttionId
     * @return
     */
    public Integer FSdaipaisong(String pttionId,Integer busiStatus){
        Integer num = pttionMapper.selectBusiStatusByBusiPstation1(pttionId, busiStatus);
        return num;
    }

    /**
     * 10. 驿站库存数加一
     * @param pttionId
     * @return
     */
    public boolean updatePttionExtend1Add(String pttionId){
        boolean bl = pttionMapper.updatePttionExtend1Add(pttionId);
        return bl;
    }

    /**
     * 11. 驿站库存数减一
     * @param pttionId
     * @return
     */
    public boolean updatePttionExtend1Reduce(String pttionId){
        boolean bl = pttionMapper.updatePttionExtend1Reduce(pttionId);
        return bl;
    }
}
