package com.chengsheng.cala.htcm.module.user

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.TabPageStateAdapter
import com.chengsheng.cala.htcm.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_coupon.*

/**
 * Author: 任和
 * CreateDate: 2018/12/23 2:41 PM
 * Description: 优惠券
 */
@SuppressLint("CheckResult")
class CouponActivity : BaseActivity() {

    private var fragments = ArrayList<Fragment>()
    private val tabArray: Array<String> = arrayOf("未使用", "已使用", "已过期")

    override fun getLayoutId(): Int {
        return R.layout.activity_coupon
    }

    override fun initView() {

        RxView.clicks(ivFinish).subscribe {
            finish()
        }

        for (tab in tabArray) {
            val fragment = CouponFragment.newInstance()
            fragments.add(fragment)
        }

        vpCoupon.adapter = TabPageStateAdapter(supportFragmentManager, fragments)
        slideTab.setViewPager(vpCoupon, tabArray)
    }

    override fun getData() {

    }
}