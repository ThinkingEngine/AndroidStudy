package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Handler
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.utils.UserUtil
import com.chengsheng.cala.htcm.utils.initCaptchaTimer
import com.chengsheng.cala.htcm.utils.releaseCaptchaTimer
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_add_card.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * Author: 任和
 * CreateDate: 2018/12/24 10:58 AM
 * Description: 添加会员卡
 */
@SuppressLint("CheckResult")
class AddMemCardActivity : BaseActivity() {

    private var uuid: String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_add_card
    }

    override fun initView() {
        tvBindMobile?.text = UserUtil.getMobile()

        //获取短信验证码
        RxView.clicks(btnGetCaptcha)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    getCaptcha()
                }

        //绑定会员卡
        RxView.clicks(btnBind)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    val cardNumber = StringUtils.getText(etCardNumber)
                    val password = StringUtils.getText(etCardPassword)
                    val captcha = StringUtils.getText(etCaptcha)

                    if (cardNumber.isEmpty()) {
                        showShortToast("请输入卡号")
                        return@subscribe
                    }
                    if (password.isEmpty()) {
                        showShortToast("请输入卡密码")
                        return@subscribe
                    }
                    if (captcha.isEmpty()) {
                        showShortToast("请输入验证码")
                        return@subscribe
                    }
                    if (uuid.isEmpty()) {
                        showShortToast("请先获取验证码")
                        return@subscribe
                    }

                    addCard(cardNumber, password, UserUtil.getMobile(), captcha, uuid)
                }
    }

    override fun getData() {

    }

    /**
     * 获取短信验证码
     */
    private fun getCaptcha() {
        showLoading()
//        MemberCardRepository.default?.sendCaptcha(UserUtil.getMobile())
        MemberCardRepository.default?.sendCaptcha("17716130287")
                ?.subscribe({
                    uuid = it["uuid"].asString
                    initCaptchaTimer(btnGetCaptcha)
                    hideLoading()
                    etCaptcha.requestFocus()
                }
                ) {
                    showError(it)
                    hideLoading()
                }
    }

    /**
     * 添加会员卡
     */
    private fun addCard(cardNumber: String, password: String,
                        mobile: String, captcha: String, uuid: String) {
        showLoading()
        MemberCardRepository.default?.bind(cardNumber, password, mobile, captcha, uuid)
                ?.subscribe({
                    hideLoading()
                    showShortToast("操作成功")
                    EventBus.getDefault().post("", GlobalConstant.DELETE_MEMBER_CARD_SUC)
                    Handler().postDelayed({
                        finish()
                    }, 300)
                }) {
                    showError(it)
                    hideLoading()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCaptchaTimer()
    }
}