package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.utils.UserUtil
import com.chengsheng.cala.htcm.utils.initCaptchaTimer
import com.chengsheng.cala.htcm.utils.releaseCaptchaTimer
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_add_card.*
import java.util.concurrent.TimeUnit

/**
 * Author: 任和
 * CreateDate: 2018/12/24 10:58 AM
 * Description: 添加会员卡
 */
@SuppressLint("CheckResult")
class AddMemCardActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_add_card
    }

    override fun initView() {
        tvBindMobile?.text = UserUtil.getMobile()

        RxView.clicks(btnGetCaptcha)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    getCaptcha()
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
                    initCaptchaTimer(btnGetCaptcha)
                    hideLoading()
                }
                ) {
                    showError(it)
                    hideLoading()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCaptchaTimer()
    }
}