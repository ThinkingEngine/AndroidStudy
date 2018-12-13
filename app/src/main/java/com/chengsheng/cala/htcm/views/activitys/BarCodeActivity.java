package com.chengsheng.cala.htcm.views.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;

public class BarCodeActivity extends BaseActivity {
    private ImageButton closeBarCode;
    private ImageView barCodeMark;
    private TextView numberBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        closeBarCode = findViewById(R.id.close_bar_code);
        barCodeMark = findViewById(R.id.bar_code_mark);
        numberBarCode = findViewById(R.id.number_bar_code);

        String number = getIntent().getStringExtra("FAMILIES_INFO");

        Bitmap bitmap = QRCodeUtil.createBarcode(number,FuncUtils.px2dip(320),FuncUtils.px2dip(85));

        barCodeMark.setImageBitmap(bitmap);

        numberBarCode.setText(number);

        closeBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
