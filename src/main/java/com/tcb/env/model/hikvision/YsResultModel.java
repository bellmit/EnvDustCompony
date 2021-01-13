package com.tcb.env.model.hikvision;

/**
 * @Author: WangLei
 * @Description: 萤石云结果
 * @Date: Create in 2019/7/3 14:59
 * @Modify by WangLei
 */
public class YsResultModel<T> {

    private T data;
    private String code;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
