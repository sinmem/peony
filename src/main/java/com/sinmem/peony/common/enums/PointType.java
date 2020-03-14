package com.sinmem.peony.common.enums;

public enum PointType {
    OTHERS("其他"),
    BONUS("奖励"),
    INVITE("邀请好友奖励"),
    PAY("消费"),
    ;
    private String key;

    PointType(String key) {
        this.key = key;
    }
}
