package com.sinmem.peony.dao.mapper;

import com.sinmem.peony.common.enums.CommentType;
import com.sinmem.peony.common.enums.Status;
import com.sinmem.peony.dao.bean.CommentBean;
import com.sinmem.peony.dao.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {
    @Insert("INSERT INTO t_comment(belong, type, content, author, status) " +
            "VALUES(#{c.belong}, #{c.type}, #{c.content}, #{c.author.id}, #{c.status})")
    Integer addComment(@Param("c") CommentBean comment);

    @Delete("DELETE FROM t_comment WHERE id = #{id}")
    Integer delComment(@Param("id") Long id);

    @Results(id = "comments", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "author", property = "author.id"),
            @Result(column = "phone_number", property = "author.phoneNumber"),
            @Result(column = "username", property = "author.username"),
            @Result(column = "id", property = "children", many = @Many(select = "com.sinmem.peony.dao.mapper.CommentMapper.getChildComment")),

    })
    @Select("SELECT  comment.*, author.id `author.id`,author.phone_number,author.username " +
            "FROM t_comment comment LEFT JOIN `user` author ON comment.author = author.id " +
            "WHERE comment.belong = #{belong} AND comment.status = #{status}")
    List<CommentBean> getComments(@Param("belong") Long belong, @Param("status") Status status);


    @Results(id = "ChildComment", value = {
            @Result(column = "author", property = "author.id"),
            @Result(column = "phone_number", property = "author.phoneNumber"),
            @Result(column = "username", property = "author.username"),

    })
    @Select("SELECT  comment.*, author.id `author.id`,author.phone_number,author.username " +
            "FROM t_comment comment LEFT JOIN `user` author ON comment.author = author.id " +
            "WHERE comment.belong = #{belong} AND comment.status = 'VALID' AND type = 'COMMENT'")
    List<CommentBean> getChildComment(@Param("belong") Long belong);

    @Results(id = "commentForAdmin", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "author", property = "author.id"),
            @Result(column = "phone_number", property = "author.phoneNumber"),
            @Result(column = "username", property = "author.username"),

    })
    @Select("SELECT comment.*, author.id `author.id`,author.phone_number,author.username " +
            "FROM t_comment comment LEFT JOIN `user` author ON comment.author = author.id " +
            "WHERE comment.status = #{comment.status}")
    List<CommentBean> getCommentsByAdmin(@Param("comment")CommentBean comment);

    @Update("UPDATE t_comment SET status = #{status} WHERE id = #{id}")
    Integer updCommentStatus(@Param("id") Long id, @Param("status") Status status);
}
