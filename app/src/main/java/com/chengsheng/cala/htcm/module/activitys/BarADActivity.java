package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.widget.ShareDialog;


/**
 * Author: 蔡浪
 * CreateDate: 2018/12/17 3:50 PM
 * Description: 轮播详情页
 */
public class BarADActivity extends BaseActivity {
    private ImageView back;
    private TextView title;
    private ImageView childTitle;
    private ImageView ad;

    private String[] ad_address = {"http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/5159f338-5349-40f0-beaf-fabb4cce2281.png",
            "http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/afae2499-839e-4abc-b914-fc84c2d16140.png",
            "http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/5c9a5875-3612-44f3-9b69-575221748b97.png"};
    private String[] ad_name = {"机构详情", "查看报告", "守护爸妈健康"};


    @Override
    public int getLayoutId() {
        return R.layout.activity_bar_ad;
    }

    @Override
    public void initView() {

        Bundle bundle = getIntent().getExtras();
        int adNum = bundle.getInt("NUM");

        back = findViewById(R.id.title_header_bar_ad).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_bar_ad).findViewById(R.id.menu_bar_title);
        childTitle = findViewById(R.id.title_header_bar_ad).findViewById(R.id.search_button);
        ad = findViewById(R.id.ad_bar_image);

        childTitle.setOnClickListener(v -> {

            new ShareDialog().showDialog().setOnShareListener(new ShareDialog.OnShareClickListener() {
                @Override
                public void shareToWeChat() {

                }

                @Override
                public void shareToMoment() {

                }

                @Override
                public void shareToQQ() {

                }

                @Override
                public void shareToQZone() {

                }

                @Override
                public void copyLink() {

                }
            });
        });

        back.setOnClickListener(v -> finish());

        childTitle.setImageResource(R.mipmap.fenxiang);
        title.setText(ad_name[adNum]);
        Glide.with(this).load(ad_address[adNum]).into(ad);
        //todo 根据屏幕宽度等比压缩图片宽高
//        listView.setAdapter(new BARBaseAdapter(ad_address[adNum]));
    }

    @Override
    public void getData() {

    }

}
