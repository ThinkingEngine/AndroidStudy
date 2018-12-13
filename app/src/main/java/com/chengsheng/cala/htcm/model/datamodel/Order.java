package com.chengsheng.cala.htcm.model.datamodel;

public class Order {
    private int id;
    private String discount_receivable;
    private String exam_status;
    private boolean is_closed_recommend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscount_receivable() {
        return discount_receivable;
    }

    public void setDiscount_receivable(String discount_receivable) {
        this.discount_receivable = discount_receivable;
    }

    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    public boolean isIs_closed_recommend() {
        return is_closed_recommend;
    }

    public void setIs_closed_recommend(boolean is_closed_recommend) {
        this.is_closed_recommend = is_closed_recommend;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", discount_receivable='" + discount_receivable + '\'' +
                ", exam_status='" + exam_status + '\'' +
                ", is_closed_recommend=" + is_closed_recommend +
                '}';
    }
}
