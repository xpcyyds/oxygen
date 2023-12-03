package com.oxygen.wechat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface WtRecordMapper {
    @Insert("insert into wtrecord (wr_Id,wrowner,wraction,wrresult,wrdate) values(#{wrId},#{wrOwner},#{wrAction},#{wrResult},#{wrDate})")
    boolean insertFSRecord(@Param("wrId")String wrId,
                           @Param("wrOwner")String wrOwner,
                           @Param("wrAction")String wrAction,
                           @Param("wrResult")String wrResult,
                           @Param("wrDate") Date wrDate);
}
