package com.oxygen.wechat.mapper;

import com.oxygen.wechat.entity.WxUserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface WxUserMapper {
    @Select("select count(*) from wx_user where open_id = #{openId}")
    Integer selectWxUser(@Param("openId")String openId);

    @Insert("insert into wx_user (wx_user_id,open_id) values(#{wxUserId},#{openId})")
    boolean insertWxUser(@Param("wxUserId")String wxUserId,
                         @Param("openId")String openId);

    @Delete("delete from wx_user where open_id=#{openId}")
    boolean deleteWxUser(@Param("openId")String openId);

}
