package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Bundle
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.widget.MemberMoreDialog
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_member_card_detail.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/23 5:40 PM
 * Description: 会员卡详情
 */
@SuppressLint("CheckResult")
class MemberCardDetailActivity : BaseActivity() {

    //会员卡id
    private var id: Int = 0
    private var moreDialog: MemberMoreDialog? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_member_card_detail
    }

    override fun initView() {
        //更多操作
        titleBar?.setRightClickListener {
            moreDialog = MemberMoreDialog().build(this) {
                val bundle = Bundle()
                bundle.putInt("id", id)
                startActivity(DeleteMemberCardActivity(), bundle)
                moreDialog?.dismissDialog()
            }.showDialog()
        }

        //修改卡密码
        RxView.clicks(layoutChangePassword).subscribe {
            startActivity(ChangeCardPwdActivity())
        }

        //交易记录
        RxView.clicks(layoutTradeDetail).subscribe {
            startActivity(TradeDetailActivity())
        }

        //充值
        RxView.clicks(btnDeposit).subscribe {
            startActivity(DepositActivity())
        }
    }

    override fun getData() {
        showLoading()
        id = intent.getIntExtra("id", 0)
        MemberCardRepository.default?.getCardDetail(id)
                ?.subscribe({

                    tvCardNumber?.text = StringUtils.addBlank(it.card_number)
                    tvBalance?.text = "￥" + it.balance
                    tvBindMobile?.text = it.bind_mobile
                    tvBindDate?.text = it.bind_at.substring(0, 10)
                    hideLoading()

                }) {
                    showError(it)
                    hideLoading()
                }
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = GlobalConstant.DELETE_MEMBER_CARD_SUC)
    fun refresh(event: String) {
        finish()
    }
}