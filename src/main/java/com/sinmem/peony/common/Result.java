package com.sinmem.peony.common;

import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.utils.GsonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.common
 * @Author: sinmem
 * @CreateTime: 2019-10-16 17:42
 * @Description: 结果类
 * @version: V1.0
 */
public class Result<T> implements Serializable {
    private static final String SUCCESS = "success";
    private int code;
    private String message;
    private List<String> messages;
    private T content;

    private Result() {
        this.code = 0;
        this.message = SUCCESS;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Msg msg){
        this.code = msg.code();
        this.message = msg.message;
    }

    public static Result success() {
        return new Result();
    }

    public static <T> Result success(String msg) {
        Result<T> result = new Result<>();
        result.setMessage(msg);
        return result;
    }

    public static <T> Result<T> success(T content) {
        Result<T> result = new Result<>();
        result.setContent(content);
        return result;
    }

    public static <T> Result<T> success(int code, String message){
        return new Result<>(code, message);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message);
    }

    public static <T> Result<T> error(Msg msg) {
        return new Result<>(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Result<T> setMessages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public T getContent() {
        return content;
    }

    public Result<T> setContent(T content) {
        this.content = content;
        return this;
    }
    public boolean isSuccess(){
        return this.code == 0;
    }

    /**
     * 重写toString方法以输出省略null属性的json字符串
     * @return
     */
    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }
}
