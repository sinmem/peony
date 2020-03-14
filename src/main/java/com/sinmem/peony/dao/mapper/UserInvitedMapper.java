package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.dto.InvitedDetailDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInvitedMapper {
    /**
     * 查询此用户有没有被其他人邀请
     * @param invitee 被邀请人id
     * @return inviter 邀请他的人
     */
    @Select("SELECT inviter FROM invite_detail WHERE invitee = #{invitee}")
    Long isInvited(@Param("invitee")Long invitee);

    /**
     * 查询此用户邀请过的其他人
     * @param inviter 邀请人id
     * @return
     */
    @Select("SELECT invitee,phone_number,create_time invitedTime FROM invite_detail WHERE inviter = #{inviter}")
    List<InvitedDetailDto> getInvitedList(@Param("inviter")Long inviter);

    @Select("SELECT user_id FROM user_invite_code WHERE invite_code = #{inviteCode}")
    Long getInviterIdByCode(@Param("inviteCode") String inviteCode);

    @Select("SELECT invite_code FROM user_invite_code WHERE user_id = #{userId}")
    String getCodeIdByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO invite_detail(inviter, invitee, phone_number) VALUES(#{inviterId}, #{invitee.id}, #{invitee.phoneNumber})")
    int addInvitedDetail(@Param("invitee")User invitee, @Param("inviterId") Long inviterId);

    @Insert("INSERT INTO user_invite_code(user_id, invite_code) VALUES(#{userId}, #{inviteCode})")
    int insertInviteCode(@Param("userId")Long userId, @Param("inviteCode") String inviteCode);
}
