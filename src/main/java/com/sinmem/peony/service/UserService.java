package com.sinmem.peony.service;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.dao.bean.User;

public interface UserService {
    Result register(User user, String validateCode, String inviteCode);
    Result getVerifyCode(String phoneNumber, Integer type);

    Result reset(User user, String validateCode);
    Result forgot(User user, String validateCode);

    Result updateUsername(User user, String username);
}
