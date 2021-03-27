package com.sinmem.peony.dao.dto;

import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.bean.TagBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.dto
 * @Author sinmem
 * @CreateTime 2021-03-27 13:44
 * @Description 标签
 */
public class TagDto extends TagBean {

    private List<LawBean> otherLaws = new ArrayList<>();

    public List<LawBean> getOtherLaws() {
        if(otherLaws.isEmpty())
            otherLaws.add(new LawBean());
        return otherLaws;
    }

    public void setOtherLaws(List<LawBean> otherLaws) {
        this.otherLaws = otherLaws;
    }
}
