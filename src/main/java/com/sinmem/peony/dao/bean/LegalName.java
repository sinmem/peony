package com.sinmem.peony.dao.bean;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.domain.bean
 * @Author: sinmem
 * @CreateTime: 2019-10-15 09:57
 * @Description: 法律名称类
 * @version: V1.0
 */
public class LegalName extends BaseBean {
    /**
     * 法律名称简称
     */
    private String abbreviation;
    /**
     * 法律名称全称
     */
    private String fullName;
    /**
     * 法律所属法条数量
     */
    private String count;
    /**
     * 法律类别
     */
    private String category;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
