package com.sinmem.peony.dao.dto;

import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.bean.LegalRemark;
import com.sinmem.peony.dao.bean.TagBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.dto
 * @Author: sinmem
 * @CreateTime: 2019-10-18 10:17
 * @Description: 完整的法条数据对象
 * @version: V1.0
 */
public class LawCompleteDto extends LawBean{
    /**
     * 法条对应标签
     */
    private List<TagDto> legalTags;
    /**
     * 法条对应案例列表
     */
    private LegalCase legalCases;
    /**
     * 法条备注
     */
    private List<LegalRemark> legalRemark;

    public List<TagDto> getLegalTags() {
        return legalTags;
    }

    public void setLegalTags(List<TagDto> legalTags) {
        this.legalTags = legalTags;
    }

    public LegalCase getLegalCases() {
        return legalCases;
    }

    public void setLegalCases(LegalCase legalCases) {
        this.legalCases = legalCases;
    }

    public List<LegalRemark> getLegalRemark() {
        return legalRemark;
    }

    public void setLegalRemark(List<LegalRemark> legalRemark) {
        this.legalRemark = legalRemark;
    }
}
