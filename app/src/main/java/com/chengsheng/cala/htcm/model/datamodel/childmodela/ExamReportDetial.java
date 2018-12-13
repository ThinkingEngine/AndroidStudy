package com.chengsheng.cala.htcm.model.datamodel.childmodela;

import java.util.List;

public class ExamReportDetial {
    private Order order;
    private Customer customer;
    private int order_type;
    private int exception_count;
    private String doctor_sign;
    private Healthy_advice advice;
    private List<examItemResult> exam_item_result;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getException_count() {
        return exception_count;
    }

    public void setException_count(int exception_count) {
        this.exception_count = exception_count;
    }

    public String getDoctor_sign() {
        return doctor_sign;
    }

    public void setDoctor_sign(String doctor_sign) {
        this.doctor_sign = doctor_sign;
    }

    public Healthy_advice getAdvice() {
        return advice;
    }

    public void setAdvice(Healthy_advice advice) {
        this.advice = advice;
    }

    public List<examItemResult> getExam_item_result() {
        return exam_item_result;
    }

    public void setExam_item_result(List<examItemResult> exam_item_result) {
        this.exam_item_result = exam_item_result;
    }

    @Override
    public String toString() {
        return "ExamReportDetial{" +
                "order=" + order +
                ", customer=" + customer +
                ", order_type=" + order_type +
                ", exception_count=" + exception_count +
                ", doctor_sign='" + doctor_sign + '\'' +
                ", advice=" + advice +
                ", exam_item_result=" + exam_item_result +
                '}';
    }
}
