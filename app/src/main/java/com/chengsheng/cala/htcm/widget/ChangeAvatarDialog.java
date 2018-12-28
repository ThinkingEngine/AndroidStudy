package com.chengsheng.cala.htcm.widget;

import android.content.Context;
import android.view.View;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseBottomDialog;

/**
 * Author: 任和
 * CreateDate: 2018/12/28 5:33 PM
 * Description:
 */
public class ChangeAvatarDialog extends BaseBottomDialog {

    private IChoicePictureListener listener;

    public ChangeAvatarDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.bottom_select_layout;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tvTakePhoto).setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onTakePhoto();
            }
            dismissDialog();
        });

        view.findViewById(R.id.tvChoiceFromGallery).setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onChoiceFromGallery();
            }
            dismissDialog();

        });

        view.findViewById(R.id.tvCancelChange).setOnClickListener(view1 -> {
            dismissDialog();
        });
    }

    public ChangeAvatarDialog setChoiceListener(IChoicePictureListener listener) {
        this.listener = listener;
        return this;
    }
}
