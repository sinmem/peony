package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.Judgment;
import com.sinmem.peony.dao.mapper.JudgmentMapper;
import com.sinmem.peony.service.JudgmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service.serviceImpl
 * @Author sinmem
 * @CreateTime 2020-03-18 00:17
 * @Description 判决书业务实现类
 */
@Service
public class JudgmentServicesImpl implements JudgmentService {
    @Resource
    private JudgmentMapper judgmentMapper;

    @Override
    public Result updJudgment(Judgment judgment) {
        return Result.success(judgmentMapper.updJudgment(judgment));
    }

    @Override
    public Result searchJudgments(Integer pageNum, Integer pageSize, String condition) {
        Page<Judgment> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(()->judgmentMapper.searchJudgments(condition));
        return Result.success(new ResultPage(page));
//        return Result.success(judgmentMapper.searchJudgments(condition));
    }
}
