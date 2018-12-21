package com.chengsheng.cala.htcm.protocol;

import java.util.List;

/**
 * Author: 任和
 * CreateDate: 2018/12/21 4:58 PM
 * Description:
 */
public class DoctorProtocol {

    /**
     * items : [{"id":7,"name":"陆华","dept":[{"id":2,"name":"测试科室02","photo_path":"http://192.168.1.119:8000/storage/support_service/976e6165-0ce6-4ca9-b07a-ce39a6840012.","intro":"这是一个测试科室，编号为02","display_order":2,"is_enable":true,"operator":"中卓-助手","exam_items":[{"name":"健康体检","intro":"个人体检：各年龄段人群体检、孕育全程体检等\r\n单位体检：入职体检、年度体检、职业健康体检"},{"name":"TTM红外检测","intro":null},{"name":"功能医学检测","intro":null},{"name":"基因检测","intro":null},{"name":"癌症早筛","intro":null}]}],"professional_title":"主任医师·博导","professional_skill":"中医妇科","intro":"毕业于成都中医药大学，医学博士学位，研究员，博士生导师。全国首届百名杰出女中医师、四川省名中医、四川省突贡专家。咨询内容：孕前优生、养生保健、生殖健康。","remark":"","display_order":6,"operator":"中卓-助手","updated_at":"2018-12-04 18:06:57","avatar_path":"","is_enable":true}]
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
         * id : 7
         * name : 陆华
         * dept : [{"id":2,"name":"测试科室02","photo_path":"http://192.168.1.119:8000/storage/support_service/976e6165-0ce6-4ca9-b07a-ce39a6840012.","intro":"这是一个测试科室，编号为02","display_order":2,"is_enable":true,"operator":"中卓-助手","exam_items":[{"name":"健康体检","intro":"个人体检：各年龄段人群体检、孕育全程体检等\r\n单位体检：入职体检、年度体检、职业健康体检"},{"name":"TTM红外检测","intro":null},{"name":"功能医学检测","intro":null},{"name":"基因检测","intro":null},{"name":"癌症早筛","intro":null}]}]
         * professional_title : 主任医师·博导
         * professional_skill : 中医妇科
         * intro : 毕业于成都中医药大学，医学博士学位，研究员，博士生导师。全国首届百名杰出女中医师、四川省名中医、四川省突贡专家。咨询内容：孕前优生、养生保健、生殖健康。
         * remark :
         * display_order : 6
         * operator : 中卓-助手
         * updated_at : 2018-12-04 18:06:57
         * avatar_path :
         * is_enable : true
         */

        private int id;
        private String name;
        private String professional_title;
        private String professional_skill;
        private String intro;
        private String remark;
        private int display_order;
        private String operator;
        private String updated_at;
        private String avatar_path;
        private boolean is_enable;
        private List<DeptBean> dept;

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

        public String getProfessional_title() {
            return professional_title;
        }

        public void setProfessional_title(String professional_title) {
            this.professional_title = professional_title;
        }

        public String getProfessional_skill() {
            return professional_skill;
        }

        public void setProfessional_skill(String professional_skill) {
            this.professional_skill = professional_skill;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getDisplay_order() {
            return display_order;
        }

        public void setDisplay_order(int display_order) {
            this.display_order = display_order;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getAvatar_path() {
            return avatar_path;
        }

        public void setAvatar_path(String avatar_path) {
            this.avatar_path = avatar_path;
        }

        public boolean isIs_enable() {
            return is_enable;
        }

        public void setIs_enable(boolean is_enable) {
            this.is_enable = is_enable;
        }

        public List<DeptBean> getDept() {
            return dept;
        }

        public void setDept(List<DeptBean> dept) {
            this.dept = dept;
        }

        public static class DeptBean {
            /**
             * id : 2
             * name : 测试科室02
             * photo_path : http://192.168.1.119:8000/storage/support_service/976e6165-0ce6-4ca9-b07a-ce39a6840012.
             * intro : 这是一个测试科室，编号为02
             * display_order : 2
             * is_enable : true
             * operator : 中卓-助手
             * exam_items : [{"name":"健康体检","intro":"个人体检：各年龄段人群体检、孕育全程体检等\r\n单位体检：入职体检、年度体检、职业健康体检"},{"name":"TTM红外检测","intro":null},{"name":"功能医学检测","intro":null},{"name":"基因检测","intro":null},{"name":"癌症早筛","intro":null}]
             */

            private int id;
            private String name;
            private String photo_path;
            private String intro;
            private int display_order;
            private boolean is_enable;
            private String operator;
            private List<ExamItemsBean> exam_items;

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

            public String getPhoto_path() {
                return photo_path;
            }

            public void setPhoto_path(String photo_path) {
                this.photo_path = photo_path;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getDisplay_order() {
                return display_order;
            }

            public void setDisplay_order(int display_order) {
                this.display_order = display_order;
            }

            public boolean isIs_enable() {
                return is_enable;
            }

            public void setIs_enable(boolean is_enable) {
                this.is_enable = is_enable;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public List<ExamItemsBean> getExam_items() {
                return exam_items;
            }

            public void setExam_items(List<ExamItemsBean> exam_items) {
                this.exam_items = exam_items;
            }

            public static class ExamItemsBean {
                /**
                 * name : 健康体检
                 * intro : 个人体检：各年龄段人群体检、孕育全程体检等
                 单位体检：入职体检、年度体检、职业健康体检
                 */

                private String name;
                private String intro;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getIntro() {
                    return intro;
                }

                public void setIntro(String intro) {
                    this.intro = intro;
                }
            }
        }
    }
}
