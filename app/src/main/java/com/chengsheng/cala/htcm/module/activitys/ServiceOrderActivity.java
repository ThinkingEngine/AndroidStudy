package com.chengsheng.cala.htcm.module.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.widget.ConditionPopupWindow;
import com.chengsheng.cala.htcm.module.fragments.ServiceOrderFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceOrderActivity extends BaseActivity implements ServiceOrderFragment.OnFragmentInteractionListener{

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
        List<Map<String,String>> listDatas = new ArrayList<>();
        Map<String,String> mapa = new HashMap<>();
        mapa.put("SELECT","false");
        mapa.put("DATA","周子轩");
        Map<String,String> mapb = new HashMap<>();
        mapb.put("SELECT","false");
        mapb.put("DATA","周父");
        Map<String,String> mapc = new HashMap<>();
        mapc.put("SELECT","false");
        mapc.put("DATA","周母");
        listDatas.add(mapa);
        listDatas.add(mapb);
        listDatas.add(mapc);

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
                windowB.showAsDropDown(clickContainerServiceOrder);
                arrowUpDownServiceOrder.setSelected(true);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }

    private void initViews() {
        serviceOrderFragment = findViewById(R.id.service_order_fragment);
        serviceOrderSelectHeader = findViewById(R.id.service_order_select_header);

        backLogin = findViewById(R.id.title_header_service_oder).findViewById(R.id.back_login);
        conditionSelectIcon = findViewById(R.id.title_header_service_oder).findViewById(R.id.condition_select_icon);
        clickContainerServiceOrder = findViewById(R.id.title_header_service_oder).findViewById(R.id.click_container_service_order);
        arrowUpDownServiceOrder = findViewById(R.id.title_header_service_oder).findViewById(R.id.arrow_up_down_service_order);
        menuBarTitle = findViewById(R.id.title_header_service_oder).findViewById(R.id.menu_bar_title);

        setTitle(conditions[0]);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setTitle(String title){
        menuBarTitle.setText(title);
        arrowUpDownServiceOrder.setSelected(false);
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

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTitle(textView.getText().toString());
                    arrowUpDownServiceOrder.setSelected(false);
                    windowB.dismiss();
                }
            });

            return view;
        }
    }
}
