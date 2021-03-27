package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.CommentType;
import com.sinmem.peony.common.enums.Status;
import com.sinmem.peony.dao.bean.CommentBean;
import com.sinmem.peony.dao.bean.Role;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.dto.LegalCaseDto;
import com.sinmem.peony.dao.mapper.CommentMapper;
import com.sinmem.peony.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service.serviceImpl
 * @Author sinmem
 * @CreateTime 2021-03-22 19:19
 * @Description 评论业务处理器
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public Result addComment(CommentBean comment) {
        comment.setStatus(Status.TO_BE_REVIEWED);
        commentMapper.addComment(comment);
        return Result.success("添加成功");
        // todo 两个枚举为空,很有问题
    }

    @Override
    public Result delComment(Long id, User user) {
        return Result.success("删除成功");
    }

    @Override
    public Result getComments(Long belong, Status status, Boolean isAll, User user) {
        List<CommentBean> comments = commentMapper.getComments(belong, status);
        return Result.success(comments);
    }

    @Override
    public Result getCommentsByAdmin(CommentBean comment) {
        Page<CommentBean> page = PageHelper.startPage(0, 0).doSelectPage(() -> commentMapper.getCommentsByAdmin(comment));
        System.out.println(Result.success(page).toString());
        return Result.success(page);
    }

    @Override
    public Result enableComment(Long id, User user) {
        if(!user.hasRole(Role.ADMIN))return Result.error(-1, "权限不足");
        commentMapper.updCommentStatus(id, Status.VALID);
        return Result.success("修改成功");
    }

    @Override
    public Result disableComment(Long id, User user) {
        if(!user.hasRole(Role.ADMIN))return Result.error(-1, "权限不足");
        commentMapper.updCommentStatus(id, Status.INVALID);
        return Result.success("修改成功");
    }
}
