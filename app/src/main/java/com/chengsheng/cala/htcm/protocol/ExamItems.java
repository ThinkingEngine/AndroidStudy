package com.chengsheng.cala.htcm.protocol;

public class ExamItems {

    private int id;
    private String name;
    private String order_status;
    private String exam_status;
    private Customer customer;
    private Amount amount;
    private Report report;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "ExamItems{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order_status='" + order_status + '\'' +
                ", exam_status='" + exam_status + '\'' +
                ", customer=" + customer +
                ", amount=" + amount +
                ", report=" + report +
                '}';
    }
}
