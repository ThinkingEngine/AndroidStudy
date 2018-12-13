package com.chengsheng.cala.htcm.model.datamodel.childmodelb;

import com.chengsheng.cala.htcm.model.datamodel.Amount;
import com.chengsheng.cala.htcm.model.datamodel.Customer;
import com.chengsheng.cala.htcm.model.datamodel.Organization;

import java.util.List;

public class ExamOrderDetail {

    private int id;
    private String status;
    private Customer customer;
    private String exam_status;
    private List<ExamPackages> exam_packages;
    private OptionalExamItems optional_exam_items;
    private Amount amount;
    private Organization organization;
    private String created_at;
    private boolean is_commented;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    public List<ExamPackages> getExam_packages() {
        return exam_packages;
    }

    public void setExam_packages(List<ExamPackages> exam_packages) {
        this.exam_packages = exam_packages;
    }

    public OptionalExamItems getOptional_exam_items() {
        return optional_exam_items;
    }

    public void setOptional_exam_items(OptionalExamItems optional_exam_items) {
        this.optional_exam_items = optional_exam_items;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isIs_commented() {
        return is_commented;
    }

    public void setIs_commented(boolean is_commented) {
        this.is_commented = is_commented;
    }

    @Override
    public String toString() {
        return "ExamOrderDetail{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", exam_status='" + exam_status + '\'' +
                ", exam_packages=" + exam_packages +
                ", optional_exam_items=" + optional_exam_items +
                ", amount=" + amount +
                ", organization=" + organization +
                ", created_at='" + created_at + '\'' +
                ", is_commented=" + is_commented +
                '}';
    }
}
