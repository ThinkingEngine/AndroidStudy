package com.chengsheng.cala.htcm.model.datamodel;

public class ExamItem {

    //检查项目名称
    private String name;
    //项目价格
    private String price;
    //检查目的
    private String exam_purpose;
    //检查注意事项
    private String precautions;
    //检查包含内容
    private String content;

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

    @Override
    public String toString() {
        return "ExamItem{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", exam_purpose='" + exam_purpose + '\'' +
                ", precautions='" + precautions + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
