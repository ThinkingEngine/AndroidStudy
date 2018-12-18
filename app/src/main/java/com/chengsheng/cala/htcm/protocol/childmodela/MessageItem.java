package com.chengsheng.cala.htcm.protocol.childmodela;

public class MessageItem {

    private int id;
    private String title;
    private String content;
    private String purpose;
    private String purpose_identifier;
    private String purpose_url;
    private boolean is_read;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose_identifier() {
        return purpose_identifier;
    }

    public void setPurpose_identifier(String purpose_identifier) {
        this.purpose_identifier = purpose_identifier;
    }

    public String getPurpose_url() {
        return purpose_url;
    }

    public void setPurpose_url(String purpose_url) {
        this.purpose_url = purpose_url;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "MessageItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", purpose='" + purpose + '\'' +
                ", purpose_identifier='" + purpose_identifier + '\'' +
                ", purpose_url='" + purpose_url + '\'' +
                ", is_read=" + is_read +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
