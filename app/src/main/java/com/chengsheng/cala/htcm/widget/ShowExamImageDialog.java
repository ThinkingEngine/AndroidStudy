package com.chengsheng.cala.htcm.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.ShowImageAdapter;
import com.chengsheng.cala.htcm.adapter.ShowImagesViewPager;


import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowExamImageDialog extends Dialog {

    private View view;
    private Context context;
    private ShowImagesViewPager showImagesViewPager;
    private List<String> imageUrls;
    private List<View> views;
    private ShowImageAdapter adapter;

    public ShowExamImageDialog(@NonNull Context context,List<String> imageUrls) {
        super(context, R.style.transparentBgDialog);
        this.context = context;
        this.imageUrls = imageUrls;
        initViews();
        initData();
    }

    private void initViews(){
        view = View.inflate(context,R.layout.dialog_images_brower_layout,null);
        showImagesViewPager = view.findViewById(R.id.exam_result_image);
        views = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.height = HTCMApp.EXACT_SCREEN_HEIGHT;
        lp.width = HTCMApp.EXACT_SCREEN_WIDTH;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    private void initData(){
        PhotoViewAttacher.OnPhotoTapListener listener = new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }
        };

        for(int i = 0;i < imageUrls.size();i++){
            final PhotoView photoView = new PhotoView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);

            photoView.setOnPhotoTapListener(listener);


            Glide.with(context).load(imageUrls.get(i)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    photoView.setImageDrawable(resource);
                }
            });

            views.add(photoView);

        }

        adapter = new ShowImageAdapter(views);
        showImagesViewPager.setAdapter(adapter);
        showImagesViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
