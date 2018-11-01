package com.chengsheng.cala.htcm.views.activitys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamAppointmentRecyclerAdapter;
import com.chengsheng.cala.htcm.views.customviews.HeaderScrollView;

import java.util.ArrayList;
import java.util.List;

public class ExamAppointmentActivity extends AppCompatActivity implements HeaderScrollView.StopCall{
    private HeaderScrollView headerScrollView;
    private RelativeLayout imageView;
    private RelativeLayout textView;

    private RecyclerView examAppointmentItem;
    private SwipeRefreshLayout refreshExamAppointmentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_appointment);
        headerScrollView = (HeaderScrollView) findViewById(R.id.scroll_view_header);
        imageView = findViewById(R.id.rl1);
        textView = findViewById(R.id.rl2);
        examAppointmentItem = findViewById(R.id.exam_appointment_recycler);
        refreshExamAppointmentPage = findViewById(R.id.refresh_exam_appointment_page);

        //测试数据
        List<String> datas = new ArrayList<>();
        for(int i = 0; i < 5;i++){
            datas.add("套餐测试");
        }

        final ExamAppointmentRecyclerAdapter adapter = new ExamAppointmentRecyclerAdapter(this,datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        examAppointmentItem.setLayoutManager(linearLayoutManager);

        examAppointmentItem.setAdapter(adapter);
        examAppointmentItem.setNestedScrollingEnabled(false);
        refreshExamAppointmentPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                data.add("new");
                Toast.makeText(ExamAppointmentActivity.this,"下拉刷新--",Toast.LENGTH_SHORT).show();
//                appointment.notifyDataSetChanged();
                refreshExamAppointmentPage.setRefreshing(false);
            }
        });

        headerScrollView.setCallback(this);


    }

    @Override
    public void stopSlide(boolean isStop) {
        if(isStop){
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }else{
            imageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
