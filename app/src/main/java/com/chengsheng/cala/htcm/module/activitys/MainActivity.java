package com.chengsheng.cala.htcm.module.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.module.fragments.FindFragment;
import com.chengsheng.cala.htcm.module.fragments.HealthFragment;
import com.chengsheng.cala.htcm.module.fragments.MainPageFragment;
import com.chengsheng.cala.htcm.module.fragments.MineFragment;
import com.chengsheng.cala.htcm.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 蔡浪
 * CreateDate:
 * Description: APP首页
 */
public class MainActivity extends BaseActivity {

    private LinearLayout mainPageButton, healthPageButton, findPageButton, minePageButton;
    private ImageView mainPageImage, healthPageImage, findPageImage, minePageImage;
    private TextView mianPageText, healthPageText, findPageText, minePageText;

    private List<Fragment> dataFragments;

    private HTCMApp app;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
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

    @Override
    public void initView() {
        mainPageButton = findViewById(R.id.main_page_button);
        healthPageButton = findViewById(R.id.health_page_button);
        findPageButton = findViewById(R.id.find_page_button);
        minePageButton = findViewById(R.id.mine_page_button);

        mainPageImage = findViewById(R.id.main_page_image);
        healthPageImage = findViewById(R.id.health_page_image);
        findPageImage = findViewById(R.id.find_page_image);
        minePageImage = findViewById(R.id.mine_page_image);

        mianPageText = findViewById(R.id.main_page_textview);
        healthPageText = findViewById(R.id.health_text);
        findPageText = findViewById(R.id.find_page_text);
        minePageText = findViewById(R.id.mine_page_text);

        app = HTCMApp.create(this);

        initFragment();

        final ViewPager mainPager = findViewById(R.id.main_view_pager);
        mainPager.addOnPageChangeListener(new ViewPagerOnPagerChangedListener());
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
                if (UserUtil.isLogin()) {
                    updateButtonSelected(false, true, false, false);
                    mainPager.setCurrentItem(1);
                } else {
                    startActivity(new LoginActivity());
                }

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
    public void getData() {

    }

    private void initFragment() {
        dataFragments = new ArrayList<>();
        Fragment mainPage = MainPageFragment.newInstance();
        Fragment healthPage = HealthFragment.newInstance();
        Fragment findPage = FindFragment.newInstance();
        Fragment minePage = MineFragment.newInstance("", "");
        dataFragments.add(mainPage);
        dataFragments.add(healthPage);
        dataFragments.add(findPage);
        dataFragments.add(minePage);
    }

    class ViewPagerOnPagerChangedListener implements ViewPager.OnPageChangeListener {

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
