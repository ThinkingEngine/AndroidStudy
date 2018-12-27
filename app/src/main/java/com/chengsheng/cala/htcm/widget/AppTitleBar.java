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
    /**
     * 页面标题
     */
    private TextView tvTitle;
    /**
     * 右边可操作图标
     */
    private ImageView ivRightAction;
    /**
     * 右边可操作文字
     */
    private TextView tvRightAction;

    private OnViewClickListener onFinishClickListener;
    private OnViewClickListener onRightImageClickListener;
    private OnViewClickListener onRightTxtClickListener;

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
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this);
        ImageView ivFinishPage = contentView.findViewById(R.id.ivFinishPager);
        tvTitle = contentView.findViewById(R.id.tvPagerTitle);
        ivRightAction = contentView.findViewById(R.id.ivRightImageAction);
        tvRightAction = contentView.findViewById(R.id.tvRightAction);
        View viewDivide = contentView.findViewById(R.id.viewDivide);

        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.titleBar);
        String title = array.getString(R.styleable.titleBar_title);
        Boolean isShowFinish = array.getBoolean(R.styleable.titleBar_isShowReturn, true);
        Boolean isShowDivide = array.getBoolean(R.styleable.titleBar_isShowDivide, true);
        int rightImgResId = array.getResourceId(R.styleable.titleBar_rightImageRes, -1);
        String rightTxt = array.getString(R.styleable.titleBar_rightTxtString);
        int rightTxtColor = array.getResourceId(R.styleable.titleBar_rightTxtColor, -1);
        array.recycle();

        /*
         * 返回按钮相关
         */
        if (ivFinishPage != null) {
            //设置是否显示返回按钮
            ivFinishPage.setVisibility(isShowFinish ? View.VISIBLE : View.INVISIBLE);
            //设置返回事件的点击事件
            ivFinishPage.setOnClickListener(view -> {
                if (onFinishClickListener != null) {
                    onFinishClickListener.onClick();
                }
            });
        }

        //设置页面标题
        if (tvTitle != null) {
            tvTitle.setText(title);
        }

        /*
         * 右边图标相关
         */
        if (ivRightAction != null) {
            //设置(-1为默认值，不显示图标)
            if (rightImgResId != -1) {
                ivRightAction.setVisibility(View.VISIBLE);
                ivRightAction.setImageResource(rightImgResId);
            }

            ivRightAction.setOnClickListener(view -> {
                if (onRightImageClickListener != null) {
                    onRightImageClickListener.onClick();
                }
            });
        }

        //设置是否显示分割线
        viewDivide.setVisibility(isShowDivide ? View.VISIBLE : View.INVISIBLE);

        /*
         * 右边文字相关
         */
        if (tvRightAction != null) {
            tvRightAction.setOnClickListener(view -> {
                        if (onRightTxtClickListener != null) {
                            onRightTxtClickListener.onClick();
                        }
                    }
            );

            //文字文本
            if (rightTxt != null) {
                tvRightAction.setVisibility(View.VISIBLE);
                tvRightAction.setText(rightTxt);
            }

            //右边文字颜色(颜色一定要抽取到color.xml下)
            if (rightTxtColor != -1) {
                tvRightAction.setVisibility(View.VISIBLE);
                tvRightAction.setTextColor(getResources().getColor(rightTxtColor));
            }
        }
    }

    /**
     * 设置页面标题
     */
    public AppTitleBar setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    /**
     * 设置返回事件
     */
    public AppTitleBar setFinishClickListener(OnViewClickListener clickListener) {
        this.onFinishClickListener = clickListener;
        return this;
    }

    /**
     * 设置右边图标点击事件
     */
    public AppTitleBar setRightImageClickListener(OnViewClickListener clickListener) {
        this.onRightImageClickListener = clickListener;
        return this;
    }

    /**
     * 设置右边文字点击事件
     */
    public AppTitleBar setRightTxtClickListener(OnViewClickListener clickListener) {
        this.onRightTxtClickListener = clickListener;
        return this;
    }
}


