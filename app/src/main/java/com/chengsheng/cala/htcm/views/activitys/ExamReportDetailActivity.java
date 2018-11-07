package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamAdviceExpandableListAdapter;
import com.chengsheng.cala.htcm.views.adapters.ExamResultRecyclerAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class ExamReportDetailActivity extends AppCompatActivity {
    private ImageView backExamReport;
    private TextView examReportDetailName;
    private TextView examReportDetailPerson;
    private TextView examReportDetailNum;
    private TextView examReportDetailDate;
    private TextView abnormalTips;
    private MyExpandableListView examAdvicesExpandable;
    private RecyclerView examResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_report_detail);

        initViews();

        ExamAdviceExpandableListAdapter adapter = new ExamAdviceExpandableListAdapter(this);
        examAdvicesExpandable.setAdapter(adapter);

        List<String> datas = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            datas.add("科室 "+i);
        }
        ExamResultRecyclerAdapter adapter1 = new ExamResultRecyclerAdapter(this,datas);
        examResultList.setLayoutManager(new LinearLayoutManager(this));
        examResultList.setAdapter(adapter1);
    }

    private void initViews(){
        backExamReport = findViewById(R.id.back_exam_report);
        examReportDetailName = findViewById(R.id.exam_report_detail_name);
        examReportDetailPerson = findViewById(R.id.exam_report_detail_person);
        examReportDetailNum = findViewById(R.id.exam_report_detail_num);
        examReportDetailDate = findViewById(R.id.exam_report_detail_date);
        abnormalTips = findViewById(R.id.abnormal_tips);
        examAdvicesExpandable = findViewById(R.id.exam_advices_expandable);
        examResultList = findViewById(R.id.exam_result_list);
    }
}
