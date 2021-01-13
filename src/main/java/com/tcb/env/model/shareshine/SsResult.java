package com.tcb.env.model.shareshine;

/**
 * 津南环保平台结果
 */
public class SsResult {

    private boolean Success;
    private String Code;
    private MessageUser Message;

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public MessageUser getMessage() {
        return Message;
    }

    public void setMessage(MessageUser message) {
        Message = message;
    }
}
