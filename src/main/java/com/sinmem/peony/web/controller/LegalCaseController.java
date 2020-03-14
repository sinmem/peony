package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
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
    @GetMapping("/getCase")
    public String getCaseByLawId(Long lawId){
        return Result.success(leaglCaseService.getCaseByLawId(lawId)).toString();
    }
    @GetMapping("/getDoCases")
    public String getDoCases(Integer pageNum, Integer pageSize){
        return leaglCaseService.getDoCases(pageNum, pageSize).toString();
    }
    @PostMapping("/addCase")
    public String addCase(LegalCase legalCase){
        return Result.success(leaglCaseService.addCase(legalCase)).toString();
    }

    @PostMapping("/validCase")
    public String validCase(Long caseId,Long lawId){
        return leaglCaseService.validCase(caseId, lawId).toString();
    }


    @PostMapping("/agreeCase")
    public String agreeCase(User user, Long caseId){
        return leaglCaseService.agreeCase(user, caseId).toString();
    }


    @PostMapping("/disagreeCase")
    public String disagreeCase(User user, Long caseId){
        return leaglCaseService.disagreeCase(user, caseId).toString();
    }
//    @PostMapping("/updCase")
    @Deprecated
    public String updateCase(LegalCase legalCase){
        return Result.success(leaglCaseService.updateCase(legalCase)).toString();
    }
    @PostMapping("/delCase")
    public String deleteCaseByLawId(Long[] lawIds){
        return Result.success(leaglCaseService.deleteCaseByLawId(lawIds)).toString();
    }

}
