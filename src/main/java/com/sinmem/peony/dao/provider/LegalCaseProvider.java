package com.sinmem.peony.dao.provider;

import com.sinmem.peony.common.enums.CaseStatus;

import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.provider
 * @Author: sinmem
 * @CreateTime: 2019-11-08 18:06
 * @Description:
 * @version: V1.0
 */
public class LegalCaseProvider {
    public String removeCase(Map<String, Object> para){
        Long[] lawIds = (Long[]) para.get("lawIds");
        StringBuilder sb = new StringBuilder("DELETE FROM t_legal_case WHERE id IN( ");
        for (Long lawId : lawIds) {
            sb.append(lawId).append(",");
        }
        return  sb.deleteCharAt(sb.length()-1).append(")").toString();
    }

    public String updCaseApps(Map<String, Object> para){
        Long[] caseIds = (Long[]) para.get("caseIds");
        CaseStatus status = (CaseStatus) para.get("status");
        String msg = (String) para.get("msg");
        StringBuilder sb = new StringBuilder("UPDATE case_application SET status = '").append(status).append("', msg = '").append(msg).append("' WHERE legal_case IN( ");
        for (Long caseId : caseIds) {
            sb.append(caseId).append(",");
        }
        return  sb.deleteCharAt(sb.length()-1).append(")").toString();
    }
}
