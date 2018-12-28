package com.chengsheng.cala.htcm.module.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.module.fragments.MyExamFragment;
import com.chengsheng.cala.htcm.adapter.MyExamPagerViewAdapter;
import com.chengsheng.cala.htcm.widget.AppTitleBar;


import java.util.ArrayList;
import java.util.List;


public class MyExamActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout myExamTabLayout;
    private ViewPager myExamPageView;
    private AppTitleBar header;//头标题

    private String[] tabs = new String[]{"全部", "待体检", "体检中", "已体检"};

    private boolean getFamilies = true;//是否已经获取到家人信息

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_exam;
    }

    @Override
    public void initView() {
        //初始化界面
        initViews();
        //初始化碎片
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabs.length; i++) {
            MyExamFragment fragment = new MyExamFragment();
            fragment.setMode(tabs[i]);
            fragments.add(fragment);
        }

        FragmentManager fm = getSupportFragmentManager();
        MyExamPagerViewAdapter adapter = new MyExamPagerViewAdapter(fm, fragments);
        myExamPageView.setAdapter(adapter);
        myExamTabLayout.setupWithViewPager(myExamPageView);

        for (int i = 0; i < tabs.length; i++) {
            myExamTabLayout.getTabAt(i).setText(tabs[i]);
        }

    }

    @Override
    public void getData() {
        //获取家人信息
//        getFamilies();
    }

    //获取家人列表
//    private void getFamilies() {
//
//        if (retrofit == null) {
//            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
//        }
//
//        if (!getFamilies) {
//        }
//        NetService service = retrofit.create(NetService.class);
//        service.getFamiliesList(token)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<FamiliesList>() {
//                    @Override
//                    public void onNext(FamiliesList list) {
//                        familiesList = list;
//                        getFamilies = true;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("TAG", "lose families");
//                        getFamilies = false;
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//
//
//    }

    private void initViews() {
        header = findViewById(R.id.at_my_exam_header);
        myExamTabLayout = findViewById(R.id.my_exam_tab_layout);
        myExamPageView = findViewById(R.id.my_exam_page_view);

        header.setTitle("我的体检");
        header.setFinishClickListener(() -> finish());


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

}
