package com.chengsheng.cala.htcm.protocol.childmodela;

import java.util.List;

public class Healthy_advice {
    private List<HAItem> healthy_advice;

    public List<HAItem> getItems() {
        return healthy_advice;
    }

    public void setItems(List<HAItem> items) {
        this.healthy_advice = items;
    }

    @Override
    public String toString() {
        return "Healthy_advice{" +
                "items=" + healthy_advice +
                '}';
    }
}
