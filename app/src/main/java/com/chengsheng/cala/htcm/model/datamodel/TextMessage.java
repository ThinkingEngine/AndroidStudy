package com.chengsheng.cala.htcm.model.datamodel;

public class TextMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
