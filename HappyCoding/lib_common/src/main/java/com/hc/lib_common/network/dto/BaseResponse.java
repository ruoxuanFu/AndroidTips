package com.hc.lib_common.network.dto;

/**
 * 网络请求返回基类
 *
 * @author furuoxuan
 */
public class BaseResponse<T> {

    private int status;
    private String msg;
    private T data;
    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
