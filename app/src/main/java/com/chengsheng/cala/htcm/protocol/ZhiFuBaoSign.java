package com.chengsheng.cala.htcm.protocol;

public class ZhiFuBaoSign {

    private String ali_sign;
    private String amount;

    public String getAli_sign() {
        return ali_sign;
    }

    public void setAli_sign(String ali_sign) {
        this.ali_sign = ali_sign;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ZhiFuBaoSign{" +
                "ali_sign='" + ali_sign + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
