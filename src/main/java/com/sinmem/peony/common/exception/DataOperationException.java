package com.sinmem.peony.common.exception;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.common.exception
 * @Author: sinmem
 * @CreateTime: 2019-11-03 22:24
 * @Description:
 * @version: V1.0
 */
public class DataOperationException extends RuntimeException{
    private int code;
    private String message;
    public DataOperationException(Msg msg) {
        super(msg.message);
        this.code = msg.code();
        this.message = msg.message;
    }

    public DataOperationException(int code, String message) {
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

    public DataOperationException setMessage(String message) {
        this.message = message;
        return this;
    }

    public DataOperationException addMessage(String msg){
        this.message = message + msg;
        return this;
    }
}
