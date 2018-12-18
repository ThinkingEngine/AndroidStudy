package com.chengsheng.cala.htcm.protocol.childmodela;

public class HAItem {
    private String disease_name;
    private String healthy_advice;

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getHealthy_advice() {
        return healthy_advice;
    }

    public void setHealthy_advice(String healthy_advice) {
        this.healthy_advice = healthy_advice;
    }

    @Override
    public String toString() {
        return "HAItem{" +
                "disease_name='" + disease_name + '\'' +
                ", healthy_advice='" + healthy_advice + '\'' +
                '}';
    }
}
