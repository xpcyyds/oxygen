package com.oxygen.wechat.mapper;

import com.oxygen.wechat.entity.PttionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PttionMapper {
    @Select("select pmvalueo from parameter where pmitem = #{pmItem}")
    String selectPmvalueoByPmitem(@Param("pmItem")String pmItem);

    @Select("select * from pttion where (concat(pttionPro,pttionCity,pttionArea) = #{busiArea}) and pttionExtend3 = 0 limit #{pageNum},#{pageSize}")//
    List<PttionEntity> selectPttionIdByBusiAreaAll(@Param("pageNum")Integer pageNum,
                                                @Param("pageSize")Integer pageSize,
                                                @Param("busiArea")String busiArea);

    @Select("select * from pttion where (concat(pttionPro,pttionCity,pttionArea) = #{busiArea}) and pttionExtend2 <> 0 ORDER BY pttionExtend1 / pttionExtend2 ASC")//
    List<PttionEntity> selectPttionByBusiAreaAll(@Param("busiArea")String busiArea);

    //分页
    @Select("select * from pttion where (concat(pttionPro,pttionCity,pttionArea) = #{busiArea}) and pttionExtend2 <> 0 ORDER BY pttionExtend1 / pttionExtend2 ASC limit #{pageNum},#{pageSize}")//
    List<PttionEntity> selectPttionIdByBusiArea(@Param("pageNum")Integer pageNum,
                                                @Param("pageSize")Integer pageSize,
                                                @Param("busiArea")String busiArea);

    @Select("select count(*) from pttion where (concat(pttionPro,pttionCity,pttionArea) = #{busiArea})")//字段拼接查询
    Integer selectIsEmptyByBusiArea(@Param("busiArea")String busiArea);

    //通过pttionId查询驿站信息
    @Select("select * from pttion where pttionId= #{pttionId}")//
    List<PttionEntity> selectPttionIdByPttionId(@Param("pttionId")String pttionId);

    //7.更改商品状态
    @Update("update pttion set pttionExtend1=pttionExtend1 - 1 where pttionId=#{pttionId}")
    public boolean updatePttionExtend1(@Param("pttionId")String pttionId);

    //查找所有驿站信息
    @Select("select * from pttion limit #{pageNum},#{pageSize}")//
    List<PttionEntity> selectAllPttion(@Param("pageNum")Integer pageNum,
                                                @Param("pageSize")Integer pageSize);
    //6.查询驿站总数
    @Select("select count(*) from pttion ")
    public int selectAllPttionCount();

    //7.驿站处理数加一
    @Update("update pttion set pttionExtend3=pttionExtend3 + 1 where pttionId=#{pttionId}")
    public boolean updatePttionExtend3(@Param("pttionId")String pttionId);

    //7.驿站处理数加一
    @Update("update pttion set pttionExtend3=pttionExtend3 - 1 where pttionId=#{pttionId}")
    public boolean updatePttionExtend31(@Param("pttionId")String pttionId);

    //8.驿站处理数加一
    @Update("update pttion set pttionExtend4=#{pttionExtend4} where pttionId=#{pttionId}")
    public boolean updatePttionExtend4(@Param("pttionId")String pttionId,
                                       @Param("pttionExtend4")Integer pttionExtend4);

    //9.从订单信息表business里查询是否有未完成派送确认的订单
    @Select("select count(*) from business where busi_Pstation1 = #{pttionId} and busi_status=#{busiStatus}")
    public Integer selectBusiStatusByBusiPstation1(@Param("pttionId")String pttionId,
                                                   @Param("busiStatus")Integer busiStatus);

    //10.驿站库存数加一
    @Update("update pttion set pttionExtend1=pttionExtend1 + 1 where pttionId=#{pttionId}")
    public boolean updatePttionExtend1Add(@Param("pttionId")String pttionId);

    //11.驿站库存数减一
    @Update("update pttion set pttionExtend1=pttionExtend1 - 1 where pttionId=#{pttionId}")
    public boolean updatePttionExtend1Reduce(@Param("pttionId")String pttionId);





    /*
    驿站管理人员
     */
    //查询驿站管理人员所属驿站
    @Select("select pttion_Id from pttion_Manager where pttion_Manager_wechat = #{pttion_Manager_wechat}")
    String selectpttionIdByWeChat(@Param("pttion_Manager_wechat")String pttion_Manager_wechat);


}
