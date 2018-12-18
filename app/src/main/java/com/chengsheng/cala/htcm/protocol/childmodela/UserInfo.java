package com.chengsheng.cala.htcm.protocol.childmodela;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private int id;
    private String username;
    private String phone_number;
    private String name;
    private String avatar_url;
    private String nickname;
    private String id_card_no;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId_card_no() {
        return id_card_no;
    }

    public void setId_card_no(String id_card_no) {
        this.id_card_no = id_card_no;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", name='" + name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", id_card_no='" + id_card_no + '\'' +
                '}';
    }
}
