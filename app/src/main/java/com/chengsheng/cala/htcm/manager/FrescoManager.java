package com.chengsheng.cala.htcm.manager;

import android.content.Context;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.tools.ScreenUtils;

/**
 * Author: 任和
 * CreateDate: 2018/12/20 11:09 AM
 * Description: 图片加载管理类
 */
public class FrescoManager {

    private FrescoManager() {

    }

    /**
     * 加载有弧度的网络图片
     *
     * @param var1 {@link Context}
     * @param var2 显示图片的控件
     * @param var3 图片url地址
     * @param var4 弧度尺寸
     */
    public static void loadRoundImage(Context var1, SimpleDraweeView var2, String var3, float var4) {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setCornersRadius(ScreenUtils.dip2px(var1, var4));
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(var1.getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        hierarchy.setRoundingParams(roundingParams);
        var2.setImageURI(var3);
        var2.setHierarchy(hierarchy);
    }
}
