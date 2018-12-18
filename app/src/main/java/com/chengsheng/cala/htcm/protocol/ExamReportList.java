package com.chengsheng.cala.htcm.protocol;

import java.util.List;

public class ExamReportList {
    private List<ExamReportItem> items;

    public List<ExamReportItem> getItems() {
        return items;
    }

    public void setItems(List<ExamReportItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ExamReportList{" +
                "items=" + items +
                '}';
    }
}
