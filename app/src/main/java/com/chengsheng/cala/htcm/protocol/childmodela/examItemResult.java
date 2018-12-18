package com.chengsheng.cala.htcm.protocol.childmodela;

import java.io.Serializable;
import java.util.List;

public class examItemResult implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private int check_item_id;
    private String name;
    private int exception_count;
    private String summary;
    private String doctor_sign;
    private int type;
    private List<singleItemResult> single_item_result;

    public int getCheck_item_id() {
        return check_item_id;
    }

    public void setCheck_item_id(int check_item_id) {
        this.check_item_id = check_item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getException_count() {
        return exception_count;
    }

    public void setException_count(int exception_count) {
        this.exception_count = exception_count;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDoctor_sign() {
        return doctor_sign;
    }

    public void setDoctor_sign(String doctor_sign) {
        this.doctor_sign = doctor_sign;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<singleItemResult> getSingle_item_result() {
        return single_item_result;
    }

    public void setSingle_item_result(List<singleItemResult> single_item_result) {
        this.single_item_result = single_item_result;
    }

    @Override
    public String toString() {
        return "examItemResult{" +
                "check_item_id=" + check_item_id +
                ", name='" + name + '\'' +
                ", exception_count=" + exception_count +
                ", summary='" + summary + '\'' +
                ", doctor_sign='" + doctor_sign + '\'' +
                ", type=" + type +
                ", single_item_result=" + single_item_result +
                '}';
    }
}
