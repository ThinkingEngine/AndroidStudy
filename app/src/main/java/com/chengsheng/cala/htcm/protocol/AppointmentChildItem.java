package com.chengsheng.cala.htcm.protocol;

public class AppointmentChildItem {

    private int id;
    private String name;
    private String price;
    private String exam_purpose;
    private String precautions;
    private String content;
    private boolean pc_deleted;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExam_purpose() {
        return exam_purpose;
    }

    public void setExam_purpose(String exam_purpose) {
        this.exam_purpose = exam_purpose;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPc_deleted() {
        return pc_deleted;
    }

    public void setPc_deleted(boolean pc_deleted) {
        this.pc_deleted = pc_deleted;
    }

    @Override
    public String toString() {
        return "AppointmentChildItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", exam_purpose='" + exam_purpose + '\'' +
                ", precautions='" + precautions + '\'' +
                ", content='" + content + '\'' +
                ", pc_deleted=" + pc_deleted +
                '}';
    }
}
