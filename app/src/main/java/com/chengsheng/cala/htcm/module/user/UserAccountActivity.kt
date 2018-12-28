package com.chengsheng.cala.htcm.module.user

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.data.repository.UserRepository
import com.chengsheng.cala.htcm.module.activitys.ChangeAvatarActivity
import com.chengsheng.cala.htcm.module.activitys.ChangePhoneActivity
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo
import com.chengsheng.cala.htcm.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_account_setting.*
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode


/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:54 AM
 * Description: 账户设置
 */
@SuppressLint("CheckResult")
class UserAccountActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_account_setting
    }

    override fun initView() {
        val userInfo: UserInfo = intent.getParcelableExtra("userInfo")
        stvNickName?.setRightString(userInfo.nickname)
        stvMobile?.setRightString(StringUtils.formatMobile(userInfo.phone_number))

        setAvatar(userInfo.avatar_url)

        //修改头像
        RxView.clicks(stvAvatar).subscribe {
            val bundle = Bundle()
            bundle.putString("avatar_url", userInfo.avatar_url)
            startActivity(ChangeAvatarActivity(), bundle)
        }

        //修改昵称
        RxView.clicks(stvNickName).subscribe {

        }

        //修改绑定的手机号
        RxView.clicks(stvMobile).subscribe {
            val bundle = Bundle()
            bundle.putString("mobile", userInfo.phone_number)
            startActivity(ChangePhoneActivity(), bundle)

        }
    }

    private fun setAvatar(avatar: String) {
        Glide.with(this)
                .asBitmap()
                .load(avatar)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                        circularBitmapDrawable.isCircular = true
                        ivUserAvatar?.setImageDrawable(circularBitmapDrawable)
                    }
                })
    }

    override fun getData() {

    }

    @Subscriber(mode = ThreadMode.MAIN, tag = "updateAvatarSuc")
    fun refresh(event: Any) {
        val newAvatar = event as String
        setAvatar(newAvatar)
        showLoading()
        UserRepository.default?.updateUserInfo("", "", newAvatar)
                ?.subscribe({

                    hideLoading()
                    showShortToast("头像修改成功")
                    
                }) {
                    hideLoading()
                }
    }
}