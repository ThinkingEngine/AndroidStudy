package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamItemExpandableListViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;

public class ExamDetailsActivity extends AppCompatActivity {
    private ImageView backButton;
    private TextView menuBarTitle;
    private MyExpandableListView examItemExpandable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);
        initViews();

//        ExamItemExpandableListViewAdapter adapter = new ExamItemExpandableListViewAdapter(this);
//        examItemExpandable.setAdapter(adapter);
    }

    private void initViews(){
        backButton = findViewById(R.id.title_header_exam_details).findViewById(R.id.back_login);
        menuBarTitle = findViewById(R.id.title_header_exam_details).findViewById(R.id.menu_bar_title);
        examItemExpandable = findViewById(R.id.exam_item_expandable);

        menuBarTitle.setText("详情");
    }
}
