package com.chengsheng.cala.htcm.model.datamodel.ExamReportModel;

public class SingleItem {

    private String name;
    private String measure_unit;
    private int type;
    private FirstItem first;
    private FirstItem second;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure_unit() {
        return measure_unit;
    }

    public void setMeasure_unit(String measure_unit) {
        this.measure_unit = measure_unit;
    }

    public FirstItem getFirst() {
        return first;
    }

    public void setFirst(FirstItem first) {
        this.first = first;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FirstItem getSecond() {
        return second;
    }

    public void setSecond(FirstItem second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "SingleItem{" +
                "name='" + name + '\'' +
                ", measure_unit='" + measure_unit + '\'' +
                ", type=" + type +
                ", first=" + first +
                ", second=" + second +
                '}';
    }
}
