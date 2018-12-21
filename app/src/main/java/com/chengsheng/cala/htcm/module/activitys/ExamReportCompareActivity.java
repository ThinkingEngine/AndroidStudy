package com.chengsheng.cala.htcm.module.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.ExamReportModel.CompareItem;
import com.chengsheng.cala.htcm.protocol.ExamReportModel.CompareModel;
import com.chengsheng.cala.htcm.network.ExamReportInterface;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.adapter.CompareReportRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-21 14:06
 * Description:体检报告 对比
 */

public class ExamReportCompareActivity extends BaseActivity {
    private ImageView back, arrow;
    private LinearLayout clickContainer;
    private TextView title;
    private RecyclerView compareItemRecycler;
    private TextView compareA,compareB;

    private Retrofit retrofit;
    private HTCMApp app;

    private PopupWindow window;

    private List<String> condition = new ArrayList<>();
    private List<CompareItem> temp;

    private CompareReportRecyclerAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_report_compare;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());

        Bundle bundle = getIntent().getExtras();

        String firstID = bundle.getString("FIRST_ID");
        String firstDate = bundle.getString("FIRST_TIME");
        String secondID = bundle.getString("SECOND_ID");
        String secondDate = bundle.getString("SECOND_TIME");

        initViews();
        setViews(firstDate,secondDate);
        getCompareReport(firstID,secondID);

        setPopupWindow();
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        back = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.back_login);
        arrow = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.arrow_up_down);
        clickContainer = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.click_container);
        title = findViewById(R.id.title_header_exam_report_compare).findViewById(R.id.menu_bar_title);
        compareItemRecycler = findViewById(R.id.compare_item_recycler);
        compareA = findViewById(R.id.compare_a);
        compareB = findViewById(R.id.compare_b);

        title.setText("全部");

        arrow.setSelected(false);


    }

    private void setViews(String dataA,String dataB){
        compareA.setText(dataA.substring(0,10));
        compareB.setText(dataB.substring(0,10));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.condition_screening_model_b_layout, null);
        ListView conditionScreeningListB = contentView.findViewById(R.id.condition_screening_list_b);
        conditionScreeningListB.setAdapter(new MyBaseAdapter());

        window = new PopupWindow(contentView);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setBackgroundDrawable(null);
        window.setOutsideTouchable(true);
        window.setTouchable(true);
    }


    private void getCompareReport(final String f, String s){
        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        ExamReportInterface examReportInterface = retrofit.create(ExamReportInterface.class);
        examReportInterface.getCompareReport(app.getTokenType()+" "+app.getAccessToken(),f,s)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CompareModel>() {
                    @Override
                    public void onNext(CompareModel compareModel) {
                        condition.add("全部");
                        List<CompareItem> items = compareModel.getItems();
                        for(int i = 0;i < items.size();i++){
                            condition.add(items.get(i).getCheck_item_name());
                        }

                        temp = items;
                        adapter = new CompareReportRecyclerAdapter(ExamReportCompareActivity.this,compareModel.getItems());
                        compareItemRecycler.setLayoutManager(new LinearLayoutManager(ExamReportCompareActivity.this));
                        compareItemRecycler.setAdapter(adapter);

                        clickContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!arrow.isSelected()){
                                    arrow.setSelected(true);
                                    if(window == null){
                                        Toast.makeText(ExamReportCompareActivity.this,"window null",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ExamReportCompareActivity.this,"window ok",Toast.LENGTH_SHORT).show();
                                    }
                                    window.showAsDropDown(clickContainer);
                                }else{
                                    arrow.setSelected(false);
                                    window.dismiss();
                                }



                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","对比报告错误:"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class MyBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return condition.size();
        }

        @Override
        public Object getItem(int position) {
            return condition.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(ExamReportCompareActivity.this).inflate(R.layout.single_text_layout,null);

            TextView textView = view.findViewById(R.id.text_mark);
            textView.setText(condition.get(position));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title.setText(condition.get(position));
                    arrow.setSelected(false);
                    List<CompareItem> test = new ArrayList<>(temp);
                    if(condition.get(position).equals("全部")){
                        CallBackDataAuth.doConditionInterface(test);
                    }else{
                        for(int i = 0;i < test.size();i++){
                            if(test.get(i).getCheck_item_name().equals(condition.get(position))){
                                CompareItem t = test.get(i);
                                test.clear();
                                test.add(t);
                            }
                        }
                        CallBackDataAuth.doConditionInterface(test);
                    }

                    window.dismiss();
                }
            });

            return view;
        }
    }


}
