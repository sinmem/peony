package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.enums.PageParam;
import com.sinmem.peony.common.exception.DataOperationException;
import com.sinmem.peony.dao.bean.TagBean;
import com.sinmem.peony.dao.mapper.TagMapper;
import com.sinmem.peony.service.TagService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service.serviceImpl
 * @Author: sinmem
 * @CreateTime: 2019-10-17 16:19
 * @Description: 法条标签业务接口实现类
 * @version: V1.0
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;
    @Override
    public ResultPage<TagBean> searchTags(Integer pageNum, Integer pageSize, String... conditions) {
        Page<TagBean> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(()->tagMapper.searchTags(conditions));
        return new ResultPage<>(page);
    }

    @Override
    public ResultPage<TagBean> getAllTags(Integer pageNum, Integer pageSize) {
        Page<TagBean> page = PageHelper.startPage(pageNum,pageSize).doSelectPage(()->tagMapper.getAllTags());
        return new ResultPage<>(page);
    }

    @Override
    @Transactional
    public int addLegalTag(String name, Long[] lawIds)throws DuplicateKeyException,DataOperationException{
        TagBean oldTag;
        Integer addCount = lawIds.length;
        Integer successCount;
        try{
            if((oldTag = tagMapper.getTagByName(name)) == null){
                oldTag = new TagBean(name, addCount);
                tagMapper.addTag(oldTag);
                successCount = tagMapper.addTagLaw(oldTag.getId(), lawIds);
            }else {
                tagMapper.updateTagCountById(oldTag.getCount()+addCount, oldTag.getId());
                successCount = tagMapper.addTagLaw(oldTag.getId(), lawIds);
            }
        }catch (DuplicateKeyException ex){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw ex;
        }
        if(successCount != lawIds.length){
            throw new DataOperationException(Msg.E20001);
        }
        return successCount;
    }

    @Override
    @Transactional
    public int renameLawTag(String tagName, Long oldTagId, Long[] lawIds)throws DataOperationException {
        Integer count;
        count = addLegalTag(tagName, lawIds);
        count *= tagMapper.removeLawTag(oldTagId, lawIds);
        if(count != lawIds.length*lawIds.length){
            throw new DataOperationException(Msg.E20002);
        }
        count = tagMapper.getCountById(oldTagId)-lawIds.length;
        if( count == 0){
            tagMapper.deleteTagById(oldTagId);
        }else if(count > 0){
            tagMapper.updateTagCountById(count, oldTagId);
        }else {
            throw new DataOperationException(Msg.E20004);
        }
        return lawIds.length;
    }

    @Override
    @Transactional
    public int removeLawTag(Long oldTagId, Long[] lawIds) {
        int count = tagMapper.getCountById(oldTagId)-lawIds.length;
        if(count < 0){
            throw new DataOperationException(Msg.E20003).addMessage(": 需删除数量过多!");
        }else if(count == 0){
            tagMapper.removeLawTag(oldTagId, lawIds);
            tagMapper.deleteTagById(oldTagId);
        }else {
            tagMapper.removeLawTag(oldTagId, lawIds);
            tagMapper.updateTagCountById(count, oldTagId);
        }
        return lawIds.length;
    }
}
