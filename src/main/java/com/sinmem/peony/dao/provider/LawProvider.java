package com.sinmem.peony.dao.provider;

import org.springframework.beans.TypeMismatchException;

import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.provider
 * @Author: sinmem
 * @CreateTime: 2019-10-20 23:39
 * @Description:
 * @version: V1.0
 */
public class LawProvider {
    public String searchLawsOnContent(Map<String, Object> para) throws ClassCastException {
        Object obj = para.get("conditions");
        if(obj instanceof String[]){
            StringBuilder sb = new StringBuilder().append("SELECT * FROM v_law WHERE ");
            String[] strs = (String[]) obj;
            for (int i=0; i<strs.length; i++) {
                if(i<strs.length-1){
                    sb.append("content LIKE '%").append(strs[i]).append("%' AND ");
                }else {
                    sb.append("content LIKE '%").append(strs[i]).append("%' ORDER BY release_time desc, legal_name asc, `no` asc");
                }
            }
            return sb.toString();
        }else throw new ClassCastException(obj.getClass() + " can not cast to [Ljava.lang.String");
    }
    public String updateLawsStatus(Map<String, Object> para) throws ClassCastException {
        Object obj = para.get("oldLaws");
        Object lawStatus = para.get("lawStatus");
        if(obj instanceof Long[]){
            StringBuilder sb = new StringBuilder().append("UPDATE t_law tl SET tl.status = '"+lawStatus+"' WHERE id IN(");
            Long[] oldLaws = (Long[]) obj;
            for (int i=0; i<oldLaws.length; i++) {
                if(i<oldLaws.length-1){
                    sb.append(oldLaws[i]).append(", ");
                }else {
                    sb.append(oldLaws[i]).append(");");
                }
            }
            return sb.toString();
        }else throw new ClassCastException(obj.getClass() + " can not cast to [Ljava.lang.Long");
    }
    public String addUpdateRecode(Map<String, Object> para) throws ClassCastException {
        Object newLawId = para.get("newLawId");
        Object obj = para.get("oldLaws");
        if(obj instanceof Long[]){
            StringBuilder sb = new StringBuilder().append("INSERT INTO law_relation(new_law,old_law) VALUES");
            Long[] oldLaws = (Long[]) obj;
            for (int i=0; i<oldLaws.length; i++) {
                if(i<oldLaws.length-1){
                    sb.append("(").append(newLawId).append(", ").append(oldLaws[i]).append("),");
                }else {
                    sb.append("(").append(newLawId).append(", ").append(oldLaws[i]).append(");");
                }
            }
            return sb.toString();
        }else throw new ClassCastException(obj.getClass() + " can not cast to [Ljava.lang.Long");
    }
}
