package com.sinmem.peony.dao.provider;

import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.provider
 * @Author: sinmem
 * @CreateTime: 2019-11-08 18:06
 * @Description:
 * @version: V1.0
 */
public class LegalRemarkProvider {
    public String removeRemark(Map<String, Object> para){
        Long[] lawIds = (Long[]) para.get("lawIds");
        StringBuilder sb = new StringBuilder("DELETE FROM t_remark WHERE law_id IN( ");
        for (Long lawId : lawIds) {
            sb.append(lawId).append(",");
        }
        return  sb.deleteCharAt(sb.length()-1).append(")").toString();
    }
}
