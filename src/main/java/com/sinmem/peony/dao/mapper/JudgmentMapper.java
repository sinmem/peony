package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.Judgment;
import com.sinmem.peony.dao.provider.JudgmentProvider;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface JudgmentMapper {

    @Select("SELECT * FROM judgments tj")
    ArrayList<Judgment> getJudgments(Integer pageNum, Integer pageSize);

//    @Select("<script>" +
//            "SELECT * FROM judgments WHERE title LIKE " +
//            "<trim prefix='\"%' suffix='%\"' >" +
//            "#{condition}" +
//            "</trim>" +
//            " OR description LIKE " +
//            "<trim prefix='\"%' suffix='%\"' >" +
//            "#{condition}" +
//            "</trim>" +
//            "</script>")
    @SelectProvider(type = JudgmentProvider.class, method = "searchJudgments")
    ArrayList<Judgment> searchJudgments(@Param("condition") String condition);

    @Select("SELECT * FROM judgments tj WHERE id = #{id}")
    Judgment getJudgmentById(@Param("id") Integer searchId);

    @Insert("INSERT INTO judgments(title, description, author, text) " +
            "VALUES(#{judgment.title}, #{judgment.description}, #{judgment.author.id}, #[judgment.text})")
    int addJudgment(@Param("judgment") Judgment judgment);

    @Delete("DELETE FROM judgments WHERE id = #{id}")
    int delJudgment(@Param("id") Integer id);

//    @DeleteProvider(type = Judgment.class, method = "")
//    int delJudgments(Integer...ids);

    @Update("<script>" +
                "UPDATE judgments " +
            "<trim prefix='SET' suffixOverrides=','>" +
            "  <if test='judgment.title!=null'>title=#{judgment.title},</if>" +
            "  <if test='judgment.description!=null'>description=#{judgment.description},</if>" +
            "  <if test='judgment.author!=null'>author=#{judgment.author.id},</if>" +
            "  <if test='judgment.text!=null'>text=#{judgment.text},</if>" +
            "</trim>" +
            " WHERE id = #{judgment.id}" +
            "</script>")
    int updJudgment(@Param("judgment") Judgment judgment);
}
