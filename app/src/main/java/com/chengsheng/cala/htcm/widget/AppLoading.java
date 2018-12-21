package com.chengsheng.cala.htcm.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.chengsheng.cala.htcm.R;

/**
 * Author: 任和
 * CreateDate: 2018/12/21 2:21 PM
 * Description:
 */
public class AppLoading extends ProgressDialog {

    public AppLoading(Context context) {
        super(context);
    }

    public AppLoading(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

