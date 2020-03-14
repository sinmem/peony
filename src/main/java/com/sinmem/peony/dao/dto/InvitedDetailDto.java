package com.sinmem.peony.dao.dto;

import java.util.Date;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.dto
 * @Author sinmem
 * @CreateTime 2020-01-04 18:37
 * @Description 邀请用户详情数据传输对象
 */
@SuppressWarnings("unused")
public class InvitedDetailDto {
    private Long invitee;
    private String phoneNumber;
    private Date invitedTime;

    public Long getInvitee() {
        return invitee;
    }

    public void setInvitee(Long invitee) {
        this.invitee = invitee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getInvitedTime() {
        return invitedTime;
    }

    public void setInvitedTime(Date invitedTime) {
        this.invitedTime = invitedTime;
    }
}
