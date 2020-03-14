package com.sinmem.peony.dao.bean;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.bean
 * @Author: sinmem
 * @CreateTime: 2019-10-17 15:48
 * @Description: 标签表实体类
 * @version: V1.0
 */
public class TagBean extends BaseBean{
    /**
     * 标签名
     */
    private String name;
    /**
     * 出现标签的法条计数
     */
    private Integer count;

    public TagBean() {
    }

    public TagBean(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public TagBean(Long id, Integer count) {
        super(id);
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
