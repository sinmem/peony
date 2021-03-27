package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.CommentType;
import com.sinmem.peony.common.enums.Status;
import com.sinmem.peony.dao.bean.CommentBean;
import com.sinmem.peony.dao.bean.User;

public interface
CommentService {
    Result addComment(CommentBean comment);

    Result delComment(Long id, User user);

    Result getComments(Long belong, Status status, Boolean isAll, User user);

    Result getCommentsByAdmin(CommentBean comment);

    Result enableComment(Long id, User user);

    Result disableComment(Long id, User user);
}
