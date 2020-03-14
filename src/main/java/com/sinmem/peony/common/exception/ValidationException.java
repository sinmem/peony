package com.sinmem.peony.common.exception;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.common.exception
 * @Author sinmem
 * @CreateTime 2020-02-18 15:48
 * @Description 字段校验错误
 */
public class ValidationException extends RuntimeException{
    private int code;
    private String message;
    public ValidationException(Msg msg) {
        super(msg.message);
        this.code = msg.code();
        this.message = msg.message;
    }

    public ValidationException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Result toResult(){
        return Result.error(code, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ValidationException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationException addMessage(String msg){
        this.message = message + msg;
        return this;
    }

}
