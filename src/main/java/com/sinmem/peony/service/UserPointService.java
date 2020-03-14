package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.PointType;
import com.sinmem.peony.dao.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface UserPointService {
    /**
     * 获得用户积分
     * @return
     */
    Result getUserPoints(User user);

    /**
     * 获取用户积分收支记录详情
     * @return
     */
    Result getPointsDetail(User user, Integer pageNum);

    /**
     * 增加用户积分
     * @param user 用户
     * @param number 积分点数
     * @param pointType 积分类型
     * @return
     */
    Result addUserPoints(User user, Integer number, PointType pointType);

    /**
     * 扣除用户积分
     * @param user 用户
     * @param number 积分
     * @param pointType 积分类型
     * @return
     */
    Result subUserPoint(User user, Integer number, PointType pointType);

    /**
     * 获取用户邀请码
     * @param user 用户
     * @return
     */
    Result getInviteCode(User user);

    /**
     * 执行邀请用户业务,B输入A的邀请码后,录入这条邀请记录并且为A增加积分
     * @param user 用户
     * @param inviteCode 邀请码
     * @return
     */
    Result doInvite(User user, String inviteCode);

    /**
     * 检查用户是否被邀请, 如果被邀请则返回邀请人,否则返回空
     * @param user 用户
     * @return user 邀请人
     */
    User isInvited(User user);

    /**
     * 获取已邀请人的列表
     * @param user 用户
     * @param pageNum 页号
     * @return
     */
    Result getInvitedList(User user, Integer pageNum);

    Result getSVip(User user, Integer time);
    Result getVip(User user, Integer time);

    Result getPointsPayDetail(User user, Integer pageNum);

    Result getPointsGetDetail(User user, Integer pageNum);
}
