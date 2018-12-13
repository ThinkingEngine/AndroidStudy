package com.chengsheng.cala.htcm.model.datamodel;

import java.util.List;

public class AssistantList {
    private List<AssistantItem> items;

    public List<AssistantItem> getItems() {
        return items;
    }

    public void setItems(List<AssistantItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AssistantList{" +
                "items=" + items +
                '}';
    }
}
