package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.bean.LegalRemark;
import com.sinmem.peony.dao.bean.TempLegalRemark;

import java.util.List;

import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service
 * @Author: sinmem
 * @CreateTime: 2019-11-04 13:54
 * @Description: 备注服务接口层
 * @version: V1.0
 */
public interface LegalRemarkService {
    /*------- 公有权限操作 -------*/
    /**
     * 通过法条id获取备注详情
     * @param lawId 法条id
     * @return 备注实体类
     */
    public LegalRemark getRemarkByLawId(Long lawId);

    /**
     * 获取所有备注申请
     * @return
     */
    public ResultPage<TempLegalRemark> getTempRemarks(Integer pageNum, Integer pageSize);
    /**
     * 提交添加备注到临时表
     * @param tempLegalRemark 临时备注信息
     * @return
     */
    public int addRemarkToTemp(TempLegalRemark tempLegalRemark);

//    /**
//     * 添加更新备注到临时表
//     * @param tempLegalRemark 临时备注信息
//     * @return
//     */
//    public String updateRemarkToTemp(TempLegalRemark tempLegalRemark);

    /*------- 管理员操作 -------*/


    /**
     * 添加备注
     * @param legalRemark 备注
     * @return 添加的备注,注意获取备注id
     */
    public LegalRemark addRemark(LegalRemark legalRemark);

    /**
     * 更新备注
     * @param legalRemark 备注
     * @return 更新的数量
     */
    public int updateRemark(LegalRemark legalRemark);

    /**
     * 通过法条备注添加
     * @param tempRemarkId
     * @return
     */
    public int adoptRemark(Long tempRemarkId);

    /**
     * 通过法条备注修改
     * @param tempRemarkId
     * @return
     */
    public int adoptRemarkUpdate(Long tempRemarkId);

    /**
     * 驳回法条备注添加/更新
     * @param tempRemarkId
     * @return
     */
    public int rejectRemark(Long tempRemarkId);

    /**
     * 根据法条id删除备注
     * @param lawIds 法条id
     * @return 删除的数量
     */
    public int deleteRemarkByLawId(Long[] lawIds);
}
