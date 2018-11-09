package com.chengsheng.cala.htcm.views.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.views.customviews.ConditionPopupWindow;
import com.chengsheng.cala.htcm.views.fragments.ExamOrderFormFragment;

import java.util.ArrayList;
import java.util.List;

public class ExamOrderFormActivity extends AppCompatActivity implements ExamOrderFormFragment.OnFragmentInteractionListener{
    private TextView title;
    private ImageView back,iconButton;
    private TabLayout orderFormSelectHeader;
    private ViewPager orderFormFragment;

    private String[] marks = {"全部","待付款","已付款","待评价","已取消"};
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_order_form);

        initViews();
        initDatas();

        for(int i = 0;i < marks.length;i++){
            orderFormSelectHeader.getTabAt(i).setText(marks[i]);
        }
    }

    private void initViews(){
        title = findViewById(R.id.title_header_exam_order_form).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_exam_order_form).findViewById(R.id.back_login);
        iconButton = findViewById(R.id.title_header_exam_order_form).findViewById(R.id.search_button);
        orderFormSelectHeader = findViewById(R.id.order_form_select_header);
        orderFormFragment = findViewById(R.id.order_form_fragment);

        title.setText("体检订单");
        iconButton.setImageResource(R.mipmap.tijian_xuanren);

        final ConditionPopupWindow window = new ConditionPopupWindow(this);
        iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.showAsDropDown(iconButton);
            }
        });

    }

    private void initDatas(){

        fragments = new ArrayList<>();

        for(int i = 0;i < marks.length;i++){
            orderFormSelectHeader.addTab(orderFormSelectHeader.newTab().setText(marks[i]));
            fragments.add(ExamOrderFormFragment.newInstance(marks[i],""));
        }

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        orderFormFragment.setAdapter(adapter);

        orderFormSelectHeader.setupWithViewPager(orderFormFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
