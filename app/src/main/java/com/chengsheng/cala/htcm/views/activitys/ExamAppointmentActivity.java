package com.chengsheng.cala.htcm.views.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamAppointmentRecyclerAdapter;
import com.chengsheng.cala.htcm.views.customviews.HeaderScrollView;
import com.chengsheng.cala.htcm.views.dialog.CustomComboDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamAppointmentActivity extends AppCompatActivity implements HeaderScrollView.StopCall {
    private HeaderScrollView headerScrollView;
    private RelativeLayout imageView;
    private RelativeLayout textView;

    private RecyclerView examAppointmentItem;
    private SwipeRefreshLayout refreshExamAppointmentPage;

    private TextView title;
    private ImageView back, search;
    private TextView allPriceA, allPriceB;
    private ImageView comboRecommendButton;

    private PopupWindow window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_appointment);

        title = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.back_login);
        search = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.search_button);
        headerScrollView = findViewById(R.id.scroll_view_header);
        imageView = findViewById(R.id.rl1);
        textView = findViewById(R.id.rl2);
        examAppointmentItem = findViewById(R.id.exam_appointment_recycler);
        refreshExamAppointmentPage = findViewById(R.id.refresh_exam_appointment_page);
        comboRecommendButton = findViewById(R.id.combo_recommend_button);

        allPriceA = findViewById(R.id.price_model_a).findViewById(R.id.all_price);
        allPriceB = findViewById(R.id.price_model_b).findViewById(R.id.all_price);


        title.setText("体检预约");
        //测试数据
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("套餐测试");
        }

        final ExamAppointmentRecyclerAdapter adapter = new ExamAppointmentRecyclerAdapter(this, datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
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
                Toast.makeText(ExamAppointmentActivity.this, "下拉刷新--", Toast.LENGTH_SHORT).show();
//                appointment.notifyDataSetChanged();
                refreshExamAppointmentPage.setRefreshing(false);
            }
        });


        //测试数据
        List<Map<String,Object>> listadata = new ArrayList<>();
        String[] d = new String[]{"100元以内","100元-200元","100元-200元","100元-200元","1000元-2000元","10000元-200000元"};
        for(int i=0;i < d.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("text",d[i]);
            listadata.add(map);
        }
        String[] from = {"text"};
        int[] to = {R.id.price_text};

        View popupView = LayoutInflater.from(this).inflate(R.layout.condition_selection_combo_bg_layout, null);
        window = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        TextView isok = popupView.findViewById(R.id.complete_price_select);
        GridView gridView = popupView.findViewById(R.id.price_selecter);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listadata,R.layout.price_bg_layout,from,to);
        gridView.setAdapter(simpleAdapter);

        window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        window.setOutsideTouchable(true);
        window.setTouchable(true);

        allPriceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   window.showAsDropDown(textView);
            }
        });

        allPriceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.showAsDropDown(imageView);
            }
        });


        isok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExamAppointmentActivity.this,"nih",Toast.LENGTH_SHORT).show();
            }
        });

       final  CustomComboDialog dialog = new CustomComboDialog(ExamAppointmentActivity.this);

        comboRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });


        headerScrollView.setCallback(this);


    }

    @Override
    public void stopSlide(boolean isStop) {
        if (isStop) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        window.dismiss();
        super.onDestroy();
    }


}
