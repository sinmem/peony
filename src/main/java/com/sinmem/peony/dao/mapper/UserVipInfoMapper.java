package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.bean.UserVipInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserVipInfoMapper {
    @Select("SELECT * FROM user_vip_info WHERE user_id = #{user.id}")
    UserVipInfo getUserVipInfo(@Param("user") User user);
}
