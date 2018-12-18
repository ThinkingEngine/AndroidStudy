package com.chengsheng.cala.htcm.protocol.childmodela;

public class RegisterError {

    private String error;
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterError{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
