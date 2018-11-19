package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamDetailTypeARecyclerAdapter;
import com.chengsheng.cala.htcm.views.adapters.ExamDetailTypeBExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExamResultUnscrambleActivity extends AppCompatActivity {

    private TextView title,childTitle;
    private ImageView back;
    private TextView examDetailName,examAbnormalTips;
    private TextView docResultText;
    private ImageView docSignatureMarkB;
    private RecyclerView examDetailTypeA;
    private ExpandableListView examDetailTypeB;
    private TextView tips;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_unscramble);

        String str = getIntent().getBundleExtra("mesg").getString("TYPE");
        initViews();
        if(str.equals("table_a")){
            examDetailTypeB.setVisibility(View.INVISIBLE);
            examDetailTypeA.setVisibility(View.VISIBLE);
            tips.setVisibility(View.VISIBLE);

            List<String> datas = new ArrayList<>();
            datas.add("心率");
            datas.add("心音");
            datas.add("肺部听诊");
            datas.add("肝脏触及");
            datas.add("脾肺触诊");
            ExamDetailTypeARecyclerAdapter adapter = new ExamDetailTypeARecyclerAdapter(this,datas);
            examDetailTypeA.setLayoutManager(new LinearLayoutManager(this));
            examDetailTypeA.setAdapter(adapter);

        }else if(str.equals("table_b")){
            examDetailTypeB.setVisibility(View.VISIBLE);
            examDetailTypeA.setVisibility(View.INVISIBLE);
            tips.setVisibility(View.INVISIBLE);
            ExamDetailTypeBExpandableListViewAdapter adapter = new ExamDetailTypeBExpandableListViewAdapter(this);
            examDetailTypeB.setAdapter(adapter);
        }
    }

    private void initViews(){
        title = findViewById(R.id.title_header_exam_result_unscramble).findViewById(R.id.menu_bar_title);
        childTitle = findViewById(R.id.title_header_exam_result_unscramble).findViewById(R.id.message_mark_text);
        back = findViewById(R.id.title_header_exam_result_unscramble).findViewById(R.id.back_login);
        examDetailName = findViewById(R.id.exam_detail_name);
        examAbnormalTips = findViewById(R.id.exam_abnormal_tips);
        docResultText = findViewById(R.id.doc_result_text);
        docSignatureMarkB = findViewById(R.id.doc_signature_mark_b);
        examDetailTypeA = findViewById(R.id.exam_detail_type_a);
        examDetailTypeB = findViewById(R.id.exam_detail_type_b);
        tips = findViewById(R.id.tips);

        title.setText("检查详情");
        childTitle.setText("报告解读");
    }
}
