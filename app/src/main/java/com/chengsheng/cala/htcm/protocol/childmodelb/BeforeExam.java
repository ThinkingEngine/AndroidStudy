package com.chengsheng.cala.htcm.protocol.childmodelb;

import java.util.List;

public class BeforeExam {

    private List<ExamItem> items;
    private Meta meta;

    public List<ExamItem> getItems() {
        return items;
    }

    public void setItems(List<ExamItem> items) {
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
        return "BeforeExam{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }
}
