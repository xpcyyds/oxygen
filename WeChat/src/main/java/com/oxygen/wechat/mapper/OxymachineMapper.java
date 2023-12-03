package com.oxygen.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.OxyMachineEntity;
import com.oxygen.wechat.entity.OxyMachineEntity2;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OxymachineMapper extends BaseMapper<OxyMachineEntity> {
    //查询是否存在制氧机编号
    @Select("select count(*) from oxymachine where omId = #{omId}")
    Integer selectOxymachineIsExist(@Param("omId")String omId);

    //新增制氧机
    @Insert("insert into oxymachine (recordId,omId,omDate) values(#{recordId},#{omId},#{omDate})")
    boolean insertOxymachine(@Param("recordId")String recordId,
                              @Param("omId")String omId,
                              @Param("omDate")String omDate);


    //1.查询当前制氧机是否可以使用，omStatus=1或6where omId = #{omId} and (omStatus = 1 or omStatus = 6 or omStatus = 8 or omStatus = 2)
    @Select("select count(*) from oxymachine where omId = #{omId} and (omStatus = 1 or omStatus = 6 )")
    Integer selectOxymachineIsCanUse(@Param("omId")String omId);

    @Select("select count(*) from oxymachine where omId = #{omId} and (omStatus = 1 or omStatus = 6 or omStatus = 8 or omStatus = 2 or omStatus = 9)")
    Integer selectOxymachineIsCanUse1(@Param("omId")String omId);

    //2.查询当前制氧机是否可以使用，omStatus=0或5
    @Select("select count(*) from oxymachine where omId = #{omId} and (omStatus = 0 or omStatus = 5)")
    Integer selectOxymachineIsReturn(@Param("omId")String omId);

    //3.查询当前制氧机的状态是否为设定值
    @Select("select count(*) from oxymachine where omId = #{omId} and omStatus = #{omStatus}")
    Integer selectOxymachineomStatus(@Param("omId")String omId,
                                     @Param("omStatus") Integer omStatus);

    //4.更新制氧机的状态
    @Update("update oxymachine set omStatus=#{omStatus} where omId=#{omId}")
    boolean updateOxymachineOmStatus(@Param("omId")String omId,
                         @Param("omStatus") Integer omStatus);

    //5.查询当前制氧机的状态
    @Select("select omStatus from oxymachine where omId = #{omId}")
    Integer selectOxymachineomStatus1(@Param("omId")String omId);

    //1.华为云平台插入数据
    @Insert("insert into oxymachine2 (recordId,omId,Latitude,Longitude,nowTime,omStatus) values(#{recordId},#{omId},#{Latitude},#{Longitude},#{nowIotDate},#{omStatus})")
    boolean insertOxymachine2(@Param("recordId")String recordId,
                            @Param("omId")String omId,
                              @Param("Latitude")Double Latitude,
                              @Param("Longitude")Double Longitude,
                              @Param("nowIotDate")String nowIotDate,
                              @Param("omStatus")Integer omStatus);

    @Insert("insert into oxymachine2 (recordId,omId,Latitude,Longitude,nowTime,omStatus,omUser) values(#{recordId},#{omId},#{Latitude},#{Longitude},#{nowIotDate},#{omStatus},#{omUser})")
    boolean insertOxymachine3(@Param("recordId")String recordId,
                              @Param("omId")String omId,
                              @Param("Latitude")Double Latitude,
                              @Param("Longitude")Double Longitude,
                              @Param("nowIotDate")String nowIotDate,
                              @Param("omStatus")Integer omStatus,
                              @Param("omUser")String omUser);

    //2.查询是否已经插入制氧机数据
    @Select("select count(*) from oxymachine2 where omId = #{omId}")
    Integer selectOxymachine2(@Param("omId")Integer omId);

    //3.查询所有制氧机数据
    @Select("select * from oxymachine2 limit #{scanNum},#{pageSize}")
    List<OxyMachineEntity2> selectAllOxymachine2(@Param("scanNum")Integer scanNum,
                                              @Param("pageSize")Integer pageSize);
    //4.查询所有制氧机总数
    @Select("select count(*) from oxymachine2 ")
    public int selectAllOxymachine2count3();

    //5.查询订单中制氧机状态为1,2,3时的用户微信号
    @Select("select busi_wechat from business where busi_mid=#{busiMid} and (busi_status=1 or busi_status=2 or busi_status=3 or busi_status=11 or busi_status=13) ")
    String selectWeChatByBusiMid(@Param("busiMid")String busiMid);

    //6.查询所有制氧机的信息
    @Select("select * from oxymachine limit #{scanNum},#{pageSize}")
    List<OxyMachineEntity> selectAllOxymachine(@Param("scanNum")Integer scanNum,
                                                 @Param("pageSize")Integer pageSize);

    //6.查询特定制氧机的所有信息
    @Select("select * from oxymachine2 where omId=#{omId} limit #{scanNum},#{pageSize} ")
    List<OxyMachineEntity2> selectOxymachineMsg(@Param("omId")String omId,
                                               @Param("scanNum")Integer scanNum,
                                               @Param("pageSize")Integer pageSize);

    //7.查询所有制氧机总数
    @Select("select count(*) from oxymachine ")
    public int selectAllOxymachineNum();

    //7.查询特定制氧机信息总数
    @Select("select count(*) from oxymachine2 where omId=#{omId}")
    public int selectOxymachineMsgNum(@Param("omId")String omId);

    //判断制氧机状态是否异常
    @Select("select count(*) from oxymachine where omId = #{omId} and (omStatus = 2 or omStatus = 3 or omStatus = 4 or omStatus = 5 or omStatus = 7)")
    public int selectOxymachineStatus(@Param("omId")String omId);
}
