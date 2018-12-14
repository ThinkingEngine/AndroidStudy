package com.chengsheng.cala.htcm.views.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesList;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.MessageEvent;
import com.chengsheng.cala.htcm.utils.UpdateConditionInterface;
import com.chengsheng.cala.htcm.views.adapters.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.views.customviews.ConditionPopupWindow;
import com.chengsheng.cala.htcm.views.fragments.ExamOrderFormFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ExamOrderFormActivity extends BaseActivity implements ExamOrderFormFragment.OnFragmentInteractionListener, UpdateConditionInterface {
    private TextView title;
    private ImageView back, iconButton;
    private TabLayout orderFormSelectHeader;
    private ViewPager orderFormFragment;

    private String[] marks = {"全部", "待付款", "已付款", "待评价", "已取消"};
    private List<Fragment> fragments;
    private List<Map<String, String>> listDatas = new ArrayList<>();

    private Retrofit retrofit;
    private HTCMApp app;
    private StringBuffer sb;

    private String id;
    private boolean isGetFamilies = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = HTCMApp.create(getApplicationContext());
        EventBus.getDefault().register(this);
        CallBackDataAuth.setUpdateConditionInterface(this);

        setContentView(R.layout.activity_exam_order_form);

        id = getIntent().getStringExtra("CUSTOMER_ID");

        getFamilies();
        initViews();
        initDatas();

        for (int i = 0; i < marks.length; i++) {
            orderFormSelectHeader.getTabAt(i).setText(marks[i]);
        }
    }

    private void initViews() {
        title = findViewById(R.id.title_header_exam_order_form).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_exam_order_form).findViewById(R.id.back_login);
        iconButton = findViewById(R.id.title_header_exam_order_form).findViewById(R.id.search_button);
        orderFormSelectHeader = findViewById(R.id.order_form_select_header);
        orderFormFragment = findViewById(R.id.order_form_fragment);
        title.setText("体检订单");
        iconButton.setImageResource(R.mipmap.tijian_xuanren);




    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        isGetFamilies = messageEvent.getFamiliesListsState();
    }

    private void initDatas() {

        fragments = new ArrayList<>();
        for (int i = 0; i < marks.length; i++) {
            orderFormSelectHeader.addTab(orderFormSelectHeader.newTab().setText(marks[i]));
            fragments.add(ExamOrderFormFragment.newInstance(marks[i], ""));
        }

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        orderFormFragment.setAdapter(adapter);

        orderFormSelectHeader.setupWithViewPager(orderFormFragment);

        final ConditionPopupWindow window = new ConditionPopupWindow(this, listDatas);
        iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.showAsDropDown(iconButton);
            }
        });
    }

    private void getFamilies() {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        service.getFamiliesList(app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FamiliesList>() {
                    @Override
                    public void onNext(FamiliesList familiesList) {
                        for (FamiliesListItem familiesListItem : familiesList.getItems()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("SELECT", "false");
                            map.put("DATA", familiesListItem.getFullname());
                            map.put("ID", String.valueOf(familiesListItem.getId()));
                            listDatas.add(map);
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ExamOrderFormActivity.this, "没有获取到家人列表!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void selectCondition(List<Map<String, String>> datas, boolean update) {

    }
}
