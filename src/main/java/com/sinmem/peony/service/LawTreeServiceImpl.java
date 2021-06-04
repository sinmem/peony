package com.sinmem.peony.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.TreeNode;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.dao.mapper.CatalogMapper;
import com.sinmem.peony.dao.mapper.LawMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service
 * @Author sinmem
 * @CreateTime 2021-05-09 21:41
 * @Description
 */
@Service
public class LawTreeServiceImpl implements LawTreeService {
    @Resource
    private CatalogMapper catalogMapper;
    @Resource
    private LawMapper lawMapper;
    private static final String LOCK = "SimpleLawTree_LOCK";



    //<editor-fold desc="Add">
    private Result addLawTree(TreeNode node, Integer lawType) {
        Integer content = catalogMapper.addNode(node);
        clearCache(lawType);
        return Result.success(content);
    }

    @Override
    public Result addMFDLawTree(TreeNode node, Integer lawType) {
        return addLawTree(node, lawType);
    }

    @Override
    @DS("OldL")
    public Result addLMFLawTree(TreeNode node, Integer lawType) {
        return addLawTree(node, lawType);
    }

    @Override
    @DS("MSFL")
    public Result addMSFLawTree(TreeNode node, Integer lawType) {
        return addLawTree(node, lawType);
    }

    @Override
    @DS("XSFL")
    public Result addXSFLawTree(TreeNode node, Integer lawType) {
        return addLawTree(node, lawType);
    }

    @Override
    @DS("XFL")
    public Result addXFLawTree(TreeNode node, Integer lawType) {
        return addLawTree(node, lawType);
    }
    //</editor-fold>

    @Override
    public Result getMFDLawTree(Long lawId, Integer lawType) {
        return getLawTree(lawId, lawType);
    }

    @Override
    @DS("OldL")
    public Result getLMFLawTree(Long lawId, Integer lawType) {
        return getLawTree(lawId, lawType);
    }

    @Override
    @DS("MSFL")
    public Result getMSFLawTree(Long lawId, Integer lawType) {
        return getLawTree(lawId, lawType);
    }

    @Override
    @DS("XSFL")
    public Result getXSFLawTree(Long lawId, Integer lawType) {
        return getLawTree(lawId, lawType);
    }

    @Override
    @DS("XFL")
    public Result getXFLawTree(Long lawId, Integer lawType) {
        return getLawTree(lawId, lawType);
    }

    @Override
    public void clearCache(Integer lawType) {
        LawTreeMap.remove(lawType);
        LawTreeIndexMap.remove(lawType);
    }

    //<editor-fold desc="Del">
    private Result<Integer> delLawTree(Long lawId, Integer lawType) {
        clearCache(lawType);
        return Result.success(catalogMapper.delNode(lawId));
    }

    @Override
    public Result delMFDLawTree(Long lawId, Integer lawType) {
        return delLawTree(lawId, lawType);
    }

    @Override
    @DS("OldL")
    public Result delLMFLawTree(Long lawId, Integer lawType) {
        return delLawTree(lawId, lawType);
    }

    @Override
    @DS("MSFL")
    public Result delMSFLawTree(Long lawId, Integer lawType) {
        return delLawTree(lawId, lawType);
    }

    @Override
    @DS("XSFL")
    public Result delXSFLawTree(Long lawId, Integer lawType) {
        return delLawTree(lawId, lawType);
    }

    @Override
    @DS("XFL")
    public Result delXFLawTree(Long lawId, Integer lawType) {
        return delLawTree(lawId, lawType);
    }
    //</editor-fold>

    //<editor-fold desc="Upd">
    private Result updLawTreeNode(TreeNode node, Integer lawType) {
        clearCache(lawType);
        return Result.success(catalogMapper.updNode(node));
    }

    @Override
    public Result updMFDLawTree(TreeNode node, Integer lawType) {
        return updLawTreeNode(node, lawType);
    }

    @Override
    @DS("OldL")
    public Result updLMFLawTree(TreeNode node, Integer lawType) {
        return updLawTreeNode(node, lawType);
    }

    @Override
    @DS("MSFL")
    public Result updMSFLawTree(TreeNode node, Integer lawType) {
        return updLawTreeNode(node, lawType);
    }

    @Override
    @DS("XSFL")
    public Result updXSFLawTree(TreeNode node, Integer lawType) {
        return updLawTreeNode(node, lawType);
    }

