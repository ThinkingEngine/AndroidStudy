package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class BannerAdapter extends PagerAdapter {


    private ViewPager viewPager;
    private List<ImageView> data;

    public BannerAdapter(ViewPager viewPager, List<ImageView> data) {
        this.viewPager = viewPager;
        this.data = data;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        viewPager.removeView(data.get(position % data.size()));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position = position % data.size();
        ViewGroup parent = (ViewGroup) data.get(position).getParent();
        if(parent != null){
            parent.removeAllViews();
        }
//        container.addView(data.get(position));
//        return data.get(position);

        ImageView imageView = data.get(position);
        viewPager.addView(imageView);
        return imageView;
    }


}
