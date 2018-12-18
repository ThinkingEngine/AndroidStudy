package com.chengsheng.cala.htcm.protocol.childmodela;

import com.chengsheng.cala.htcm.protocol.Pagination;

import java.util.List;

public class MessageList {

    private List<MessageItem> items;
    private Pagination meta;

    public List<MessageItem> getItems() {
        return items;
    }

    public void setItems(List<MessageItem> items) {
        this.items = items;
    }

    public Pagination getMeta() {
        return meta;
    }

    public void setMeta(Pagination meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "MessageList{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }
}
