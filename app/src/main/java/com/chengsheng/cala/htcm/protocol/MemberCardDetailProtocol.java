package com.chengsheng.cala.htcm.protocol;

/**
 * Author: 任和
 * CreateDate: 2018/12/25 2:26 PM
 * Description:
 */
public class MemberCardDetailProtocol {

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
}
