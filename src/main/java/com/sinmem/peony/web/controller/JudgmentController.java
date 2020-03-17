package com.sinmem.peony.web.controller;

import com.sinmem.peony.dao.bean.Judgment;
import com.sinmem.peony.service.JudgmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.controller
 * @Author sinmem
 * @CreateTime 2020-03-15 22:59
 * @Description 判决书控制器
 */
@RestController
@RequestMapping("/judgments")
public class JudgmentController {
    @Resource
    private JudgmentService judgmentService;

    @GetMapping("/getJudgments")
    public String listJudgments(Integer pageNum, Integer pageSize){
        return "";
    }

    @GetMapping("/searchJudgments")
    public String listSearchJudgments(Integer pageNum, Integer pageSize, String condition){
        return judgmentService.searchJudgments(pageNum, pageSize, condition).toString();
    }

    @GetMapping("/getJudgmentById")
    public String getJudgmentById(Integer searchId){
        return "";
    }

    @PostMapping("/addJudgment")
    public String addJudgment(Judgment judgment){
        return "";
    }
    @PostMapping("/delJudgment")
    public String delJudgment(Integer id){
        return "";
    }
    @PostMapping("/delJudgments")
    public String delJudgments(Integer...ids){
        return "";
    }
    @PostMapping("/updJudgment")
    public String updJudgment(Judgment judgment){
        return judgmentService.updJudgment(judgment).toString();
    }
}
