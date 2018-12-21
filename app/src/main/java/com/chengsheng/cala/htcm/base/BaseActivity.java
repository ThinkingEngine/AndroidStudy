package com.chengsheng.cala.htcm.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.manager.ActivityManager;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.ToastUtil;
import com.chengsheng.cala.htcm.widget.AppLoading;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.Synchronized;

import static com.chengsheng.cala.htcm.data.ResponseExtentionKt.checkError;

/**
 * Author: 任和
 * CreateDate: 2018/12/17 8:50 PM
 * Description: Activity父类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //默认竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getAppManager().addActivity(this);
        initView();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().finishActivity(this);
    }

    /**
     * 获取当前layoutId
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 获取数据
     */
    public abstract void getData();

    /**
     * Toast短提示
     */
    public void showShortToast(String content) {
        ToastUtil.showShortToast(this, content);
    }

    /**
     * Toast长提示
     */
    public void showLongToast(String content) {
        ToastUtil.showLongToast(this, content);
    }

    /**
     * 显示请求接口返回的信息
     */
    public void showError(Throwable throwable) {
        showShortToast(checkError(throwable, this));
    }

    /**
     * 跳转到指定页面
     */
    public void startActivity(BaseActivity activity) {
        ActivityUtil.Companion.startActivity(this, activity);
    }

    /**
     * 跳转到指定页面(携带参数)
     * <p>
     * val bundle = Bundle()
     * bundle.putString("id", id)
     * startActivity(bundle, TestActivity())
     * <p>
     * 接收参数：
     * val id: String = intent.getStringExtra("id")
     */
    public void startActivity(BaseActivity activity, Bundle bundle) {
        ActivityUtil.Companion.startActivity(this, activity, bundle);
    }

    /**
     * 根据登录状态跳转到对应页
     */
    public void startActivityWithLoginStatus(BaseActivity activity) {
        ActivityUtil.Companion.startActivityWithLoginStatus(this, activity);
    }

    /**
     * 根据登录状态跳转到对应页(携带参数)
     * <p>
     * val bundle = Bundle()
     * bundle.putString("id", id)
     * startActivity(bundle, TestActivity())
     * <p>
     * 接收参数：
     * val id: String = intent.getStringExtra("id")
     */
    public void startActivityWithLoginStatus(BaseActivity activity, Bundle bundle) {
        ActivityUtil.Companion.startActivityWithLoginStatus(this, activity, bundle);
    }

    @Synchronized
    protected void showLoading() {
        try {
            if (null == progress) {
                progress = new AppLoading(this, R.style.transparentProgressDialog);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
            }
            if (null != progress && !progress.isShowing()) {
                progress.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Synchronized
    protected void hideLoading() {
        try {
            if (null != progress && progress.isShowing()) {
                if (null != progress && progress.isShowing()) {
                    //防止出现闪一下问题，延迟执行
                    Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(new Observer<Long>() {
                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            progress.dismiss();
                        }

                        @Override
                        public void onSubscribe(Disposable d) {
                            if (d.isDisposed()) {
                                d.dispose();
                            }
                        }

                        @Override
                        public void onNext(Long aLong) {

                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
