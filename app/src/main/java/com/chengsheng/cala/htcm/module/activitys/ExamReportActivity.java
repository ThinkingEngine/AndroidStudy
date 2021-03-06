package com.chengsheng.cala.htcm.module.activitys;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.data.repository.MemberRepository;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.protocol.FamiliesList;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.module.fragments.ExamReprotListFragment;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.widget.OnViewClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ExamReportActivity extends BaseActivity implements ExamReprotListFragment.OnFragmentInteractionListener {
    private TabLayout examReportHeader;
    private ViewPager examReportPage;
    private TextView title;
    private ImageView back;
    private RelativeLayout noFamiliesContent;
    private ImageView noContentsF;
    private AppTitleBar atExamReport;

    private String[] tabIcons;

    private List<Fragment> fragments;
    private List<String> tabTitle;

    private Retrofit retrofit;
    private HTCMApp app;
    private String authorization;

    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_report;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        authorization = app.getTokenType() + " " + app.getAccessToken();

        initViews();
        updateFamiliesList();

    }

    @Override
    public void getData() {

    }

    private void initViews() {
        atExamReport = findViewById(R.id.at_exam_report);
        examReportHeader = findViewById(R.id.exam_report_header);
        examReportPage = findViewById(R.id.exam_report_page);
        noFamiliesContent = findViewById(R.id.no_families_content);
        noContentsF = findViewById(R.id.no_contents_f);

        atExamReport.setTitle("体检报告");
        atExamReport.setFinishClickListener(() -> finish());

        noContentsF.setOnClickListener(v -> updateFamiliesList());
    }


    private void initValue(List<FamiliesListItem> datas) {
        fragments = new ArrayList<>();
        tabTitle = new ArrayList<>();
        tabIcons = new String[datas.size()];

        if (datas.size() < 4) {
            examReportHeader.setTabMode(TabLayout.MODE_FIXED);
            examReportHeader.setTabGravity(TabLayout.GRAVITY_CENTER);
        } else {
            examReportHeader.setTabMode(TabLayout.MODE_SCROLLABLE);
            examReportHeader.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        for (int i = 0; i < datas.size(); i++) {
            fragments.add(ExamReprotListFragment.newInstance(String.valueOf(datas.get(i).getId()), authorization));
            tabTitle.add(datas.get(i).getFullname());
            tabIcons[i] = datas.get(i).getAvatar_path();
        }


        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);

        examReportPage.setAdapter(adapter);
        examReportHeader.setupWithViewPager(examReportPage);

        examReportHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupTabIcon(datas);
    }

    private void setupTabIcon(List<FamiliesListItem> datas) {
        for (int i = 0; i < datas.size(); i++) {
            examReportHeader.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_header_layout, null);
        SimpleDraweeView headerIcon = view.findViewById(R.id.header_icon_report);
        TextView nameReport = view.findViewById(R.id.name_report);
        headerIcon.setImageURI(tabIcons[position]);
        nameReport.setText(tabTitle.get(position));

        if (position != 0) {
            view.setAlpha(0.5f);
        } else {
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }


        return view;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void changeTabSelect(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator anim = ObjectAnimator.ofFloat(view, "", 1.0f, 1.1f).setDuration(200);
        anim.start();
        anim.addUpdateListener(animation -> {
            float cVal = (Float) animation.getAnimatedValue();
            view.setAlpha(0.5f + (cVal - 1f) * (0.5f / 0.1f));
            view.setScaleX(cVal);
            view.setScaleY(cVal);
        });
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator anim = ObjectAnimator.ofFloat(view, "", 1.0f, 0.9f).setDuration(200);
        anim.start();
        anim.addUpdateListener(animation -> {
            float cVal = (Float) animation.getAnimatedValue();
            view.setAlpha(1f - (1f - cVal) * (0.5f / 0.1f));
            view.setScaleX(cVal);
            view.setScaleY(cVal);
        });
    }

    private void updateFamiliesList() {
        showLoading();
        MemberRepository
                .Companion.getDefault()
                .getMember()
                .subscribe(new DefaultObserver<FamiliesList>() {
            @Override
            public void onNext(FamiliesList familiesList) {
                hideLoading();
                if (!familiesList.getItems().isEmpty()) {
                    noFamiliesContent.setVisibility(View.INVISIBLE);
                    initValue(familiesList.getItems());
                } else {
                    noFamiliesContent.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                showError(e);
                finish();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
