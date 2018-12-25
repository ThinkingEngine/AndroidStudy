package com.chengsheng.cala.htcm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

/**
 * Author: 任和
 * CreateDate: 2018/12/17 6:10 PM
 * Description: APP通用标题栏
 */
public class AppTitleBar extends FrameLayout {
    private View contentView;
    private ImageView ivFinishPage;
    private TextView tvTitle;
    private ImageView ivRightAction;

    private OnViewClickListener onFinishClickListener;
    private OnViewClickListener onRightClickListener;

    public AppTitleBar(Context context) {
        super(context);
    }

    public AppTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this);
        ivFinishPage = contentView.findViewById(R.id.iv_finish_pager);
        tvTitle = contentView.findViewById(R.id.tv_pager_title);
        ivRightAction = contentView.findViewById(R.id.iv_right_action);
        View viewDivide = contentView.findViewById(R.id.viewDivide);

        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.titleBar);
        String title = array.getString(R.styleable.titleBar_title);
        Boolean isShowFinish = array.getBoolean(R.styleable.titleBar_isShowReturn, true);
        Boolean isShowDivide = array.getBoolean(R.styleable.titleBar_isShowDivide, true);
        int rightImgResId = array.getResourceId(R.styleable.titleBar_rightImageView, -1);
        array.recycle();

        if (ivFinishPage != null) {
            //设置是否显示返回按钮
            ivFinishPage.setVisibility(isShowFinish ? View.VISIBLE : View.INVISIBLE);
            //设置返回事件的点击事件
            ivFinishPage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onFinishClickListener != null) {
                        onFinishClickListener.onClick();
                    }
                }
            });
        }

        //设置页面标题
        if (tvTitle != null) {
            tvTitle.setText(title);
        }

        if (ivRightAction != null) {
            //设置右边操作按钮图标(-1为默认值，不显示图标)
            if (rightImgResId != -1) {
                ivRightAction.setVisibility(View.VISIBLE);
                ivRightAction.setImageResource(rightImgResId);
            }

            //设置右边点击事件的返回事件
            ivRightAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightClickListener.onClick();
                }
            });
        }

        //设置是否显示分割线
        viewDivide.setVisibility(isShowDivide ? View.VISIBLE : View.INVISIBLE);

    }

    /**
     * 设置页面标题
     */
    public AppTitleBar setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    /**
     * 设置返回事件回调
     */
    public AppTitleBar setFinishClickListener(OnViewClickListener clickListener) {
        this.onFinishClickListener = clickListener;
        return this;
    }

    /**
     * 设置右边操作按钮点击事件回调
     */
    public AppTitleBar setRightClickListener(OnViewClickListener clickListener) {
        this.onRightClickListener = clickListener;
        return this;
    }
}


