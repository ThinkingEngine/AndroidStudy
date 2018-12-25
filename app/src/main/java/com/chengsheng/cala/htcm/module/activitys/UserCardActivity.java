package com.chengsheng.cala.htcm.module.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserCardActivity extends BaseActivity {

    private ImageView closeUserCardButton;
    private SimpleDraweeView userHeaderCard;
    private TextView userNameCard;
    private ImageView userQrCard;
    private TextView userIdCard;



    @Override
    public int getLayoutId() {
        return R.layout.activity_user_card;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        FamiliesListItem data = (FamiliesListItem) bundle.getSerializable("FAMILIES_INFO");
        Bitmap bitmap = QRCodeUtil.createQRImage(data.getHealth_card_no(),FuncUtils.dip2px(220),FuncUtils.dip2px(220));
        initViews();

        userNameCard.setText(data.getFullname());
        userHeaderCard.setImageURI(data.getAvatar_path());
        userIdCard.setText(data.getHealth_card_no());
        userQrCard.setImageBitmap(bitmap);

        closeUserCardButton.setOnClickListener(v -> finish());

    }

    @Override
    public void getData() {

    }

    private void initViews() {
        closeUserCardButton = findViewById(R.id.close_user_card_button);
        userHeaderCard = findViewById(R.id.user_header_card);
        userNameCard = findViewById(R.id.user_name_card);
        userQrCard = findViewById(R.id.user_qr_card);
        userIdCard = findViewById(R.id.user_id_card);
    }
}
