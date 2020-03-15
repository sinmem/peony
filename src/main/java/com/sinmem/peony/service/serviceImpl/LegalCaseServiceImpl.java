package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.common.enums.CaseStatus;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.exception.DataOperationException;
import com.sinmem.peony.common.exception.ValidationException;
import com.sinmem.peony.dao.bean.CaseApplication;
import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.bean.TempLegalRemark;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.dto.LegalCaseDto;
import com.sinmem.peony.dao.mapper.CaseApplicationMapper;
import com.sinmem.peony.dao.mapper.LegalCasesMapper;
import com.sinmem.peony.service.LeaglCaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service.serviceImpl
 * @Author: sinmem
 * @CreateTime: 2019-11-04 14:10
 * @Description: 法条案例业务接口实现类
 * @version: V1.0
 */
@Service
public class LegalCaseServiceImpl implements LeaglCaseService {
    public static final boolean AGREE = true;
    public static final boolean DISAGREE = true;
    @Resource
    private LegalCasesMapper legalCasesMapper;
    @Resource
    private CaseApplicationMapper caseAppMapper;
    @Override
    public LegalCase getCaseByLawId(Long lawId) {
        return legalCasesMapper.getCaseByLawId(lawId);
    }

    @Override
    public Result getDoCases(Integer pageNum, Integer pageSize) {
        Page<LegalCaseDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(()->legalCasesMapper.getCases());
        return Result.success(new ResultPage<>(page));
    }

    @Override
    public Result getCaseApplications(User user) {
        return Result.success(caseAppMapper.getCaseAppByUsr(user.getId()));
    }

    @Override
    public Result addCaseApplications(User user, CaseApplication caseApplication) {
        return Result.success(caseAppMapper.addCaseAppByUsr(user.getId(), caseApplication));
    }

    public Result updCaseApplications(User user, CaseStatus status) {
        return Result.success(caseAppMapper.updCaseAppByUsr(user.getId(), status));
    }

    @Override
    public Result delCaseApplications(User user, Integer caseId) {
        if(caseAppMapper.hasRecode(caseId, user.getId()) > 0){
            return Result.success(caseAppMapper.delCaseAppByUsr(caseId));
        }
        throw new DataOperationException(Msg.E40011).setMessage("错误的删除选项: 找不到匹配的项");
    }

    @Override
    public LegalCase addCase(LegalCase legalCase) {
        legalCasesMapper.addCase(legalCase);
        return legalCase;
    }

    @Override
    public int updateCase(LegalCase legalCase) {
        return legalCasesMapper.updCase(legalCase);
    }

    @Override
    public int deleteCaseByLawId(Long[] lawIds) {
        return legalCasesMapper.delCase(lawIds);
    }

    @Override
    @Transactional
    public Result disagreeCase(User user, Long caseId) {
        hasDo(user, caseId);
        legalCasesMapper.addDisagree(caseId);
        legalCasesMapper.addusrCR(user,caseId,DISAGREE);
        return Result.success();
    }

    @Override
    @Transactional
    public Result validCase(Long caseId, Long lawId) {
        if(legalCasesMapper.hasCase(lawId)>0){
            throw new ValidationException(Msg.E40011).setMessage("所操作法条已有通过的案例!");
        }
        if(legalCasesMapper.validCase(caseId) != 1){
            throw new ValidationException(Msg.E40011).setMessage("所操作案例不存在!");
        }
        ArrayList<Long> ids = legalCasesMapper.getIdsByLawId(lawId);
        if(!ids.isEmpty()){
            legalCasesMapper.delCase(ids.toArray(new Long[0]));
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result agreeCase(User user, Long caseId) {
        hasDo(user, caseId);
        legalCasesMapper.addAgreeCase(caseId);
        legalCasesMapper.addusrCR(user,caseId,AGREE);
        return Result.success();
    }

    /**
     * 校验是否已经进行或踩或者赞
     */
    private void hasDo(User user, Long caseId){
        if(legalCasesMapper.hasDo(user, caseId)!=1){
            throw new ValidationException(Msg.E40011).setMessage("已经点过踩或者赞!");
        }
    }
}
