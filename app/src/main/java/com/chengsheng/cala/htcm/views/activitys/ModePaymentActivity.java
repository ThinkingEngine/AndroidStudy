package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class ModePaymentActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_payment);

        title = findViewById(R.id.title_header_mode_payment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_mode_payment).findViewById(R.id.back_login);

        title.setText("支付方式");
    }
}
