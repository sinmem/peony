package com.sinmem.peony.common.utils;

import javax.validation.constraints.NotEmpty;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.common.utils
 * @Author: sinmem
 * @CreateTime: 2019-10-17 18:01
 * @Description: 字符串操作工具类
 * @version: V1.0
 */
public class Stringutils {
    /**
     * 将数组转为字符串(默认用"+"拼接在末尾且修剪末尾的"+")
     * @param strArray 给定的数组
     * @return
     */
    public static String arrayToString(@NotEmpty String[] strArray){
        return arrayToString(strArray, "+", true, true);
    }

    /**
     * 将数组转为字符串(默认拼接在末尾且修剪末尾的连字符)
     * @param strArray 给定的数组
     * @param newPattern 用于拼接的字符
     * @return
     */
    public static String arrayToString(@NotEmpty String[] strArray, String newPattern){
        return arrayToString(strArray, newPattern, true, true);
    }

    /**
     * 将数组转为字符串(默认拼接修剪首尾的连字符)
     * @param strArray 给定的数组
     * @param newPattern 用于拼接的字符
     * @param atTail 是否拼在尾部
     * @return
     */
    public static String arrayToString(@NotEmpty String[] strArray, String newPattern, boolean atTail){
        return arrayToString(strArray, newPattern, atTail, true);
    }

    /**
     * 将数组转为字符串
     * @param strArray 给定的数组
     * @param newPattern 用于拼接的字符
     * @param atTail 是否拼在尾部
     * @param istrimPattern 是否修剪首尾连字符
     * @return
     */
    public static String arrayToString(@NotEmpty String[] strArray, String newPattern, boolean atTail, boolean istrimPattern){

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < strArray.length; index++) {
            if(istrimPattern){
                //特定开头和结尾
                if(index == 0){
                    sb.append(strArray[index]);
                    continue;
                }
                if(index == strArray.length - 1){
                    sb.append(strArray[index]);
                    continue;
                }
                //将给定字符与字符串拼装
                assembleString(sb, atTail, newPattern, strArray[index]);
            }else{
                assembleString(sb, atTail, newPattern, strArray[index]);
            }

        }
        return sb.toString();
    }

    private static void assembleString(StringBuilder sb, boolean atTail, String pattern, String str){
        if(atTail){
            sb.append(str).append(pattern);
        }else{
            sb.append(pattern).append(str);
        }
    }
}
