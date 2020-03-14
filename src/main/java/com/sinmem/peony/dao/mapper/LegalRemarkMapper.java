package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.LegalRemark;
import com.sinmem.peony.dao.bean.TempLegalRemark;
import com.sinmem.peony.dao.provider.LegalRemarkProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LegalRemarkMapper {
    @Select("SELECT content, law_id, type FROM t_remark WHERE law_id = #{lawId} ORDER BY type")
    public LegalRemark getRemarkByLawId(Long LawId);

    @Update("UPDATE t_remark SET content = #{remark.content} WHERE law_id = #{remark.lawId} AND type = #{remark.type}")
    public int updRemark(@Param("remark") LegalRemark legalRemark);

    @Insert("INSERT INTO t_remark(content, law_id, type) " +
            "VALUES(#{remark.content}, #{remark.lawId}, #{remark.type})")
    @Options(useGeneratedKeys = true, keyProperty = "remark.id")
    public int addRemark(@Param("remark") LegalRemark legalRemark);


//    @Delete("DELETE t_remark WHERE law_id = #{lawId}")
    @DeleteProvider(type = LegalRemarkProvider.class, method = "removeRemark")
    public int delRemark(@Param("lawIds") Long[] lawIds);

    /*------ 从临时表输入 ------*/
    @Insert("INSERT INTO t_remark(content, law_id, type) SELECT ttr.content, ttr.law_id, ttr.type FROM t_temp_remark ttr WHERE ttr.id = #{tempRemarkId}")
    public int addRemarkFromTemp(Long tempRemarkId);

    @Update("UPDATE t_remark tr, (SELECT ttr.content, ttr.law_id, ttr.type FROM t_temp_remark ttr WHERE ttr.id = #{tempRemarkId}) rm " +
            "SET tr.content = rm.content WHERE tr.law_id = rm.law_id AND tr.type = rm.type")
    public int updRemarkFromTemp(Long tempRemarkId);
    /*------ 临时表操作 ------*/
    @Select("SELECT * FROM t_temp_remark ORDER BY update_time")
    public List<TempLegalRemark> getTempRemarks();

    @Insert("INSERT INTO t_temp_remark(content, law_id, type, submitter, submission_type) " +
            "VALUES(#{remark.content}, #{remark.lawId}, #{remark.type}, #{remark.submitter}, #{remark.submissionType})")
    public int addRemarkToTemp(@Param("remark") TempLegalRemark tempLegalRemark);

    @Delete("DELETE FROM t_temp_remark WHERE id = #{tempRemarkId}")
    public int delTempRemark(Long tempRemarkId);
}
