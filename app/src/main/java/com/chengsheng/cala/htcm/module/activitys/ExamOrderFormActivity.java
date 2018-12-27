package com.chengsheng.cala.htcm.module.activitys;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.fragments.ExamOrderFormFragment;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.FamiliesList;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.MessageEvent;
import com.chengsheng.cala.htcm.utils.UpdateConditionInterface;

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

/**
 * 体检订单
 */
public class ExamOrderFormActivity extends BaseActivity implements UpdateConditionInterface {
    private TabLayout orderFormSelectHeader;
    private ViewPager orderFormFragment;

    private String[] marks = {"全部", "待付款", "已付款", "待评价", "已取消"};
    private List<Fragment> fragments;
    private List<Map<String, String>> listDatas = new ArrayList<>();

    private Retrofit retrofit;
    private HTCMApp app;

    private void initViews() {
        orderFormSelectHeader = findViewById(R.id.order_form_select_header);
        orderFormFragment = findViewById(R.id.order_form_fragment);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        boolean isGetFamilies = messageEvent.getFamiliesListsState();
    }

    private void initDatas() {

        fragments = new ArrayList<>();
        for (String mark : marks) {
            orderFormSelectHeader.addTab(orderFormSelectHeader.newTab().setText(mark));
//            fragments.add(ExamOrderFormFragment.newInstance(mark, ""));
            ExamOrderFragment fragment = new ExamOrderFragment();
            fragment.setMark(mark);
            fragments.add(fragment);
        }

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        orderFormFragment.setAdapter(adapter);
        orderFormSelectHeader.setupWithViewPager(orderFormFragment);

        //筛选
//        final ConditionPopupWindow window = new ConditionPopupWindow(this, listDatas);
//        iconButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                window.showAsDropDown(iconButton);
//            }
//        });
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_order_form;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        EventBus.getDefault().register(this);
        CallBackDataAuth.setUpdateConditionInterface(this);


        String id = getIntent().getStringExtra("CUSTOMER_ID");

        getFamilies();
        initViews();
        initDatas();

        for (int i = 0; i < marks.length; i++) {
            orderFormSelectHeader.getTabAt(i).setText(marks[i]);
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void selectCondition(List<Map<String, String>> datas, boolean update) {

    }
}
