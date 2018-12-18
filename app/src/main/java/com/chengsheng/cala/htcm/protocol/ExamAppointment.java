package com.chengsheng.cala.htcm.protocol;

import java.util.List;

public class ExamAppointment {
    private int id;//
    private String name;//
    private String price;//
    private String applicable_sex;//
    private String photo_path;
    private int display_order;//
    private int current_sales_num;//
    private String intro;
    private String exam_notice;
    private boolean is_hot;
    private List<PackageTag> package_tag;
    private boolean pc_deleted;//


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

    public String getApplicable_sex() {
        return applicable_sex;
    }

    public void setApplicable_sex(String applicable_sex) {
        this.applicable_sex = applicable_sex;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getCurrent_sales_num() {
        return current_sales_num;
    }

    public void setCurrent_sales_num(int current_sales_num) {
        this.current_sales_num = current_sales_num;
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

    public List<PackageTag> getPackage_tag() {
        return package_tag;
    }

    public void setPackage_tag(List<PackageTag> package_tag) {
        this.package_tag = package_tag;
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
                ", applicable_sex='" + applicable_sex + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", display_order=" + display_order +
                ", current_sales_num=" + current_sales_num +
                ", intro='" + intro + '\'' +
                ", exam_notice='" + exam_notice + '\'' +
                ", is_hot=" + is_hot +
                ", package_tag=" + package_tag +
                ", pc_deleted=" + pc_deleted +
                '}';
    }
}
