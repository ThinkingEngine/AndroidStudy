package com.chengsheng.cala.htcm.protocol;

public class OrderID {

    private int order_id;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "OrderID{" +
                "order_id=" + order_id +
                '}';
    }
}
