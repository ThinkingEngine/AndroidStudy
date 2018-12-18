package com.chengsheng.cala.htcm.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.module.account.LoginActivity
import com.chengsheng.cala.htcm.utils.UserUtil.isLogin

/**
 * Author: 任和
 * CreateDate: 2018/12/18 11:12 AM
 * Description: Activity页面间跳转
 */
class ActivityUtil {

    companion object {
        /**
         * 跳转到指定页面
         */
        fun startActivity(context: Context, activity: BaseActivity) {
            context.startActivity(Intent(context, activity::class.java))
        }

        /**
         * 跳转到指定页面(携带参数)
         *
         *  val bundle = Bundle()
         *  bundle.putString("id", id)
         *  startActivity(bundle, TestActivity())
         *
         *  接收参数：
         *  val id: String = intent.getStringExtra("id")
         */
        fun startActivity(context: Context, activity: BaseActivity, bundle: Bundle) {
            val intent = Intent(context, activity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

        /**
         * 根据登录状态跳转到对应页
         */
        fun startActivityWithLoginStatus(context: Context, activity: BaseActivity) {
            val target: BaseActivity = if (isLogin()) activity else LoginActivity()
            startActivity(context, target)
        }

        /**
         * 根据登录状态跳转到对应页(携带参数)
         *
         *  val bundle = Bundle()
         *  bundle.putString("id", id)
         *  startActivity(bundle, TestActivity())
         *
         *  接收参数：
         *  val id: String = intent.getStringExtra("id")
         */
        fun startActivityWithLoginStatus(context: Context, activity: BaseActivity, bundle: Bundle) {
            val target: BaseActivity = if (isLogin()) activity else LoginActivity()
            startActivity(context, target, bundle)
        }
    }
}