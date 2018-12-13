package com.chengsheng.cala.htcm.model.datamodel.childmodelb;

public class ExamCustomer {
    private String name;
    private String sex;
    private int age;
    private examOrRegistration exam_or_registration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public examOrRegistration getExam_or_registration() {
        return exam_or_registration;
    }

    public void setExam_or_registration(examOrRegistration exam_or_registration) {
        this.exam_or_registration = exam_or_registration;
    }

    @Override
    public String toString() {
        return "ExamCustomer{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", exam_or_registration=" + exam_or_registration +
                '}';
    }
}
