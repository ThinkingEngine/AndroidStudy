package com.chengsheng.cala.htcm.module.user

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.CouponAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshFragment

/**
 * Author: 任和
 * CreateDate: 2018/12/23 3:13 PM
 * Description:
 */
class CouponFragment : BaseRefreshFragment<Any>() {

    override fun initViews(view: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.layout_refresh_common
    }

    companion object {
        fun newInstance(): CouponFragment {
            return CouponFragment()
        }
    }

    override fun getData(page: Int) {
        fillData(ArrayList(), R.mipmap.wuyouhuiquan, "暂无优惠券")
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<Any>? {
        return CouponAdapter(ArrayList())
    }

}