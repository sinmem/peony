package com.sinmem.peony.common.enums;

import java.util.HashMap;

/**
 * @Description 验证码类型
 */
public class CodeType {
    private static final HashMap<Integer, String> map;
    static{
        map = new HashMap<>();
        map.put(1,"REGISTER_");
        map.put(2,"LOGIN_");
        map.put(3,"RESET_PWD_");
        map.put(4,"OTHER_");
    }

    public static String getValue(Integer key){
        return map.get(key);
    }

    @Override
    public String toString() {
        return "CodeType{map:" + map.toString() + "}";
    }
}
