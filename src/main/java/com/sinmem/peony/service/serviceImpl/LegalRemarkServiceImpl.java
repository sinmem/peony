package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.LegalRemark;
import com.sinmem.peony.dao.bean.TempLegalRemark;
import com.sinmem.peony.dao.mapper.LegalRemarkMapper;
import com.sinmem.peony.service.LegalRemarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service.serviceImpl
 * @Author: sinmem
 * @CreateTime: 2019-11-04 14:11
 * @Description: 法条备注业务实现类
 * @version: V1.0
 */
@Service
public class LegalRemarkServiceImpl implements LegalRemarkService {
    @Resource
    private LegalRemarkMapper legalRemarkMapper;
    // 公有权限操作
    @Override@Transactional
    public LegalRemark getRemarkByLawId(Long lawId) {
        return legalRemarkMapper.getRemarkByLawId(lawId);
    }

    @Override
    public ResultPage<TempLegalRemark> getTempRemarks(Integer pageNum, Integer pageSize) {
        Page<TempLegalRemark> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(()->legalRemarkMapper.getTempRemarks());
        return new ResultPage<>(page);
    }

    @Override@Transactional
    public int addRemarkToTemp(TempLegalRemark tempLegalRemark) {
        return legalRemarkMapper.addRemarkToTemp(tempLegalRemark);
    }

//    @Override@Transactional
//    public String updateRemarkToTemp(TempLegalRemark tempLegalRemark) {
//        return null;
//    }

    // 管理员权限操作

    @Override@Transactional
    
    public LegalRemark addRemark(LegalRemark legalRemark) {
        legalRemarkMapper.addRemark(legalRemark);
        return legalRemark;
    }

    @Override@Transactional
    public int updateRemark(LegalRemark legalRemark) {
        return legalRemarkMapper.updRemark(legalRemark);
    }

    @Override@Transactional
    public int adoptRemark(Long tempRemarkId) {
        legalRemarkMapper.addRemarkFromTemp(tempRemarkId);
        return rejectRemark(tempRemarkId);
    }

    @Override@Transactional
    public int adoptRemarkUpdate(Long tempRemarkId) {
        legalRemarkMapper.updRemarkFromTemp(tempRemarkId);
        return rejectRemark(tempRemarkId);
    }

    @Override@Transactional
    public int rejectRemark(Long tempRemarkId) {
        return legalRemarkMapper.delTempRemark(tempRemarkId);
    }

    @Override@Transactional
    public int deleteRemarkByLawId(Long[] lawIds) {
        return legalRemarkMapper.delRemark(lawIds);
    }
}
