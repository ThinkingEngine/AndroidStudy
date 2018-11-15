package com.chengsheng.cala.htcm.model.datamodel;

import java.util.Arrays;

public class ExamAppointment {
    private int id;
    private String name;
    private String price;
    private String applicable_sex_text;
    private String photo_path;
    private int actual_sales_num;
    private String intro;
    private String exam_notice;
    private boolean is_hot;
    private int[] age_group;
    private String[] package_tag;
    private int[] screen_disease_type;
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

    public int[] getAge_group() {
        return age_group;
    }

    public void setAge_group(int[] age_group) {
        this.age_group = age_group;
    }

    public String[] getPackage_tag() {
        return package_tag;
    }

    public void setPackage_tag(String[] package_tag) {
        this.package_tag = package_tag;
    }

    public int[] getScreen_disease_type() {
        return screen_disease_type;
    }

    public void setScreen_disease_type(int[] screen_disease_type) {
        this.screen_disease_type = screen_disease_type;
    }

    public boolean isPc_deleted() {
        return pc_deleted;
    }

    public void setPc_deleted(boolean pc_deleted) {
        this.pc_deleted = pc_deleted;
    }

    @Override
    public String toString() {
        return "ExamAppointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", applicable_sex_text='" + applicable_sex_text + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", actual_sales_num=" + actual_sales_num +
                ", intro='" + intro + '\'' +
                ", exam_notice='" + exam_notice + '\'' +
                ", is_hot=" + is_hot +
                ", age_group=" + Arrays.toString(age_group) +
                ", package_tag=" + Arrays.toString(package_tag) +
                ", screen_disease_type=" + Arrays.toString(screen_disease_type) +
                ", pc_deleted=" + pc_deleted +
                '}';
    }
}
