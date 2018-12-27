package com.chengsheng.cala.htcm.module.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.module.fragments.ServiceOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class ServiceOrderActivity extends BaseActivity implements ServiceOrderFragment.OnFragmentInteractionListener{

    private TabLayout serviceOrderSelectHeader;
    private ViewPager serviceOrderFragment;
    private AppTitleBar appTitleBar;

    private String[] marks = {"全部","待付款","已付款","待评价","已取消"};
    private List<Fragment> fragments;

    private PopupWindow windowB;
    private String[] conditions = {"全部","特色服务","医疗美容"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_order;
    }

    @Override
    public void initView() {
        initViews();
        initDatas();
        setPopupWindow();

        for(int i = 0;i < marks.length;i++){
            serviceOrderSelectHeader.getTabAt(i).setText(marks[i]);
        }

//        final ConditionPopupWindow window = new ConditionPopupWindow(this,listDatas);
//
//        conditionSelectIcon.setOnClickListener(v -> window.showAsDropDown(conditionSelectIcon));
//
//        clickContainerServiceOrder.setOnClickListener(v -> {
//            windowB.showAsDropDown(clickContainerServiceOrder);
//            arrowUpDownServiceOrder.setSelected(true);
//        });
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        serviceOrderFragment = findViewById(R.id.service_order_fragment);
        serviceOrderSelectHeader = findViewById(R.id.service_order_select_header);

        appTitleBar = findViewById(R.id.at_service_order);
        appTitleBar.setTitle("全部");
        appTitleBar.setFinishClickListener(() -> finish());

    }



    private void initDatas(){

        fragments = new ArrayList<>();

        for(int i = 0;i < marks.length;i++){
            serviceOrderSelectHeader.addTab(serviceOrderSelectHeader.newTab().setText(marks[i]));
            fragments.add(ServiceOrderFragment.newInstance(marks[i],""));
        }

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        serviceOrderFragment.setAdapter(adapter);

        serviceOrderSelectHeader.setupWithViewPager(serviceOrderFragment);
    }

    private void setPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.condition_screening_model_b_layout, null);


        ListView conditionScreeningListB = contentView.findViewById(R.id.condition_screening_list_b);
        conditionScreeningListB.setAdapter(new MyBaseAdapter());

        windowB = new PopupWindow(contentView);
        windowB.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        windowB.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        windowB.setBackgroundDrawable(null);
        windowB.setOutsideTouchable(true);
        windowB.setTouchable(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class MyBaseAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return conditions.length;
        }

        @Override
        public Object getItem(int position) {
            return conditions[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(ServiceOrderActivity.this).inflate(R.layout.single_text_layout,null);

            final TextView textView = view.findViewById(R.id.text_mark);
            textView.setText(conditions[position]);

            textView.setOnClickListener(v -> {
                setTitle(textView.getText().toString());
                windowB.dismiss();
            });

            return view;
        }
    }
}
