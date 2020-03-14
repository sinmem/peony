package com.sinmem.peony.common.enums;

public enum RemarkType {
    REMARK( "备注",1),
    DISSENT("异议",2),
    ;
    private String key;
    private Integer value;

    RemarkType(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
