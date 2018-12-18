package com.chengsheng.cala.htcm.protocol;

import java.util.Arrays;

public class AppointmentBody {

    private int customer_id;//下单人的id
    private String reserve_date;
    private int exam_package_id;//套餐id
    private String[] exam_item_ids;//可选项



    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getExam_package_id() {
        return exam_package_id;
    }

    public void setExam_package_id(int exam_package_id) {
        this.exam_package_id = exam_package_id;
    }

    public String[] getExam_item_ids() {
        return exam_item_ids;
    }

    public void setExam_item_ids(String[] exam_item_ids) {
        this.exam_item_ids = exam_item_ids;
    }

    @Override
    public String toString() {
        return "AppointmentBody{" +
                "customer_id=" + customer_id +
                ", reserve_date='" + reserve_date + '\'' +
                ", exam_package_id=" + exam_package_id +
                ", exam_item_ids=" + Arrays.toString(exam_item_ids) +
                '}';
    }
}
