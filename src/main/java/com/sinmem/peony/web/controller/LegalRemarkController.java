package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.enums.RemarkSubmissionType;
import com.sinmem.peony.dao.bean.LegalRemark;
import com.sinmem.peony.dao.bean.TempLegalRemark;
import com.sinmem.peony.service.LegalRemarkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.controller
 * @Author: sinmem
 * @CreateTime: 2019-11-04 13:50
 * @Description: 备注控制层
 * @version: V1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/web/v1/remark")
public class LegalRemarkController {
    @Resource
    private LegalRemarkService legalRemarkService;
    /*------- 公有权限操作 -------*/
    @GetMapping("/getRemark")
    public String getRemarkByLawId(Long lawId){
        return Result.success(legalRemarkService.getRemarkByLawId(lawId)).toString();
    }
    @GetMapping("/getTempRemarks")
    public String getTempRemarks(Integer pageNum, Integer pageSize){
        return Result.success(legalRemarkService.getTempRemarks(pageNum, pageSize)).toString();
    }
    // 以下四个提交到临时表
    @PostMapping("/addRemarkToTemp")
    public String addRemarkToTemp(TempLegalRemark tempLegalRemark){
        return Result.success(legalRemarkService.addRemarkToTemp(tempLegalRemark)).toString();
    }

    /*------- 管理员操作 -------*/

    /**
     * 通过备注操作申请
     * @param tempLegalRemark 临时备注
     * @return
     */
    @PostMapping("/adoptRemark")
    public String adoptRemark(TempLegalRemark tempLegalRemark){
        if(tempLegalRemark.getSubmissionType() == RemarkSubmissionType.ADD){
            return Result.success(legalRemarkService.adoptRemark(tempLegalRemark.getId())).toString();
        }else if(tempLegalRemark.getSubmissionType() == RemarkSubmissionType.UPDATE){
            return Result.success(legalRemarkService.adoptRemarkUpdate(tempLegalRemark.getId())).toString();
        }else {
            return Result.error(Msg.E30001).toString();
        }
    }

    /**
     * 驳回备注操作申请
     * @param tempLegalRemarkId 临时备注id
     * @return
     */
    @PostMapping("/rejectRemark")
    public String rejectRemark(Long tempLegalRemarkId){
        return Result.success(legalRemarkService.rejectRemark(tempLegalRemarkId)).toString();
    }


    @PostMapping("/addRemark")
    public String addRemark(LegalRemark legalRemark){
        return Result.success(legalRemarkService.addRemark(legalRemark)).toString();
    }
    @PostMapping("/updRemark")
    public String updateRemark(LegalRemark legalRemark){
        return Result.success(legalRemarkService.updateRemark(legalRemark)).toString();
    }
    @PostMapping("/delRemark")
    public String deleteRemarkByLawId(Long[] lawIds){
        return Result.success(legalRemarkService.deleteRemarkByLawId(lawIds)).toString();
    }
}
