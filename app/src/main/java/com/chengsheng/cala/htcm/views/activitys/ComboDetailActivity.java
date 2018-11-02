package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamItemExpandableListViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;

public class ComboDetailActivity extends AppCompatActivity {

    private TextView title,immediateAppointment;
    private ImageView collect,share,back;
    private MyExpandableListView examItemComboExpandable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_detail);

        initViews();
        ExamItemExpandableListViewAdapter adapter = new ExamItemExpandableListViewAdapter(this);
        examItemComboExpandable.setAdapter(adapter);

        immediateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboDetailActivity.this,AffirmAppointmentActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initViews(){
        title = findViewById(R.id.title_header_combo_detail).findViewById(R.id.menu_bar_title);
        collect = findViewById(R.id.title_header_combo_detail).findViewById(R.id.collect_icon);
        share = findViewById(R.id.title_header_combo_detail).findViewById(R.id.share);
        back = findViewById(R.id.title_header_combo_detail).findViewById(R.id.back_login);
        immediateAppointment = findViewById(R.id.immediate_appointment);
        examItemComboExpandable = findViewById(R.id.exam_item_combo_expandable);

        title.setText("套餐详情");
    }
}
