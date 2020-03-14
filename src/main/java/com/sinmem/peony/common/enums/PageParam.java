package com.sinmem.peony.common.enums;

public enum PageParam {
    LAW_PAGE_NUM(1),
    LAW_PAGE_SIZE(20),
    TAG_PAGE_NUM(1),
    TAG_PAGE_SIZE(50),
    NAME_PAGE_NUM(1),
    NAME_PAGE_SIZE(50),
    ;
    private int number;
    PageParam(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
