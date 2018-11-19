package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.IntelligentCheckARecyclerAdapter;
import com.chengsheng.cala.htcm.views.adapters.IntelligentCheckBRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntelligentCheckActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    private ImageView barCodeMarkIntelligent;
    private TextView numberBarCodeIntelligent;
    private TextView itemPersonName,itemPersonSex,itemPersonAge;
    private RecyclerView intelligentCheckRecyclerA,intelligentCheckRecyclerB;
    private TextView checkedLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligent_check);

        initViews();

        List<Map<String,String>> datasA = new ArrayList<>();
        List<Map<String,String>> datasB = new ArrayList<>();
        Map<String,String> dataA = new HashMap<>();
        dataA.put("STATE","TYPE_B");
        Map<String,String> dataB = new HashMap<>();
        dataB.put("STATE","TYPE_A");
        Map<String,String> dataC = new HashMap<>();
        dataC.put("STATE","TYPE_C");
        Map<String,String> dataE = new HashMap<>();
        dataE.put("STATE","TYPE_A");
        Map<String,String> dataD = new HashMap<>();
        dataD.put("STATE","TYPE_C");
        datasA.add(dataA);
        datasA.add(dataB);
        datasA.add(dataC);
        datasB.add(dataD);
        datasB.add(dataE);

        IntelligentCheckARecyclerAdapter adapter1 = new IntelligentCheckARecyclerAdapter(this,datasA);
        IntelligentCheckBRecyclerAdapter adapter2 = new IntelligentCheckBRecyclerAdapter(this,datasB);
        intelligentCheckRecyclerA.setLayoutManager(new LinearLayoutManager(this));
        intelligentCheckRecyclerB.setLayoutManager(new LinearLayoutManager(this));
        intelligentCheckRecyclerA.setSaveEnabled(false);
        intelligentCheckRecyclerB.setSaveEnabled(false);
        intelligentCheckRecyclerA.setAdapter(adapter1);
        intelligentCheckRecyclerB.setAdapter(adapter2);

    }

    private void initViews() {
        back = findViewById(R.id.title_header_intelligent_check).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_intelligent_check).findViewById(R.id.menu_bar_title);
        barCodeMarkIntelligent = findViewById(R.id.bar_code_mark_intelligent);
        numberBarCodeIntelligent = findViewById(R.id.number_bar_code_intelligent);
        itemPersonName = findViewById(R.id.item_person_name);
        itemPersonSex = findViewById(R.id.item_person_sex);
        itemPersonAge = findViewById(R.id.item_person_age);
        intelligentCheckRecyclerA = findViewById(R.id.intelligent_check_recycler_a);
        intelligentCheckRecyclerB = findViewById(R.id.intelligent_check_recycler_b);
        checkedLine = findViewById(R.id.checked_line);

        title.setText("智能导检");
    }
}
