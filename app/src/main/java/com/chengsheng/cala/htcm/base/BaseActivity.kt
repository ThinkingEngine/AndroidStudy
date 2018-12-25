package com.chengsheng.cala.htcm.base

import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.data.checkError
import com.chengsheng.cala.htcm.manager.ActivityManager
import com.chengsheng.cala.htcm.utils.ActivityUtil
import com.chengsheng.cala.htcm.utils.ToastUtil
import com.chengsheng.cala.htcm.widget.AppLoading
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_card.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:00 PM
 * Description: Activity父类
 */
abstract class BaseActivity : AppCompatActivity() {

    private var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //默认竖屏显示
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ActivityManager.getAppManager().addActivity(this)
        initView()
        getData()

        EventBus.getDefault().register(this)

        titleBar?.setFinishClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        ActivityManager.getAppManager().finishActivity(this)
    }

    /**
     * 获取当前layoutId
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 获取数据
     */
    abstract fun getData()

    /**
     * Toast短提示
     */
    fun showShortToast(content: String) {
        ToastUtil.showShortToast(this, content)
    }

    /**
     * Toast长提示
     */
    fun showLongToast(content: String) {
        ToastUtil.showLongToast(this, content)
    }

    /**
     * 显示请求接口返回的信息
     */
    fun showError(throwable: Throwable) {
        showShortToast(checkError(throwable, this))
    }

    /**
     * 跳转到指定页面
     */
    fun startActivity(activity: BaseActivity) {
        ActivityUtil.startActivity(this, activity)
    }

    /**
     * 跳转到指定页面(携带参数)
     *
     *
     * val bundle = Bundle()
     * bundle.putString("id", id)
     * startActivity(bundle, TestActivity())
     *
     *
     * 接收参数：
     * val id: String = intent.getStringExtra("id")
     */
    fun startActivity(activity: BaseActivity, bundle: Bundle) {
        ActivityUtil.startActivity(this, activity, bundle)
    }

    /**
     * 根据登录状态跳转到对应页
     */
    fun startActivityWithLoginStatus(activity: BaseActivity) {
        ActivityUtil.startActivityWithLoginStatus(this, activity)
    }

    /**
     * 根据登录状态跳转到对应页(携带参数)
     *
     *
     * val bundle = Bundle()
     * bundle.putString("id", id)
     * startActivity(bundle, TestActivity())
     *
     *
     * 接收参数：
     * val id: String = intent.getStringExtra("id")
     */
    fun startActivityWithLoginStatus(activity: BaseActivity, bundle: Bundle) {
        ActivityUtil.startActivityWithLoginStatus(this, activity, bundle)
    }

    @Synchronized
    protected fun showLoading() {
        try {
            if (null == progress) {
                progress = AppLoading(this, R.style.transparentProgressDialog)
                progress!!.isIndeterminate = true
                progress!!.setCancelable(false)
                progress!!.setCanceledOnTouchOutside(false)
            }
            if (null != progress && !progress!!.isShowing) {
                progress!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Synchronized
    protected fun hideLoading() {
        try {
            if (null != progress && progress!!.isShowing) {
                if (null != progress && progress!!.isShowing) {
                    //防止出现闪一下问题，延迟执行
                    Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(object : Observer<Long> {
                        override fun onNext(t: Long) {

                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onComplete() {
                            progress!!.dismiss()
                        }

                        override fun onSubscribe(d: Disposable) {
                            if (d.isDisposed) {
                                d.dispose()
                            }
                        }
                    })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}