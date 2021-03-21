package com.sinmem.peony.dao.bean;

import com.sinmem.peony.common.enums.CommentType;
import com.sinmem.peony.common.enums.Status;

import java.util.Date;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.bean
 * @Author sinmem
 * @CreateTime 2021-03-19 00:28
 * @Description 各种各样的评论
 */
public class comment extends BaseBean {
    private String content;
    private Date createTime;
    private User author;
    private CommentType type;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }
}
