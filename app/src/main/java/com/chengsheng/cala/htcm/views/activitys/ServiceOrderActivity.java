package com.chengsheng.cala.htcm.views.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.views.customviews.ConditionPopupWindow;
import com.chengsheng.cala.htcm.views.fragments.ExamOrderFormFragment;
import com.chengsheng.cala.htcm.views.fragments.ServiceOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class ServiceOrderActivity extends AppCompatActivity implements ServiceOrderFragment.OnFragmentInteractionListener{

    private TabLayout serviceOrderSelectHeader;
    private ViewPager serviceOrderFragment;
    private ImageView backLogin, conditionSelectIcon;
    private LinearLayout clickContainerServiceOrder;
    private ImageView arrowUpDownServiceOrder;
    private TextView menuBarTitle;

    private String[] marks = {"全部","待付款","已付款","待评价","已取消"};
    private List<Fragment> fragments;

    private PopupWindow windowB;
    private String[] conditions = {"全部","特色服务","医疗美容"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_order);

        initViews();
        initDatas();
        setPopupWindow();

        for(int i = 0;i < marks.length;i++){
            serviceOrderSelectHeader.getTabAt(i).setText(marks[i]);
        }

        //临时数据
        List<String> listDatas = new ArrayList<>();
        listDatas.add("全部");
        listDatas.add("周子轩");
        listDatas.add("周父");
        listDatas.add("周母");

        final ConditionPopupWindow window = new ConditionPopupWindow(this,listDatas);

        conditionSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.showAsDropDown(conditionSelectIcon);
            }
        });

        clickContainerServiceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(window == null){
                    Toast.makeText(ServiceOrderActivity.this,"window null",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ServiceOrderActivity.this,"window ok",Toast.LENGTH_SHORT).show();
                }

                windowB.showAsDropDown(clickContainerServiceOrder);
            }
        });
    }

    private void initViews() {
        serviceOrderFragment = findViewById(R.id.service_order_fragment);
        serviceOrderSelectHeader = findViewById(R.id.service_order_select_header);

        backLogin = findViewById(R.id.title_header_service_oder).findViewById(R.id.back_login);
        conditionSelectIcon = findViewById(R.id.title_header_service_oder).findViewById(R.id.condition_select_icon);
        clickContainerServiceOrder = findViewById(R.id.title_header_service_oder).findViewById(R.id.click_container_service_order);
        arrowUpDownServiceOrder = findViewById(R.id.title_header_service_oder).findViewById(R.id.arrow_up_down_service_order);
        menuBarTitle = findViewById(R.id.title_header_service_oder).findViewById(R.id.menu_bar_title);


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

            TextView textView = view.findViewById(R.id.text_mark);
            textView.setText(conditions[position]);

            return view;
        }
    }
}
