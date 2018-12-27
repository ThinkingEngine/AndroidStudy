package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.protocol.MemberCardDetailProtocol
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_change_card_pwd.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:10 PM
 * Description: 修改卡密码
 */
@SuppressLint("CheckResult")
class ChangeCardPwdActivity : BaseActivity() {

    private var cardData: MemberCardDetailProtocol? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_change_card_pwd
    }

    override fun initView() {
        //获取卡数据
        cardData = intent.getParcelableExtra("cardData")
        tvBindCardNumber.text = StringUtils.addBlank(cardData?.card_number)

        //忘记卡密码
        RxView.clicks(tvForgetPwd).subscribe {
            val bundle = Bundle()
            bundle.putParcelable("cardData", cardData)
            startActivity(FindCardPwdAStep1Activity(), bundle)
        }

        //修改密码
        RxView.clicks(btnConfirmChange).subscribe {
            val oldPassword = StringUtils.getText(etOldPassword)
            val newPassword = StringUtils.getText(etNewPassword)
            val confirmPassword = StringUtils.getText(etNewConfirmPassword)

            if (oldPassword.isEmpty()) {
                showShortToast("请输入旧密码")
                return@subscribe
            }
            if (newPassword.isEmpty()) {
                showShortToast("请输入新密码")
                return@subscribe
            }
            if (confirmPassword.isEmpty()) {
                showShortToast("请再次输入新密码")
                return@subscribe
            }
            if (newPassword != confirmPassword) {
                showShortToast("新密码两次输入不一致")
                return@subscribe
            }

            changePassword(oldPassword, newPassword, cardData?.id!!)
        }
    }

    override fun getData() {

    }

    /**
     * 修改密码
     */
    private fun changePassword(oldPassword: String, newPassword: String, id: Int) {
        showLoading()
        MemberCardRepository.default?.changePassword(id, oldPassword, newPassword)
                ?.subscribe({
                    hideLoading()
                    showShortToast("密码修改成功")
                    Handler().postDelayed({
                        finish()
                    }, 300)
                }) {
                    hideLoading()
                    showError(it)
                }
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = GlobalConstant.UPDATE_MEMBER_CARD_SUCCESS)
    fun refresh(event: String) {
        finish()
    }
}