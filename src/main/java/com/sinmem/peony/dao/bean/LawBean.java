package com.sinmem.peony.dao.bean;

import com.sinmem.peony.common.enums.LawStatus;

import java.time.Year;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.domain.bean
 * @Author: sinmem
 * @CreateTime: 2019-10-15 09:40
 * @Description: 法律条文内容类
 * @version: V1.0
 */
public class LawBean extends BaseBean {
    /**
     * 法律条文编号(该条记录是xxx法律的第几条)
     */
    private Integer no;
    /**
     * 法律条文内容
     */
    private String content;
    /**
     * 法律全名称(该条属于的XXX法律)
     */
    private String fullName;
    /**
     * 法律类型(该条所属的法律类型, 如劳动法)
     */
    private String category;
    /**
     * 法律条文状态(表示该条法律条文现在是否正在生效)
     */
    private LawStatus status;
    /**
     * 法律发布时间(仅精确到年)
     */
    private Year releaseTime;
    /**
     * 法条案例计数
     */
    private Byte caseCount;
    /**
     * 法条备注计数
     */
    private Byte remarkCount;

    private Long legalId;

    public Long getLegalId() {
        return legalId;
    }

    public void setLegalId(Long legalId) {
        this.legalId = legalId;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LawStatus getStatus() {
        return status;
    }

    public void setStatus(LawStatus status) {
        this.status = status;
    }

    public Year getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Year releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Byte getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(Byte caseCount) {
        this.caseCount = caseCount;
    }

    public Byte getRemarkCount() {
        return remarkCount;
    }

    public void setRemarkCount(Byte remarkCount) {
        this.remarkCount = remarkCount;
    }
}
