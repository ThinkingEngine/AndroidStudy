package com.chengsheng.cala.htcm.protocol;

public class DiseaseType {
    private String name;
    private String photo_path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    @Override
    public String toString() {
        return "DiseaseType{" +
                "name='" + name + '\'' +
                ", photo_path='" + photo_path + '\'' +
                '}';
    }
}
