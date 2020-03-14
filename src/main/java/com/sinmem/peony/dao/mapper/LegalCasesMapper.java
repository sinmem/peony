package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.dto.LegalCaseDto;
import com.sinmem.peony.dao.provider.LegalCaseProvider;
import org.apache.ibatis.annotations.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface LegalCasesMapper {
    @Select("SELECT name, content,dissent_count,agree_count FROM t_legal_case WHERE is_valid = true AND law_id = #{lawId}")
    public LegalCase getCaseByLawId(Long LawId);

    @Select("SELECT tlc.id, tlc.law_id, tlc.content, tlc.update_time, vl.no, vl.full_name FROM t_legal_case tlc " +
            "LEFT JOIN v_law vl ON vl.id = tlc.law_id WHERE is_valid = false")
    ArrayList<LegalCaseDto> getCases();

    @Insert("INSERT INTO t_legal_case(content, law_id) VALUES(#{legalCase.content}, #{legalCase.lawId})")
    @Options(useGeneratedKeys = true, keyProperty = "legalCase.id")
    public  int addCase(@Param("legalCase") LegalCase legalCase);

    @Update("UPDATE t_legal_case SET name = #{legalCase.name}, content = #{legalCase.content} WHERE law_id = #{legalCase.lawId}")
    public int updCase(@Param("legalCase") LegalCase legalCase);

//    @Delete("DELETE FROM t_legal_case WHERE id = #{lawId}")
    @DeleteProvider(type = LegalCaseProvider.class, method = "removeCase")
    public int delCase(@Param("lawIds")Long[] lawIds);


    @Update("UPDATE t_legal_case SET dissent_count = dissent_count+1 WHERE id = #{caseId};")
    void addDisagree(@Param("caseId") Long caseId);

    @Update("UPDATE t_legal_case SET agree_count = agree_count+1 WHERE id = #{caseId};")
    void addAgreeCase(@Param("caseId")Long caseId);

    @Insert("INSERT INTO usrc_agree(case_id,user_id,type) VALUES(#{caseId},#{user.id},#{type}) ")
    void addusrCR(@Param("user")User user, @Param("caseId")Long caseId, @Param("type")Boolean type);

    @Select("SELECT count(*) FROM usrc_agree WHERE case_id = #{caseId} AND user_id=#{user.id}")
    int hasDo(@Param("user")User user, @Param("caseId")Long caseId);

    @Update("UPDATE t_legal_case SET is_valid = true WHERE id = #{caseId}")
    int validCase(@Param("caseId") Long caseId);

    /**
     * 根据id获取本法条为通过但存在的案例
     * @param lawId
     * @return
     */
    @Select("SELECT id FROM t_legal_case WHERE law_id = #{lawId} AND is_valid = false")
    ArrayList<Long> getIdsByLawId(@Param("lawId") Long lawId);

    @Select("SELECT COUNT(*) FROM t_legal_case WHERE law_id = #{lawId} AND is_valid = true")
    Integer hasCase(@Param("lawId") Long lawId);


}
