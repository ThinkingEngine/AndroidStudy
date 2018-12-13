package com.chengsheng.cala.htcm.views.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserCardActivity extends BaseActivity {

    private ImageView closeUserCardButton;
    private SimpleDraweeView userHeaderCard;
    private TextView userNameCard;
    private ImageView userQrCard;
    private TextView userIdCard;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card);

        Bundle bundle = getIntent().getExtras();
        FamiliesListItem data = (FamiliesListItem) bundle.getSerializable("FAMILIES_INFO");

        Bitmap bitmap = QRCodeUtil.createQRImage(data.getHealth_card_no(),FuncUtils.dip2px(220),FuncUtils.dip2px(220));
        Log.e("QRC","bitmap是否为空:"+ String.valueOf(bitmap == null));
        initViews();

        userNameCard.setText(data.getFullname());
        userHeaderCard.setImageURI(data.getAvatar_path());
        userIdCard.setText(data.getHealth_card_no());
        userQrCard.setImageBitmap(bitmap);

        closeUserCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initViews() {
        closeUserCardButton = findViewById(R.id.close_user_card_button);
        userHeaderCard = findViewById(R.id.user_header_card);
        userNameCard = findViewById(R.id.user_name_card);
        userQrCard = findViewById(R.id.user_qr_card);
        userIdCard = findViewById(R.id.user_id_card);
    }
}
