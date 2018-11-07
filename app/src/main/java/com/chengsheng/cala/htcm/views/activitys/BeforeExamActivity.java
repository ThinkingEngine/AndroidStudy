package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class BeforeExamActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back;
    private TextView examDateDetailBefore,examAddressDetailBefore;
    private ImageView userCode;
    private TextView userNameBeforeExam,userSomeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_exam);
    }

    private void initViews(){
        title = findViewById(R.id.title_header_before_exam).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_before_exam).findViewById(R.id.back_login);

        examAddressDetailBefore = findViewById(R.id.exam_address_detail_before);
        examDateDetailBefore = findViewById(R.id.exam_date_detail_before);
        userCode = findViewById(R.id.user_code);
        userNameBeforeExam = findViewById(R.id.user_name_before_exam);
        userSomeInfo = findViewById(R.id.user_some_info);

        title.setText("检前须知");
    }
}
