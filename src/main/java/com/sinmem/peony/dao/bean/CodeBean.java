package com.sinmem.peony.dao.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.common.context
 * @Author sinmem
 * @CreateTime 2020-01-05 14:36
 * @Description 验证码记录数据对象
 */
public class CodeBean implements Serializable {
    private String code;
    private Long createTime;

    public CodeBean(String code, Long createTime) {
        this.code = code;
        this.createTime = createTime;
    }

    public CodeBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    @Override
    public String toString() {
        return "{\"code\":"+code+", \"createTime\":"+createTime+'}';
    }
}
