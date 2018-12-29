package com.chengsheng.cala.htcm.module.user.account

import android.annotation.SuppressLint
import android.os.Handler
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant.UPDATE_NICKNAME_SUCCESS
import com.chengsheng.cala.htcm.data.repository.UserRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_change_nickname.*
import org.simple.eventbus.EventBus

/**
 * Author: 任和
 * CreateDate: 2018/12/28 8:17 PM
 * Description: 修改昵称
 */
@SuppressLint("CheckResult")
class ChangeNickNameActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_change_nickname
    }

    override fun initView() {
        val oldNickName = intent.getStringExtra("oldNickName")
        etNewNickName.hint = oldNickName

        RxView.clicks(btnChangeNickName).subscribe {
            val nickName = StringUtils.getText(etNewNickName)
            if (nickName.isEmpty()) {
                showShortToast("请输入昵称")
                return@subscribe
            }

            showLoading()
            UserRepository.default?.updateUserInfo("", nickName, "")
                    ?.subscribe({ res ->

                        hideLoading()
                        showShortToast("修改成功")
                        EventBus.getDefault().post("", UPDATE_NICKNAME_SUCCESS)
                        Handler().postDelayed({
                            finish()
                        }, 300)

                    }) {
                        hideLoading()
                        showError(it)
                    }
        }
    }

    override fun getData() {

    }
}