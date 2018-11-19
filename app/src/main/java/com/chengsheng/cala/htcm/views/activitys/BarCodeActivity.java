package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class BarCodeActivity extends AppCompatActivity {
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
    }
}
