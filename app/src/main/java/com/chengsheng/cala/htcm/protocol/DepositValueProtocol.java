package com.chengsheng.cala.htcm.protocol;

/**
 * Author: 任和
 * CreateDate: 2018/12/26 11:18 AM
 * Description:
 */
public class DepositValueProtocol {
    private int value;
    private boolean isSelected;

    public DepositValueProtocol(int value, boolean isSelected) {
        this.value = value;
        this.isSelected = isSelected;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
