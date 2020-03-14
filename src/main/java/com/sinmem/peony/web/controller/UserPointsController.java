package com.sinmem.peony.web.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.enums.PointType;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.service.UserPointService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.controller
 * @Author sinmem
 * @CreateTime 2020-01-04 13:42
 * @Description 用户积分控制器
 */
@RestController
@RequestMapping("/user/points")
public class UserPointsController {
    @Resource
    private UserPointService userPointService;
    /**
     * 获得用户积分
     * @return
     */
    @GetMapping("/myPoints")
    public String getUserPoints(User user){
        return userPointService.getUserPoints(user).toString();
    }

    /**
     * 获取用户积分收支记录详情
     * @return
     */
    @GetMapping("/myPointsDetail")
    public String getPointsDetail(User user, Integer pageNum){
        pageNum = pageNum<1?1:pageNum;
        return userPointService.getPointsDetail(user, pageNum).toString();
    }

    @GetMapping("/myPointsPayDetail")
    public String getPointsPayDetail(User user, Integer pageNum){
        pageNum = pageNum<1?1:pageNum;
        return userPointService.getPointsPayDetail(user, pageNum).toString();
    }

    @GetMapping("/myPointsGetDetail")
    public String getPointsGetDetail(User user, Integer pageNum){
        pageNum = pageNum<1?1:pageNum;
        return userPointService.getPointsGetDetail(user, pageNum).toString();
    }

    /**
     * 增加用户积分
     * @param user 用户
     * @param number 积分点数
     * @return
     */
    @PostMapping("/addPoints")
    public String addUserPoints(User user, Integer number, PointType pointType){
        if(pointType == PointType.BONUS || pointType == PointType.INVITE || pointType == PointType.OTHERS){
            return userPointService.addUserPoints(user, number, pointType).toString();
        }
        return Result.error(Msg.E40011).setMessage("积分类型错误").toString();
    }

    /**
     * 扣除用户积分
     * @param user 用户
     * @param number 积分
     * @return
     */
    @PostMapping("/subPoints")
    public String subUserPoint(User user, Integer number, PointType pointType){
        if(pointType == PointType.OTHERS){
            return userPointService.subUserPoint(user, number, pointType).toString();
        }
        return Result.error(Msg.E40011).setMessage("积分类型错误").toString();
    }

    /**
     * 获取用户邀请码
     * @param user 用户
     * @return
     */
    @GetMapping("/getInviteCode")
    public String getInviteCode(User user){
        return userPointService.getInviteCode(user).toString();
    }

    /**
     * 执行邀请用户业务,B输入A的邀请码后,录入这条邀请记录并且为A增加积分
     * @param user 用户
     * @return
     */
    @PostMapping("/doInvite")
    public String doInvite(User user, String inviteCode){
        return userPointService.doInvite(user, inviteCode).toString();
    }

    /**
     * 检查用户是否被邀请, 如果被邀请则返回邀请人,否则返回空
     * @param user 用户
     * @return
     */
    @GetMapping("/isInvited")
    public String isInvited(User user){
        return Result.success(userPointService.isInvited(user)).toString();
    }

    /**
     * 获取已邀请人的列表
     * @param user
     * @return
     */
    @GetMapping("/getInvitedList")
    public String getInvitedList(User user, Integer pageNum){
        pageNum = pageNum<1?1:pageNum;
        return userPointService.getInvitedList(user, pageNum).toString();
    }

    @PostMapping("/renewalsSVip")
    public String getSVip(User user, Integer time){
        return userPointService.getSVip(user, time).toString();
    }

    @PostMapping("/renewalsVip")
    public String getVip(User user, Integer time){
        return userPointService.getVip(user, time).toString();
    }
}
