package com.chengsheng.cala.htcm.module.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.AIAssistantRecyclerAdapter;
import com.chengsheng.cala.htcm.adapter.BannerAdapter;
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
import java.util.Objects;

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

    Handler mHandler = new Handler();

    private ViewPager bodyBanner;
    private MyRecyclerView newsRecyclerView;
    private MyRecyclerView appointmentRecyclerView;
    private SwipeRefreshLayout refreshPage;
    private ImageView appointmentExamMark;
    private ImageView myExamMark;
    private ImageView examEeportMark;
    private RelativeLayout aiAssistant;
    private RelativeLayout recommendNews;
    private LinearLayout pointGroup;
    private FrameLayout newMessage;
    private ImageView currentHasMessage;

    private HTCMApp app;

    private int[] barImages = {R.mipmap.bannera, R.mipmap.bannerb, R.mipmap.bannerc};//bar图片数据

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = HTCMApp.create(getContext());
        CallBackDataAuth.setUpdateAIAssisont(this);
        CallBackDataAuth.setUpdateStateInterface(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //生成主页面视图.
        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);

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
        pointGroup = rootView.findViewById(R.id.point_group);
        newMessage = rootView.findViewById(R.id.title_header_main_page).findViewById(R.id.new_message);
        currentHasMessage = rootView.findViewById(R.id.title_header_main_page).findViewById(R.id.current_has_message);

        appointmentRecyclerView.setFocusable(false);
        appointmentRecyclerView.setNestedScrollingEnabled(false);
        newsRecyclerView.setFocusable(false);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setNestedScrollingEnabled(false);
        currentHasMessage.setVisibility(View.INVISIBLE);


        //检测当前是否有未读信息。
        updateServiceSMS();

        //为“智能助理”列表注入数据
        updateAIAssistant();
        updateNews();

        final List<ImageView> data = new ArrayList<>();
        for (int i = 0; i < barImages.length; i++) {
            ImageView imageView = new ImageView(getContext());
            final int finalI = i;
            //查看详情
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), BarADActivity.class);
                    intent.putExtra("NUM", finalI);
                    getContext().startActivity(intent);
                }
            });
            imageView.setImageResource(barImages[i]);
            data.add(imageView);
        }
        for (int i = 0; i < barImages.length; i++) {
            ImageView point = new ImageView(getContext());
            point.setImageResource(R.drawable.selecter_white_dot);
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
            if (i > 0) {
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.point);
                point.setSelected(false);
            } else {
                point.setSelected(true);
            }

            point.setLayoutParams(params);
            pointGroup.addView(point);
        }

        BannerAdapter bannerAdapter = new BannerAdapter(bodyBanner, data);
        bodyBanner.setAdapter(bannerAdapter);

        bodyBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            int lastPosition;

            @Override
            public void onPageSelected(int i) {
                i = i % data.size();
                pointGroup.getChildAt(i).setSelected(true);
                pointGroup.getChildAt(lastPosition).setSelected(false);
                lastPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentPosition = bodyBanner.getCurrentItem();
                if (currentPosition == Objects.requireNonNull(bodyBanner.getAdapter()).getCount() - 1) {
                    bodyBanner.setCurrentItem(0);
                } else {
                    bodyBanner.setCurrentItem(currentPosition + 1);
                }

                mHandler.postDelayed(this, 5000);
            }
        }, 5000);


        //跳转到“智能助理”
        aiAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new AIAssistantActivity());
            }
        });

        //跳转到 “我的体检”
        myExamMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new MyExamActivity());
            }
        });

        //跳转到“体检报告”
        examEeportMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new ExamReportActivity());
            }
        });

        //刷新主页面
        refreshPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (UserUtil.isLogin()) {
                    updateServiceSMS();
                    updateNews();
                    updateAIAssistant();
                } else {
                    updateNews();
                }

            }
        });

        //跳转到“体检预约”
        appointmentExamMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExamAppointmentActivity.class);
                startActivity(intent);
            }
        });

        recommendNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewsListActivity.class);
                startActivity(intent);
            }
        });

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new ServiceMessageActivity());
            }
        });

        return rootView;
    }

    @Override
    public void updateResult(boolean status) {
        if (status) {
            updateServiceSMS();
            updateAIAssistant();
        }
    }

    @Override
    public void updateServiceMessage(boolean status) {
        Log.e("TAG", "status" + status);
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
//                            if (recommendedNews.getItems().size() == 4 && recommendedNews.getMeta().getPagination().getTotal_pages() > 1) {
//                                View footer = LayoutInflater.from(getContext()).inflate(R.layout.recycler_footer_layout, null);
//                                TextView textView = footer.findViewById(R.id.recycler_footer);
//                                textView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Toast.makeText(getContext(), "点击尾部", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                footAdapter.addFooterView(footer);
//                            }


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
            AIAssistantRecyclerAdapter appointment = new AIAssistantRecyclerAdapter(getContext(), temp, -2, "");
            appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            appointmentRecyclerView.setAdapter(appointment);
            return;
        }

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getAIAssistants(UserUtil.getTokenType() + " " + UserUtil.getAccessToken(), "1")
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
                        AIAssistantRecyclerAdapter appointment;
                        if (datas.isEmpty()) {
                            appointment = new AIAssistantRecyclerAdapter(getContext(), datas, 0, "");
                        } else {
                            appointment = new AIAssistantRecyclerAdapter(getContext(), datas, 1, "");
                        }
                        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        appointmentRecyclerView.setAdapter(appointment);
                        refreshPage.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshPage.setRefreshing(false);
                        List<AssistantItem> temp = new ArrayList<>();
                        AIAssistantRecyclerAdapter appointment = new AIAssistantRecyclerAdapter(getContext(), temp, -1, e.toString());
                        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        appointmentRecyclerView.setAdapter(appointment);
                    }

                    @Override
                    public void onComplete() {
                        refreshPage.setRefreshing(false);
                    }
                });
    }

}