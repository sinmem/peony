package com.sinmem.peony.dao.dto;

import com.sinmem.peony.dao.bean.LegalCase;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.dto
 * @Author sinmem
 * @CreateTime 2020-02-29 11:28
 * @Description
 */
public class LegalCaseDto extends LegalCase {
    private Integer no;
    private String fullName;

    public LegalCaseDto(Integer no, String fullName) {
        this.no = no;
        this.fullName = fullName;
    }
}
