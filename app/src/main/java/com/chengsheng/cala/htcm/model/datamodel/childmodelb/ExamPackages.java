package com.chengsheng.cala.htcm.model.datamodel.childmodelb;

import java.util.List;

public class ExamPackages {

    private int id;
    private String name;
    private String price;
    private String notice;
    private List<ExamItem> exam_item_charges;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<ExamItem> getExam_item_charges() {
        return exam_item_charges;
    }

    public void setExam_item_charges(List<ExamItem> exam_item_charges) {
        this.exam_item_charges = exam_item_charges;
    }

    @Override
    public String toString() {
        return "ExamPackages{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", notice='" + notice + '\'' +
                ", exam_item_charges=" + exam_item_charges +
                '}';
    }
}
