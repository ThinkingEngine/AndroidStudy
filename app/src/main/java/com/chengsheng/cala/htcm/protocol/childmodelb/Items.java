package com.chengsheng.cala.htcm.protocol.childmodelb;

import java.util.List;

public class Items {
    private List<ExamItem> items;

    public List<ExamItem> getItems() {
        return items;
    }

    public void setItems(List<ExamItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Items{" +
                "items=" + items +
                '}';
    }
}
