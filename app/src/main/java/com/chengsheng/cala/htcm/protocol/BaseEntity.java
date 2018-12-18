package com.chengsheng.cala.htcm.protocol;


/**
 * Author: 任和
 * CreateDate: 2018/12/18 2:20 PM
 * Description:
 */
public class BaseEntity<T> {
    private int code;
    private String message = "";
    private T data;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "BaseEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success='" + success + '\'' +
                ", data=" + data +
                '}';
    }
}
