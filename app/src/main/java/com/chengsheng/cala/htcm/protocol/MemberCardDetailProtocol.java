package com.chengsheng.cala.htcm.protocol;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: 任和
 * CreateDate: 2018/12/25 2:26 PM
 * Description:
 */
public class MemberCardDetailProtocol implements Parcelable {

    /**
     * id : 3
     * card_number : 14516802209681058
     * balance : 323.50
     * bind_at : 2018-12-24 16:09:48
     * bind_mobile : 15328078093
     */

    private int id;
    private String card_number;
    private String balance;
    private String bind_at;
    private String bind_mobile;

    protected MemberCardDetailProtocol(Parcel in) {
        id = in.readInt();
        card_number = in.readString();
        balance = in.readString();
        bind_at = in.readString();
        bind_mobile = in.readString();
    }

    public static final Creator<MemberCardDetailProtocol> CREATOR = new Creator<MemberCardDetailProtocol>() {
        @Override
        public MemberCardDetailProtocol createFromParcel(Parcel in) {
            return new MemberCardDetailProtocol(in);
        }

        @Override
        public MemberCardDetailProtocol[] newArray(int size) {
            return new MemberCardDetailProtocol[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBind_at() {
        return bind_at;
    }

    public void setBind_at(String bind_at) {
        this.bind_at = bind_at;
    }

    public String getBind_mobile() {
        return bind_mobile;
    }

    public void setBind_mobile(String bind_mobile) {
        this.bind_mobile = bind_mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(card_number);
        parcel.writeString(balance);
        parcel.writeString(bind_at);
        parcel.writeString(bind_mobile);
    }
}
