package com.sinmem.peony.service;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.dao.bean.ReadingBean;
import org.springframework.web.bind.annotation.GetMapping;

public interface ReadingService {
    Result getReading(Long lawId);

    Result addReading(ReadingBean reading);

    Result updReading(ReadingBean reading);

    Result delReading(Long lawId);
}
