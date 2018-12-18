package com.chengsheng.cala.htcm.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chengsheng.cala.htcm.R;

public class CustomComboDialog extends Dialog {
    private ImageView closeRecommend;

    public CustomComboDialog(Context context) {
        super(context);
    }

    public CustomComboDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomComboDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootViews = LayoutInflater.from(getContext()).inflate(R.layout.custom_combo_dialog_layout, null);
        setContentView(rootViews);

        closeRecommend = rootViews.findViewById(R.id.close_recommend);
        closeRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
