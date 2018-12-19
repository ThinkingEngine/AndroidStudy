package com.chengsheng.cala.htcm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.ToastUtil;

import static com.chengsheng.cala.htcm.data.ResponseExtentionKt.checkError;

/**
 * Author: 任和
 * CreateDate: 2018/12/18 9:36 AM
 * Description: Fragment父类
 */
public abstract class BaseFragment extends Fragment {

    protected Context context = getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * 获取当前layoutId
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView(View view);

    /**
     * 获取数据
     */
    public abstract void getData();

    /**
     * 跳转到指定页面
     */
    public void startActivity(BaseActivity activity) {
        ActivityUtil.Companion.startActivity(context, activity);
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
        ActivityUtil.Companion.startActivity(context, activity, bundle);
    }

    /**
     * 根据登录状态跳转到对应页
     */
    public void startActivityWithLoginStatus(BaseActivity activity) {
        ActivityUtil.Companion.startActivityWithLoginStatus(context, activity);
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
        ActivityUtil.Companion.startActivityWithLoginStatus(context, activity, bundle);
    }

    /**
     * Toast短提示
     */
    public void showShortToast(String content) {
        if (content != null) {
            ToastUtil.showShortToast(context, content);
        }
    }

    /**
     * Toast长提示
     */
    public void showLongToast(String content) {
        if (content != null) {
            ToastUtil.showLongToast(context, content);
        }
    }

    /**
     * 显示请求接口返回的信息
     */
    public void showError(Throwable throwable) {
        showShortToast(checkError(throwable, context));
    }
}


