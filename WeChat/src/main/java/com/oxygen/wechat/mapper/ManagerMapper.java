package com.oxygen.wechat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ManagerMapper {
    @Select("select count(*) from manager where mg_Name = #{mgName} and mg_Pass = #{mgPass}")
    Integer selectFSLogin(@Param("mgName")String mgName,
                            @Param("mgPass")String mgPass);

    @Select("select count(*) from manager where mg_Name = #{mgName}")
    Integer selectFSLoginByMgName(@Param("mgName")String mgName);

    @Select("select mg_Status from manager where mg_Name = #{mgName} and mg_Pass = #{mgPass} and mg_Mobile = #{mgMobile}")
    Integer selectFSQuanx(@Param("mgName")String mgName,
                          @Param("mgPass")String mgPass,
                          @Param("mgMobile")String mgMobile);

    /*
    驿站管理人员
     */
    @Select("select count(*) from pttion_Manager where pttion_Manager_wechat = #{pttion_Manager_wechat}")
    Integer selectFSLoginPttionManager(@Param("pttion_Manager_wechat")String pttion_Manager_wechat);

    //查询当前驿站的管理人员的微信号
    @Select("select pttion_Manager_wechat from pttion_Manager where pttion_Id = #{pttionId}")
    String  selectPttionManagerOpenId(@Param("pttionId")String pttionId);
}
