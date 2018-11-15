package com.chengsheng.cala.htcm.model.datamodel;

import java.util.List;

public class FamiliesList {
    private List<FamiliesListItem> items;

    public List<FamiliesListItem> getItems() {
        return items;
    }

    public void setItems(List<FamiliesListItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "FamiliesList{" +
                "items=" + items +
                '}';
    }
}
