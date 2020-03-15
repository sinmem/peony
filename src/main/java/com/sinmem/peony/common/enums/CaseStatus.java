package com.sinmem.peony.common.enums;

public enum CaseStatus {
    SUBMIT( "已申请",1),
    PASS("已通过",2),
    REJECT("被拒绝",3),
    ;
    private String key;
    private Integer value;

    CaseStatus(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
