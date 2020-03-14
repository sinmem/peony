package com.sinmem.peony.dao.provider;

import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.provider
 * @Author: sinmem
 * @CreateTime: 2019-10-21 00:06
 * @Description:
 * @version: V1.0
 */
public class LegalNameProvider {
    public String searchLawsOnFullName(Map<String, Object> para) throws ClassCastException {
        Object obj = para.get("conditions");
        if(obj instanceof String[]){
            StringBuilder sb = new StringBuilder().append("SELECT tln.id,tln.full_name,tln.count FROM t_legal_name tln WHERE ");
            String[] strs = (String[]) obj;
            for (int i=0; i<strs.length; i++) {
                if(i<strs.length-1){
                    sb.append("full_name LIKE '%").append(strs[i]).append("%' AND ");
                }else {
                    sb.append("full_name LIKE '%").append(strs[i]).append("%' ORDER BY CONVERT(tln.full_name USING GBK)");
                }
            }
            return sb.toString();
        }else throw new ClassCastException(obj.getClass() + " can not cast to [Ljava.lang.String");
    }
}
