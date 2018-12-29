package com.chengsheng.cala.htcm.module.user.account

import android.annotation.SuppressLint
import android.os.Bundle
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.ChangeMobileType
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.UserRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.utils.UserUtil
import com.chengsheng.cala.htcm.utils.initCaptchaTimer
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_msm_verification.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:01 PM
 * Description: 短信验证
 */
@SuppressLint("CheckResult")
class MsmVerificationActivity : BaseActivity() {

    @ChangeMobileType
    private var type = ChangeMobileType.PASSWORD
    private var codeId = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_msm_verification
    }

    override fun initView() {
        type = intent.getIntExtra("type", ChangeMobileType.MSM)

        tvBindMobile?.text = StringUtils.formatMobile(UserUtil.getMobile())

        //获取验证码
        RxView.clicks(btnGetCaptcha).subscribe { res ->
            showLoading()
            UserRepository.default?.sendCaptcha(UserUtil.getMobile(), "change_phone_number")
                    ?.subscribe({

                        hideLoading()
                        initCaptchaTimer(btnGetCaptcha)
                        showShortToast("验证码已发送，请注意查收")
                        codeId = it["code_id"].asString

                    }) {
                        showError(it)
                        hideLoading()
                    }
        }

        //下一步
        RxView.clicks(btnNextStep).subscribe {
            val captcha = StringUtils.getText(etChangeMobileCaptcha)
            if (codeId.isEmpty()) {
                showShortToast("请先获取验证码")
                return@subscribe
            }
            if (captcha.isEmpty()) {
                showShortToast("请输入验证码")
                return@subscribe
            }

            showLoading()
            UserRepository.default?.verificationCaptcha(captcha, codeId, "change_phone_number")
                    ?.subscribe({

                        hideLoading()
                        val bundle = Bundle()
                        bundle.putInt("type", type)
                        bundle.putString("oldCodeId", codeId)
                        startActivity(ChangeMobileActivity(), bundle)

                    }) {
                        hideLoading()
                        showError(it)
                    }
        }
    }

    override fun getData() {

    }

    @Subscriber(mode = ThreadMode.MAIN, tag = GlobalConstant.BIND_NEW_MOBILE_SUCCESS)
    fun changeMobileSuc(event: Any) {
        finish()
    }
}