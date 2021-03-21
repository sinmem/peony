package com.sinmem.peony.service.serviceImpl;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.dao.bean.ReadingBean;
import com.sinmem.peony.dao.mapper.ReadingMapper;
import com.sinmem.peony.service.ReadingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service.serviceImpl
 * @Author sinmem
 * @CreateTime 2021-03-21 17:40
 * @Description 解读
 */
@Service
public class ReadingServiceImpl implements ReadingService {
    @Resource
    private ReadingMapper readingMapper;

    @Override
    public Result getReading(Long lawId) {
        ReadingBean reading = readingMapper.getReading(lawId);
        if(reading==null){
            return Result.success("未获取到解读");
        }
        return Result.success(reading);
    }

    @Override
    public Result addReading(ReadingBean reading) {
        if(readingMapper.getReading(reading.getLawId())!=null){
            return Result.error(-1, "添加失败, 已存在解读!");
        }
        Integer result = readingMapper.addReading(reading);
        return result != 1 ? Result.error(-1, "添加失败") : Result.success("添加成功");
    }

    @Override
    public Result updReading(ReadingBean reading) {
        Integer result = readingMapper.updReading(reading);
        return result != 1 ? Result.error(-1, "更新失败") : Result.success("更新成功");
    }

    @Override
    public Result delReading(Long lawId) {
        Integer result = readingMapper.delReading(lawId);
        return result != 1 ? Result.error(-1,"删除失败") : Result.success("删除成功");
    }
}
