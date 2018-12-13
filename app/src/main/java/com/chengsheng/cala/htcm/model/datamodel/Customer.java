package com.chengsheng.cala.htcm.model.datamodel;

public class Customer {

    private int id;
    private String name;
    private String mobile;
    private String avatar;
    private ReservationOrRegistration reservation_or_registration;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ReservationOrRegistration getReservation_or_registration() {
        return reservation_or_registration;
    }

    public void setReservation_or_registration(ReservationOrRegistration reservation_or_registration) {
        this.reservation_or_registration = reservation_or_registration;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", reservation_or_registration=" + reservation_or_registration +
                '}';
    }
}
