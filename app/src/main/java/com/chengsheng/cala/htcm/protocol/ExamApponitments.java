package com.chengsheng.cala.htcm.protocol;

import java.util.List;

public class ExamApponitments {

    private List<ExamAppointment> items;
    private Pagination meta;


    public List<ExamAppointment> getItems() {
        return items;
    }

    public void setItems(List<ExamAppointment> items) {
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
        return "ExamApponitments{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }
}
