package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.service.TagService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.controller
 * @Author: sinmem
 * @CreateTime: 2019-10-18 14:22
 * @Description: 法条标签控制器
 * @version: V1.0
 */
@RestController
@CrossOrigin
@RequestMapping("web/v1/tag")
public class TagController {
    @Resource
    private TagService tagService;
    @GetMapping("/getAllTags")
    public String getAllTags(Integer pageNum, Integer pageSize){
        return Result.success(tagService.getAllTags(pageNum, pageSize)).toString();
    }
    @GetMapping("/searchTags")
    public String searchTags(Integer pageNum, Integer pageSize, String...conditions){
        return Result.success(tagService.searchTags(pageNum, pageSize, conditions)).toString();
    }

    /**
     * 添加法条的标签
     * @param tagName
     * @param lawIds
     * @return
     */
    @PostMapping("mange/addLawTag")
    public String addLegalTag(String tagName, Long[] lawIds){
        try{
            int successCount = tagService.addLegalTag(tagName, lawIds);
            if(successCount != lawIds.length){
                return Result.error(Msg.E20001).toString();
            }
        }catch (DuplicateKeyException ex){
            return Result.error(Msg.E20005).toString();
        }
        return Result.success(0,"标签添加成功, 共添加"+lawIds.length+"条数据").toString();
    }

    /**
     * 修改法条所属的标签(将法条所拥有的一个标签重命名成另一个标签,
     * 旧的标签信息不修改直接新增; 如果这时旧标签没有再与法条有关联则删除)
     * @param tagName 新的标签名称
     * @param oldTagId 旧的标签id
     * @param lawIds 需要修改标签的法条id
     * @return
     */
    @PostMapping("mange/renameLawTag")
    public String renameLawTag(String tagName, Long oldTagId, Long...lawIds){
        if (tagName == null || oldTagId == null || lawIds == null){
            return Result.error(Msg.E00001).toString();
        }
        int i = tagService.renameLawTag(tagName, oldTagId, lawIds);
        return Result.success().toString();
    }

    /**
     * 删除法条的某个标签(当该标签的不再与任何法条有关联是进行删除)
     * @param oldTagId 旧的标签id
     * @param lawIds 需要修改标签的法条id
     * @return
     */
    @PostMapping("mange/removeLawTag")
    public String removeLawTag(Long oldTagId, Long...lawIds){
        if (oldTagId == null || lawIds == null){
            return Result.error(Msg.E00001).toString();
        }
        int i = tagService.removeLawTag(oldTagId, lawIds);
        return Result.success().toString();
    }
}
