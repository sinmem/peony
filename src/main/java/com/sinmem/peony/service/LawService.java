package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.bean.TagBean;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.dao.dto.LawCompleteDto;
import com.sinmem.peony.dao.dto.TagLawsDto;

import java.time.Year;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service.serviceImpl
 * @Author: sinmem
 * @CreateTime: 2019-10-16 15:12
 * @Description: 法条业务层接口
 * @version: V1.0
 */

public interface LawService {
    /**
     * 根据关键词在内容列搜索法条接口方法
     * @param conditions
     * @return List>BeanDto
     */
    public ResultPage<LawBriefDto> searchLawsOnContent(String[] conditions, Integer pageNum, Integer pageSize);

    /**
     * 根据id获取完整的法条信息
     * @param id
     * @return
     */
    public LawCompleteDto getLawById(Long id);

    public ResultPage<LawBriefDto> getLawsByTag(Long tagId, Integer pageNum, Integer pageSize);

    public ResultPage<LawBriefDto> getLawsByFullName(Long fullNameId, Integer pageNum, Integer pageSize);

    /**
     * 根据法条标签id数组查找法条
     * @param pageNum 页数
     * @param pageSize 分页大小
     * @param tags 标签id数组
     * @return
     */
    public List<TagLawsDto> getLawsByTags(TagBean[] tags, Integer pageNum, Integer pageSize);

    public ResultPage<LawBriefDto> getThisTagOthers(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw);

    /**
     * 修改法条, 同时将旧的法条状态修改为"被修改的"
     * @param newLaw 新的法条
     * @param oldLaws 旧法条id数组
     * @return Result
     */
    Result modifyLaws(LawBean newLaw, Year minYear, Long...oldLaws);
    /**
     * 修改法条,同时将旧的法条改为"无效的"
     * @param newLaw 新法条
     * @param oldLaws 旧法条id数组
     * @return Result
     */
    Result reviseLaws(LawBean newLaw, Year minYear, Long...oldLaws);

    /**
     * 通过当前法条Id搜索与之有关的新法条
     * @param thisLawId 当前法条Id
     * @return Result
     */
    Result getNewLaw(Long thisLawId);

    /**
     * 通过当前Id获取与之有关的旧法条
     * @param thisLawId 当前法条Id
     * @return Result
     */
    Result getOldLaws(Long thisLawId);

    Result getLegals();

    /**
     * 获取
     * @return
     * @param lawId
     */
    Result getSimpleLawTree(Long lawId);
}
