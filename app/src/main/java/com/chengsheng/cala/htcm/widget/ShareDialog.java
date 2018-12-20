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
 * CreateDate: 2018/12/20 3:36 PM
 * Description: 分享提示框
 */
public class ShareDialog {

    private Dialog shareDialog;
    private OnShareClickListener shareClickListener;

    @SuppressLint("CheckResult")
    public ShareDialog build(Context context) {
        shareDialog = new Dialog(context, R.style.bottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_share, null);
        shareDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        Objects.requireNonNull(shareDialog.getWindow()).setGravity(Gravity.BOTTOM);
        shareDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        TextView tvShareQQ = contentView.findViewById(R.id.tvShareQQ);
        TextView tvShareWechat = contentView.findViewById(R.id.tvShareWechat);
        TextView tvShareMoment = contentView.findViewById(R.id.tvShareMoment);
        TextView tvShareQzone = contentView.findViewById(R.id.tvShareQzone);
        TextView tvCopyLink = contentView.findViewById(R.id.tvCopyLink);
        TextView tvCancelShare = contentView.findViewById(R.id.tvCancelShare);

        RxView.clicks(tvCancelShare).subscribe(it -> {
            this.dismissDialog();
        });

        RxView.clicks(tvShareQQ).subscribe(it -> {
            if (shareClickListener != null) {
                shareClickListener.shareToQQ();
            }
        });

        RxView.clicks(tvShareWechat).subscribe(it -> {
            if (shareClickListener != null) {
                shareClickListener.shareToWeChat();
            }
        });

        RxView.clicks(tvShareMoment).subscribe(it -> {
            if (shareClickListener != null) {
                shareClickListener.shareToMoment();
            }
        });

        RxView.clicks(tvShareQzone).subscribe(it -> {
            if (shareClickListener != null) {
                shareClickListener.shareToQZone();
            }
        });

        RxView.clicks(tvCopyLink).subscribe(it -> {
            if (shareClickListener != null) {
                shareClickListener.copyLink();
            }
        });

        return this;
    }

    /**
     * 显示分享框
     */
    public ShareDialog showDialog() {
        if (shareDialog != null) {
            shareDialog.show();
        }

        return this;
    }

    /**
     * 隐藏分享框
     */
    public ShareDialog dismissDialog() {
        if (shareDialog != null) {
            shareDialog.dismiss();
        }

        return this;
    }

    /**
     * 设置分享渠道监听
     */
    public ShareDialog setOnShareListener(OnShareClickListener shareClickListener) {
        this.shareClickListener = shareClickListener;
        return this;
    }

    public interface OnShareClickListener {
        /**
         * 分享给微信好友
         */
        void shareToWeChat();

        /**
         * 分享到朋友圈
         */
        void shareToMoment();

        /**
         * 分享给QQ好友
         */
        void shareToQQ();

        /**
         * 分享到QQ空间
         */
        void shareToQZone();

        /**
         * 复制链接
         */
        void copyLink();
    }

}
