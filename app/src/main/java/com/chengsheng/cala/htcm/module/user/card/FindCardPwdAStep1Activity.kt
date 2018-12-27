package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Bundle
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.protocol.MemberCardDetailProtocol
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.utils.initCaptchaTimer
import com.chengsheng.cala.htcm.utils.releaseCaptchaTimer
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_find_card_pwd_1.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:10 PM
 * Description: 找回卡密码第一步
 */
@SuppressLint("CheckResult")
class FindCardPwdAStep1Activity : BaseActivity() {

    private var uuid: String = ""
    private var cardData: MemberCardDetailProtocol? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_find_card_pwd_1
    }

    override fun initView() {
        cardData = intent.getParcelableExtra("cardData")
        tvBindMobile?.text = cardData?.bind_mobile

        //获取验证码
        RxView.clicks(btnGetCardCaptcha).subscribe { _ ->
            showLoading()
            MemberCardRepository.default?.sendCardCaptcha(cardData?.id!!)
                    ?.subscribe({
                        uuid = it["uuid"].asString
                        hideLoading()
                        showShortToast("短信验证码已发送，请注意查收")
                        initCaptchaTimer(btnGetCardCaptcha)
                    }) {
                        hideLoading()
                        showError(it)
                    }
        }

        //下一步
        RxView.clicks(btnNextStep).subscribe {
            val captcha = StringUtils.getText(etFindCaptcha)
            if (uuid.isEmpty()) {
                showShortToast("请先获取验证码")
                return@subscribe
            }
            if (captcha.isEmpty()) {
                showShortToast("请输入验证码")
                return@subscribe
            }

            checkCaptcha(captcha)
        }
    }

    private fun checkCaptcha(captcha: String) {
        showLoading()
        MemberCardRepository.default?.checkCardCaptcha(cardData?.id!!, captcha, uuid)
                ?.subscribe({

                    hideLoading()
                    val bundle = Bundle()
                    bundle.putInt("id", cardData?.id!!)
                    startActivity(FindCardPwdAStep2Activity(), bundle)

                }) {
                    hideLoading()
                    showError(it)
                }
    }

    override fun getData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCaptchaTimer()
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = GlobalConstant.UPDATE_MEMBER_CARD_SUCCESS)
    fun refresh(event: String) {
        finish()
    }
}