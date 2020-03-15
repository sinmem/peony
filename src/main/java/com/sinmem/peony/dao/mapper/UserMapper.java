package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Results(id = "userDetail",value = {
            @Result(property = "id", column = "id", id = true),
//            @Result(property = "phoneNumber", column = "phone_number"),
//            @Result(property = "username", column = "username"),
//            @Result(property = "accountStatus", column = "account_status"),
//            @Result(property = "passwordStatus", column = "password_status"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "com.sinmem.peony.dao.mapper.RoleMapper.getRolesByUserId")),
    })
    @Select("SELECT * FROM user tu WHERE tu.phone_number = #{phoneNumber}")
    public User getUserByPhone(@Param("phoneNumber") String phoneNumber);

    @Select("SELECT tu.id, tu.phone_number, tu.username FROM user tu WHERE tu.id = #{userId}")
    public User getUserById(@Param("userId") Long userId);

    @Select("SELECT tu.id FROM user tu WHERE tu.phone_number = #{phoneNumber}")
    public Long getUserIdByPhone(@Param("phoneNumber") String phoneNumber);

    @Insert("INSERT INTO user(phone_number, password) VALUES(#{usr.phoneNumber}, #{usr.password})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "usr.id")
    int addUser(@Param("usr") User usr);

    @Update("UPDATE user SET phone_number = #{user.phoneNumber} WHERE id = #{user.id}")
    int updatePhoneNumber(@Param("user")User user);

    @Update("UPDATE user SET username = #{user.username} WHERE id = #{user.id}")
    int updateUserName(@Param("user")User user);

    @Update("UPDATE user SET password = #{user.password} WHERE phone_number = #{user.phoneNumber}")
    int updatePWDByPhone(@Param("user")User user);
}
