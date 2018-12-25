package com.chengsheng.cala.htcm.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Objects;


/**
 * Author: 任和
 * CreateDate: 2018/12/25 3:00 PM
 * Description: 会员卡更多操作
 */
public class MemberMoreDialog {

    private Dialog shareDialog;

    @SuppressLint("CheckResult")
    public MemberMoreDialog build(Context context, OnDeleteListener listener) {
        shareDialog = new Dialog(context, R.style.bottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_member_more, null);
        shareDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        Objects.requireNonNull(shareDialog.getWindow()).setGravity(Gravity.BOTTOM);
        shareDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        TextView tvDelete = contentView.findViewById(R.id.tvDelete);
        TextView tvCancelDelete = contentView.findViewById(R.id.tvCancelDelete);

        RxView.clicks(tvCancelDelete).subscribe(it -> {
            this.dismissDialog();
        });

        RxView.clicks(tvDelete).subscribe(it -> {
            if (listener != null) {
                listener.onDelete();
            }
        });

        return this;
    }

    public MemberMoreDialog showDialog() {
        if (shareDialog != null) {
            shareDialog.show();
        }

        return this;
    }

    public MemberMoreDialog dismissDialog() {
        if (shareDialog != null) {
            shareDialog.dismiss();
        }

        return this;
    }

    public interface OnDeleteListener {
        void onDelete();
    }

}
