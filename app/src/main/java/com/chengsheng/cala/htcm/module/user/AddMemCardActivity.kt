package com.chengsheng.cala.htcm.module.user

import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_card.*

/**
 * Author: 任和
 * CreateDate: 2018/12/24 10:58 AM
 * Description: 添加会员卡
 */
class AddMemCardActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_add_card
    }

    override fun initView() {
        titleBar?.setFinishClickListener {
            finish()
        }
    }

    override fun getData() {

    }
}