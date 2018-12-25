package com.chengsheng.cala.htcm.module.activitys;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.module.fragments.ModeFamiliesFragment;

public class ModeFamiliesInfoActivity extends BaseActivity implements ModeFamiliesFragment.OnFragmentInteractionListener{

    private ModeFamiliesFragment fragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mode_families_info;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        FamiliesDetailInfo info = (FamiliesDetailInfo) bundle.getSerializable("FAMILIES_INFO");
        String modeParam = bundle.getString("MODE");
        fragment = ModeFamiliesFragment.newInstance(modeParam,info);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.mode_fragment_container,fragment);
        ft.commit();
    }

    @Override
    public void getData() {

    }


    @Override
    public void onFragmentInteraction(Boolean isBack) {
        if(isBack){
            finish();
        }
    }
}
