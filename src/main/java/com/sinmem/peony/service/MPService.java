package com.sinmem.peony.service;

import java.util.List;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service
 * @Author sinmem
 * @CreateTime 2019-12-29 12:42
 * @Description 用户积分业务接口
 */
public interface MPService {
    boolean addPoints();
    boolean subPoint();
    int getPointCount();
    List queryPointDetails();


}
