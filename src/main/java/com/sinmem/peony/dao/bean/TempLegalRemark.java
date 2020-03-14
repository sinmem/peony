package com.sinmem.peony.dao.bean;

import com.sinmem.peony.common.enums.RemarkSubmissionType;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.dao.bean
 * @Author: sinmem
 * @CreateTime: 2019-11-04 14:53
 * @Description: 法条备注临时表
 * @version: V1.0
 */
public class TempLegalRemark extends LegalRemark {
    /**
     * 法条备注修改提交者
     */
    private Long submitter;
    /**
     * 法条备注修改类型(添加/更新)
     */
    private RemarkSubmissionType submissionType;

    public Long getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Long submitter) {
        this.submitter = submitter;
    }

    public RemarkSubmissionType getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(RemarkSubmissionType submissionType) {
        this.submissionType = submissionType;
    }
}
