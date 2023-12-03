package com.oxygen.wechat.service;

import com.oxygen.wechat.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    ManagerMapper managerMapper;
    /**
     * 1. 查询人员是否存在
     * @param mgName
     * @return
     */
    public boolean selectFSLoginByMgName(String mgName){
        Integer num = managerMapper.selectFSLoginByMgName(mgName);
        return num == 1 ? true : false;
    }
}
