package com.chengsheng.cala.htcm.protocol.articleModel;

import java.io.Serializable;

public class NewsTitlesItem implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private int id;
    private String name;
    private String remark;
    private boolean is_enable;
    private int display_order;
    private String operator;
    private String updated_at;

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

    @Override
    public String toString() {
        return "NewsTitlesItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", is_enable=" + is_enable +
                ", display_order=" + display_order +
                ", operator='" + operator + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
