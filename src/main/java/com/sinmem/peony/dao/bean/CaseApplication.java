package com.sinmem.peony.dao.bean;

import com.sinmem.peony.common.enums.CaseStatus;

import java.sql.Timestamp;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.bean
 * @Author sinmem
 * @CreateTime 2020-03-15 22:51
 * @Description 案用户例申请记录
 */
public class CaseApplication extends BaseBean {
    private User user;
    private LegalCase legalCase;
    private CaseStatus status;
    private String msg;
    private Timestamp createTime;
    private Timestamp updateTime;


}
