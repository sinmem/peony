package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.common.enums.LawStatus;
import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.dao.dto.LawCompleteDto;
import com.sinmem.peony.dao.provider.LawProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LawMapper {

    @Results(id = "LawCompleteMap",value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "no", column = "no"),
            @Result(property = "content", column = "content"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "status", column = "status"),
            @Result(property = "releaseTime", column = "release_time"),
            @Result(property = "legalTags", column = "id",
                    many = @Many(select = "com.sinmem.peony.dao.mapper.TagMapper.queryTagByLawId")),
            @Result(property = "legalCases", column = "id",
                    one = @One(select = "com.sinmem.peony.dao.mapper.LegalCasesMapper.getCaseByLawId")),
            @Result(property = "legalRemark", column = "id",
                    one = @One(select = "com.sinmem.peony.dao.mapper.LegalRemarkMapper.getRemarkByLawId")),
    })
    @Select("SELECT * FROM v_law vl WHERE vl.id = #{id}")
    public LawCompleteDto queryLawById(Long id);

    @Results(id = "LawMap",value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "no", column = "no"),
            @Result(property = "content", column = "content"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "status", column = "status"),
            @Result(property = "releaseTime", column = "release_time"),
            @Result(property = "legalTags", column = "id",
                    many = @Many(select = "com.sinmem.peony.dao.mapper.TagMapper.queryTagByLawId")),
            @Result(property = "caseCount", column = "case_count"),
            @Result(property = "remarkCount", column = "remark_count"),
    })
    @SelectProvider(type = LawProvider.class, method = "searchLawsOnContent")
    public List<LawBriefDto> searchLawsOnContent(@Param("conditions") String[] conditions);

    @ResultMap("LawMap")
    @Select("SELECT vl.* from v_law AS vl,(SELECT law_id FROM t_tag_law WHERE tag_id = #{tagId}) AS rm WHERE rm.law_id = vl.id ORDER BY release_time desc, legal_name asc, `no` asc")
    public List<LawBriefDto> getLawsByTag(Long tagId);

    @ResultMap("LawMap")
    @Select("SELECT vl.* from v_law AS vl,(SELECT law_id FROM t_tag_law WHERE tag_id = #{thisTag} AND law_id <> #{thisLaw} ) AS rm WHERE rm.law_id = vl.id ORDER BY release_time desc, legal_name asc, `no` asc")
    public List<LawBriefDto> getThisTagOthers(@Param("thisTag") Long thisTag, @Param("thisLaw") Long thisLaw);

    @ResultMap("LawMap")
    @Select("SELECT tl.*,rm.full_name FROM t_law tl,(SELECT tln.full_name,tln.id FROM t_legal_name tln WHERE tln.id = #{fullNameId}) rm WHERE rm.id = tl.legal_name ORDER BY release_time desc, legal_name asc, `no` asc")
    public List<LawBriefDto> getLawsByFullName(Long fullNameId);

    @Select("SELECT count(*) FROM t_law tl WHERE tl.no = #{no} AND tl.legal_name = #{legalId}")
    int noContains(@Param("no") Integer no, @Param("legalId")Long legalId);

    @Select("SELECT * FROM v_law tl RIGHT JOIN law_relation rl " +
            "ON rl.new_law = tl.id WHERE rl.old_law = #{thisLawId}")
    List<LawBriefDto> getNewLaw(@Param("thisLawId") Long thisLawId);

    @Select("SELECT * FROM v_law tl RIGHT JOIN law_relation rl ON rl.old_law = tl.id WHERE rl.new_law = #{thisLawId}")
    List<LawBriefDto> getOldLaws(@Param("thisLawId") Long thisLawId);

    @Insert("INSERT INTO t_law(no, content, legal_name,release_time) VALUES(#{newLaw.no},#{newLaw.content},#{newLaw.legalId},#{newLaw.releaseTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "newLaw.id")
    int addLaw(@Param("newLaw") LawBean newLaw);

    @UpdateProvider(type = LawProvider.class, method = "updateLawsStatus")
    int updateLawsStatus(@Param("oldLaws")Long[] oldLaws, @Param("lawStatus")LawStatus modified);

    @InsertProvider(type = LawProvider.class, method = "addUpdateRecode")
    int addUpdateRecode(@Param("newLawId")Long id, @Param("oldLaws")Long[] oldLaws);
}
