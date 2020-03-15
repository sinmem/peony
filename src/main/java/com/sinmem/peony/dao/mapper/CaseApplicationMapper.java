package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.common.enums.CaseStatus;
import com.sinmem.peony.dao.bean.CaseApplication;
import com.sinmem.peony.dao.bean.LegalCase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface CaseApplicationMapper {
    /**
     * 向用户案例申请记录表中添加用户的案例申请记录
     * @param CaseApp 法条案例记录
     * @param uid 用户id
     * @return
     */
    @Insert("INSERT INTO case_application() VALUES()")
    int addCaseAppByUsr(@Param("uid")Long uid, @Param("CaseApp")CaseApplication CaseApp);

    /**
     * 根据用户id从用户案例申请记录表中获取用户的案例申请记录
     * @param uid
     * @return
     */
    @Select("SELECT * FROM case_application WHERE user= #{uid}")
    ArrayList<CaseApplication> getCaseAppByUsr(@Param("uid")Long uid);

    /**
     * 根据id从用户案例申请记录表中更新用户的案例申请记录状态
     * @param id
     * @return
     */
    @Select("UPDATE SET case_application status = #{status} WHERE id= #{id}")
    int updCaseAppByUsr(@Param("id")Long id, @Param("status")CaseStatus status);

    /**
     * 根据记录id从用户案例申请记录表中删除用户的案例申请记录
     * @param id
     * @return
     */
    @Delete("DELETE FROM case_application WHERE id= #{id}")
    int delCaseAppByUsr(@Param("id")Integer id);

    /**
     * 根据记录id和用户id,判断该条记录是否有
     * @param id
     * @param uid
     * @return
     */
    @Select("SELECT COUNT(*) FROM case_application WHERE id = #{id} AND user = #{uid}")
    int hasRecode(@Param("id")Integer id, @Param("uid")Long uid);
}
