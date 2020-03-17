package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.dao.bean.Judgment;

public interface JudgmentService {
    Result updJudgment(Judgment judgment);

    Result searchJudgments(Integer pageNum, Integer pageSize, String condition);
}
