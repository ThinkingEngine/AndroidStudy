package com.chengsheng.cala.htcm.views.activitys;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class MyCollectionActivity extends AppCompatActivity {
    private TextView titleText;
    private TabLayout tabHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        titleText = (TextView) findViewById(R.id.title_header_my_collection).findViewById(R.id.menu_bar_title);
        tabHeader = (TabLayout) findViewById(R.id.my_collection_tab_header);

        titleText.setText("我的收藏");

        tabHeader.addTab(tabHeader.newTab().setText("体检套餐"));
        tabHeader.addTab(tabHeader.newTab().setText("医生"));
        tabHeader.addTab(tabHeader.newTab().setText("特色服务"));
        tabHeader.addTab(tabHeader.newTab().setText("文章资讯"));
        tabHeader.addTab(tabHeader.newTab().setText("常见疾病"));
    }
}
