package com.chengsheng.cala.htcm.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.chengsheng.cala.htcm.R

/**
 * Author: 任和
 * CreateDate: 2018/12/17 4:24 PM
 * Description: APP顶部标题栏
 */
class AppTitleBar : FrameLayout {

    private var ivFinishPage: ImageView? = null
    private var tvPageTitle: TextView? = null
    private var ivRightAction: ImageView? = null

    private var rightActionClickListener: OnViewClickListener? = null
    private var finishClickListener: OnViewClickListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this)
        ivFinishPage = contentView.findViewById<View>(R.id.iv_finish_pager) as ImageView
        tvPageTitle = contentView.findViewById<View>(R.id.tv_pager_title) as TextView
        ivRightAction = contentView.findViewById(R.id.iv_right_action) as ImageView

        //获取自定义的属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.titleBar)
        val title = array.getString(R.styleable.titleBar_title)

        val isShowFinish = array.getBoolean(R.styleable.titleBar_isShowReturn, true)
        val rightImgResId: Int = array.getResourceId(R.styleable.titleBar_rightImageView, -1)
        array.recycle()

        if (rightImgResId != -1) {
            ivRightAction?.setImageResource(rightImgResId)
        } else {
            ivRightAction?.visibility = View.INVISIBLE
        }

        //设置返回键是否显示
        ivFinishPage?.visibility = if (isShowFinish) View.VISIBLE else View.INVISIBLE

        //设置默认标题
        if (tvPageTitle != null && !TextUtils.isEmpty(title)) {
            tvPageTitle?.text = title
        }

        //返回事件
        ivFinishPage?.setOnClickListener { v ->
            if (finishClickListener != null) {
                finishClickListener?.onClick()
            }
        }

        //右边操作事件
        ivRightAction?.setOnClickListener {
            if (rightActionClickListener != null) {
                rightActionClickListener?.onClick()
            }
        }
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String): AppTitleBar {
        if (tvPageTitle != null) {
            tvPageTitle?.text = title
        }
        return this
    }

    /**
     * 设置返回点击事件
     */
    fun setOnFinishClickListener(returnClickListener: OnViewClickListener): AppTitleBar {
        this.finishClickListener = returnClickListener
        return this
    }

    /**
     * 设置右边操作按钮点击事件
     */
    fun setOnRightClickListener(rightActionClickListener: OnViewClickListener): AppTitleBar {
        this.rightActionClickListener = rightActionClickListener
        return this
    }

    interface OnViewClickListener {
        fun onClick()
    }
}
