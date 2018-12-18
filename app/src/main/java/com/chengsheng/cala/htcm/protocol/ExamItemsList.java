package com.chengsheng.cala.htcm.protocol;

import java.util.List;

public class ExamItemsList {

    private List<ExamItems> items;

    public List<ExamItems> getItems() {
        return items;
    }

    public void setItems(List<ExamItems> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ExamItemsList{" +
                "items=" + items +
                '}';
    }
}
