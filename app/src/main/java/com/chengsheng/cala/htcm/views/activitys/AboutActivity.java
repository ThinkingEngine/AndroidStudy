package com.chengsheng.cala.htcm.views.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class AboutActivity extends BaseActivity {
    private TextView menuBarTitle;
    private ImageView backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();
    }

    private void initViews(){
        menuBarTitle = findViewById(R.id.title_header_about).findViewById(R.id.menu_bar_title);
        backLogin = findViewById(R.id.title_header_about).findViewById(R.id.back_login);

        menuBarTitle.setText("关于");
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
