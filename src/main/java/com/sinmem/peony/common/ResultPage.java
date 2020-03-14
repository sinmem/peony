package com.sinmem.peony.common;

import com.github.pagehelper.Page;

import java.io.Serializable;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.common
 * @Author: sinmem
 * @CreateTime: 2019-11-02 02:10
 * @Description: 分页查询结果页
 * @version: V1.0
 */
public class ResultPage<T> implements Serializable {
    private int pageNum;//页码(当前页1到pages)
    private int pageSize;//分页大小
    private long total;//总条数
    private int pages;// 总页数
    private Page<T> pageContent;//页面内容

    public ResultPage(Page<T> pageContent) {
        this.pageNum = pageContent.getPageNum();
        this.pageSize = pageContent.getPageSize();
        this.total = pageContent.getTotal();
        this.pages = pageContent.getPages();
        this.pageContent = pageContent;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }

    public Page<T> getPageContent() {
        return pageContent;
    }

}
