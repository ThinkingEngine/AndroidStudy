package com.chengsheng.cala.htcm.protocol.childmodela;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {

    private int id;
    private String username;
    private String phone_number;
    private String name;
    private String avatar_url;
    private String nickname;
    private String id_card_no;

    protected UserInfo(Parcel in) {
        id = in.readInt();
        username = in.readString();
        phone_number = in.readString();
        name = in.readString();
        avatar_url = in.readString();
        nickname = in.readString();
        id_card_no = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(phone_number);
        dest.writeString(name);
        dest.writeString(avatar_url);
        dest.writeString(nickname);
        dest.writeString(id_card_no);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

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
}
