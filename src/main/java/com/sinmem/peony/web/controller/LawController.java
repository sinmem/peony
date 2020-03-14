package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.utils.GsonUtils;
import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.bean.TagBean;
import com.sinmem.peony.service.LawService;
import com.sinmem.peony.service.LegalNameService;
import com.sinmem.peony.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.controller
 * @Author: sinmem
 * @CreateTime: 2019-10-16 14:20
 * @Description: 法律内容控制器
 * @version: V1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/web/v1/law")
public class LawController {
    @Resource
    private LawService lawService;

    @Resource
    private TagService tagService;

    @Resource
    private LegalNameService legalNameService;
    /**
     * 通过关键词查找法条内容相符的法条
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param conditions 条件数组
     * @return
     */
    @GetMapping("/search/onContent")
    public String searchOnContent(Integer pageNum, Integer pageSize, String...conditions){
        return Result.success(lawService.searchLawsOnContent(conditions, pageNum, pageSize)).toString();
    }

    /**
     * 通过标签id查找法条
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param tag 标签Id
     * @return
     */
    @GetMapping("/getLawsByTag")
    public String getLawsByTag(Integer pageNum, Integer pageSize, Long tag){
        return Result.success(lawService.getLawsByTag(tag, pageNum, pageSize)).toString();
    }

    /**
     * 通过标签id集合查找法条
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param tags 标签Id的Json字符串
     * @return
     */
    @GetMapping("/getLawsByTags")
    public String getLawsByTags(Integer pageNum, Integer pageSize, String tags){
        TagBean[] A = GsonUtils.fromJson(tags, TagBean[].class);
        return Result.success(lawService.getLawsByTags(A, pageNum, pageSize)).toString();
    }

    /**
     * 通过法律名称id查找法条
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param fullName 全称
     * @return
     */
    @GetMapping("/getLawsByFullName")
    public String getLawsByFullName(Integer pageNum, Integer pageSize, Long fullName){
        return Result.success(lawService.getLawsByFullName(fullName, pageNum, pageSize)).toString();
    }

    @GetMapping("/searchFullName")
    public String searchFullNames(Integer pageNum, Integer pageSize, String...conditions){
        return Result.success(legalNameService.searchFullNames(pageNum, pageSize, conditions)).toString();
    }
    /**
     * 通过id获取法条
     * @param lawId 法条id
     * @return
     */
    @GetMapping("/getLawById")
    public String getLawById(Long lawId){
        return Result.success(lawService.getLawById(lawId)).toString();
    }


    /**
     * 通过要查询的标签id和本条法条id查询忽略本法条的含有给定标签id的
     * 法条(排除标签查找时候已经显示出的标签id不就行查找)
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param thisTag 要查找的标签id
     * @param thisLaw 本条法条id
     * @param showTag 已进行搜索显示的标签id
     * @return
     */
    @GetMapping("/getThisTagOthers")
    public String getThisTagOthers(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw,  Long...showTag){
        for (Long tag : showTag) {
            if(thisTag.equals(tag)){
                return Result.error(Msg.E21002).setMessage("所查询数据已呈现").toString();
            }
        }
        return Result.success(lawService.getThisTagOthers(pageNum, pageSize, thisTag, thisLaw)).toString();
    }

    /**
     * 修改法条, 同时将旧的法条状态修改为"被修改的"
     * @param newLaw 新的法条
     * @param oldLaws 旧法条id数组
     * @return 操作状态
     */
    @PostMapping("/modifyLaws")
    public String modifyLaws(LawBean newLaw, Long[] oldLaws){
        System.out.println("oldLaws: "+ Arrays.toString(oldLaws));
        return lawService.modifyLaws(newLaw, Year.parse("2000"), oldLaws).toString();
    }

    /**
     * 修改法条,同时将旧的法条改为"无效的"
     * @param newLaw 新法条
     * @param oldLaws 旧法条id数组
     * @return 操作状态
     */
    @PostMapping("/reviseLaws")
    public String reviseLaws(LawBean newLaw, Long[] oldLaws){
        System.out.println("oldLaws: "+ Arrays.toString(oldLaws));
        return lawService.reviseLaws(newLaw, Year.parse("2000"), oldLaws).toString();
    }

    /**
     * 通过当前法条Id搜索与之有关的新法条
     * @param thisLawId 当前法条Id
     * @return 之有关的新法条
     */
    @GetMapping("/getNewLaw")
    public String getNewLaw(Long thisLawId){
        return lawService.getNewLaw(thisLawId).toString();
    }

    /**
     * 通过当前Id获取与之有关的旧法条
     * @param thisLawId 当前法条Id
     * @return 与之有关的旧法条
     */
    @GetMapping("/getOldLaws")
    public String getOldLaws(Long thisLawId){
        return lawService.getOldLaws(thisLawId).toString();
    }

    @GetMapping("/getLegals")
    public String getLegals(){
        return lawService.getLegals().toString();
    }
}
