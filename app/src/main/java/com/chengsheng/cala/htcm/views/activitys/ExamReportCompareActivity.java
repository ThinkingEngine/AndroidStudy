package com.chengsheng.cala.htcm.views.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.CompareReportRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExamReportCompareActivity extends AppCompatActivity {
    private ImageView back, arrow;
    private LinearLayout clickContainer;
    private TextView title;
    private RecyclerView compareItemRecycler;

    private PopupWindow window;

    private String[] conditions = {"全部","实验室检查","内科","外科","血常规","尿常规"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_report_compare);

        initViews();
        setPopupWindow();

        List<String> datas = new ArrayList<>();
        datas.add("实验室检查");
        datas.add("内科");
        datas.add("血常规");
        CompareReportRecyclerAdapter adapter = new CompareReportRecyclerAdapter(this,0,datas);
        compareItemRecycler.setLayoutManager(new LinearLayoutManager(this));
        compareItemRecycler.setAdapter(adapter);


        clickContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(window == null){
                    Toast.makeText(ExamReportCompareActivity.this,"window null",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExamReportCompareActivity.this,"window ok",Toast.LENGTH_SHORT).show();
                }
                window.showAsDropDown(clickContainer);

            }
        });
    }

    private void initViews() {
        back = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.back_login);
        arrow = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.arrow_up_down);
        clickContainer = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.click_container);
        title = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.menu_bar_title);
        compareItemRecycler = findViewById(R.id.compare_item_recycler);

        title.setText("全部");

        arrow.setSelected(false);
    }

    private void setPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.condition_screening_model_b_layout, null);
        ListView conditionScreeningListB = contentView.findViewById(R.id.condition_screening_list_b);
        conditionScreeningListB.setAdapter(new MyBaseAdapter());

        window = new PopupWindow(contentView);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setBackgroundDrawable(null);
        window.setOutsideTouchable(true);
        window.setTouchable(true);
    }

    public class MyBaseAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return conditions.length;
        }

        @Override
        public Object getItem(int position) {
            return conditions[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(ExamReportCompareActivity.this).inflate(R.layout.single_text_layout,null);

            TextView textView = view.findViewById(R.id.text_mark);
            textView.setText(conditions[position]);

            return view;
        }
    }


}
