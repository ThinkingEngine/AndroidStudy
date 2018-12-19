package com.chengsheng.cala.htcm.module.find

import android.view.View
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 4:36 PM
 * Description: 发现
 */
class FindFragment : BaseFragment() {

    companion object {
        fun newInstance(): FindFragment {
            return FindFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_find
    }

    override fun initView(view: View?) {
    }

    override fun getData() {

    }
}