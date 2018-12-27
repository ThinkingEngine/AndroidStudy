package com.chengsheng.cala.htcm.module.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.FamiliesList;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.adapter.MyExamPagerViewAdapter;
import com.chengsheng.cala.htcm.widget.ConditionPopupWindow;
import com.chengsheng.cala.htcm.module.fragments.MyExamAllFragment;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


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

    private Retrofit retrofit;
    private ZLoadingDialog loadingDialog;
    private boolean getFamilies = true;//是否已经获取到家人信息

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_exam;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("加载中...");
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setCancelable(false);

        //初始化Activity数据.
        token = app.getTokenType() + " " + app.getAccessToken();
        //获取家人信息
        getFamilies();
        //初始化界面
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
        searchButton.setOnClickListener(v -> {
            if(familiesList.getItems().isEmpty()){
                getFamilies();
            }else{
                if (familiesList != null && listDatas.isEmpty()) {
                    Map<String, String> header = new HashMap<>();
                    header.put("SELECT", "false");
                    header.put("DATA", "");
                    header.put("ID", "");
                    listDatas.add(0, header);
                    for (FamiliesListItem item : familiesList.getItems()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("SELECT", "false");
                        map.put("DATA", item.getFullname());
                        map.put("ID", String.valueOf(item.getId()));
                        listDatas.add(map);
                    }
                }
                window.showAsDropDown(searchButton);
            }

        });
    }

    @Override
    public void getData() {

    }

    //获取家人列表
    private void getFamilies() {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        if(!getFamilies){
            loadingDialog.show();
        }
        NetService service = retrofit.create(NetService.class);
        service.getFamiliesList(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FamiliesList>() {
                    @Override
                    public void onNext(FamiliesList list) {
                        familiesList = list;
                        getFamilies = true;
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showShortToast("未能获取到家人列表,请重试!");
                        getFamilies = false;
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
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
