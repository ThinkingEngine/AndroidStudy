package com.chengsheng.cala.htcm.model.datamodel.ExamReportModel;

import java.util.List;

public class CompareItem {
    private String check_item_name;
    private int type;
    private List<SingleItem> single_item;
    private boolean condition = true;

    public String getCheck_item_name() {
        return check_item_name;
    }

    public void setCheck_item_name(String check_item_name) {
        this.check_item_name = check_item_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SingleItem> getSingle_item() {
        return single_item;
    }

    public void setSingle_item(List<SingleItem> single_item) {
        this.single_item = single_item;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "CompareItem{" +
                "check_item_name='" + check_item_name + '\'' +
                ", type=" + type +
                ", single_item=" + single_item +
                '}';
    }
}
