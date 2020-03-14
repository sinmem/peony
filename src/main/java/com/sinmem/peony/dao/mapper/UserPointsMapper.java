package com.sinmem.peony.dao.mapper;


import com.sinmem.peony.dao.bean.PointDetail;
import com.sinmem.peony.dao.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserPointsMapper {
    /**
     * 新增积分详情记录
     *
     * @param pointDetail
     * @return
     */
    @Insert("INSERT INTO point_detail(user_id, points_detail, points_count, detail_type, remark) " +
            "VALUES(#{pointDetail.user.id}, #{pointDetail.pointsDetail} , #{pointDetail.pointsCount}," +
            " #{pointDetail.detailType}, #{pointDetail.remark})")
    Integer addPoints(@Param("pointDetail") PointDetail pointDetail);

    /**
     * 新增用户积分记录
     *
     * @param user 用户信息-需要用户id
     * @return
     */
    @Insert("INSERT INTO user_point(user_id) VALUES(#{user.id})")
    Integer insertPoint(@Param("user") User user);

    /**
     * 更新用户积分
     *
     * @param pointDetail
     * @return
     */
    @Update("UPDATE user_point SET points_count = #{pointDetail.pointsCount} WHERE user_id = #{pointDetail.user.id}")
    Integer updateUserPoint(@Param("pointDetail") PointDetail pointDetail);

    @Select("SELECT tup.points_count FROM user_point tup WHERE user_id = #{user.id}")
    Integer getUserPointCount(@Param("user") User user);

    @Select("SELECT tpd.points_detail, tpd.points_count, tpd.detail_type, tpd.remark, tpd.create_time FROM point_detail tpd WHERE user_id = #{user.id}")
    List<PointDetail> queryPointDetails(@Param("user") User user);

    @Select("SELECT tpd.points_detail, tpd.points_count, tpd.detail_type, tpd.remark, tpd.create_time FROM point_detail tpd WHERE user_id = #{user.id} AND detail_type = 'PAY'")
    List<PointDetail> queryPointPayDetails(@Param("user") User user);

    @Select("SELECT tpd.points_detail, tpd.points_count, tpd.detail_type, tpd.remark, tpd.create_time FROM point_detail tpd WHERE user_id = #{user.id} AND detail_type IN('BONUS','INVITE')")
    List<PointDetail> queryPointGetDetails(@Param("user") User user);
}
