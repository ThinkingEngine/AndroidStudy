package com.chengsheng.cala.htcm.utils;

public class MessageEvent {

    private boolean isGetFamiliesList;

    public boolean getFamiliesListsState(){
        return isGetFamiliesList;
    }

    public void setFamiliesState(boolean state){
        this.isGetFamiliesList = state;
    }
}
