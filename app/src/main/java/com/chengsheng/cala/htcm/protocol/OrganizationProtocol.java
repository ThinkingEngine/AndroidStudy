package com.chengsheng.cala.htcm.protocol;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author: 任和
 * CreateDate: 2018/12/21 9:35 AM
 * Description:
 */
public class OrganizationProtocol {

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

        private PaginationBean pagination;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public static class PaginationBean {

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

    public static class ItemsBean implements Parcelable {
        private int id;
        private String name;
        private String photo_path;
        private String intro;
        private int display_order;
        private String remark;
        private boolean is_enable;
        private String operator;
        private String updated_at;
        private List<ExamItemsBean> exam_items;

        protected ItemsBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
            photo_path = in.readString();
            intro = in.readString();
            display_order = in.readInt();
            remark = in.readString();
            is_enable = in.readByte() != 0;
            operator = in.readString();
            updated_at = in.readString();
            exam_items = in.readArrayList(ExamItemsBean.class.getClassLoader());
        }

        public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
            @Override
            public ItemsBean createFromParcel(Parcel in) {
                return new ItemsBean(in);
            }

            @Override
            public ItemsBean[] newArray(int size) {
                return new ItemsBean[size];
            }
        };

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public List<ExamItemsBean> getExam_items() {
            return exam_items;
        }

        public void setExam_items(List<ExamItemsBean> exam_items) {
            this.exam_items = exam_items;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(name);
            parcel.writeString(photo_path);
            parcel.writeString(intro);
            parcel.writeInt(display_order);
            parcel.writeString(remark);
            parcel.writeByte((byte) (is_enable ? 1 : 0));
            parcel.writeString(operator);
            parcel.writeString(updated_at);
            parcel.writeList(exam_items);
        }

        public static class ExamItemsBean implements Parcelable {
            /**
             * name : 科室测试
             * intro : null
             */

            private String name;
            private String intro;

            protected ExamItemsBean(Parcel in) {
                name = in.readString();
                intro = in.readString();
            }

            public static final Creator<ExamItemsBean> CREATOR = new Creator<ExamItemsBean>() {
                @Override
                public ExamItemsBean createFromParcel(Parcel in) {
                    return new ExamItemsBean(in);
                }

                @Override
                public ExamItemsBean[] newArray(int size) {
                    return new ExamItemsBean[size];
                }
            };

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(name);
                parcel.writeString(intro);
            }
        }
    }
}
