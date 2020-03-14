package com.sinmem.peony.dao.bean;

import java.io.Serializable;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.domain.bean
 * @Author: sinmem
 * @CreateTime: 2019-10-15 09:37
 * @Description: 通用属性bean
 * @version: V1.0
 */
public class BaseBean implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    public BaseBean() {
    }

    public BaseBean(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
