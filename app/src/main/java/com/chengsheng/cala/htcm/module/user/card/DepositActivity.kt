package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import com.alipay.sdk.app.PayTask
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.DepositValueAdapter
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.constant.PayType
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.protocol.DepositValueProtocol
import com.chengsheng.cala.htcm.protocol.MemberCardDetailProtocol
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_deposit.*
import org.simple.eventbus.EventBus

/**
 * Author: 任和
 * CreateDate: 2018/12/24 3:22 PM
 * Description: 充值
 */
@SuppressLint("CheckResult")
class DepositActivity : BaseActivity() {

    private var cardData: MemberCardDetailProtocol? = null

    private var adapter: DepositValueAdapter? = null
    //充值金额
    private var amount: Double = 200.0
    //支付方式
    @PayType
    private var payType: Int = PayType.ALIPAY

    override fun getLayoutId(): Int {
        return R.layout.activity_deposit
    }

    override fun initView() {
        cardData = intent.getParcelableExtra("cardData")
        adapter = DepositValueAdapter(ArrayList(), object : DepositValueAdapter.OnSelectListener {
            override fun onSelected(position: Int, data: DepositValueProtocol) {
                for (index in 0 until adapter?.data?.size!!) {
                    adapter?.data?.get(index)?.isSelected = false
                }
                adapter?.data?.get(position)?.isSelected = true
                adapter?.notifyDataSetChanged()
                amount = data.value.toDouble()
            }
        })

        tvCardNumber.text = StringUtils.addBlank(cardData?.card_number)
        tvCardBalance.text = "￥" + cardData?.balance

        ivSelectAlipay.isSelected = true
        rvDeposit?.layoutManager = GridLayoutManager(this, 3)
        rvDeposit?.adapter = adapter

        //选择支付宝支付
//        RxView.clicks(layoutAlipay).subscribe {
//            ivSelectAlipay.isSelected = true
//            ivSelectWechat.isSelected = false
//            payType = PayType.ALIPAY
//        }
//
//        //选择微信支付
//        RxView.clicks(layoutWechatPay).subscribe {
//            ivSelectAlipay.isSelected = false
//            ivSelectWechat.isSelected = true
//            payType = PayType.WECHAT
//        }

        //支付
        RxView.clicks(btnDeposit).subscribe {
            //amount为0时是其他金额
            if (amount == 0.0) {
                //获取输入的金额
                if (StringUtils.getText(etDepositAmount).isEmpty()) {
                    showShortToast("请输入充值金额")
                    return@subscribe
                }
                amount = StringUtils.getText(etDepositAmount).toDouble()
            }

            createOrder()
        }
    }

    /**
     * 1.创建支付宝订单
     */
    private fun createOrder() {
        showLoading()
        MemberCardRepository.default?.createOrder(cardData?.id!!, amount)
                ?.subscribe({

                    getAlipaySign(it["id"].asString)

                }) {
                    hideLoading()
                    showError(it)
                }
    }

    /**
     * 2.获取支付宝签名
     */
    private fun getAlipaySign(orderId: String) {

        MemberCardRepository.default?.getAlipaySign(orderId)
                ?.subscribe({
                    hideLoading()
                    val payThread: Thread
                    payThread = Thread {
                        val payTask = PayTask(this)
                        val result = payTask.pay(it["ali_sign"].asString, true)
                        val msg = Message()
                        msg.what = 1
                        msg.obj = result
                        mHandler.sendMessage(msg)
                    }
                    payThread.start()

                }) {
                    hideLoading()
                    showError(it)
                }
    }

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                val payResult = msg.obj.toString()
                if (payResult.isNotEmpty()) {
                    when {
                        payResult.contains("resultStatus={9000}") -> {
                            EventBus.getDefault().post("", GlobalConstant.DELETE_MEMBER_CARD_SUC)
                            showShortToast("充值成功")
                            Handler().postDelayed({
                                finish()
                            }, 300)
                        }
                        payResult.contains("resultStatus={6001}") -> {
                            showShortToast("支付已取消")
                        }
                        else -> showShortToast("充值失败")
                    }
                }
            }
        }
    }

    override fun getData() {
        val data: ArrayList<DepositValueProtocol> = ArrayList()
        data.add(DepositValueProtocol(200, true))
        data.add(DepositValueProtocol(500, false))
        data.add(DepositValueProtocol(1000, false))
        data.add(DepositValueProtocol(2000, false))
        data.add(DepositValueProtocol(3000, false))
        data.add(DepositValueProtocol(5000, false))
        data.add(DepositValueProtocol(8000, false))
        data.add(DepositValueProtocol(0, false)) //其他金额
        adapter?.setNewData(data)
    }
}