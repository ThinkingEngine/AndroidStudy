package com.chengsheng.cala.htcm.views.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.views.fragments.ExamReprotListFragment;

import java.util.ArrayList;
import java.util.List;

public class ExamReportActivity extends AppCompatActivity implements ExamReprotListFragment.OnFragmentInteractionListener{
    private TabLayout examReportHeader;
    private ViewPager examReportPage;
    private TextView title;
    private ImageView back;

    private ExamReprotListFragment fragment;
    private int[] tabIcons = {R.mipmap.touxiang,R.mipmap.touxiang,R.mipmap.touxiang};

    private List<Fragment> fragments;
    private List<String> tabTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_report);

        initViews();
        initValue();
    }

    private void initViews(){
        title = findViewById(R.id.title_header_exam_report).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_exam_report).findViewById(R.id.back_login);
        examReportHeader = findViewById(R.id.exam_report_header);
        examReportPage = findViewById(R.id.exam_report_page);

        title.setText("体检报告");


    }

    private void initValue(){
        fragments = new ArrayList<>();
        fragments.add(ExamReprotListFragment.newInstance("1",""));
        fragments.add(ExamReprotListFragment.newInstance("2",""));
        fragments.add(ExamReprotListFragment.newInstance("3",""));
        tabTitle = new ArrayList<>();
        tabTitle.add("王树彤");
        tabTitle.add("王洛伊");
        tabTitle.add("李凯璇");

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);

        examReportPage.setAdapter(adapter);
        examReportHeader.setupWithViewPager(examReportPage);

        setupTabIcon();
    }

    private void setupTabIcon(){
        examReportHeader.getTabAt(0).setCustomView(getTabView(0));
        examReportHeader.getTabAt(1).setCustomView(getTabView(1));
        examReportHeader.getTabAt(2).setCustomView(getTabView(2));
    }

    private View getTabView(int position){
        View view = LayoutInflater.from(this).inflate(R.layout.tab_header_layout,null);
        ImageView headerIcon = view.findViewById(R.id.header_icon_report);
        TextView nameReport = view.findViewById(R.id.name_report);
        headerIcon.setImageResource(tabIcons[position]);
        nameReport.setText(tabTitle.get(position));

        return view;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
