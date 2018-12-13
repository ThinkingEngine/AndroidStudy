package com.chengsheng.cala.htcm.model.datamodel.childmodela;

import java.io.Serializable;
import java.util.Arrays;

public class singleItemResult implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private String name;
    private String examine_saws;
    private String measure_unit;
    private int type;
    private boolean is_exception;
    private int low_limit;
    private int upper_limit;
    private String[] images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamine_saws() {
        return examine_saws;
    }

    public void setExamine_saws(String examine_saws) {
        this.examine_saws = examine_saws;
    }

    public String getMeasure_unit() {
        return measure_unit;
    }

    public void setMeasure_unit(String measure_unit) {
        this.measure_unit = measure_unit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIs_exception() {
        return is_exception;
    }

    public void setIs_exception(boolean is_exception) {
        this.is_exception = is_exception;
    }

    public int getLow_limit() {
        return low_limit;
    }

    public void setLow_limit(int low_limit) {
        this.low_limit = low_limit;
    }

    public int getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(int upper_limit) {
        this.upper_limit = upper_limit;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "singleItemResult{" +
                "name='" + name + '\'' +
                ", examine_saws='" + examine_saws + '\'' +
                ", measure_unit='" + measure_unit + '\'' +
                ", check_item_type='" + type + '\'' +
                ", is_exception=" + is_exception +
                ", low_limit=" + low_limit +
                ", upper_limit=" + upper_limit +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
