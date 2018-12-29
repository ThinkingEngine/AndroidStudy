package com.chengsheng.cala.htcm.utils

import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.chengsheng.cala.htcm.HTCMApplication
import com.chengsheng.cala.htcm.R

/**
 * Author: 任和
 * CreateDate: 2018/12/25 5:34 PM
 * Description: 短信验证码倒计时
 */

private var countDownTimer: CountDownTimer? = null

/**
 * 验证码倒计时
 */
fun initCaptchaTimer(textView: TextView) {
    countDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            textView.visibility = View.VISIBLE
            textView.text = (HTCMApplication.appContext.getString(R.string.count_down_time,
                    millisUntilFinished / 1000))
        }

        override fun onFinish() {
            textView.text = "获得验证码"
            textView.isEnabled = true
            releaseCaptchaTimer()
        }
    }

    countDownTimer?.start()
    textView.isEnabled = false
}

/**
 * 回收验证码倒计时执行对象
 */
fun releaseCaptchaTimer() {
    if (countDownTimer != null) {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}