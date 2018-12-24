package com.chengsheng.cala.htcm.widget;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import com.chengsheng.cala.htcm.R;
import com.luck.picture.lib.tools.ScreenUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author: 任和
 * CreateDate: 2018/12/23 5:30 PM
 * Description: 会员卡二维码
 */
public class MemberCardPopWindow extends BasePopupWindow {

    public MemberCardPopWindow(Context context) {
        super(context);
        setBackPressEnable(true);
        setDismissWhenTouchOutside(true);
    }

    @Override
    protected Animation initShowAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, -ScreenUtils.dip2px(getContext(), 350f), 0);
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(1));
        return translateAnimation;
    }

    @Override
    protected Animation initExitAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0, -ScreenUtils.dip2px(getContext(), 350f));
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(-4));
        return translateAnimation;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_member_card);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_animation);
    }
}
