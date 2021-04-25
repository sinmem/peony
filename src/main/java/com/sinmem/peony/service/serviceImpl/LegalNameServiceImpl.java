package com.sinmem.peony.service.serviceImpl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.dao.bean.LegalName;
import com.sinmem.peony.dao.mapper.LegalNameMapper;
import com.sinmem.peony.service.LegalNameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.service.serviceImpl
 * @Author: sinmem
 * @CreateTime: 2019-10-17 16:18
 * @Description: 法律名称业务接口实现类
 * @version: V1.0
 */
@Service
public class LegalNameServiceImpl implements LegalNameService {
    @Resource
    private LegalNameMapper legalNameMapper;

    @Override
    public ResultPage<LegalName> searchFullNames(Integer pageNum, Integer pageSize, String... conditions) {
        Page<LegalName> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> legalNameMapper.searchFullNames(conditions));
        return new ResultPage<>(page);
    }
}
