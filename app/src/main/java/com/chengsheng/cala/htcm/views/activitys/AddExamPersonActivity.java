package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.CanFamiliesListRecycler;

import java.util.ArrayList;
import java.util.List;

public class AddExamPersonActivity extends AppCompatActivity {
    private TextView title;
    private ImageView back;
    private RelativeLayout familiesManageBox;
    private TextView admitAdd;
    private RecyclerView familiesListRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_person);

        initViews();

        //临时数据
        List<String> datas = new ArrayList<>();
        datas.add("王可可");
        datas.add("王四加");

        if(familiesListRecycler != null){
            CanFamiliesListRecycler adapter = new CanFamiliesListRecycler(this,datas);
            familiesListRecycler.setLayoutManager(new LinearLayoutManager(this));
            familiesListRecycler.setAdapter(adapter);
        }else{
            Log.e("ERROR","视图初始化失败!");
        }
    }

    private void initViews(){
        title = findViewById(R.id.title_header_add_exam_person).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_add_exam_person).findViewById(R.id.back_login);
        familiesManageBox = findViewById(R.id.families_manage_box);
        admitAdd = findViewById(R.id.admit_add);
        familiesListRecycler = findViewById(R.id.families_list_recycler);

        title.setText("添加体检人");
    }
}
