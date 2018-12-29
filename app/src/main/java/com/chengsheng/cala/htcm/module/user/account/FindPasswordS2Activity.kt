package com.chengsheng.cala.htcm.module.user.account

import android.annotation.SuppressLint
import android.os.Handler
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.data.repository.UserRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_find_login_password_2.*
import org.simple.eventbus.EventBus

/**
 * Author: 任和
 * CreateDate: 2018/12/29 4:03 PM
 * Description: 找回登录密码第二步：设置新密码
 */
@SuppressLint("CheckResult")
class FindPasswordS2Activity : BaseActivity() {

    private var codeId = ""
    private var mobile = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_find_login_password_2
    }

    override fun initView() {
        codeId = intent.getStringExtra("codeId")
        mobile = intent.getStringExtra("mobile")

        RxView.clicks(btnFindPassword).subscribe {
            val newPassword = StringUtils.getText(etNewLoginPassword)
            val confirmPassword = StringUtils.getText(etNewConfirmLoginPassword)
            if (newPassword.isEmpty()) {
                showShortToast("请输入新密码")
                return@subscribe
            }
            if (confirmPassword.isEmpty()) {
                showShortToast("请输入确认密码")
                return@subscribe
            }
            if (confirmPassword != newPassword) {
                showShortToast("两次密码输入不一致")
                return@subscribe
            }

            showLoading()
            UserRepository.default?.resetLoginPassword(newPassword, "", codeId,
                    "phone_number", mobile)
                    ?.subscribe({

                        hideLoading()
                        showShortToast("密码设置成功")
                        EventBus.getDefault().post("", "findPasswordSuc")
                        Handler().postDelayed({
                            finish()
                        }, 300)

                    }) {
                        showError(it)
                        hideLoading()
                    }
        }
    }

    override fun getData() {

    }
}