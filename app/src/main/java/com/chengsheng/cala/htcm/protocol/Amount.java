package com.chengsheng.cala.htcm.protocol;

public class Amount {
    private String received;
    private String discount_receivable;

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getDiscount_receivable() {
        return discount_receivable;
    }

    public void setDiscount_receivable(String discount_receivable) {
        this.discount_receivable = discount_receivable;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "received='" + received + '\'' +
                ", discount_receivable='" + discount_receivable + '\'' +
                '}';
    }
}
