package com.sinmem.peony.service;

import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.common.exception.DataOperationException;
import com.sinmem.peony.dao.bean.TagBean;
import com.sinmem.peony.dao.dto.LawBriefDto;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * 法条标签业务接口
 */
public interface TagService {
    /**
     * 通过关键词查找标签
     * @param pageNum
     * @param pageSize
     * @param conditions
     * @retur
     */
    public ResultPage<TagBean> searchTags(Integer pageNum, Integer pageSize, String...conditions);

    public ResultPage<TagBean> getAllTags(Integer pageNum, Integer pageSize);

    /**
     * 为法条添加标签
     * @param name 要添加的标签名字
     * @param lawIds 需要添加该标签的法条id数组
     * @return 增加标签的法条条数
     */
    public int addLegalTag(String name, Long[] lawIds)throws DuplicateKeyException;

    /**
     * 修改法条所属的标签(将法条所拥有的一个标签重命名成另一个标签,
     * 旧的标签信息不修改直接新增; 如果这时旧标签没有再与法条有关联则删除)
     * @param lawIds 需要修改标签的法条id
     * @param tagName 新的标签名称
     * @param oldTagId 旧的标签id
     * @return 成功修改的法条条数
     */
    public int renameLawTag(String tagName, Long oldTagId, Long[] lawIds) throws DataOperationException;

    /**
     * 删除法条的某个标签(当该标签的不再与任何法条有关联是进行删除)
     * @param lawIds 需要修改标签的法条id
     * @param oldTagId 旧的标签id
     * @return 移除的法条标签条数
     */
    public int removeLawTag(Long oldTagId, Long[] lawIds);
}
