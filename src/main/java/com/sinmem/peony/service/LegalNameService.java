package com.sinmem.peony.service;

import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.LegalName;

/**
 * 法律名称业务接口
 */
public interface LegalNameService {
    /**
     * 通过关键词查找全称相符的法条接口方法
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param conditions 查询关键词
     * @return 法律名称集合
     */
    public ResultPage<LegalName> searchFullNames(Integer pageNum, Integer pageSize, String...conditions);
}
