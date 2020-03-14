package com.sinmem.peony.dao.dto;

import com.sinmem.peony.dao.bean.BaseBean;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.dto
 * @Author sinmem
 * @CreateTime 2020-02-25 01:27
 * @Description
 */
public class LegalNameDto extends BaseBean {
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
