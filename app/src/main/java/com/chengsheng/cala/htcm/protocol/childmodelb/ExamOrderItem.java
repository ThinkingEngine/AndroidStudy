package com.chengsheng.cala.htcm.protocol.childmodelb;


import com.chengsheng.cala.htcm.protocol.Amount;
import com.chengsheng.cala.htcm.protocol.Customer;

import java.util.List;

public class ExamOrderItem {
    private int id;
    private List<PackageAndOptional> package_and_optional;
    private String order_status;
    private Customer customer;
    private Amount amount;
    private boolean is_commented;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PackageAndOptional> getPackage_and_optional() {
        return package_and_optional;
    }

    public void setPackage_and_optional(List<PackageAndOptional> package_and_optional) {
        this.package_and_optional = package_and_optional;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
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

    public boolean isIs_commented() {
        return is_commented;
    }

    public void setIs_commented(boolean is_commented) {
        this.is_commented = is_commented;
    }

    @Override
    public String toString() {
        return "ExamOrderItem{" +
                "id=" + id +
                ", package_and_optional=" + package_and_optional +
                ", order_status='" + order_status + '\'' +
                ", customer=" + customer +
                ", amount=" + amount +
                ", is_commented=" + is_commented +
                '}';
    }
}
