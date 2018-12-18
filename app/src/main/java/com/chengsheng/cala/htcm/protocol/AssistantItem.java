package com.chengsheng.cala.htcm.protocol;

import com.chengsheng.cala.htcm.protocol.childmodela.RecommendExamItem;

public class AssistantItem {

    private Customer customer;
    private Order order;
    private Report report;
    private RecommendExamItem recommend_exam_item;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public RecommendExamItem getRecommend_exam_item() {
        return recommend_exam_item;
    }

    public void setRecommend_exam_item(RecommendExamItem recommend_exam_item) {
        this.recommend_exam_item = recommend_exam_item;
    }

    @Override
    public String toString() {
        return "AssistantItem{" +
                "customer=" + customer +
                ", order=" + order +
                ", report=" + report +
                ", recommend_exam_item=" + recommend_exam_item +
                '}';
    }
}
