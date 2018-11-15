package com.chengsheng.cala.htcm.model.datamodel;

import java.util.List;

public class AppointmentDetail {

    private int id;
    private String name;
    private String price;
    private String applicable_sex_text;
    private String photo_path;
    private int actual_sales_num;
    private String intro;
    private String exam_notice;
    private boolean is_hot;
    private List<Integer> age_group;
    private List<String> package_tag;
    private List<Integer> screen_disease_type;
    private List<AppointmentItem> optional_exams_item_format;
    private List<ExamItem> items;
    private Organization organization;

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

    public String getApplicable_sex_text() {
        return applicable_sex_text;
    }

    public void setApplicable_sex_text(String applicable_sex_text) {
        this.applicable_sex_text = applicable_sex_text;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public int getActual_sales_num() {
        return actual_sales_num;
    }

    public void setActual_sales_num(int actual_sales_num) {
        this.actual_sales_num = actual_sales_num;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getExam_notice() {
        return exam_notice;
    }

    public void setExam_notice(String exam_notice) {
        this.exam_notice = exam_notice;
    }

    public boolean isIs_hot() {
        return is_hot;
    }

    public void setIs_hot(boolean is_hot) {
        this.is_hot = is_hot;
    }

    public List<Integer> getAge_group() {
        return age_group;
    }

    public void setAge_group(List<Integer> age_group) {
        this.age_group = age_group;
    }

    public List<String> getPackage_tag() {
        return package_tag;
    }

    public void setPackage_tag(List<String> package_tag) {
        this.package_tag = package_tag;
    }

    public List<Integer> getScreen_disease_type() {
        return screen_disease_type;
    }

    public void setScreen_disease_type(List<Integer> screen_disease_type) {
        this.screen_disease_type = screen_disease_type;
    }

    public List<AppointmentItem> getOptional_exams_item_format() {
        return optional_exams_item_format;
    }

    public void setOptional_exams_item_format(List<AppointmentItem> optional_exams_item_format) {
        this.optional_exams_item_format = optional_exams_item_format;
    }

    public List<ExamItem> getItems() {
        return items;
    }

    public void setItems(List<ExamItem> items) {
        this.items = items;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "AppointmentDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", applicable_sex_text='" + applicable_sex_text + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", actual_sales_num=" + actual_sales_num +
                ", intro='" + intro + '\'' +
                ", exam_notice='" + exam_notice + '\'' +
                ", is_hot=" + is_hot +
                ", age_group=" + age_group +
                ", package_tag=" + package_tag +
                ", screen_disease_type=" + screen_disease_type +
                ", optional_exams_item_format=" + optional_exams_item_format +
                ", items=" + items +
                ", organization=" + organization +
                '}';
    }
}
