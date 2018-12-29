package com.chengsheng.cala.htcm.module.user.account

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.Handler
import com.chengsheng.cala.htcm.HTCMApplication
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.ChangeMobileType
import com.chengsheng.cala.htcm.constant.GlobalConstant.BIND_NEW_MOBILE_SUCCESS
import com.chengsheng.cala.htcm.data.repository.UserRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.utils.UserUtil
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_change_mobile.*
import org.simple.eventbus.EventBus

/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:01 PM
 * Description: 更换手机号
 */
@SuppressLint("CheckResult")
class ChangeMobileActivity : BaseActivity() {

    @ChangeMobileType
    private var type: Int = ChangeMobileType.MSM
    private var oldCodeId = ""
    private var newCodeId = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_change_mobile
    }

    override fun initView() {
        type = intent.getIntExtra("type", ChangeMobileType.MSM)
        oldCodeId = intent.getStringExtra("oldCodeId")

        //获取验证码
        RxView.clicks(btnSendNewMobileCaptcha).subscribe {
            val mobile = StringUtils.getText(etNewMobile)
            if (mobile.isEmpty()) {
                showShortToast("请输入手机号")
                return@subscribe
            }

            showLoading()
            UserRepository.default?.sendCaptcha(mobile, "bind_phone_number")
                    ?.subscribe({ res ->

                        hideLoading()
                        initCaptchaTimer()
                        showShortToast("验证码已发送，请注意查收")
                        newCodeId = res["code_id"].asString

                    }) { e ->
                        hideLoading()
                        showError(e)
                    }
        }

        //绑定手机号
        RxView.clicks(btnChangeMobile).subscribe {
            val mobile = StringUtils.getText(etNewMobile)
            val captcha = StringUtils.getText(etNewCaptcha)

            if (mobile.isEmpty()) {
                showShortToast("请输入手机号")
                return@subscribe
            }
            if (captcha.isEmpty()) {
                showShortToast("请输入验证码")
                return@subscribe
            }
            if (newCodeId.isEmpty()) {
                showShortToast("请先获取验证码")
                return@subscribe
            }

            //验证短信验证码是否正确
            showLoading()
            UserRepository.default?.verificationCaptcha(captcha, newCodeId, "bind_phone_number")
                    ?.subscribe({

                        bindNewMobile(mobile)

                    }) { e ->
                        hideLoading()
                        showError(e)
                    }
        }
    }

    /**
     * 绑定新手机号
     */
    private fun bindNewMobile(mobile: String) {
        val changeType: String = if (type == ChangeMobileType.PASSWORD) {
            "password"
        } else {
            "old_phone_number"
        }
        UserRepository.default?.bindNewMobile(changeType, UserUtil.getPassword(),
                newCodeId, oldCodeId, mobile)
                ?.subscribe({

                    hideLoading()
                    showShortToast("新手机号已绑定")
                    UserUtil.setMobile(mobile)
                    EventBus.getDefault().post("", BIND_NEW_MOBILE_SUCCESS)
                    Handler().postDelayed({
                        finish()
                    }, 300)

                }) { e ->
                    hideLoading()
                    showError(e)
                }
    }

    override fun getData() {

    }


    private var countDownTimer: CountDownTimer? = null

    /**
     * 验证码倒计时
     */
    private fun initCaptchaTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                btnSendNewMobileCaptcha.text = (HTCMApplication.appContext.getString(R.string.count_down_time,
                        millisUntilFinished / 1000))
            }

            override fun onFinish() {
                btnSendNewMobileCaptcha.text = "获得验证码"
                btnSendNewMobileCaptcha.isEnabled = true
                releaseCaptchaTimer()
            }
        }

        countDownTimer?.start()
        btnSendNewMobileCaptcha.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCaptchaTimer()
    }

    private fun releaseCaptchaTimer() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            countDownTimer = null
        }
    }
}