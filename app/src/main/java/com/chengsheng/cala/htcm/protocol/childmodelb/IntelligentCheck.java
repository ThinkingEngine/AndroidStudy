package com.chengsheng.cala.htcm.protocol.childmodelb;

public class IntelligentCheck {

    private ExamCustomer exam_customer;
    private Items unexamined;
    private Items examined;

    public ExamCustomer getExam_customer() {
        return exam_customer;
    }

    public void setExam_customer(ExamCustomer exam_customer) {
        this.exam_customer = exam_customer;
    }

    public Items getUnexamined() {
        return unexamined;
    }

    public void setUnexamined(Items unexamined) {
        this.unexamined = unexamined;
    }

    public Items getExamined() {
        return examined;
    }

    public void setExamined(Items examined) {
        this.examined = examined;
    }

    @Override
    public String toString() {
        return "IntelligentCheck{" +
                "exam_customer=" + exam_customer +
                ", unexamined=" + unexamined +
                ", examined=" + examined +
                '}';
    }
}
