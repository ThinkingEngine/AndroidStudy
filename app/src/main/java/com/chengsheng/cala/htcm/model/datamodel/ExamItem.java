package com.chengsheng.cala.htcm.model.datamodel;

import java.io.Serializable;

public class ExamItem implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    //检查项目名称
    private String name;
    //包含内容
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ExamItem{" +
                "name='" + name + '\'' +
                ", precautions='" + content + '\'' +
                '}';
    }
}
