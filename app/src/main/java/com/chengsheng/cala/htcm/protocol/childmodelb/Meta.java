package com.chengsheng.cala.htcm.protocol.childmodelb;

import com.chengsheng.cala.htcm.protocol.Organization;

import java.util.List;

public class Meta {
    private ExamCustomer exam_customer;
    private Organization organization;
    private List<String> package_notice;

    public ExamCustomer getExam_customer() {
        return exam_customer;
    }

    public void setExam_customer(ExamCustomer exam_customer) {
        this.exam_customer = exam_customer;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<String> getPackage_notice() {
        return package_notice;
    }

    public void setPackage_notice(List<String> package_notice) {
        this.package_notice = package_notice;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "exam_customer=" + exam_customer +
                ", organization=" + organization +
                ", package_notice=" + package_notice +
                '}';
    }
}
