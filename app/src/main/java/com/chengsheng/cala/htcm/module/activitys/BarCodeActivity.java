package com.chengsheng.cala.htcm.module.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;

/**
 * Author: 蔡浪
 * CreateDate: 2018/12/17 3:50 PM
 * Description:
 */
public class BarCodeActivity extends BaseActivity {
    private ImageView closeBarCode;
    private ImageView barCodeMark;
    private TextView numberBarCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bar_code;
    }

    @Override
    public void initView() {
        closeBarCode = findViewById(R.id.close_bar_code);
        barCodeMark = findViewById(R.id.bar_code_mark);
        numberBarCode = findViewById(R.id.number_bar_code);

        String number = getIntent().getStringExtra("FAMILIES_INFO");

        Bitmap bitmap = QRCodeUtil.createBarcode(number, FuncUtils.dip2px(320), FuncUtils.dip2px(85));

        barCodeMark.setImageBitmap(bitmap);

        numberBarCode.setText(number);

        closeBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getData() {

    }
}
