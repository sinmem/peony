package com.sinmem.peony.common.enums;

public enum RemarkSubmissionType {
    ADD("添加",1),
    UPDATE("更新",2),
    ;
    private String key;
    private Integer value;

    RemarkSubmissionType(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
