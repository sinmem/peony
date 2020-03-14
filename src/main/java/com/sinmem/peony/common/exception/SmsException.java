package com.sinmem.peony.common.exception;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.common.exception
 * @Author sinmem
 * @CreateTime 2020-02-07 21:55
 * @Description 短信发送失败错误类
 */
public class SmsException extends RuntimeException {
    protected int code;
    protected String message;
    public SmsException(Msg msg) {
        super(msg.message);
        this.code = msg.code();
        this.message = msg.message;
    }

    public SmsException(int code, String message) {
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

    public void setMessage(String message) {
        this.message = message;
    }

    public SmsException addMessage(String msg){
        this.message = message + msg;
        return this;
    }
}
