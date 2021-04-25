package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.TreeNode;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CatalogMapper {
    @Select("SELECT * FROM t_catalog WHERE parent = #{parent}")
    List<TreeNode> getChildren(@Param("parent") Long parent);

    @Delete("DELETE FROM t_catalog WHERE id = #{id}")
    Integer delNode(@Param("id") Long id);

    @Insert("INSERT INTO t_catalog(content, title, style, extra, parent) " +
            "VALUES(#{node.content}, #{node.title}, #{node.style}, #{node.extra}, #{node.parent})")
    Integer addNode(@Param("node") TreeNode node);

    @Update("UPDATE t_catalog SET content = #{node.content}, title=#{node.title}, " +
            "extra=#{node.extra} WHERE id = #{node.id}")
    Integer updNode(@Param("node") TreeNode node);

    @Select("SELECT id FROM t_catalog WHERE extra LIKE CONCAT('%\"lawId\":',#{lawId},'%')")
    Long getNodeId(@Param("lawId") Long lawId);
}
