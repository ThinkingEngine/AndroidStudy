package com.chengsheng.cala.htcm.data.exception;


public class DefaultErrorException extends Exception {
    public String code;
    public String type;
    public String message;

    public DefaultErrorException(String detailMessage) {
        this("404", detailMessage);
    }

    public DefaultErrorException(String error, String detailMessage) {
        this(error, detailMessage, "-1");
    }

    public DefaultErrorException(String error, String detailMessage, String type) {
        super(detailMessage);
        this.code = error;
        this.type = type;
    }
}
