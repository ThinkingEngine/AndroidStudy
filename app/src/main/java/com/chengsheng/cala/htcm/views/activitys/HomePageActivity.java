package com.chengsheng.cala.htcm.views.activitys;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.views.fragments.FindFragment;
import com.chengsheng.cala.htcm.views.fragments.HealthFragment;
import com.chengsheng.cala.htcm.views.fragments.MainPageFragment;
import com.chengsheng.cala.htcm.views.fragments.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements MainPageFragment.OnFragmentInteractionListener,
        HealthFragment.OnFragmentInteractionListener,
        FindFragment.OnFragmentInteractionListener,
        MineFragment.OnFragmentInteractionListener {


    private LinearLayout mainPageButton, healthPageButton, findPageButton, minePageButton;
    private ImageView mainPageImage, healthPageImage, findPageImage, minePageImage;
    private TextView mianPageText, healthPageText, findPageText, minePageText;

    private List<Fragment> dataFragments;

    private HTCMApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = HTCMApp.create(this);
        setContentView(R.layout.activity_home_page);

        initView();
        initFragemnts();

        final ViewPager mainPager = findViewById(R.id.main_view_pager);
        mainPager.addOnPageChangeListener(new ViewPagerOnPagerChangedLisenter());
        FragmentManager fm = getSupportFragmentManager();

        if (!dataFragments.isEmpty()) {
            MainViewPagerAdapter mva = new MainViewPagerAdapter(fm, dataFragments);
            mainPager.setAdapter(mva);
        }
        mainPager.setCurrentItem(0);
        updateButtonSelected(true, false, false, false);

        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonSelected(true, false, false, false);
                mainPager.setCurrentItem(0);
            }
        });

        healthPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonSelected(false, true, false, false);
                mainPager.setCurrentItem(1);
            }
        });

        findPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonSelected(false, false, true, false);
                mainPager.setCurrentItem(2);
            }
        });

        minePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonSelected(false, false, false, true);
                mainPager.setCurrentItem(3);
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void updateButtonSelected(boolean a, boolean b, boolean c, boolean d) {
        mainPageImage.setSelected(a);
        mainPageImage.setEnabled(a);
        healthPageImage.setSelected(b);
        healthPageImage.setEnabled(b);
        findPageImage.setSelected(c);
        findPageImage.setEnabled(c);
        minePageImage.setSelected(d);
        minePageImage.setEnabled(d);
        if (a) {
            mianPageText.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mianPageText.setTextColor(getResources().getColor(R.color.colorThrText));
        }
        if (b) {
            healthPageText.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            healthPageText.setTextColor(getResources().getColor(R.color.colorThrText));
        }
        if (c) {
            findPageText.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            findPageText.setTextColor(getResources().getColor(R.color.colorThrText));
        }
        if (d) {
            minePageText.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            minePageText.setTextColor(getResources().getColor(R.color.colorThrText));
        }
    }

    private void initView() {
        mainPageButton = findViewById(R.id.include2).findViewById(R.id.main_page_button);
        healthPageButton = findViewById(R.id.include2).findViewById(R.id.health_page_button);
        findPageButton = findViewById(R.id.include2).findViewById(R.id.find_page_button);
        minePageButton = findViewById(R.id.include2).findViewById(R.id.mine_page_button);

        mainPageImage = findViewById(R.id.include2).findViewById(R.id.main_page_image);
        healthPageImage = findViewById(R.id.include2).findViewById(R.id.health_page_image);
        findPageImage = findViewById(R.id.include2).findViewById(R.id.find_page_image);
        minePageImage = findViewById(R.id.include2).findViewById(R.id.mine_page_image);

        mianPageText = findViewById(R.id.include2).findViewById(R.id.main_page_textview);
        healthPageText = findViewById(R.id.include2).findViewById(R.id.health_text);
        findPageText = findViewById(R.id.include2).findViewById(R.id.find_page_text);
        minePageText = findViewById(R.id.include2).findViewById(R.id.mine_page_text);
    }

    private void initFragemnts() {
        dataFragments = new ArrayList<>();
        Fragment mainPage = MainPageFragment.newInstance("", "");
        Fragment healthPage = HealthFragment.newInstance(app.getTokenType(),app.getAccessToken());
        Fragment findPage = FindFragment.newInstance("", "");
        Fragment minePage = MineFragment.newInstance("", "");
        dataFragments.add(mainPage);
        dataFragments.add(healthPage);
        dataFragments.add(findPage);
        dataFragments.add(minePage);
    }


    class ViewPagerOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            boolean[] state = new boolean[dataFragments.size()];
            state[i] = true;
            updateButtonSelected(state[0], state[1], state[2], state[3]);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
