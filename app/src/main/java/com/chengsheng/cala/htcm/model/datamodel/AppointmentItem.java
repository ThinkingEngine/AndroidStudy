package com.chengsheng.cala.htcm.model.datamodel;

import java.util.List;

public class AppointmentItem {

    private int id;
    private String name;
    private String explain;
    private List<AppointmentChildItem> exam_item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<AppointmentChildItem> getExam_item() {
        return exam_item;
    }

    public void setExam_item(List<AppointmentChildItem> exam_item) {
        this.exam_item = exam_item;
    }

    @Override
    public String toString() {
        return "AppointmentItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", explain='" + explain + '\'' +
                ", exam_item=" + exam_item +
                '}';
    }
}
