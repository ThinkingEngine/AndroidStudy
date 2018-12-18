package com.chengsheng.cala.htcm.data.retrofit.exception;

public class BusinessException extends RuntimeException {

    public int code;

    public BusinessException(String detailMessage) {
        this(-1, detailMessage);
    }

    public BusinessException(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }
}
