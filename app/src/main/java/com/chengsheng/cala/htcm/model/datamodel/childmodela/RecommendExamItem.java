package com.chengsheng.cala.htcm.model.datamodel.childmodela;

public class RecommendExamItem {

    private int exam_item_id;
    private String name;
    private int payment_status;
    private int exam_status;
    private String content;
    private String exam_purpose;
    private String notice;
    private boolean before_meal;
    private int wait_person;
    private String exam_address;

    public int getExam_item_id() {
        return exam_item_id;
    }

    public void setExam_item_id(int exam_item_id) {
        this.exam_item_id = exam_item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public int getExam_status() {
        return exam_status;
    }

    public void setExam_status(int exam_status) {
        this.exam_status = exam_status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExam_purpose() {
        return exam_purpose;
    }

    public void setExam_purpose(String exam_purpose) {
        this.exam_purpose = exam_purpose;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isBefore_meal() {
        return before_meal;
    }

    public void setBefore_meal(boolean before_meal) {
        this.before_meal = before_meal;
    }

    public int getWait_person() {
        return wait_person;
    }

    public void setWait_person(int wait_person) {
        this.wait_person = wait_person;
    }

    public String getExam_address() {
        return exam_address;
    }

    public void setExam_address(String exam_address) {
        this.exam_address = exam_address;
    }

    @Override
    public String toString() {
        return "RecommendExamItem{" +
                "exam_item_id=" + exam_item_id +
                ", name='" + name + '\'' +
                ", payment_status=" + payment_status +
                ", exam_status=" + exam_status +
                ", content='" + content + '\'' +
                ", exam_purpose='" + exam_purpose + '\'' +
                ", notice='" + notice + '\'' +
                ", before_meal=" + before_meal +
                ", wait_person=" + wait_person +
                ", exam_address='" + exam_address + '\'' +
                '}';
    }
}
