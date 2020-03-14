package com.sinmem.peony.common.exception;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;

import java.util.List;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.conf
 * @Author: sinmem
 * @CreateTime: 2019-12-20 17:06
 * @Description: 页面索引控制器
 * @version: V1.0
 */
public class BizException extends RuntimeException {
    private int code;
    private String message;
    private List<String> messages;
    private Object content;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(Msg msg) {
        super(msg.message);
        this.code = msg.code();
        this.message = msg.message;
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

    public List<String> getMessages() {
        return messages;
    }

    public BizException setMessages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public Object getContent() {
        return content;
    }

    public BizException setContent(Object content) {
        this.content = content;
        return this;
    }

    public Result toResult() {
        return Result.error(code, message).setContent(content);
    }
}
