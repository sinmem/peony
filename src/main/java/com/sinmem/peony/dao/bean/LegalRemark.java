package com.sinmem.peony.dao.bean;

import com.sinmem.peony.common.enums.RemarkType;

import java.util.Date;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.domain.bean
 * @Author: sinmem
 * @CreateTime: 2019-10-15 10:27
 * @Description: 法律条文备注类, 用于给该条法条添加备注
 * @version: V1.0
 */
public class LegalRemark extends BaseBean {
    /**
     * 法条备注正文内容
     */
    private String content;
    /**
     * 备注类型
     */
    private RemarkType type;
    /**
     * 法条备注更新时间
     */
    private Date updateTime;

    private Long lawId;

    public Long getLawId() {
        return lawId;
    }

    public void setLawId(Long lawId) {
        this.lawId = lawId;
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

    public RemarkType getType() {
        return type;
    }

    public void setType(RemarkType type) {
        this.type = type;
    }
}
