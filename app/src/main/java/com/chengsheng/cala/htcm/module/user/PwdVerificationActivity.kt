package com.chengsheng.cala.htcm.module.user

import android.annotation.SuppressLint
import android.os.Bundle
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.ChangeMobileType
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.utils.UserUtil
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_login_pwd_verification.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/28 8:47 PM
 * Description: 修改绑定手机号-密码验证第一步
 */
@SuppressLint("CheckResult")
class PwdVerificationActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login_pwd_verification
    }

    override fun initView() {
        //通过密码验证
        RxView.clicks(btnNextStep).subscribe {
            val password = StringUtils.getText(etCheckLoginPwd)
            if (password.isEmpty()) {
                showShortToast("请输入登录密码")
                return@subscribe
            }
            if (password != UserUtil.getPassword()) {
                showShortToast("登录密码不正确")
                return@subscribe
            }

            val bundle = Bundle()
            bundle.putInt("type", ChangeMobileType.PASSWORD)
            startActivity(MsmVerificationActivity(), bundle)
        }

        //通过短信验证
        RxView.clicks(layoutVerificationByMSM).subscribe {
            val bundle = Bundle()
            bundle.putInt("type", ChangeMobileType.MSM)
            startActivity(MsmVerificationActivity())
        }
    }

    override fun getData() {

    }

    @Subscriber(mode = ThreadMode.MAIN, tag = GlobalConstant.BIND_NEW_MOBILE_SUCCESS)
    fun changeMobileSuc(event: Any) {
        finish()
    }
}