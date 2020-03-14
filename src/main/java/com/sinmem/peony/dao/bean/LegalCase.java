package com.sinmem.peony.dao.bean;

import java.util.Date;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.common.enums
 * @Author: sinmem
 * @CreateTime: 2019-10-15 10:06
 * @Description: 法律案例
 * @version: V1.0
 */
public class LegalCase extends BaseBean {
    /**
     * 法律案例名称(类似于标题)
     */
    private String name;
    /**
     * 法律案例连接
     */
    private String content;

    private Long lawId;
    /**
     * 案例更新时间
     */
    private Date updateTime;
    private Integer agreeCount;
    private Integer dissentCount;

    public Integer getDissentCount() {
        return dissentCount;
    }

    public void setDissentCount(Integer dissentCount) {
        this.dissentCount = dissentCount;
    }

    private Boolean isValid;

    public Integer getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(Integer agreeCount) {
        this.agreeCount = agreeCount;
    }

    public Boolean isValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getLawId() {
        return lawId;
    }

    public void setLawId(Long lawId) {
        this.lawId = lawId;
    }
}
