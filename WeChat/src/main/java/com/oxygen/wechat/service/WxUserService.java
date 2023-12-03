package com.oxygen.wechat.service;

import com.oxygen.wechat.entity.WxUserEntity;
import com.oxygen.wechat.mapper.WxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class WxUserService {

    @Autowired
    WxUserMapper wxUserMapper;

    /**
     * 1. 查询是否已经绑定公众号
     * @param openId
     * @return
     */
    public boolean getByOpenId(String openId){
        Integer num = wxUserMapper.selectWxUser(openId);
        return num == 1 ? true : false;
    }

    /**
     * 2. 添加一条新的游客记录
     * @param openId
     * @return
     */
    public boolean insertWxUser(String openId){
        //生成唯一idpersonWeChat, String personName, String personMobile
        String id= UUID.randomUUID().toString();
        //替换uuid中的"-"
        id=id.replace("-", "");
        System.out.println(id);
        Date time = new Date();
        System.out.println(time);
        boolean bl = wxUserMapper.insertWxUser(id,openId);
        return bl;
    }

    /**
     * 3. 删除一条新的游客记录
     * @param openId
     * @return
     */
    public void removeByOpenId(String openId){
        boolean bl = wxUserMapper.deleteWxUser(openId);
    }

}
