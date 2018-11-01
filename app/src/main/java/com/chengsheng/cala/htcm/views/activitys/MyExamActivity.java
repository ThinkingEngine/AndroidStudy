package com.chengsheng.cala.htcm.views.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.MyExamPagerViewAdapter;
import com.chengsheng.cala.htcm.views.fragments.MyExamAllFragment;


import java.util.ArrayList;
import java.util.List;

public class MyExamActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,MyExamAllFragment.OnFragmentInteractionListener{
    private TextView menuBarTitle;
    private ImageView searchButton;
    private TabLayout myExamTabLayout;
    private ViewPager myExamPageView;

    private String[] tabs = new String[]{"全部","待体检","体检中","已体检"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exam);

        initViews();

        List<Fragment> fragments = new ArrayList<>();
        for(int i = 0;i < tabs.length;i++){
            fragments.add(MyExamAllFragment.newInstance("第"+i+1,""));
        }


        FragmentManager fm = getSupportFragmentManager();
        MyExamPagerViewAdapter adapter = new MyExamPagerViewAdapter(fm,fragments);
        myExamPageView.setAdapter(adapter);
        myExamTabLayout.setupWithViewPager(myExamPageView);

        for(int i = 0; i < tabs.length;i++){
            myExamTabLayout.getTabAt(i).setText(tabs[i]);
        }

    }

    private void initViews(){
        menuBarTitle = findViewById(R.id.title_header_my_exam).findViewById(R.id.menu_bar_title);
        searchButton = findViewById(R.id.title_header_my_exam).findViewById(R.id.search_button);
        myExamTabLayout = findViewById(R.id.my_exam_tab_layout);
        myExamPageView = findViewById(R.id.my_exam_page_view);

        menuBarTitle.setText("我的体检");
        searchButton.setImageResource(R.mipmap.tijian_xuanren);

        for(int i = 0; i < tabs.length;i++){
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
