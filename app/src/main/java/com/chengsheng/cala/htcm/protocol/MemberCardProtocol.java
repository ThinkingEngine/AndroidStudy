package com.chengsheng.cala.htcm.protocol;

import java.util.List;

/**
 * Author: 任和
 * CreateDate: 2018/12/25 10:08 AM
 * Description:
 */
public class MemberCardProtocol {

    /**
     * items : [{"id":3,"card_number":"14516802209681058","balance":"323.50","bind_at":"2018-12-24 16:09:48"}]
     * meta : {"pagination":{"total":1,"count":1,"per_page":15,"current_page":1,"total_pages":1}}
     */

    private MetaBean meta;
    private List<ItemsBean> items;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class MetaBean {
        /**
         * pagination : {"total":1,"count":1,"per_page":15,"current_page":1,"total_pages":1}
         */

        private PaginationBean pagination;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public static class PaginationBean {
            /**
             * total : 1
             * count : 1
             * per_page : 15
             * current_page : 1
             * total_pages : 1
             */

            private int total;
            private int count;
            private int per_page;
            private int current_page;
            private int total_pages;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getPer_page() {
                return per_page;
            }

            public void setPer_page(int per_page) {
                this.per_page = per_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getTotal_pages() {
                return total_pages;
            }

            public void setTotal_pages(int total_pages) {
                this.total_pages = total_pages;
            }
        }
    }

    public static class ItemsBean {
        /**
         * id : 3
         * card_number : 14516802209681058
         * balance : 323.50
         * bind_at : 2018-12-24 16:09:48
         */

        private int id;
        private String card_number;
        private String balance;
        private String bind_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBind_at() {
            return bind_at;
        }

        public void setBind_at(String bind_at) {
            this.bind_at = bind_at;
        }
    }
}