    @Override
    @DS("XFL")
    public Result updXFLawTree(TreeNode node, Integer lawType) {
        return updLawTreeNode(node, lawType);
    }
    //</editor-fold>

    //<editor-fold desc="获取相关的其他法条">
    private ResultPage getOtherLaw(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        Page<LawBriefDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> lawMapper.getThisTagOthers2(thisTag, thisLaw));
        return new ResultPage(page);
    }

    @Override
    public ResultPage<LawBriefDto> getMFDLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        return getOtherLaw(pageNum, pageSize, thisTag, thisLaw);
    }

    @Override
    @DS("OldL")
    public ResultPage<LawBriefDto> getLMFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        return getOtherLaw(pageNum, pageSize, thisTag, thisLaw);
    }

    @Override
    @DS("MSFL")
    public ResultPage<LawBriefDto> getMSFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        return getOtherLaw(pageNum, pageSize, thisTag, thisLaw);
    }

    @Override
    @DS("XSFL")
    public ResultPage<LawBriefDto> getXSFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        return getOtherLaw(pageNum, pageSize, thisTag, thisLaw);
    }

    @Override
    @DS("XFL")
    public ResultPage<LawBriefDto> getXFLawOther(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        return getOtherLaw(pageNum, pageSize, thisTag, thisLaw);
    }
    //</editor-fold>

    private Result<Map<String, Object>> getLawTree(Long lawId, Integer lawType) {
        TreeNode treeNode = generateLawTree(lawType);
        Map<String, Object> map = new HashMap<>();
        map.put("tree", treeNode);
        Long nodeId = catalogMapper.getNodeId(lawId);
        if(nodeId!=null){
            List parentList = getParentList(nodeId, lawType);
            map.put("parentList", parentList);
        }
        return Result.success(map);
    }


    private TreeNode generateLawTree(Integer lawType) {
        TreeNode LawTree = LawTreeMap.get(lawType);
        Map<Long, TreeNode> treeIndex = LawTreeIndexMap.get(lawType);
        if (LawTree == null||treeIndex==null) {
            synchronized (LOCK) {
                if (LawTree == null||treeIndex==null) {
                    treeIndex = new HashMap<>();
                    TreeNode root = createRoot(lawType);
                    LawTree = root;
                    List<TreeNode> children = getChildren(root.getId(), treeIndex);
                    treeIndex.put(root.getId(), root);
                    root.setChildren(children);
                    LawTreeMap.put(lawType, LawTree);
                    LawTreeIndexMap.put(lawType, treeIndex);
                }
            }
        }
        return LawTree;
    }

    private final Map<Integer,Map<Long, TreeNode>> LawTreeIndexMap = new HashMap<>();
    private final Map<Integer, TreeNode> LawTreeMap = new HashMap<>();


    private List<TreeNode> getChildren(Long parent, Map<Long, TreeNode> treeIndex) {
        List<TreeNode> children = catalogMapper.getChildren(parent);
        if (!children.isEmpty()) {
            for (TreeNode child : children) {
                treeIndex.put(child.getId(), child);
                if ("catalog".equals(child.getStyle())) {
                    child.setChildren(getChildren(child.getId(), treeIndex));
                }
            }
        }
        return children;
    }

    private TreeNode createRoot(Integer lawType) {
        TreeNode root = new TreeNode();
        root.setId(0L);
        String content;
        if (lawType == null) content = "民法典";
        else
            switch (lawType) {
                case 2:
                    content = "老民法";
                    break;
                case 3:
                    content = "民诉法";
                    break;
                case 4:
                    content = "刑诉法";
                    break;
                case 5:
                    content = "刑法";
                    break;
                default:
                    content = "民法典";
            }
        root.setContent(content);
        root.setTitle(content);
        root.setStyle("catalog");
        return root;
    }

    private List getParentList(Long nodeId, Integer lawType) {
        List<Long> result = new ArrayList();
        getParent(nodeId, lawType, result);
        return result;
    }

    private void getParent(Long nodeId, Integer lawType, List<Long> result) {
        Map<Long, TreeNode> treeIndex = LawTreeIndexMap.get(lawType);
        TreeNode node = treeIndex.get(nodeId);
        result.add(node.getId());
        if (node.getId() != 0) {
            getParent(node.getParent(), lawType, result);
        }
    }
}
