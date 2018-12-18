package com.chengsheng.cala.htcm.protocol;

public class ExamReportItem {

    private String id;
    private int orderId;
    private String name;
    private String issued_date;
    private String path;
    private boolean isSelect = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(String issued_date) {
        this.issued_date = issued_date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "ExamReportItem{" +
                "id='" + id + '\'' +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", issued_date='" + issued_date + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
