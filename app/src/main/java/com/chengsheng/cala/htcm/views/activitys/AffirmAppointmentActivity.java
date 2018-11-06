package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.AffirmAppointmentExamPersonAdapter;
import com.chengsheng.cala.htcm.views.adapters.OtherSelectionItemAdapter;

public class AffirmAppointmentActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back;
    private TextView test,immediatePay;
    private RecyclerView examPersonRecycler,otherSelectItemRecycler;
    private RelativeLayout keyIllnessScreeningCoatiner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm_appointment);
        //初始化Activity的视图。
        initViews();

        immediatePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmAppointmentActivity.this,ModePaymentActivity.class);
                startActivity(intent);
            }
        });

        keyIllnessScreeningCoatiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmAppointmentActivity.this,AddExamPersonActivity.class);
                startActivity(intent);
            }
        });

        if(examPersonRecycler != null){
            examPersonRecycler.setLayoutManager(new LinearLayoutManager(this));

            AffirmAppointmentExamPersonAdapter adapter = new AffirmAppointmentExamPersonAdapter(this);

            examPersonRecycler.setAdapter(adapter);
            examPersonRecycler.setNestedScrollingEnabled(false);
        }else{
            Log.e("ERROR","Recycler异常!");
        }

        if(otherSelectItemRecycler != null){
            otherSelectItemRecycler.setLayoutManager(new LinearLayoutManager(this));

            OtherSelectionItemAdapter adapter = new OtherSelectionItemAdapter(this);

            otherSelectItemRecycler.setAdapter(adapter);
            examPersonRecycler.setNestedScrollingEnabled(false);
        }else{
            Log.e("ERROR","Recycler异常!");
        }

    }

    private void initViews(){
        title = findViewById(R.id.title_header_affirm_appointment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_affirm_appointment).findViewById(R.id.back_login);
        test = findViewById(R.id.affirm_appointment_model_a).findViewById(R.id.appointment_name);
        immediatePay = findViewById(R.id.immediate_pay);
        examPersonRecycler = findViewById(R.id.affirm_appointment_model_b).findViewById(R.id.exam_person_recycler);
        otherSelectItemRecycler = findViewById(R.id.affirm_appointment_model_c).findViewById(R.id.other_select_item_recycler);
        keyIllnessScreeningCoatiner = findViewById(R.id.affirm_appointment_model_b).findViewById(R.id.key_illness_screening_coatiner);

        title.setText("确认预约");
        test.setText("测试文本");
    }
}
