package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.MemberCardAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshActivity
import com.chengsheng.cala.htcm.constant.GlobalConstant.UPDATE_MEMBER_CARD_SUCCESS
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.protocol.MemberCardProtocol
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.member_card_list_footer.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

/**
 * Author: 任和
 * CreateDate: 2018/12/23 4:10 PM
 * Description: 会员卡
 */
@SuppressLint("CheckResult")
class MemberCardActivity : BaseRefreshActivity<MemberCardProtocol.ItemsBean>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_member_card
    }

    override fun initViews() {
        //添加卡
        RxView.clicks(layoutAddCard).subscribe {
            startActivity(AddMemCardActivity())
        }
    }

    override fun getData(page: Int) {
        MemberCardRepository.default?.getCardList()
                ?.subscribe({

                    if (it.items != null && it.items.isNotEmpty()) {
                        recyclerView?.visibility = View.VISIBLE
                        fillData(it.items)
                    } else {
                        smartRefreshLayout?.finishRefresh()
                        smartRefreshLayout?.finishLoadmore()
                        recyclerView?.visibility = View.GONE
                    }

                }) {
                    showError(it)
                }
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<MemberCardProtocol.ItemsBean>? {
        return MemberCardAdapter(this, ArrayList())
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = UPDATE_MEMBER_CARD_SUCCESS)
    fun refresh(event: String) {
        getData(FIRST_PAGE)
    }
}