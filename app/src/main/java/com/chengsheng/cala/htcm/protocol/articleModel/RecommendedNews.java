package com.chengsheng.cala.htcm.protocol.articleModel;

import java.util.List;

public class RecommendedNews {

    private List<RecommendedItem> items;
    private Meta meta;

    public List<RecommendedItem> getItems() {
        return items;
    }

    public void setItems(List<RecommendedItem> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "RecommendedNews{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }
}
