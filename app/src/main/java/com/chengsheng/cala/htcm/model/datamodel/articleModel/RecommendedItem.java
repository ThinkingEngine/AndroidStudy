package com.chengsheng.cala.htcm.model.datamodel.articleModel;

import java.io.Serializable;

public class RecommendedItem implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private int id;
    private boolean is_online;
    private String article_type_name;
    private String title;
    private String cover_photo_path;
    private int display_order;
    private int shared_num;
    private int basic_read_num;
    private int current_read_num;
    private int basic_collected_num;
    private int current_collected_num;
    private String  online_at;
    private String offline_at;
    private String url;
    private boolean is_recommended;
    private String remark;
    private String operator;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getArticle_type_name() {
        return article_type_name;
    }

    public void setArticle_type_name(String article_type_name) {
        this.article_type_name = article_type_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_photo_path() {
        return cover_photo_path;
    }

    public void setCover_photo_path(String cover_photo_path) {
        this.cover_photo_path = cover_photo_path;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getShared_num() {
        return shared_num;
    }

    public void setShared_num(int shared_num) {
        this.shared_num = shared_num;
    }

    public int getBasic_read_num() {
        return basic_read_num;
    }

    public void setBasic_read_num(int basic_read_num) {
        this.basic_read_num = basic_read_num;
    }

    public int getCurrent_read_num() {
        return current_read_num;
    }

    public void setCurrent_read_num(int current_read_num) {
        this.current_read_num = current_read_num;
    }

    public int getBasic_collected_num() {
        return basic_collected_num;
    }

    public void setBasic_collected_num(int basic_collected_num) {
        this.basic_collected_num = basic_collected_num;
    }

    public int getCurrent_collected_num() {
        return current_collected_num;
    }

    public void setCurrent_collected_num(int current_collected_num) {
        this.current_collected_num = current_collected_num;
    }

    public String getOnline_at() {
        return online_at;
    }

    public void setOnline_at(String online_at) {
        this.online_at = online_at;
    }

    public String getOffline_at() {
        return offline_at;
    }

    public void setOffline_at(String offline_at) {
        this.offline_at = offline_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIs_recommended() {
        return is_recommended;
    }

    public void setIs_recommended(boolean is_recommended) {
        this.is_recommended = is_recommended;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "RecommendedItem{" +
                "id=" + id +
                ", is_online=" + is_online +
                ", article_type_name='" + article_type_name + '\'' +
                ", title='" + title + '\'' +
                ", cover_photo_path='" + cover_photo_path + '\'' +
                ", display_order=" + display_order +
                ", shared_num=" + shared_num +
                ", basic_read_num=" + basic_read_num +
                ", current_read_num=" + current_read_num +
                ", basic_collected_num=" + basic_collected_num +
                ", current_collected_num=" + current_collected_num +
                ", online_at='" + online_at + '\'' +
                ", offline_at='" + offline_at + '\'' +
                ", url='" + url + '\'' +
                ", is_recommended=" + is_recommended +
                ", remark='" + remark + '\'' +
                ", operator='" + operator + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
