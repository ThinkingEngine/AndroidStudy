package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class CommonUseActivity extends AppCompatActivity {
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_use);

        titleText = (TextView) findViewById(R.id.title_header_common_use).findViewById(R.id.menu_bar_title);

        titleText.setText("通用");
    }
}
