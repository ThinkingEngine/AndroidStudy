package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Handler
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_find_card_pwd_2.*
import org.simple.eventbus.EventBus

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:10 PM
 * Description: 找回卡密码第二步
 */
@SuppressLint("CheckResult")
class FindCardPwdAStep2Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_find_card_pwd_2
    }

    override fun initView() {
        val id = intent.getIntExtra("id", 0)

        //找回密码
        RxView.clicks(btnFindPwd).subscribe {
            val newPassword = StringUtils.getText(etFindNewPwd)
            val confirmPassword = StringUtils.getText(etFindConfirmPwd)

            if (newPassword.isEmpty() || newPassword.length != 6) {
                showShortToast("请输入6位置新密码")
                return@subscribe
            }

            if (confirmPassword.isEmpty() || confirmPassword.length != 6) {
                showShortToast("请输入6位确认密码")
                return@subscribe
            }

            if (newPassword != confirmPassword) {
                showShortToast("两次密码输入不一致")
                return@subscribe
            }

            findPassword(id, newPassword)
        }
    }

    /**
     * 找回密码
     */
    private fun findPassword(id: Int, password: String) {
        showLoading()
        MemberCardRepository.default?.findPassword(id, password)
                ?.subscribe({

                    hideLoading()
                    showShortToast("密码设置成功")
                    EventBus.getDefault().post("", GlobalConstant.DELETE_MEMBER_CARD_SUC)
                    Handler().postDelayed({
                        finish()
                    }, 300)

                }) {
                    hideLoading()
                    showError(it)
                }
    }

    override fun getData() {

    }
}