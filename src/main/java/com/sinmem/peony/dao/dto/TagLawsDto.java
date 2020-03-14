package com.sinmem.peony.dao.dto;

import com.sinmem.peony.dao.bean.TagBean;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.dto
 * @Author: sinmem
 * @CreateTime: 2019-11-02 18:34
 * @Description: 存储标签和法条集合的数据传输对象
 * @version: V1.0
 */
public class TagLawsDto implements Serializable {
    private TagBean tag;
    private List<LawBriefDto> laws;

    public TagLawsDto() {
    }

    public TagLawsDto(TagBean tag, List<LawBriefDto> laws) {
        this.tag = tag;
        this.laws = laws;
    }

    public TagBean getTag() {
        return tag;
    }

    public void setTag(TagBean tag) {
        this.tag = tag;
    }

    public List<LawBriefDto> getLaws() {
        return laws;
    }

    public void setLaws(List<LawBriefDto> laws) {
        this.laws = laws;
    }
}
