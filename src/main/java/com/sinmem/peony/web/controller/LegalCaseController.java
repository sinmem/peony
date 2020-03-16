package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.CaseStatus;
import com.sinmem.peony.dao.bean.CaseApplication;
import com.sinmem.peony.dao.bean.LegalCase;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.service.LeaglCaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.controller
 * @Author: sinmem
 * @CreateTime: 2019-11-04 13:49
 * @Description: 案例控制层
 * @version: V1.0
 */
@RestController
@RequestMapping("/web/v1/case")
public class LegalCaseController {
    @Resource
    private LeaglCaseService leaglCaseService;

    /**
     * 根据法条id获取案例
     * @param lawId
     * @return
     */
    @GetMapping("/getCase")
    public String getCaseByLawId(Long lawId){
        return Result.success(leaglCaseService.getCaseByLawId(lawId)).toString();
    }

    /**
     * 获取指定页的案例申请列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getDoCases")
    public String getDoCases(Integer pageNum, Integer pageSize){
        return leaglCaseService.getDoCases(pageNum, pageSize).toString();
    }

    /**
     * 添加案例
     * @param legalCase
     * @return
     */
    @PostMapping("/addCase")
    public String addCase(LegalCase legalCase){
        return Result.success(leaglCaseService.addCase(legalCase)).toString();
    }

    /**
     * 通过案例
     * @param caseApplication
     * @param lawId
     * @return
     */
    @PostMapping("/validCase")
    public String validCase(CaseApplication caseApplication, Long lawId){
        return leaglCaseService.validCase(caseApplication, lawId).toString();
    }

    /**
     * 为案例点赞
     * @param user
     * @param caseId
     * @return
     */
    @PostMapping("/agreeCase")
    public String agreeCase(User user, Long caseId){
        return leaglCaseService.agreeCase(user, caseId).toString();
    }

    /**
     * 为案例点踩
     * @param user
     * @param caseId
     * @return
     */
    @PostMapping("/disagreeCase")
    public String disagreeCase(User user, Long caseId){
        return leaglCaseService.disagreeCase(user, caseId).toString();
    }

    /**
     * 更新案例-->映射改为管理员编辑案例
     * @param legalCase
     * @return
     */
//    @PostMapping("/editCase")
    @Deprecated
    public String updateCase(LegalCase legalCase){
        return Result.success(leaglCaseService.updateCase(legalCase)).toString();
    }

    /**
     * 删除案例
     * @param lawIds
     * @return
     */
    @PostMapping("/delCase")
    public String deleteCaseByLawId(Long[] lawIds){
        return Result.success(leaglCaseService.deleteCaseByLawId(lawIds)).toString();
    }

    @GetMapping("/getCaseApplications")
    public String CaseApplicationsGet(User user, Integer pageNum, Integer pageSize){
        return Result.success(leaglCaseService.getCaseApplications(user,pageNum, pageSize)).toString();
    }

    @GetMapping("/delCaseApplication")
    public String CaseApplicationsDel(User user, Integer CaseId){
        return Result.success(leaglCaseService.delCaseApplications(user, CaseId)).toString();
    }

//    @GetMapping("/updCaseApplication") 内部调用没必要暴露
//    public String CaseApplicationsUpd(User user, CaseStatus status){
//        return Result.success(leaglCaseService.updCaseApplications(user, status)).toString();
//    }

    @GetMapping("/addCaseApplication")
    public String CaseApplicationsAdd(User user,  CaseApplication caseApplication){
        return Result.success(leaglCaseService.addCaseApplications(user, caseApplication)).toString();
    }

}
