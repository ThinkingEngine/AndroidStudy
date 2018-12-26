package com.chengsheng.cala.htcm.protocol;

import java.util.List;

/**
 * Author: 任和
 * CreateDate: 2018/12/26 5:30 PM
 * Description:
 */
public class TradeRecordProtocol {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 23
         * card_id : 13
         * change_type : 1
         * change_money : 100.00
         * change_date : 2018-12-26 03:42:14
         * status : 1
         */

        private int id;
        private int card_id;
        private int change_type;
        private String change_money;
        private String change_date;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCard_id() {
            return card_id;
        }

        public void setCard_id(int card_id) {
            this.card_id = card_id;
        }

        public int getChange_type() {
            return change_type;
        }

        public void setChange_type(int change_type) {
            this.change_type = change_type;
        }

        public String getChange_money() {
            return change_money;
        }

        public void setChange_money(String change_money) {
            this.change_money = change_money;
        }

        public String getChange_date() {
            return change_date;
        }

        public void setChange_date(String change_date) {
            this.change_date = change_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
