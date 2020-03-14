package com.sinmem.peony.common.enums;

public enum LawStatus {
    VALID( "有效的",1),
    INVALID("失效的",2),
    MODIFIED("被修改的",3),
    ;
    private String key;
    private Integer value;

    LawStatus(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
