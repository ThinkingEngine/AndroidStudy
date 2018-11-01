package com.chengsheng.cala.htcm.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class HeaderScrollView extends ScrollView {
    private static StopCall stopCall;
    private int upH;

    public HeaderScrollView(Context context) {
        super(context);
    }

    public HeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        upH = dp2px(127);
    }

    public HeaderScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(t > upH){
            stopCall.stopSlide(true);
        }else {
            stopCall.stopSlide(false);
        }
    }

    public static void setCallback(StopCall c){
        stopCall = c;
    }

    public interface StopCall{
        public void stopSlide(boolean isStop);
    }

    private int dp2px(int dp){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}
