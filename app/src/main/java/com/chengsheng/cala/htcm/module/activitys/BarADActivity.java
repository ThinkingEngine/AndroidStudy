package com.chengsheng.cala.htcm.module.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

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
    private ListView listView;
    private Dialog dialog;
    private View dialogView;
    private TranslateAnimation ta;

    private String[] ad_address = {"http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/5159f338-5349-40f0-beaf-fabb4cce2281.png",
            "http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/afae2499-839e-4abc-b914-fc84c2d16140.png",
            "http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/5c9a5875-3612-44f3-9b69-575221748b97.png"};
    private String[] ad_name = {"机构详情", "查看报告", "守护爸妈健康"};
    private int[] images = {R.mipmap.pengyouquan,
            R.mipmap.weixinhaoyou,
            R.mipmap.qqhaoyou,
            R.mipmap.qqkongjian,
            R.mipmap.fuzhilianjie};

    private List<String> shareList;

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
            shareList = new ArrayList<>();
            shareList.add("朋友圈");
            shareList.add("微信好友");
            shareList.add("QQ好友");
            shareList.add("QQ空间");
            shareList.add("复制链接");
            shareSelection();
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

    private void shareSelection() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog_bottom);
            dialogView = LayoutInflater.from(this).inflate(R.layout.share_combo_bg_layout, null);
            dialog.setContentView(dialogView);
        }

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


        GridView gridView = dialogView.findViewById(R.id.share_way);
        Button dissen = dialogView.findViewById(R.id.share_down);


        ta = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_PARENT, 0);
        ta.setInterpolator(new AccelerateInterpolator());
        ta.setDuration(200);

        dissen.setOnClickListener(v -> dialog.dismiss());

        BarADActivity.ShareAdapter adapter = new BarADActivity.ShareAdapter();
        gridView.setAdapter(adapter);
    }

    class ShareAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return shareList.size();
        }

        @Override
        public Object getItem(int position) {
            return shareList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(BarADActivity.this).inflate(R.layout.share_way_icon_bg_layout, null);
            }

            ImageView shareWayIcon = convertView.findViewById(R.id.share_way_icon);
            TextView textView = convertView.findViewById(R.id.share_way_text);
            shareWayIcon.setImageResource(images[position]);
            textView.setText(shareList.get(position));
            return convertView;
        }

    }


}
