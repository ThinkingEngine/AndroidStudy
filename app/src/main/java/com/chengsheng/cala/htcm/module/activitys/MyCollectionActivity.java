package com.chengsheng.cala.htcm.module.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.module.fragments.CollectionFragment;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionActivity extends BaseActivity implements CollectionFragment.OnFragmentInteractionListener{

    private TextView titleText;
    private TabLayout tabHeader;
    private ViewPager collectionBody;


    private List<Fragment> fragments;
    private String[] marks = {"体检套餐","医生","特色服务","文章资讯","常见疾病"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        titleText =  findViewById(R.id.title_header_my_collection).findViewById(R.id.menu_bar_title);
        tabHeader =  findViewById(R.id.collection_header);
        collectionBody = findViewById(R.id.collection_body);

        titleText.setText("我的收藏");

        initDatas();

        for(int i = 0;i < marks.length;i++){
            tabHeader.getTabAt(i).setText(marks[i]);
        }
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void initDatas(){

        fragments = new ArrayList<>();
        for(int i = 0;i < marks.length;i++){
            tabHeader.addTab(tabHeader.newTab().setText(marks[i]));
            fragments.add(CollectionFragment.newInstance(marks[i],""));
        }

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        collectionBody.setAdapter(adapter);

        tabHeader.setupWithViewPager(collectionBody);
    }



}
