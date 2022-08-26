package com.hc.lib_common.network.http;

/**
 * @author furuoxuan
 */
public class ResponseThrowableException extends Exception {
    public int code;
    public String message;

    public ResponseThrowableException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
