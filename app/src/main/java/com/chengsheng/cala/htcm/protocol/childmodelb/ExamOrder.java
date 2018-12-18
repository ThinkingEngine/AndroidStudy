package com.chengsheng.cala.htcm.protocol.childmodelb;

import com.chengsheng.cala.htcm.protocol.articleModel.Meta;

import java.util.List;

public class ExamOrder {
    private List<ExamOrderItem> items;
    private Meta meta;

    public List<ExamOrderItem> getItems() {
        return items;
    }

    public void setItems(List<ExamOrderItem> items) {
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
        return "ExamOrder{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }
}
