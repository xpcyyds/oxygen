package com.oxygen.wechat.mapper;

import com.oxygen.wechat.entity.ParameterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ParameterMapper {
    @Select("select pmvalueo,pmvaluet,pmvalues from parameter where pmitem = #{pmItem}")
    ParameterEntity selectFSFee(@Param("pmItem")String pmItem);

    @Select("select pmvalueo,pmvaluet,pmvalues,pmvaluef,pmvaluei,pmvaluex from parameter where pmitem = #{pmItem}")
    ParameterEntity selectFSQuery(@Param("pmItem")String pmItem);
}
