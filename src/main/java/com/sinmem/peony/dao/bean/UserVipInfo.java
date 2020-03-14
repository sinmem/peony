package com.sinmem.peony.dao.bean;

import java.util.Date;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.bean
 * @Author sinmem
 * @CreateTime 2020-02-21 18:37
 * @Description 用户Vip信息类
 */
public class UserVipInfo extends BaseBean {
    private Long userId;
    private Boolean vipStatus;
    private Date vipDueTime;
    private Boolean sVipStatus;
    private Date sVipDueTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Boolean vipStatus) {
        this.vipStatus = vipStatus;
    }


    public Boolean getsVipStatus() {
        return sVipStatus;
    }

    public void setsVipStatus(Boolean sVipStatus) {
        this.sVipStatus = sVipStatus;
    }

    public Date getVipDueTime() {
        return vipDueTime;
    }

    public void setVipDueTime(Date vipDueTime) {
        this.vipDueTime = vipDueTime;
    }

    public Date getsVipDueTime() {
        return sVipDueTime;
    }

    public void setsVipDueTime(Date sVipDueTime) {
        this.sVipDueTime = sVipDueTime;
    }
}
