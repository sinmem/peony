package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("SELECT tr.id, tr.role FROM role tr,user_role tur WHERE tr.id = tur.role_id AND tur.user_id = #{userId}")
    public List<Role> getRolesByUserId(@Param("userId")Integer userId);

    @Insert("INSERT INTO user_role(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int addUserByUserId(@Param("userId")Long userId, @Param("roleId")Integer roleId);
}
