package com.chengsheng.cala.htcm.protocol;

import java.util.List;

/**
 * Author: 任和
 * CreateDate: 2018/12/19 6:04 PM
 * Description:
 */
public class RecommendProProtocol {

    /**
     * items : [{"id":1,"name":"特色服务","recommend":[{"id":3,"name":"特色中医助孕管理","price":null,"current_sales_num":513,"intro":"针对长期备孕不成功或为孕育宝宝做准备的人群，通过中医整体调养，辨证施治。","banner_photo":"http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/ddabbf31-6a0e-4a38-9e0c-27565b73223b.png","cover_photo":"http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/c73c6297-cb57-4d72-b0ae-2b8cd79d4649.png"}]},{"id":2,"name":"医疗美容","recommend":[{"id":12,"name":"测试医疗美容新增1","price":null,"current_sales_num":213,"intro":"测试医疗美容新增","banner_photo":"http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/64efbd87-846f-4355-8b91-dff8b0ffb3aa.png","cover_photo":"http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/64efbd87-846f-4355-8b91-dff8b0ffb3aa.png"}]}]
     * meta : {"pagination":{"total":2,"count":2,"per_page":15,"current_page":1,"total_pages":1}}
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
         * pagination : {"total":2,"count":2,"per_page":15,"current_page":1,"total_pages":1}
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
             * total : 2
             * count : 2
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
         * id : 1
         * name : 特色服务
         * recommend : [{"id":3,"name":"特色中医助孕管理","price":null,"current_sales_num":513,"intro":"针对长期备孕不成功或为孕育宝宝做准备的人群，通过中医整体调养，辨证施治。","banner_photo":"http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/ddabbf31-6a0e-4a38-9e0c-27565b73223b.png","cover_photo":"http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/c73c6297-cb57-4d72-b0ae-2b8cd79d4649.png"}]
         */

        private int id;
        private String name;
        private List<RecommendBean> recommend;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        public static class RecommendBean {
            /**
             * id : 3
             * name : 特色中医助孕管理
             * price : null
             * current_sales_num : 513
             * intro : 针对长期备孕不成功或为孕育宝宝做准备的人群，通过中医整体调养，辨证施治。
             * banner_photo : http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/ddabbf31-6a0e-4a38-9e0c-27565b73223b.png
             * cover_photo : http://api.peis-mobile.zz-tech.com.cn:85/storage/support_service/c73c6297-cb57-4d72-b0ae-2b8cd79d4649.png
             */

            private int id;
            private String name;
            private Object price;
            private int current_sales_num;
            private String intro;
            private String banner_photo;
            private String cover_photo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public int getCurrent_sales_num() {
                return current_sales_num;
            }

            public void setCurrent_sales_num(int current_sales_num) {
                this.current_sales_num = current_sales_num;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getBanner_photo() {
                return banner_photo;
            }

            public void setBanner_photo(String banner_photo) {
                this.banner_photo = banner_photo;
            }

            public String getCover_photo() {
                return cover_photo;
            }

            public void setCover_photo(String cover_photo) {
                this.cover_photo = cover_photo;
            }
        }
    }
}
