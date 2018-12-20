package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.widget.ShareDialog
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.title_bar_type_service_detail.*

/**
 * Author: 任和
 * CreateDate: 2018/12/20 2:34 PM
 * Description: 服务详情
 */
@SuppressLint("CheckResult")
class ServiceDetailActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_service_detail
    }

    override fun initView() {
        RxView.clicks(ivFinishDetail).subscribe {
            finish()
        }

        //收藏
        RxView.clicks(ivCollect).subscribe {

        }

        //分享
        RxView.clicks(ivShare).subscribe {
            ShareDialog()
                    .build(this)
                    .showDialog()
                    .setOnShareListener(object : ShareDialog.OnShareClickListener {
                        override fun shareToWeChat() {
                            showShortToast("微信好友")
                        }

                        override fun shareToMoment() {
                            showShortToast("朋友圈")
                        }

                        override fun shareToQQ() {
                            showShortToast("QQ")
                        }

                        override fun shareToQZone() {
                            showShortToast("空间")
                        }

                        override fun copyLink() {
                            showShortToast("复制链接")
                        }
                    })
        }
    }

    override fun getData() {

    }
}