package com.sinmem.peony.common.utils;

import com.google.gson.Gson;

/**
 * json转换工具
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.common.utils
 * @Author: sinmem
 * @CreateTime: 2019-10-16 22:16
 * @Description: json转换工具
 * @version: V1.0
 */
public class GsonUtils {
    private static final Gson G = new Gson();
    /**
     * 对象转Json字符串(自动忽略null属性)
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        return G.toJson(obj);
    }

    /**
     * json字符串转java对象
     * @param json
     * @param type
     * @return
     */
    public static <T>T fromJson(String json, Class<T> type){
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * json字符串转java对象数组
     * @param json
     * @param type
     * @return
     */
    public static <T>T srrayFromJson(String json, Class<T> type){
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}
