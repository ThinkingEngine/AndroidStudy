package com.chengsheng.cala.htcm.module.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chengsheng.cala.htcm.R;

import com.chengsheng.cala.htcm.adapter.AIAssistantAdapter;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.activitys.AIAssistantActivity;
import com.chengsheng.cala.htcm.module.activitys.BarADActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamAppointmentActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamReportActivity;
import com.chengsheng.cala.htcm.module.activitys.MyExamActivity;
import com.chengsheng.cala.htcm.module.activitys.NewsListActivity;
import com.chengsheng.cala.htcm.module.activitys.ServiceMessageActivity;
import com.chengsheng.cala.htcm.network.ArticlesService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.AssistantList;
import com.chengsheng.cala.htcm.protocol.articleModel.RecommendedNews;
import com.chengsheng.cala.htcm.protocol.childmodela.NureadMessage;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateAIAssisont;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.chengsheng.cala.htcm.widget.FooterAdapter;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;

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
public class HomeFragment extends Fragment implements UpdateAIAssisont, UpdateStateInterface {

    private Retrofit retrofit;

    private BGABanner bodyBanner;
    private MyRecyclerView newsRecyclerView;
    private MyRecyclerView appointmentRecyclerView;
    private SwipeRefreshLayout refreshPage;
    private ImageView appointmentExamMark;
    private ImageView myExamMark;
    private ImageView examEeportMark;
    private RelativeLayout aiAssistant;
    private RelativeLayout recommendNews;
    private FrameLayout newMessage;
    private ImageView currentHasMessage;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CallBackDataAuth.setUpdateAIAssisont(this);
        CallBackDataAuth.setUpdateStateInterface(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //生成主页面视图.
        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        initViews(rootView);

        //检测当前是否有未读信息。
        updateServiceSMS();
        //为“智能助理”列表注入数据
        updateAIAssistant();
        //新闻列表
        updateNews();

        BGALocalImageSize localImageSize = new BGALocalImageSize(1080,504,540,252);
        bodyBanner.setData(localImageSize,ImageView.ScaleType.CENTER_CROP,R.mipmap.bannera,R.mipmap.bannerb,R.mipmap.bannerc);
        bodyBanner.setDelegate((banner, itemView, model, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("NUM",position);
            ActivityUtil.Companion.startActivity(getContext(),new BarADActivity(),bundle);
        });

        //跳转到“智能助理”
        aiAssistant.setOnClickListener(v -> ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new AIAssistantActivity()));

        //跳转到 “我的体检”
        myExamMark.setOnClickListener(v -> ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new MyExamActivity()));

        //跳转到“体检报告”
        examEeportMark.setOnClickListener(v -> ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new ExamReportActivity()));

        //刷新主页面
        refreshPage.setOnRefreshListener(() -> {
            if (UserUtil.isLogin()) {
                updateServiceSMS();
                updateNews();
                updateAIAssistant();
            } else {
                updateNews();
            }

        });

        //跳转到“体检预约”
        appointmentExamMark.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ExamAppointmentActivity.class);
            startActivity(intent);
        });

        recommendNews.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), NewsListActivity.class);
            startActivity(intent);
        });

        newMessage.setOnClickListener(v -> ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new ServiceMessageActivity()));

        return rootView;
    }

    @Override
    public void updateResult(boolean status) {
        if (status) {
            updateServiceSMS();
            updateAIAssistant();
            updateNews();
        }
    }

    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            updateServiceSMS();
            updateAIAssistant();
        }
    }

    private void updateNews() {
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
                        refreshPage.setRefreshing(false);
                        FooterAdapter footAdapter = new FooterAdapter(recommendedNews.getItems(), getContext());
                        newsRecyclerView.setAdapter(footAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshPage.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        refreshPage.setRefreshing(false);
                    }
                });


    }

    private void updateServiceSMS() {
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
        service.getAIAssistants(UserUtil.getTokenType() + " " + UserUtil.getAccessToken(), "1","")
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
                        refreshPage.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshPage.setRefreshing(false);
                        List<AssistantItem> temp = new ArrayList<>();
                        AIAssistantAdapter appointment = new AIAssistantAdapter(getContext(), temp, -1, e.toString());
                        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        appointmentRecyclerView.setAdapter(appointment);
                    }

                    @Override
                    public void onComplete() {
                        refreshPage.setRefreshing(false);
                    }
                });
    }

    private void initViews(View rootView){
        //关联主页面控件。
        bodyBanner = rootView.findViewById(R.id.banner_a);//bar
        appointmentRecyclerView = rootView.findViewById(R.id.appointment_recycler_view);
        newsRecyclerView = rootView.findViewById(R.id.recommend_news_recycler_view);
        refreshPage = rootView.findViewById(R.id.refresh_main_page);
        appointmentExamMark = rootView.findViewById(R.id.appointment_exam_mark);
        myExamMark = rootView.findViewById(R.id.my_exam_mark);
        aiAssistant = rootView.findViewById(R.id.ai_assistant);
        recommendNews = rootView.findViewById(R.id.recommend_news);
        examEeportMark = rootView.findViewById(R.id.exam_report_mark);
        newMessage = rootView.findViewById(R.id.title_header_main_page).findViewById(R.id.new_message);
        currentHasMessage = rootView.findViewById(R.id.title_header_main_page).findViewById(R.id.current_has_message);

        appointmentRecyclerView.setFocusable(false);
        appointmentRecyclerView.setNestedScrollingEnabled(false);
        newsRecyclerView.setFocusable(false);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setNestedScrollingEnabled(false);
        currentHasMessage.setVisibility(View.INVISIBLE);
    }


}
