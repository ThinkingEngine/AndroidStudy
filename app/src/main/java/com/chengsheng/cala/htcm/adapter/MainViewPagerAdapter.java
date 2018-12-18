package com.chengsheng.cala.htcm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public MainViewPagerAdapter(FragmentManager fm,List<Fragment> dataFragments){
        super(fm);
        this.list = dataFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list != null ? list.size():0;
    }
}
