package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class AffirmAppointmentActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back;
    private TextView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm_appointment);

        initViews();
    }

    private void initViews(){
        title = findViewById(R.id.title_header_affirm_appointment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_affirm_appointment).findViewById(R.id.back_login);
        test = findViewById(R.id.affirm_appointment_model_a).findViewById(R.id.appointment_name);

        title.setText("确认预约");
        test.setText("测试文本");
    }
}
