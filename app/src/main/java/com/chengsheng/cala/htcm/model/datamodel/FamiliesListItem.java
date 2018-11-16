package com.chengsheng.cala.htcm.model.datamodel;

import java.io.Serializable;

public class FamiliesListItem implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private int id;
    private String mobile;
    private String fullname;
    private String id_card_no;
    private boolean is_auth;
    private String owner_relationship;
    private String avatar_path;
    private String health_card_no;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId_card_no() {
        return id_card_no;
    }

    public void setId_card_no(String id_card_no) {
        this.id_card_no = id_card_no;
    }

    public boolean isIs_auth() {
        return is_auth;
    }

    public void setIs_auth(boolean is_auth) {
        this.is_auth = is_auth;
    }

    public String getOwner_relationship() {
        return owner_relationship;
    }

    public void setOwner_relationship(String owner_relationship) {
        this.owner_relationship = owner_relationship;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getHealth_card_no() {
        return health_card_no;
    }

    public void setHealth_card_no(String health_card_no) {
        this.health_card_no = health_card_no;
    }

    @Override
    public String toString() {
        return "FamiliesListItem{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", fullname='" + fullname + '\'' +
                ", id_card_no='" + id_card_no + '\'' +
                ", is_auth=" + is_auth +
                ", owner_relationship='" + owner_relationship + '\'' +
                ", avatar_path='" + avatar_path + '\'' +
                ", health_card_no='" + health_card_no + '\'' +
                '}';
    }
}
