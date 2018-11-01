package com.chengsheng.cala.htcm.views.activitys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.AIAssistantSubRecyclerView;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class AIAssistantActivity extends AppCompatActivity {
    private TextView title;
    private TextView subhead;
    private RecyclerView aiAssistantList;
    private SwipeRefreshLayout refreshAiAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiassistant);

        title = findViewById(R.id.title_header_ai_assistant).findViewById(R.id.menu_bar_title);
        subhead = findViewById(R.id.title_header_ai_assistant).findViewById(R.id.message_mark_text);
        aiAssistantList = findViewById(R.id.ai_assistant_list);
        refreshAiAssistant = findViewById(R.id.refresh_ai_assistant);

        List<String> datas = new ArrayList<>();
        for(int i = 0;i < 8;i++){
            datas.add("测试字段");
        }

        AIAssistantSubRecyclerView adapter = new AIAssistantSubRecyclerView(this,datas);
        aiAssistantList.setLayoutManager(new LinearLayoutManager(this));
        aiAssistantList.setAdapter(adapter);


        refreshAiAssistant.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                data.add("new");
                Toast.makeText(AIAssistantActivity.this,"下拉刷新--",Toast.LENGTH_SHORT).show();
//                appointment.notifyDataSetChanged();
                refreshAiAssistant.setRefreshing(false);
            }
        });
        title.setText("智能助理");
    }
}
