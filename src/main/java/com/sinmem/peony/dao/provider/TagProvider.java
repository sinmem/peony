package com.sinmem.peony.dao.provider;

import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.provider
 * @Author: sinmem
 * @CreateTime: 2019-10-21 00:11
 * @Description:
 * @version: V1.0
 */
public class TagProvider {
    public String searchLawsOnTag(Map<String, Object> para) throws ClassCastException {
        Object obj = para.get("conditions");
        if(obj instanceof String[]){
            StringBuilder sb = new StringBuilder().append("SELECT tt.* FROM t_tag tt WHERE ");
            String[] strs = (String[]) obj;
            for (int i=0; i<strs.length; i++) {
                if(i<strs.length-1){
                    sb.append("name LIKE '%").append(strs[i]).append("%' AND ");
                }else {
                    sb.append("name LIKE '%").append(strs[i]).append("%' ORDER BY CONVERT(tt.name USING GBK)");
                }
            }
            return sb.toString();
        }else throw new ClassCastException(obj.getClass() + " can not cast to [Ljava.lang.String");
    }

    public String addTagLaw(Map<String, Object> para){
        Long tagId = (Long) para.get("tagId");
        Long[] lawIds = (Long[]) para.get("lawIds");
        StringBuilder sb = new StringBuilder("INSERT INTO t_tag_law(tag_id, law_id) VALUES");
        for (Long lawId : lawIds) {
            sb.append("(").append(tagId).append(",").append(lawId).append("),");
        }
        return  sb.deleteCharAt(sb.length()-1).toString();
    }

    public String removeLawTag(Map<String, Object> para){
        Long tagId = (Long) para.get("tagId");
        Long[] lawIds = (Long[]) para.get("lawIds");
        StringBuilder sb = new StringBuilder("DELETE FROM t_tag_law WHERE tag_id = ").append(tagId).append(" AND law_id IN( ");
        for (Long lawId : lawIds) {
            sb.append(lawId).append(",");
        }
        return  sb.deleteCharAt(sb.length()-1).append(")").toString();
    }
}
