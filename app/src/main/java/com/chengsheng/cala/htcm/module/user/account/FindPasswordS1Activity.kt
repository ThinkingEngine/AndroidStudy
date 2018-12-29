package com.chengsheng.cala.htcm.module.user.account

import android.annotation.SuppressLint
import android.os.Bundle
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.data.repository.UserRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.utils.initCaptchaTimer
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_find_login_password.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/29 4:03 PM
 * Description: 找回登录密码第一步操作
 */
@SuppressLint("CheckResult")
class FindPasswordS1Activity : BaseActivity() {

    private var codeId = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_find_login_password
    }

    override fun initView() {
        //获取短信验证码
        RxView.clicks(btnSendCaptcha).subscribe {
            val mobile = StringUtils.getText(etMobile)
            if (mobile.isEmpty()) {
                showShortToast("请输入手机号")
                return@subscribe
            }

            showLoading()
            UserRepository.default?.sendCaptcha(mobile, "change_password")
                    ?.subscribe({ res ->

                        hideLoading()
                        initCaptchaTimer(btnSendCaptcha)
                        showShortToast("验证码已发送，请注意查收")
                        codeId = res["code_id"].asString

                    }) { e ->
                        hideLoading()
                        showError(e)
                    }
        }

        //验证短信验证码
        RxView.clicks(btnNextStep).subscribe {
            val mobile = StringUtils.getText(etMobile)
            if (mobile.isEmpty()) {
                showShortToast("请输入手机号")
                return@subscribe
            }
            val captcha = StringUtils.getText(etCaptcha)
            if (captcha.isEmpty()) {
                showShortToast("请输入验证码")
                return@subscribe
            }
            if (codeId.isEmpty()) {
                showShortToast("请先获取验证码")
                return@subscribe
            }

            showLoading()
            UserRepository.default?.verificationCaptcha(captcha, codeId, "change_password")
                    ?.subscribe({

                        hideLoading()
                        val bundle = Bundle()
                        bundle.putString("mobile", mobile)
                        bundle.putString("codeId", codeId)
                        startActivity(FindPasswordS2Activity(), bundle)

                    }) {
                        hideLoading()
                        showError(it)
                    }
        }

    }

    override fun getData() {

    }

    @Subscriber(mode = ThreadMode.MAIN, tag = "findPasswordSuc")
    fun refresh(event: Any) {
        finish()
    }
}