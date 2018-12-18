package com.chengsheng.cala.htcm.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chengsheng.cala.htcm.manager.ActivityManager;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.ToastUtil;

/**
 * Author: 任和
 * CreateDate: 2018/12/17 8:50 PM
 * Description: Activity父类
 */
public abstract class BaseActivity extends AppCompatActivity {

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
}
