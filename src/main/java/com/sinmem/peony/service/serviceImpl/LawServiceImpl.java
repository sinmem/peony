package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.common.enums.LawStatus;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.exception.DataOperationException;
import com.sinmem.peony.common.exception.ValidationException;
import com.sinmem.peony.common.utils.GsonUtils;
import com.sinmem.peony.dao.bean.LawBean;
import com.sinmem.peony.dao.bean.LegalName;
import com.sinmem.peony.dao.bean.TagBean;
import com.sinmem.peony.dao.bean.TreeNode;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.dao.dto.LawCompleteDto;
import com.sinmem.peony.dao.dto.TagLawsDto;
import com.sinmem.peony.dao.mapper.LawMapper;
import com.sinmem.peony.dao.mapper.LegalNameMapper;
import com.sinmem.peony.dao.mapper.TagMapper;
import com.sinmem.peony.service.LawService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service.serviceImpl
 * @Author: sinmem
 * @CreateTime: 2019-10-16 15:14
 * @Description: 法条业务层接口实现
 * @version: V1.0
 */
@Service
public class LawServiceImpl implements LawService {
    @Resource
    private LawMapper lawMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private LegalNameMapper legalNameMapper;

    @Override
    public ResultPage<LawBriefDto> searchLawsOnContent(String[] conditions, Integer pageNum, Integer pageSize) {
        Page<LawBriefDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> lawMapper.searchLawsOnContent(conditions));
        return new ResultPage(page);
    }

    @Override
    public LawCompleteDto getLawById(Long id) {
        return lawMapper.queryLawById(id);
    }

    @Override
    public ResultPage<LawBriefDto> getLawsByTag(Long tagId, Integer pageNum, Integer pageSize) {
        Page<LawBriefDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> lawMapper.getLawsByTag(tagId));
        return new ResultPage(page);
    }

    @Override
    public ResultPage<LawBriefDto> getLawsByFullName(Long fullNameId, Integer pageNum, Integer pageSize) {
        Page<LawBriefDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> lawMapper.getLawsByFullName(fullNameId));
        return new ResultPage(page);
    }

    /**
     * 需求:根据前端给出的页面大小和页号查询标签数组中id对应的法条,
     * 给出1,20->
     * 1.从标签数组中取出第一个id进行分页查询
     * 2.将结果放入List<TagLawsDto>
     * 3.检查page判断是否数据达到一页的要求,达到直接返回否则取出下一个id进行执行查询
     * 4.重复3直到数据满足一页的数量要求或者标签数组中全部id被用完
     * 给出n,20(n不等于1)->
     * 1.明确查询的起始位置
     * 2.传入起止位置进行查询
     * 3.将结果放入List<TagLawsDto>
     * 4.执行上面的步骤3,4
     */
    @Override
    public List<TagLawsDto> getLawsByTags(TagBean[] tags, Integer pageNum, Integer pageSize) {
        List<TagLawsDto> tagLawsList = new ArrayList<>();
        Page<LawBriefDto> page;
        int totalOffset = (pageNum - 1) * pageSize;// 总偏移量
        int limit = pageSize;// 剩余查询数量
        int offset = 0;// 剩余偏移量
        int tagIndex = 0;// tag数组索引
        if (pageNum > 1) {
            // 遍历到分页查询的起始下标和起始偏移量
            for (TagBean tag : tags) {
                offset = totalOffset;
                if (tag.getCount() > totalOffset) {
                    break;
                } else {
                    totalOffset -= tag.getCount();
                    // 当遍历数组到最后一个, 且totalOffset任然大于零时就相当于查询越界(在查询最后一条记录之后的记录);
                    // 若这时totalOffset等于
                    tagIndex++;
                    if (tagIndex == tags.length && totalOffset > 0) {
                        throw new DataOperationException(Msg.E21001);
                    } else if (tagIndex == tags.length && totalOffset == 0) {
                        throw new DataOperationException(Msg.W60001);
                    }
                }
            }
        }
        while (limit > 0) {

            Long tagId = tags[tagIndex].getId();

            page = PageHelper.offsetPage(offset, limit);
            lawMapper.getLawsByTag(tagId);

            if (page.size() == 0) {//防止死循环
                throw new RuntimeException("执行分页查询并放入数据/n==========/n");
            }
            limit -= page.size();
            TagLawsDto tagLawsDto = new TagLawsDto(tagMapper.getTagById(tagId), page);
            tagLawsList.add(tagLawsDto);
            // 每个tagID被用于查询后还需要查询则表明需要换tagID->offset需要重新置零;
            // 又因如不需要查询则不需要关心offset的值,于是放在这段代码放在那个位置都行
            if (++tagIndex >= tags.length) {
                break;
            }
            offset = 0;
        }
        return tagLawsList;
    }

    @Override
    public ResultPage<LawBriefDto> getThisTagOthers(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        Page<LawBriefDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> lawMapper.getThisTagOthers(thisTag, thisLaw));
        return new ResultPage(page);
    }
    @Override
    public ResultPage<LawBriefDto> getThisTagOthers2(Integer pageNum, Integer pageSize, Long thisTag, Long thisLaw) {
        Page<LawBriefDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> lawMapper.getThisTagOthers2(thisTag, thisLaw));
        return new ResultPage(page);
    }

    @Transactional
    @Override
    public Result modifyLaws(LawBean newLaw, Year minYear, Long... oldLaws) {
        validateLaw(newLaw, minYear);
        //批量修改旧法条,插入新法条,插入记录
        int addCount = lawMapper.addLaw(newLaw);
        int updCount = lawMapper.updateLawsStatus(oldLaws, LawStatus.MODIFIED);
        int recode = lawMapper.addUpdateRecode(newLaw.getId(), oldLaws);
        if (updCount != oldLaws.length || recode != oldLaws.length || addCount != 1) {
            throw new ValidationException(Msg.E20000).setMessage("修改法条失败!");
        }
        return Result.success();
    }

    @Transactional
    @Override
    public Result reviseLaws(LawBean newLaw, Year minYear, Long... oldLaws) {
        validateLaw(newLaw, minYear);
        int addCount = lawMapper.addLaw(newLaw);
        int updCount = lawMapper.updateLawsStatus(oldLaws, LawStatus.INVALID);
        int recode = lawMapper.addUpdateRecode(newLaw.getId(), oldLaws);
        if (updCount != oldLaws.length || recode != oldLaws.length || addCount != 1) {
            throw new ValidationException(Msg.E20000).setMessage("修改法条失败!");
        }
        return Result.success();
    }

    @Override
    public Result getNewLaw(Long thisLawId) {
        List<LawBriefDto> newLaws = lawMapper.getNewLaw(thisLawId);
        if (newLaws.isEmpty())
            return Result.error(Msg.E00001).setMessage("查询结果为空");
        return Result.success(newLaws);
    }

    @Override
    public Result getOldLaws(Long thisLawId) {
        List<LawBriefDto> oldLaws = lawMapper.getOldLaws(thisLawId);
        if (oldLaws.isEmpty())
            return Result.error(Msg.E00001).setMessage("查询结果为空");
        return Result.success();
    }

    @Override
    public Result getLegals() {
        return Result.success().setContent(legalNameMapper.getAllSimpleLegal());
    }

    @Override
    public Result getSimpleLawTree(Long lawId) {
        TreeNode simpleTree = getSimpleTree();
        HashMap<String, Object> result = new HashMap<>();
        result.put("legal", simpleTree);
        if (lawId != null) {
            LawBean thisLaw = lawMapper.getSimpleLawById(lawId);
            List<LawBean> lawList = lawMapper.getSimpleLawByLegalName(thisLaw.getLegalId());
            if(!lawList.isEmpty())result.put("lawList", generateLaws(lawList));
        }
        return Result.success(result);
    }

    private TreeNode SimpleLawTree;

    private synchronized TreeNode getSimpleTree() {
        if (SimpleLawTree == null) {
            TreeNode root = createRoot();
            SimpleLawTree = root;
            List<LegalName> allLegal = legalNameMapper.getAll();
            root.setChildren(generateCategories(allLegal));
        }
        return SimpleLawTree;
    }

    private List<TreeNode> generateCategories(List<LegalName> allLegal) {
        ArrayList<TreeNode> children = new ArrayList<>();
        if (allLegal == null || allLegal.isEmpty()) return children;
        Map<String, List<LegalName>> legalMap = allLegal.stream().collect(Collectors.groupingBy(LegalName::getCategory));
        for (Map.Entry<String, List<LegalName>> item : legalMap.entrySet()) {
            TreeNode node = new TreeNode();
            node.setId(-1L);
            node.setContent(item.getKey() + "(" + item.getValue().size() + "条)");
            node.setTitle(item.getKey());
            node.setChildren(generateLegals(item.getValue()));
            node.setExtra("Category");
            children.add(node);
        }
        return children;
    }

    private List<TreeNode> generateLegals(List<LegalName> aCategoryLegal) {
        ArrayList<TreeNode> children = new ArrayList<>();
        if (aCategoryLegal == null || aCategoryLegal.isEmpty()) return children;
        for (LegalName item : aCategoryLegal) {
            TreeNode node = new TreeNode();
            node.setId(item.getId());
            node.setContent(item.getAbbreviation() + "(" + item.getCount() + "条)");
            node.setTitle(item.getFullName());
            node.setExtra("Legal");
            children.add(node);
        }
        return children;
    }
    private List<TreeNode> generateLaws(List<LawBean> lawList) {
        ArrayList<TreeNode> children = new ArrayList<>();
        if (lawList == null || lawList.isEmpty()) return children;
        for (LawBean item : lawList) {
            TreeNode node = new TreeNode();
            node.setId(item.getId());
            String content = item.getContent();
            int index = content.indexOf("条");
            if(index>0) {
                String substring = content.substring(0, index + 1);
                node.setContent(substring);
            }else {
                node.setContent(content);
            }
            node.setTitle(content);
            node.setExtra("Legal");
            children.add(node);
        }
        return children;
    }

    private TreeNode createRoot() {
        TreeNode root = new TreeNode();
        root.setId(0L);
        root.setContent("民法典");
        root.setTitle("民法典");
        return root;
    }


    private void validateLaw(LawBean law, Year minYear) {
        // 必要字段非空校验
        if (law.getNo() == null ||
                law.getReleaseTime() == null ||
                !StringUtils.hasText(law.getContent()) ||
                law.getLegalId() == null)
            throw new ValidationException(Msg.E40012).setMessage("字段不能为空");
        // 范围值范围校验
        if (Year.now().compareTo(law.getReleaseTime()) < 0 && minYear.compareTo(law.getReleaseTime()) > 0)
            throw new ValidationException(Msg.E40013).setMessage("发布时间范围错误");
        //关联项是否关联
        if (legalNameMapper.isContains(law.getLegalId()) < 0)
            throw new ValidationException(Msg.E40014).setMessage("输入的法律名称不存在");
        if (lawMapper.noContains(law.getNo(), law.getLegalId()) > 0)
            throw new ValidationException(Msg.E40014).setMessage("输入的法条编号已存在");
    }
}
