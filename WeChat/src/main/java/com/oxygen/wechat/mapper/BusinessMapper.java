package com.oxygen.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oxygen.wechat.entity.BusinessEntity;
import com.oxygen.wechat.entity.ManagerEntity;
import org.apache.ibatis.annotations.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BusinessMapper extends BaseMapper<BusinessEntity> {


    @Select("select count(*) from business where busi_wechat = #{personWeChat} and busi_is_status = #{busiIsStatus}")
    Integer selectFSLease(@Param("personWeChat")String personWeChat,
                          @Param("busiIsStatus")Integer busiIsStatus);

    @Select("select busi_Id from business where busi_wechat = #{personWeChat} and busi_is_status = #{busiIsStatus}")
    String selectFSLeaseByIsStatus(@Param("personWeChat")String personWeChat,
                          @Param("busiIsStatus")Integer busiIsStatus);

    @Select("select busi_Id,busi_Name,busi_Number,busi_mid,busi_Pdate,busi_Adate,busi_Rdate,busi_status,busi_add,busi_area from business where busi_wechat = #{personWeChat} and busi_is_status = #{busiIsStatus}")
    Map selectFSSearch(@Param("personWeChat")String personWeChat,
                       @Param("busiIsStatus")Integer busiIsStatus);

    @Insert("insert into business (busi_Id,busi_wechat,busi_Name,busi_Number,busi_area,busi_add,busi_Pdate,busiType,busi_status) values(#{busiId},#{personWeChat},#{personName},#{personMobile},#{busiArea},#{busiAdd},#{busiPdate},#{busiType},0)")
    boolean insertFSInbusi(@Param("busiId")String busiId,
                           @Param("personWeChat")String personWeChat,
                           @Param("personName")String personName,
                           @Param("personMobile")String personMobile,
                           @Param("busiArea")String busiArea,
                           @Param("busiAdd")String busiAdd,
                           @Param("busiPdate") String busiPdate,
                           @Param("busiType") Integer busiType);
    //垃圾堆订单下单输入模式  订单状态99
    @Insert("insert into business2 (busi_Id,busi_wechat,busi_Name,busi_Number,busi_area,busi_add,busi_Pdate,busiType,busi_status) values(#{busiId},#{personWeChat},#{personName},#{personMobile},#{busiArea},#{busiAdd},#{busiPdate},#{busiType},99)")
    boolean insertFSInbusi2(@Param("busiId")String busiId,
                           @Param("personWeChat")String personWeChat,
                           @Param("personName")String personName,
                           @Param("personMobile")String personMobile,
                           @Param("busiArea")String busiArea,
                           @Param("busiAdd")String busiAdd,
                           @Param("busiPdate") String busiPdate,
                           @Param("busiType") Integer busiType);

    //垃圾堆查询数据  订单状态99
    @Select("select * from business2 where busi_Id= #{busiId} and (busi_status=99 or busi_status=100)")
    BusinessEntity selectFSInbusi2(@Param("busiId")String busiId);

    //垃圾堆查询数据  订单状态99
    @Select("select count(*) from business2 where busi_wechat= #{personWeChat} and busi_status=99")
    Integer selectFSInbusi2Num1(@Param("personWeChat")String personWeChat);

    //通过订单号查用户
    @Select("select busi_wechat from business where busi_Id= #{busiId}")
    String selectFSInbusiBybusiId(@Param("busiId")String busiId);

    //通过订单号查制氧机编号
    @Select("select busi_mid from business where busi_Id= #{busiId}")
    String selectBusiMidBybusiId(@Param("busiId")String busiId);
    //垃圾堆查询数据  订单状态100
    @Select("select count(*) from business2 where busi_wechat= #{personWeChat} and busi_status=100")
    Integer selectFSInbusi2Num2(@Param("personWeChat")String personWeChat);

    //垃圾堆删除数据
    @Delete("delete  from business2 where busi_Id= #{busiId} ")
    boolean deleteFSInbusi2(@Param("busiId")String busiId);

    //垃圾堆删除数据  订单状态99
    @Delete("delete  from business2 where busi_wechat= #{personWeChat} and (busi_status=99 or busi_status=100)")
    boolean deleteFSInbusi2ByWeChat(@Param("personWeChat")String personWeChat);


    @Insert("insert into business (busi_Id,busi_wechat,busi_Name,busi_Number,busi_status,busi_Pdate,busi_Adate,busi_Pstation1,busiType) values(#{busiId},#{personWeChat},#{personName},#{personMobile},7,#{busiPdate},#{busiPdate},#{busiPstation1},#{busiType})")
    boolean insertFSInbusi0(@Param("busiId")String busiId,
                           @Param("personWeChat")String personWeChat,
                           @Param("personName")String personName,
                           @Param("personMobile")String personMobile,
                           @Param("busiPdate") String busiPdate,
                           @Param("busiPstation1") String busiPstation1,
                           @Param("busiType") Integer busiType);



    //删除订单
    @Delete("delete from business where busi_Id = #{busiId}")
    boolean deleteFSInbusi0(@Param("busiId")String busiId);
//    @Insert("insert into business (busi_Id,busi_wechat,busi_add,busi_Pdate,busi_status) values(#{busiId},#{personWeChat},#{busiAdd},#{busiPdate},#{busiStatus})")
//    boolean insertFSSail(@Param("busiId")String busiId,
//                         @Param("personWeChat")String personWeChat,
//                         @Param("busiAdd")String busiAdd,
//                         @Param("busiPdate") Date busiPdate,
//                         @Param("busiStatus") Integer busiStatus);

    @Insert("insert into business (busi_Id,busi_wechat,busi_add,busi_Pdate,busi_status,busi_is_status) values(#{busiId},#{personWeChat},#{busiAdd},#{busiPdate},#{busiStatus},#{busiIsStatus})")
    boolean insertFSSailBySail(@Param("busiId")String busiId,
                         @Param("personWeChat")String personWeChat,
                         @Param("busiAdd")String busiAdd,
                         @Param("busiPdate") String busiPdate,
                         @Param("busiStatus") Integer busiStatus,
                               @Param("busiIsStatus") Integer busiIsStatus      );

    @Update("update business set busi_status=#{busiStatus},busi_is_status=#{busiIsStatus} where busi_Id=#{busiId}")
    boolean updateFSSail(@Param("busiId")String busiId,
                         @Param("busiStatus") Integer busiStatus,
                         @Param("busiIsStatus") Integer busiIsStatus   );

    @Update("update business set busi_status=#{busiStatus}, busi_return_area=#{busiReturnArea}, busi_return_add=#{busiReturnAdd}, busi_Rdate=#{busiRdate}, busi_Fee=#{busiFee} where busi_Id=#{busiId}")
    boolean updateFSGuihuan(@Param("busiId")String busiId,
                            @Param("busiStatus")Integer busiStatus,
                            @Param("busiReturnArea")String busiReturnArea,
                            @Param("busiReturnAdd")String busiReturnAdd,
                            @Param("busiRdate")String busiRdate,
                            @Param("busiFee")Integer busiFee);

    //添加缓存订单
    @Insert("insert into business2 (busi_Id,busi_wechat) values(#{busiId},#{personWeChat}) ")
    boolean insertFSInbusi2ById(@Param("busiId")String busiId,
                                @Param("personWeChat")String personWeChat);

    //通过用户查订单号,订单状态为1或2
    @Select("select busi_Id from business where busi_wechat= #{personWeChat} and (busi_status = 1 or busi_status = 2)")
    String selectFSInbusiBypersonWeChat(@Param("personWeChat")String personWeChat);
    //添加缓存订单状态为100
    @Update("update business2 set busi_status=100, busi_return_area=#{busiReturnArea}, busi_return_add=#{busiReturnAdd}, busi_Rdate=#{busiRdate}, busi_Fee=#{busiFee} where busi_Id=#{busiId}")
    boolean updateFSGuihuan2(@Param("busiId")String busiId,
                            @Param("busiReturnArea")String busiReturnArea,
                            @Param("busiReturnAdd")String busiReturnAdd,
                            @Param("busiRdate")String busiRdate,
                            @Param("busiFee")Integer busiFee);

    @Select("select count(*) from business where busi_wechat = #{personWeChat}")
    Integer selectFSLeaseByWeChat(@Param("personWeChat")String personWeChat);

    //4.查询单个商品
//    @Select("select * from goods where goods_Name like concat('%',#{goodsName},'%')")
//    public List<ManagerEntity> getByName(String goodsName);

    //5.查询全部商品
    @Select("select * from business where busi_status=#{busiStatus} limit #{scanNum},#{pageSize}")
    public List<BusinessEntity> getAll(@Param("scanNum")Integer scanNum,
                                      @Param("pageSize")Integer pageSize,
                                      @Param("busiStatus")Integer busiStatus);

    //5.查询全部商品
    @Select("select * from business where busi_status=#{busiStatus} and busiType=#{busiType} limit #{scanNum},#{pageSize}")
    public List<BusinessEntity> getAll1(@Param("scanNum")Integer scanNum,
                                       @Param("pageSize")Integer pageSize,
                                       @Param("busiStatus")Integer busiStatus,
                                        @Param("busiType")Integer busiType);

    //5.查询全部商品
    @Select("select * from business where busi_status=#{busiStatus} and busiType=#{busiType} and busi_Pstation1=#{pttionId}  limit #{scanNum},#{pageSize}")
    public List<BusinessEntity> getAll2(@Param("scanNum")Integer scanNum,
                                        @Param("pageSize")Integer pageSize,
                                        @Param("busiStatus")Integer busiStatus,
                                        @Param("busiType")Integer busiType,
                                        @Param("pttionId")String pttionId);

    //5.查询全部商品TYPE=NULL
    @Select("select * from business where busi_status=#{busiStatus}  and busi_Pstation1=#{pttionId}  limit #{scanNum},#{pageSize}")
    public List<BusinessEntity> getAll3(@Param("scanNum")Integer scanNum,
                                        @Param("pageSize")Integer pageSize,
                                        @Param("busiStatus")Integer busiStatus,
                                        @Param("pttionId")String pttionId);

    //6.查询商品总数null
    @Select("select count(*) from business where busi_status=#{busiStatus}")
    public int count(@Param("busiStatus")Integer busiStatus);

    //6.查询商品总数
    @Select("select count(*) from business where busi_status=#{busiStatus} and busiType=#{busiType}")
    public int count1(@Param("busiStatus")Integer busiStatus,
                      @Param("busiType")Integer busiType);

    //6.驿站人员查询商品总数
    @Select("select count(*) from business where busi_status=#{busiStatus} and busiType=#{busiType} and busi_Pstation1=#{pttionId}")
    public int count2(@Param("busiStatus")Integer busiStatus,
                      @Param("busiType")Integer busiType,
                      @Param("pttionId")String pttionId);

    //6.驿站人员查询商品总数
    @Select("select count(*) from business where busi_status=#{busiStatus} and busi_Pstation1=#{pttionId}")
    public int count3(@Param("busiStatus")Integer busiStatus,
                      @Param("pttionId")String pttionId);

    //7.更改商品状态
    @Update("update business set busi_status=#{busiStatus} where busi_Id=#{busiId}")
    public boolean updateBus(@Param("busiId")String busiId,
                             @Param("busiStatus")Integer busiStatus);

    //8.更改订单显示状态
    @Update("update business set busi_is_status=#{busiIsStatus} where busi_Id=#{busiId}")
    public boolean updateBusSta(@Param("busiId")String busiId,
                             @Param("busiIsStatus")Integer busiIsStatus);


    //8.添加发出驿站信息
    @Update("update business set busi_Pstation1=#{busiPstation1} where busi_Id=#{busiId}")
    public boolean updateBusiPstation1(@Param("busiId")String busiId,
                             @Param("busiPstation1")String busiPstation1);

    //8.添加发出驿站信息
    @Update("update business set busi_Pstation2=#{busiPstation2} where busi_Id=#{busiId}")
    public boolean updateBusiPstation2(@Param("busiId")String busiId,
                                       @Param("busiPstation2")String busiPstation2);

    //9
    @Select("select busi_Pstation1 from business where busi_Id=#{busiId}")
    String selectbusiPstation1BybusiId(@Param("busiId")String busiId);

    //10.查询当前订单状态是否为设定值
    @Select("select count(*) from business where busi_wechat=#{personWeChat} and busi_status=#{busiStatus} ")
    Integer selectBusByBusiStatusWechat(@Param("personWeChat")String personWeChat,
                                   @Param("busiStatus")Integer busiStatus);

    //10.查询当前用户是否拥有设定值的订单
    @Select("select count(*) from business where busi_Id=#{busiId} and busi_status=#{busiStatus} ")
    Integer selectBusByBusiStatus(@Param("busiId")String busiId,
                                  @Param("busiStatus")Integer busiStatus);

    //10.查询当前用户是否拥有设定值的订单
    @Select("select count(*) from business where busi_mid=#{busiMid} and busi_status=#{busiStatus} ")
    Integer selectBusByBusiMid(@Param("busiMid")String busiMid,
                                  @Param("busiStatus")Integer busiStatus);
    //10.查询当前用户是否拥有设定值的订单
    @Select("select busi_Id from business where busi_mid=#{busiMid} and busi_status=#{busiStatus} ")
    String selectBusByBusiMid1(@Param("busiMid")String busiMid,
                               @Param("busiStatus")Integer busiStatus);

    //11.订单与制氧机编号匹配
    @Update("update business set busi_mid=#{omId} where busi_Id=#{busiId}")
    public boolean updateBusiMid(@Param("busiId")String busiId,
                             @Param("omId")String omId);
    //12.订单更新送达时间
    @Update("update business set busi_Adate=#{busiAdate} where busi_Id=#{busiId}")
    public boolean updateBusiAdate(@Param("busiId")String busiId,
                                 @Param("busiAdate")String busiAdatemId);

    //12.订单更新送达时间
    @Update("update business set busi_Ddate=#{busiDdate} where busi_Id=#{busiId}")
    public boolean updateBusiDdate(@Param("busiId")String busiId,
                                   @Param("busiDdate")String busiDdatemId);

    //13.订单更新支付状态
    @Update("update business set busiPay=1 where busi_Id=#{busiId}")
    public boolean updateBusiPay(@Param("busiId")String busiId);

    //通过制氧机号查询用户名
    @Select("select busi_Name from business where busi_mid=#{busiMid} and busi_is_status=1 ")
    String selectUserByBusiMid(@Param("busiMid")String busiMid);


    //8.扫码枪扫码状态改为8
//    @Update("update business set busi_status=#{busiStatus} where busi_Id=#{busiId}")
//    public boolean scanCodeBus(@Param("busiId")String busiId,
//                             @Param("busiStatus")Integer busiStatus);
}
