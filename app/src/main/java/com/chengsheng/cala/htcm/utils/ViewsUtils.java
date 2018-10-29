package com.chengsheng.cala.htcm.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewsUtils {


    public static void setMargins(View v,int left,int top,int right,int bottom){
        if(v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            mp.setMargins(left,top,right,bottom);
            v.requestLayout();
        }
    }

}
