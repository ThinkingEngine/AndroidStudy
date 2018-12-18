package com.chengsheng.cala.htcm.protocol;

public class Report {
    private boolean issued;

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return "Report{" +
                "issued=" + issued +
                '}';
    }
}
