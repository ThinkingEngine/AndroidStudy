package com.chengsheng.cala.htcm.model.datamodel.ExamReportModel;

import java.util.List;

public class CompareModel {
    private List<CompareItem> items;

    public List<CompareItem> getItems() {
        return items;
    }

    public void setItems(List<CompareItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CompareModel{" +
                "items=" + items +
                '}';
    }
}
