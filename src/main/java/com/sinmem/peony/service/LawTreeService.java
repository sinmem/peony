package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.TreeNode;
import com.sinmem.peony.dao.dto.LawBriefDto;

public interface LawTreeService {
    Result addMFDLawTree(TreeNode node, Integer lawType);
    Result addLMFLawTree(TreeNode node, Integer lawType);
    Result addMSFLawTree(TreeNode node, Integer lawType);
    Result addXSFLawTree(TreeNode node, Integer lawType);
    Result addXFLawTree(TreeNode node, Integer lawType);
    
    Result getMFDLawTree(Long lawId, Integer lawType);
    Result getLMFLawTree(Long lawId, Integer lawType);
    Result getMSFLawTree(Long lawId, Integer lawType);
    Result getXSFLawTree(Long lawId, Integer lawType);
    Result getXFLawTree(Long lawId, Integer lawType);
    void clearCache(Integer lawType);

    Result delMFDLawTree(Long lawId, Integer lawType);
    Result delLMFLawTree(Long lawId, Integer lawType);
    Result delMSFLawTree(Long lawId, Integer lawType);
    Result delXSFLawTree(Long lawId, Integer lawType);
    Result delXFLawTree(Long lawId, Integer lawType);

    Result updMFDLawTree(TreeNode node, Integer lawType);
    Result updLMFLawTree(TreeNode node, Integer lawType);
    Result updMSFLawTree(TreeNode node, Integer lawType);
    Result updXSFLawTree(TreeNode node, Integer lawType);
    Result updXFLawTree(TreeNode node, Integer lawType);

    ResultPage<LawBriefDto> getMFDLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw);
    ResultPage<LawBriefDto> getLMFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw);
    ResultPage<LawBriefDto> getMSFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw);
    ResultPage<LawBriefDto> getXSFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw);
    ResultPage<LawBriefDto> getXFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw);
}
