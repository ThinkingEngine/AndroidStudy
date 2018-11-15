package com.chengsheng.cala.htcm.model.datamodel;

public class SMSVerificationResult {
    private String code_id;

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    @Override
    public String toString() {
        return "SMSVerificationResult{" +
                "code_id='" + code_id + '\'' +
                '}';
    }
}
