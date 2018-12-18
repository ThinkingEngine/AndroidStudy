package com.chengsheng.cala.htcm.protocol.articleModel;

import java.util.List;

public class NewsTitles {

    private List<NewsTitlesItem> items;
    private Meta meta;

    public List<NewsTitlesItem> getItems() {
        return items;
    }

    public void setItems(List<NewsTitlesItem> items) {
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
        return "NewsTitles{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }
}
