package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.CommentType;
import com.sinmem.peony.common.enums.Status;
import com.sinmem.peony.dao.bean.CommentBean;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.controller
 * @Author sinmem
 * @CreateTime 2021-03-22 19:15
 * @Description 评论控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/web/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public String addComment(CommentBean comment, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof User) {
            comment.setAuthor((User) userDetails);
            return commentService.addComment(comment).toString();
        }
        return Result.error(-100, "登录失败").toString();
    }

    @PostMapping("/delComment")
    public String delComment(Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user;
        if (userDetails instanceof User) {
            user = (User) userDetails;
            return commentService.delComment(id, user).toString();
        }
        return Result.error(-100, "登录失败").toString();
    }

    @GetMapping("/getComments")
    public String getComments(Long belong, Status status, Boolean isAll, @AuthenticationPrincipal UserDetails userDetails) {
        User user;
        if (userDetails instanceof User) {
            user = (User) userDetails;
            return commentService.getComments(belong, status, isAll, user).toString();
        }
        return Result.error(-100, "登录失败").toString();
    }

    @GetMapping("/getCommentsByAdmin")
    public String getCommentsByAdmin(CommentBean comment, @AuthenticationPrincipal UserDetails userDetails) {
        User user;
        if (userDetails instanceof User) {
            comment.setAuthor((User) userDetails);
            return commentService.getCommentsByAdmin(comment).toString();
        }
        return Result.error(-100, "登录失败").toString();
    }

    @PostMapping("/enableComment")
    public String enableComment(Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user;
        if (userDetails instanceof User) {
            user = (User) userDetails;
            return commentService.enableComment(id, user).toString();
        }
        return Result.error(-100, "登录失败").toString();
    }

    @PostMapping("/disableComment")
    public String disableComment(Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user;
        if (userDetails instanceof User) {
            user = (User) userDetails;
            return commentService.disableComment(id, user).toString();
        }
        return Result.error(-100, "登录失败").toString();
    }
}
