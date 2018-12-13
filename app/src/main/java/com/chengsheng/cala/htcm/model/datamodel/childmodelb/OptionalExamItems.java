package com.chengsheng.cala.htcm.model.datamodel.childmodelb;

import com.chengsheng.cala.htcm.model.datamodel.ExamItem;

import java.util.List;

public class OptionalExamItems {

    private String name;
    private String price;
    private List<ExamItem> exam_item_charges;

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

    public List<ExamItem> getExam_item_charges() {
        return exam_item_charges;
    }

    public void setExam_item_charges(List<ExamItem> exam_item_charges) {
        this.exam_item_charges = exam_item_charges;
    }

    @Override
    public String toString() {
        return "OptionalExamItems{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", exam_item_charges=" + exam_item_charges +
                '}';
    }
}
