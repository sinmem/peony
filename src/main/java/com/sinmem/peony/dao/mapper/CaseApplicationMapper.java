package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.common.enums.CaseStatus;
import com.sinmem.peony.dao.bean.CaseApplication;
import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.provider.LegalCaseProvider;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface CaseApplicationMapper {
    /**
     * 向用户案例申请记录表中添加用户的案例申请记录
     * @param CaseApp 法条案例记录
     * @param uid 用户id
     * @return
     */
    @Insert("INSERT INTO case_application(user, legal_case, msg) VALUES(#{uid}, #{caseApp.legalCase.id}, #{caseApp.msg})")
    Integer addCaseAppByUsr(@Param("uid")Long uid, @Param("caseApp")CaseApplication CaseApp);

    /**
     * 根据用户id从用户案例申请记录表中获取用户的案例申请记录
     * @param uid 用户id
     * @return
     */
    @Select("SELECT * FROM case_application WHERE user= #{uid}")
    ArrayList<CaseApplication> getCaseAppByUsr(@Param("uid")Long uid);

    /**
     * 根据id从用户案例申请记录表中更新用户的案例申请记录状态
     * @param caseId 案例id
     * @param caseApplication 用户案例申请详细
     * @return
     */
    @Update("UPDATE case_application SET status = #{caseApp.status},msg = #{caseApp.msg} WHERE legal_case = #{caseId}")
    Integer updCaseAppByUsr(@Param("caseId")Long caseId, @Param("caseApp")CaseApplication caseApplication);

    /**
     * 根据记录id从用户案例申请记录表中删除用户的案例申请记录
     * @param id 记录id
     * @return
     */
    @Delete("DELETE FROM case_application WHERE id= #{id}")
    Integer delCaseAppByUsr(@Param("id")Integer id);

    /**
     * 根据记录id和用户id,判断该条记录是否有
     * @param id 记录id
     * @param uid 用户id
     * @return
     */
    @Select("SELECT COUNT(*) FROM case_application WHERE id = #{id} AND user = #{uid}")
    Integer hasRecode(@Param("id")Integer id, @Param("uid")Long uid);

    @UpdateProvider(type = LegalCaseProvider.class, method = "updCaseApps")
    Integer updCaseApps(@Param("status") CaseStatus status, @Param("msg") String msg, @Param("caseIds") Long[] caseIds);
}
