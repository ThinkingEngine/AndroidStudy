package com.chengsheng.cala.htcm.module.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.module.fragments.AddFamilyFragment;
import com.chengsheng.cala.htcm.module.fragments.FamiliyManageListFragment;
import com.chengsheng.cala.htcm.module.home.MainActivity;

public class FamilyManageActivity extends BaseActivity implements AddFamilyFragment.OnAddFamilyFragmentInteractionListener {

    private ImageView backHomeButton;
    private TextView titleHeader;
    private TextView addFamily;

    private FamiliyManageListFragment familyListFragment;
    private AddFamilyFragment addFamilyFragment;

    private FragmentManager fm;

    private String stats = "manage";

    private HTCMApp app;

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_manage;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(this);

        initViews();

        familyListFragment = new FamiliyManageListFragment();
        addFamilyFragment = AddFamilyFragment.newInstance(app.getTokenType(), app.getAccessToken());

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        boolean addMark = getIntent().getBooleanExtra("ADD_MARK", false);
        if (addMark) {
            ft.add(R.id.family_manage_container, addFamilyFragment);
        } else {
            ft.add(R.id.family_manage_container, familyListFragment);
        }
        ft.commit();
        stats = "manage";

        addFamily.setOnClickListener(v -> {
            FragmentTransaction ft1 = fm.beginTransaction();
            if (stats.equals("manage")) {
                ft1.replace(R.id.family_manage_container, addFamilyFragment);
                ft1.commit();
                stats = "add";
                titleHeader.setText("添加家人");
                addFamily.setVisibility(View.INVISIBLE);
            } else {
                Log.e("states", "当前状态错误");
            }

        });

        backHomeButton.setOnClickListener(v -> {
            FragmentTransaction ft12 = fm.beginTransaction();
            if (stats.equals("add")) {
                ft12.add(R.id.family_manage_container, familyListFragment);
                ft12.commit();
                stats = "manage";
                titleHeader.setText("家人管理");
                addFamily.setVisibility(View.VISIBLE);

            } else if (stats.equals("manage")) {
                Intent intent = new Intent(FamilyManageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        backHomeButton = findViewById(R.id.title_header_family_manage).findViewById(R.id.back_login);
        titleHeader = findViewById(R.id.title_header_family_manage).findViewById(R.id.menu_bar_title);
        addFamily = findViewById(R.id.title_header_family_manage).findViewById(R.id.message_mark_text);

        titleHeader.setText("家人管理");
        addFamily.setText("添加家人");

        addFamily.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddFamilyFragmentInteraction(Bundle bundle, boolean isAdd) {
        if (isAdd) {
            String str = bundle.getString("STATE");
            if (str.equals("manage")) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.family_manage_container, familyListFragment);
                ft.commit();
                stats = "manage";
                titleHeader.setText("家人管理");
                addFamily.setVisibility(View.VISIBLE);
            }
        }


    }
    
}
