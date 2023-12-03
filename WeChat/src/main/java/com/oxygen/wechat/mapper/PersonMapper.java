package com.oxygen.wechat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Mapper
@Repository
public interface PersonMapper {

    @Select("select count(*) from person where person_weChat = #{personWeChat}")
    Integer selectFSWetChat(@Param("personWeChat")String personWeChat);

    @Insert("insert into person (person_id,person_weChat,person_status,person_date) values(#{personId},#{personWeChat},#{personStatus},#{personDate})")
    boolean insertFSPerson(@Param("personId")String personId,
                           @Param("personWeChat")String personWeChat,
                           @Param("personStatus")Integer personStatus,
                           @Param("personDate") Date personDate);
}
