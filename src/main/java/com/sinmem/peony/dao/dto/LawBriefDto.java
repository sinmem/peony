package com.sinmem.peony.dao.dto;

import com.sinmem.peony.dao.bean.BaseBean;
import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.bean.TagBean;

import java.time.Year;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.dto
 * @Author: sinmem
 * @CreateTime: 2019-10-17 15:59
 * @Description: 简洁的法条数据传输对象
 * @version: V1.0
 */
public class LawBriefDto extends LawBean {
    /**
     * 法条对应标签
     */
    private List<TagBean> legalTags;

    public List<TagBean> getLegalTags() {
        return legalTags;
    }

    public void setLegalTags(List<TagBean> legalTags) {
        this.legalTags = legalTags;
    }
}
