package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.dao.bean.TagBean;
import com.sinmem.peony.dao.provider.TagProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TagMapper {
    @Select("SELECT tt.id, tt.`name` " +
            "FROM(t_tag_law ttl LEFT JOIN t_law tl on tl.id = ttl.law_id) LEFT JOIN t_tag tt on ttl.tag_id = tt.id " +
            "WHERE tl.id = #{lawId}")
    public TagBean queryTagByLawId(Long lawId);

//    不再使用全文索引, 由于这里可能存在对同一列的多次模糊条件, 在这里不好拼接, 于是换成SelectProvider自定义SQL语句
//    LawMapper,LegalNameMapper中使用同理
//    @Select("SELECT tl.id, tl.content FROM (SELECT tt.id FROM t_tag tt WHERE MATCH(name) AGAINST(#{condition} IN BOOLEAN MODE)" +
//            ") as rm, t_law as tl, t_tag_law as ttl WHERE rm.id = ttl.tag_id AND ttl.law_id = tl.id")

    @SelectProvider(type = TagProvider.class, method = "searchLawsOnTag")
    public List<TagBean> searchTags(@Param("conditions")String[] condition);


    @Select("SELECT tt.id, tt.name, tt.count FROM t_tag tt ORDER BY CONVERT(tt.name USING GBK)")
    public List<TagBean> getAllTags();

    /**
     * 通过标签名字获取该标签的count,id字段的值,count = 0表示曾经没有这个标签要新增
     * @param name
     * @return
     */
    @Select("SELECT id,count FROM t_tag WHERE name = #{name}")
    public TagBean getTagByName(String name);

    @Select("SELECT * FROM t_tag WHERE id = #{tagId}")
    public TagBean getTagById(Long tagId);

    @Select("SELECT * FROM t_tag WHERE law_id = #{tagId}")
    public List<TagBean> getTagByLawId(Long lawId);

    @Select("SELECT tt.count FROM t_tag tt WHERE id = #{TagId}")
    public Integer getCountById(Long TagId);
    /**
     * 新增标签
     * @param tag
     * @return
     */
    @Insert("INSERT INTO t_tag(name, count) VALUES(#{tag.name},#{tag.count})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "tag.id")
    public Long addTag(@Param("tag") TagBean tag);

    @Update("UPDATE t_tag SET count = #{count} WHERE id = #{id}")
    public Integer updateTagCountById(@Param("count")Integer count, @Param("id")Long id);

    // m4莫名其妙报这个错,先换方法
//Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException:
//### Error updating database.  Cause: java.lang.NullPointerException
//### The error may exist in com/sinmem/peony/dao/mapper/TagMapper.java (best guess)
//### The error may involve com.sinmem.peony.dao.mapper.TagMapper.addTagLaw
//### The error occurred while executing an update
//### Cause: java.lang.NullPointerException] with root cause
//    @Insert("<script>" +
//            "INSERT INTO t_tag_law(law_id) VALUES" +
//            "<foreach item='lawId' collections='lawIds' separator=','>" +
//            "(#{lawId})" +
//            "</foreach></script>")

    @InsertProvider(type = TagProvider.class, method = "addTagLaw")
    public Integer addTagLaw(@Param("tagId") Long tagId, @Param("lawIds")Long[] lawIds);

    @DeleteProvider(type = TagProvider.class, method = "removeLawTag")
    public Integer removeLawTag(@Param("tagId") Long tagId, @Param("lawIds")Long[] lawIds);

    @Delete(("DELETE FROM t_tag WHERE id = #{tagId}"))
    public Integer deleteTagById(@Param("tagId")Long tagId);
}
