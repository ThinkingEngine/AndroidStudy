package com.chengsheng.cala.htcm.protocol.childmodela;

public class NureadMessage {

    private int unread;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    @Override
    public String toString() {
        return "NureadMessage{" +
                "unread=" + unread +
                '}';
    }
}
