package com.chengsheng.cala.htcm.model.datamodel.childmodelb;


import com.chengsheng.cala.htcm.model.datamodel.Amount;
import com.chengsheng.cala.htcm.model.datamodel.Customer;
import com.chengsheng.cala.htcm.model.datamodel.Organization;
import com.chengsheng.cala.htcm.model.datamodel.Report;

import java.util.List;

public class UserExamDetail {

    private int id;
    private String name;
    private String order_status;
    private String exam_status;
    private boolean can_autonomous;
    private Customer customer;
    private Amount amount;
    private List<String> notices;
    private List<ExamItem> exam_item;
    private Organization organization;
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

    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        this.notices = notices;
    }

    public List<ExamItem> getExam_item() {
        return exam_item;
    }

    public void setExam_item(List<ExamItem> exam_item) {
        this.exam_item = exam_item;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public boolean isCan_autonomous() {
        return can_autonomous;
    }

    public void setCan_autonomous(boolean can_autonomous) {
        this.can_autonomous = can_autonomous;
    }

    @Override
    public String toString() {
        return "UserExamDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order_status='" + order_status + '\'' +
                ", exam_status='" + exam_status + '\'' +
                ", can_autonomous=" + can_autonomous +
                ", customer=" + customer +
                ", amount=" + amount +
                ", notices=" + notices +
                ", exam_item=" + exam_item +
                ", organization=" + organization +
                ", report=" + report +
                '}';
    }
}
