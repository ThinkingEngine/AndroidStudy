package com.chengsheng.cala.htcm.protocol.ExamReportModel;

public class FirstItem {

    private String examine_saw;
    private int low_limit;
    private int upper_limit;
    private boolean is_exception;
    private int exception_type;

    public String getExamine_saw() {
        return examine_saw;
    }

    public void setExamine_saw(String examine_saw) {
        this.examine_saw = examine_saw;
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

    public boolean isIs_exception() {
        return is_exception;
    }

    public void setIs_exception(boolean is_exception) {
        this.is_exception = is_exception;
    }

    public int getException_type() {
        return exception_type;
    }

    public void setException_type(int exception_type) {
        this.exception_type = exception_type;
    }

    @Override
    public String toString() {
        return "FirstItem{" +
                "examine_saw='" + examine_saw + '\'' +
                ", low_limit=" + low_limit +
                ", upper_limit=" + upper_limit +
                ", is_exception=" + is_exception +
                ", exception_type=" + exception_type +
                '}';
    }
}
