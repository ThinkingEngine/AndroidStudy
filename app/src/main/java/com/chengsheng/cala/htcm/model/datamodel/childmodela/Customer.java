package com.chengsheng.cala.htcm.model.datamodel.childmodela;

public class Customer {

    private String name;
    private Registration registration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", registration=" + registration +
                '}';
    }
}
