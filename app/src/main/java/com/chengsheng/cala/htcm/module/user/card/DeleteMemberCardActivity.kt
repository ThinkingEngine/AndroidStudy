package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Handler
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_delete_member.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * Author: 任和
 * CreateDate: 2018/12/25 3:15 PM
 * Description: 解绑会员卡
 */
@SuppressLint("CheckResult")
class DeleteMemberCardActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_delete_member
    }

    override fun initView() {
        RxView.clicks(btnDelete)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe {
                    val password = StringUtils.getText(etPassword)
                    if (password.isEmpty() || password.length < 6) {
                        showShortToast("请输入6位卡密码")
                        return@subscribe
                    }
                    delete(password)
                }
    }

    override fun getData() {

    }

    /**
     * 解绑
     */
    private fun delete(password: String) {
        showLoading()
        val id = intent.getIntExtra("id", 0)
        MemberCardRepository.default?.deleteMemberCard(id, password)
                ?.subscribe({

                    hideLoading()
                    showShortToast("操作成功")
                    EventBus.getDefault().post("", GlobalConstant.UPDATE_MEMBER_CARD_SUCCESS)
                    Handler().postDelayed({
                        finish()
                    }, 300)

                }) {
                    etPassword.setText("")
                    showError(it)
                    hideLoading()
                }
    }
}