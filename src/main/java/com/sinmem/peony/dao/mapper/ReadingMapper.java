package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.ReadingBean;
import org.apache.ibatis.annotations.*;

public interface ReadingMapper {
    @Results(value={
            @Result(column = "author_name", property = "author.username"),
            @Result(column = "author", property = "author.id")
    })
    @Select("SELECT * FROM t_reading WHERE law_id = #{lawId}")
    ReadingBean getReading(@Param("lawId") Long lawId);

    @Insert("INSERT INTO t_reading(law_id,author,author_name,content) " +
            "VALUES(#{reading.lawId}, #{reading.author.id}, #{reading.author.username}, #{reading.content})")
    Integer addReading(@Param("reading") ReadingBean reading);

    @Update("UPDATE t_reading SET author = #{reading.author.id}," +
            "author_name=#{reading.author.username}," +
            "content = #{reading.content} WHERE law_id = #{reading.lawId}")
    Integer updReading(@Param("reading") ReadingBean reading);

    @Delete("DELETE FROM t_reading WHERE law_id = #{lawId}")
    Integer delReading(@Param("lawId") Long lawId);

}
