package com.sinmem.peony.dao.provider;

import java.util.Map;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.provider
 * @Author sinmem
 * @CreateTime 2020-03-18 00:48
 * @Description 判决SQL查询代理
 */
public class JudgmentProvider {
    public String searchJudgments(Map<String, Object> para){
        String  condition = (String)para.get("condition");
            StringBuilder sb = new StringBuilder().append("SELECT * FROM judgments WHERE title LIKE '%")
                    .append(condition).append("%' OR description LIKE '%").append(condition).append("%'");
            return sb.toString();
    }
}
