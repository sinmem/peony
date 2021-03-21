package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.dao.bean.LegalName;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.dao.dto.LegalNameDto;
import com.sinmem.peony.dao.provider.LegalNameProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface LegalNameMapper {

    @SelectProvider(type = LegalNameProvider.class, method = "searchLawsOnFullName")
    public List<LegalName> searchFullNames(@Param("conditions") String[] condition);

    @Select("SELECT abbreviation, full_name FROM t_legal_name WHERE id = #{id}")
    public LegalName queryLegalNameById(Long id);

    @Select("SELECT count(*) FROM t_legal_name tln WHERE tln.id = #{id}")
    int isContains(@Param("id")Long id);

    @Select("SELECT id,full_name FROM t_legal_name")
    List<LegalNameDto> getAllSimpleLegal();

    @Select("SELECT * FROM t_legal_name")
    List<LegalName> getAll();
}
