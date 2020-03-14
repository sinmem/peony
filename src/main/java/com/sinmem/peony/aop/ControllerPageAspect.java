package com.sinmem.peony.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.aop
 * @Author: sinmem
 * @CreateTime: 2019-11-05 01:30
 * @Description: 控制层分页切面
 * @version: V1.0
 */
@Aspect
@Component
public class ControllerPageAspect {
    @Value("${peony.page.pageNumber}")
    private Integer pageNumber;
    @Value("${peony.page.sPageSize}")
    private Integer sPageSize;
    @Value("${peony.page.mPageSize}")
    private Integer mPageSize;
    @Value("${peony.page.lPageSize}")
    private Integer lPageSize;

    /**
     * 小号分页大小
     */
    @Pointcut("execution(public * com.sinmem.peony.web.controller.LawController.get*(Integer,Integer,..))||" +
            "execution(public * com.sinmem.peony.web.controller.LawController.searchOnContent(..))")
    public void sPage(){}

    @Around("sPage()")
    public Object beforeLawPage(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("我是前置通知，我将要执行一个方法了1111111111");
        return pjp.proceed(pageCheck(pjp,pageNumber,sPageSize));
    }

    /**
     * 中等大小分页
     */
    @Pointcut("execution(public * com.sinmem.peony.web.controller.TagController.*(Integer,Integer,..))||" +
            "execution(public * com.sinmem.peony.web.controller.LawController.searchFullNames(..))||" +
            "execution(public * com.sinmem.peony.web.controller.LegalRemarkController.getTempRemarks(..))||"+
            "execution(public * com.sinmem.peony.web.controller.LegalCaseController.getDoCases(..))")
    public void mPage(){

    }

    @Around("mPage()")
    public Object aroundLegalNamePage(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("我是前置通知，我将要执行一个方法了333333333");
        return pjp.proceed(pageCheck(pjp,pageNumber,mPageSize));
    }

    private Object[] pageCheck(ProceedingJoinPoint pjp, Integer defaultNum, Integer defaultSize){
        Object[] objs = pjp.getArgs();
        Integer pageNum,pageSize;
        pageNum = (Integer) objs[0];
        pageSize = (Integer) objs[1];
        objs[0] = pageNum == null ? defaultNum:pageNum;
        objs[1] = (pageSize == null || pageSize <= 0 || pageSize >100) ? defaultSize : pageSize;
        return objs;
    }
}
