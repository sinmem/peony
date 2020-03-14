package com.sinmem.peony.dao.bean;

import com.sinmem.peony.common.enums.PointType;

import java.util.Date;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.bean
 * @Author sinmem
 * @CreateTime 2019-12-29 12:48
 * @Description 积分详情
 */
public class PointDetail extends BaseBean {
    private User user;
    private Integer pointsDetail;
    private Integer pointsCount;
    private PointType detailType;
    private String remark;
    private Date createTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPointsDetail() {
        return pointsDetail;
    }

    public void setPointsDetail(Integer pointsDetail) {
        this.pointsDetail = pointsDetail;
    }

    public Integer getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(Integer pointsCount) {
        this.pointsCount = pointsCount;
    }

    public PointType getDetailType() {
        return detailType;
    }

    public void setDetailType(PointType detailType) {
        this.detailType = detailType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
