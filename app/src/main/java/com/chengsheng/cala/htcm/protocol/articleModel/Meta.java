package com.chengsheng.cala.htcm.protocol.articleModel;

import com.chengsheng.cala.htcm.protocol.Pagination;

public class Meta {
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "pagination=" + pagination +
                '}';
    }
}
