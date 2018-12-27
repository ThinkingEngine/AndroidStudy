package com.chengsheng.cala.htcm.module.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.AIAssistantAdapter;
import com.chengsheng.cala.htcm.base.BaseRefreshFragment;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.activitys.AIAssistantActivity;
import com.chengsheng.cala.htcm.module.activitys.BarADActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamAppointmentActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamReportActivity;
import com.chengsheng.cala.htcm.module.activitys.MessageActivity;
import com.chengsheng.cala.htcm.module.activitys.MyExamActivity;
import com.chengsheng.cala.htcm.module.activitys.NewsListActivity;
import com.chengsheng.cala.htcm.network.ArticlesService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.AssistantList;
import com.chengsheng.cala.htcm.protocol.articleModel.RecommendedItem;
import com.chengsheng.cala.htcm.protocol.articleModel.RecommendedNews;
import com.chengsheng.cala.htcm.protocol.childmodela.NureadMessage;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.chengsheng.cala.htcm.widget.FooterAdapter;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Author:
 * CreateDate:
 * Description: APP首页
 */
public class HomeFragment extends BaseRefreshFragment<RecommendedItem> {

    private Retrofit retrofit;

    private MyRecyclerView newsRecyclerView;
    private MyRecyclerView appointmentRecyclerView;
    private ImageView currentHasMessage;
    private SmartRefreshLayout smartRefreshLayout;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void getData() {

    }

    @Override
    public void initViews(@NotNull View rootView) {
        BGABanner bodyBanner = rootView.findViewById(R.id.banner_a);
        appointmentRecyclerView = rootView.findViewById(R.id.appointment_recycler_view);
        newsRecyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayout aiAssistant = rootView.findViewById(R.id.ai_assistant);
        LinearLayout recommendNews = rootView.findViewById(R.id.recommend_news);
        FrameLayout newMessage = rootView.findViewById(R.id.new_message);
        currentHasMessage = rootView.findViewById(R.id.current_has_message);
        smartRefreshLayout = rootView.findViewById(R.id.smartRefreshLayout);

        appointmentRecyclerView.setFocusable(false);
        appointmentRecyclerView.setNestedScrollingEnabled(false);
        newsRecyclerView.setFocusable(false);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        newsRecyclerView.setNestedScrollingEnabled(false);
        currentHasMessage.setVisibility(View.INVISIBLE);
        smartRefreshLayout.setEnableLoadmore(false);

        //新闻列表
        getRecommendNews();

        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 504,
                540, 252);
        bodyBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP, R.mipmap.bannera,
                R.mipmap.bannerb, R.mipmap.bannerc);
        bodyBanner.setDelegate((banner, itemView, model, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("NUM", position);
            startActivity(new BarADActivity(), bundle);
        });

        //智能助理
        aiAssistant.setOnClickListener(v -> startActivityWithLoginStatus(new AIAssistantActivity()));

        //我的体检
        rootView.findViewById(R.id.tvMyExam).setOnClickListener(v ->
                startActivityWithLoginStatus(new MyExamActivity()));

        //体检报告
        rootView.findViewById(R.id.tvExamReport).setOnClickListener(v ->
                startActivityWithLoginStatus(new ExamReportActivity()));

        //体检预约
        rootView.findViewById(R.id.tvExamAppointment).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ExamAppointmentActivity.class);
            startActivity(intent);
        });

        recommendNews.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), NewsListActivity.class);
            startActivity(intent);
        });

        newMessage.setOnClickListener(v -> {
            startActivityWithLoginStatus(new MessageActivity());
        });
    }

    @Override
    public void getData(int page) {
        if (UserUtil.isLogin()) {
            getSystemSMS();
            updateAIAssistant();
        }
    }

    @Nullable
    @Override
    public BaseQuickAdapter<RecommendedItem> getCurrentAdapter() {
        return null;
    }

    /**
     * 获取推荐资讯
     */
    private void getRecommendNews() {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        ArticlesService service1 = retrofit.create(ArticlesService.class);
        service1.getRecommendedNews("display_order:asc")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RecommendedNews>() {
                    @Override
                    public void onNext(RecommendedNews recommendedNews) {
                        FooterAdapter footAdapter = new FooterAdapter(recommendedNews.getItems(), context);
                        newsRecyclerView.setAdapter(footAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取系统消息
     */
    private void getSystemSMS() {
        if (!UserUtil.isLogin()) {
            return;
        }

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getUnreadMessageNum(UserUtil.getTokenType() + " " + UserUtil.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<NureadMessage>() {
                    @Override
                    public void onNext(NureadMessage nureadMessage) {
                        if (nureadMessage.getUnread() > 0) {
                            currentHasMessage.setVisibility(View.VISIBLE);
                        } else {
                            currentHasMessage.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 更新智能助理数据
     */
    private void updateAIAssistant() {
        if (!UserUtil.isLogin()) {
            List<AssistantItem> temp = new ArrayList<>();
            AIAssistantAdapter appointment = new AIAssistantAdapter(getContext(), temp, -2, "");
            appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            appointmentRecyclerView.setAdapter(appointment);
            return;
        }

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getAIAssistants(UserUtil.getTokenType() + " " + UserUtil.getAccessToken(), "1", "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AssistantList>() {
                    @Override
                    public void onNext(AssistantList assistantList) {
                        List<AssistantItem> datas = new ArrayList<>();
                        for (AssistantItem assistantItem : assistantList.getItems()) {
                            if (!assistantItem.getOrder().isIs_closed_recommend()) {
                                datas.add(assistantItem);
                            }
                        }
                        AIAssistantAdapter appointment;
                        if (datas.isEmpty()) {
                            appointment = new AIAssistantAdapter(getContext(), datas, 0, "");
                        } else {
                            appointment = new AIAssistantAdapter(getContext(), datas, 1, "");
                        }
                        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        appointmentRecyclerView.setAdapter(appointment);
                        smartRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        List<AssistantItem> temp = new ArrayList<>();
                        AIAssistantAdapter appointment = new AIAssistantAdapter(getContext(), temp, -1, e.toString());
                        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        appointmentRecyclerView.setAdapter(appointment);
                        smartRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSystemSMS();
    }
}
