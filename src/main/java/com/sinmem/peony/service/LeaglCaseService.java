package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.bean.User;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service
 * @Author: sinmem
 * @CreateTime: 2019-11-04 13:54
 * @Description: 案例服务接口层
 * @version: V1.0
 */
public interface LeaglCaseService {
    /**
     * 通过法条id获取案例
     * @param lawId 法条id
     * @return
     */
    public LegalCase getCaseByLawId(Long lawId);

    /**
     * 添加案例
     * @param legalCase 法条案例
     * @return
     */
    public LegalCase addCase(LegalCase legalCase);

    /**
     * 更新案例
     * @param legalCase 法条案例
     * @return
     */
    @Deprecated
    public int updateCase(LegalCase legalCase);

    /**
     * 根据法条id删除案例
     * @param lawIds 法条id
     * @return
     */
    public int deleteCaseByLawId(Long[] lawIds);

    Result disagreeCase(User user, Long caseId);

    Result validCase(Long caseId, Long lawId);

    Result agreeCase(User user, Long caseId);

    Result getDoCases(Integer pageNum, Integer pageSize);
}
