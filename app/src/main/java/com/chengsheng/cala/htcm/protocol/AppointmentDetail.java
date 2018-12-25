package com.chengsheng.cala.htcm.protocol;

import com.chengsheng.cala.htcm.protocol.childmodelb.ExamItem;

import java.io.Serializable;
import java.util.List;

public class AppointmentDetail implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private int id;//
    private String name;//
    private String price;//
    private String applicable_sex;//
    private String cover_photo;//
    private String banner_photo;//
    private int current_sales_num;//
    private String intro;//
    private String exam_notice;//
    private boolean is_hot;//
    private String reserve_notice;
    private List<PackageTag> package_tag;//
    private List<DiseaseType> screen_disease_type;//
    private List<com.chengsheng.cala.htcm.protocol.childmodelb.ExamItem> exam_items;//
    private Organization organization;//

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getBanner_photo() {
        return banner_photo;
    }

    public void setBanner_photo(String banner_photo) {
        this.banner_photo = banner_photo;
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

    public List<DiseaseType> getScreen_disease_type() {
        return screen_disease_type;
    }

    public void setScreen_disease_type(List<DiseaseType> screen_disease_type) {
        this.screen_disease_type = screen_disease_type;
    }

    public List<ExamItem> getExam_items() {
        return exam_items;
    }

    public void setExam_items(List<ExamItem> exam_items) {
        this.exam_items = exam_items;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getReserve_notice() {
        return reserve_notice;
    }

    public void setReserve_notice(String reserve_notice) {
        this.reserve_notice = reserve_notice;
    }

    @Override
    public String toString() {
        return "AppointmentDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", applicable_sex='" + applicable_sex + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                ", banner_photo='" + banner_photo + '\'' +
                ", current_sales_num=" + current_sales_num +
                ", intro='" + intro + '\'' +
                ", exam_notice='" + exam_notice + '\'' +
                ", is_hot=" + is_hot +
                ", package_tag=" + package_tag +
                ", screen_disease_type=" + screen_disease_type +
                ", exam_items=" + exam_items +
                ", organization=" + organization +
                '}';
    }
}
