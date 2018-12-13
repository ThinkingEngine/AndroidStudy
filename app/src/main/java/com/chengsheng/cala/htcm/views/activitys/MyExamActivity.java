package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesList;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.adapters.MyExamPagerViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.ConditionPopupWindow;
import com.chengsheng.cala.htcm.views.fragments.MyExamAllFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MyExamActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
                                                             MyExamAllFragment.OnFragmentInteractionListener {
    private TextView menuBarTitle;
    private ImageView searchButton;
    private TabLayout myExamTabLayout;
    private ViewPager myExamPageView;

    private String[] tabs = new String[]{"全部", "待体检", "体检中", "已体检"};
    private FamiliesList familiesList;

    private HTCMApp app;
    private String token;

    private MyRetrofit myRetrofit;
    private Retrofit retrofit;
    private NetService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = HTCMApp.create(getApplicationContext());
        setContentView(R.layout.activity_my_exam);

        //初始化Activity数据.
        token = app.getTokenType() + " " + app.getAccessToken();
        if (myRetrofit == null) {
            myRetrofit = MyRetrofit.createInstance();
        }
        if (retrofit == null) {
            retrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        }
        if (service == null) {
            service = retrofit.create(NetService.class);
        }
        if (familiesList == null) {
            service.getFamiliesList(token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<FamiliesList>() {
                        @Override
                        public void onNext(FamiliesList list) {
                            familiesList = list;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG", "请求家人列表失败! " + e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }


        initViews();

        //初始化碎片
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabs.length; i++) {
            fragments.add(MyExamAllFragment.newInstance(tabs[i], token));
        }

        FragmentManager fm = getSupportFragmentManager();
        MyExamPagerViewAdapter adapter = new MyExamPagerViewAdapter(fm, fragments);
        myExamPageView.setAdapter(adapter);
        myExamTabLayout.setupWithViewPager(myExamPageView);

        for (int i = 0; i < tabs.length; i++) {
            myExamTabLayout.getTabAt(i).setText(tabs[i]);
        }

        final List<Map<String, String>> listDatas = new ArrayList<>();
        final ConditionPopupWindow window = new ConditionPopupWindow(this, listDatas);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (familiesList != null && listDatas.isEmpty()) {
                    Map<String, String> header = new HashMap<>();
                    header.put("SELECT", "false");
                    header.put("DATA", "");
                    header.put("ID","");
                    listDatas.add(0,header);
                    for (FamiliesListItem item : familiesList.getItems()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("SELECT", "false");
                        map.put("DATA", item.getFullname());
                        map.put("ID",String.valueOf(item.getId()));
                        listDatas.add(map);
                    }
                }
                window.showAsDropDown(searchButton);
            }
        });

    }

    private void initViews() {
        menuBarTitle = findViewById(R.id.title_header_my_exam).findViewById(R.id.menu_bar_title);
        searchButton = findViewById(R.id.title_header_my_exam).findViewById(R.id.search_button);
        myExamTabLayout = findViewById(R.id.my_exam_tab_layout);
        myExamPageView = findViewById(R.id.my_exam_page_view);

        menuBarTitle.setText("我的体检");
        searchButton.setImageResource(R.mipmap.tijian_xuanren);


        for (int i = 0; i < tabs.length; i++) {
            myExamTabLayout.addTab(myExamTabLayout.newTab().setText(tabs[i]));
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        myExamPageView.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
