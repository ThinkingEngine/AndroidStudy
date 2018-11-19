package com.chengsheng.cala.htcm.views.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamAppointment;
import com.chengsheng.cala.htcm.model.datamodel.ExamApponitments;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.adapters.ExamAppointmentRecyclerAdapter;
import com.chengsheng.cala.htcm.views.customviews.HeaderScrollView;
import com.chengsheng.cala.htcm.views.dialog.CustomComboDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ExamAppointmentActivity extends AppCompatActivity implements HeaderScrollView.StopCall {
    private HeaderScrollView headerScrollView;
    private RelativeLayout imageView;
    private RelativeLayout textView;

    private RecyclerView examAppointmentItem;//预约列表
    private SwipeRefreshLayout refreshExamAppointmentPage;

    private TextView title;
    private ImageView back, search;
    private TextView allPriceA, allPriceB;
    private ImageView comboRecommendButton;

    private PopupWindow window;

    private ExamAppointmentRecyclerAdapter adapter;
    private ExamApponitments examApponitmentsDatas;

    private String tempStr;


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
        examAppointmentItem = findViewById(R.id.exam_appointment_recycler);//体检预约列表。
        refreshExamAppointmentPage = findViewById(R.id.refresh_exam_appointment_page);
        comboRecommendButton = findViewById(R.id.combo_recommend_button);

        title.setText("体检预约");//修改标题

        allPriceA = findViewById(R.id.price_model_a).findViewById(R.id.all_price);
        allPriceB = findViewById(R.id.price_model_b).findViewById(R.id.all_price);

        //获取网络数据。
        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        Retrofit retrofit = myRetrofit.createURL(GlobalConstant.API_URL);
        NetService service = retrofit.create(NetService.class);
        service.getComboInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamApponitments>() {
                    @Override
                    public void onNext(ExamApponitments list) {
                        examApponitmentsDatas = list;
                        adapter = new ExamAppointmentRecyclerAdapter(ExamAppointmentActivity.this, examApponitmentsDatas.getItems());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExamAppointmentActivity.this) {
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
                        examAppointmentItem.setLayoutManager(linearLayoutManager);
                        examAppointmentItem.setAdapter(adapter);
                        examAppointmentItem.setNestedScrollingEnabled(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TEST", "onError" + e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.e("TEST", "onComplete".toString());
                    }
                });

        //下拉刷新
        refreshExamAppointmentPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ExamAppointmentActivity.this, "下拉刷新--", Toast.LENGTH_SHORT).show();
                refreshExamAppointmentPage.setRefreshing(false);
            }
        });


        //测试数据
        List<Map<String, Object>> listadata = new ArrayList<>();
        final String[] d = new String[]{"100元以内", "100元-200元", "100元-200元", "100元-200元", "1000元-2000元", "10000元-200000元"};
        final boolean[] select = {false,false,false,false,false,false};
        for (int i = 0; i < d.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", d[i]);
            listadata.add(map);
        }
        String[] from = {"text"};
        int[] to = {R.id.price_text};

        View popupView = LayoutInflater.from(this).inflate(R.layout.condition_selection_combo_bg_layout, null);
        window = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        TextView isok = popupView.findViewById(R.id.complete_price_select);
        GridView gridView = popupView.findViewById(R.id.price_selecter);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listadata, R.layout.price_bg_layout, from, to);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView price = view.findViewById(R.id.price_text);
                if(select[position]){
                    select[position] = false;
                    price.setTextColor(getResources().getColor(R.color.colorSecText));
                    price.setSelected(false);
                }else{
                    select[position] = true;
                    price.setSelected(true);
                    price.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

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
                Toast.makeText(ExamAppointmentActivity.this, "nih", Toast.LENGTH_SHORT).show();
            }
        });

        final CustomComboDialog dialog = new CustomComboDialog(ExamAppointmentActivity.this);

        //定制套餐
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
    protected void onResume() {
        super.onResume();


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
