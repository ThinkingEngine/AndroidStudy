package com.chengsheng.cala.htcm.protocol;

import java.io.Serializable;

public class Organization implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;
    //医院名称
    private String name;
    //医院地址
    private String address;
    //医院电话
    private String contact_telephone;
    //医院资质
    private String qualification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_telephone() {
        return contact_telephone;
    }

    public void setContact_telephone(String contact_telephone) {
        this.contact_telephone = contact_telephone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact_telephone='" + contact_telephone + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}
