package com.sinmem.peony.common.exception;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.common.exception
 * @Author sinmem
 * @CreateTime 2020-02-07 21:54
 * @Description 错误的手机号类
 */
public class ErrorPhoneNumberException extends SmsException {
    public ErrorPhoneNumberException(Msg msg) {
        super(msg);
    }

    public ErrorPhoneNumberException(int code, String message) {
        super(code, message);
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

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorPhoneNumberException addMessage(String msg){
        this.message = message + msg;
        return this;
    }
}
