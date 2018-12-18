package com.chengsheng.cala.htcm.module.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.module.TestActivity;
import com.chengsheng.cala.htcm.adapter.AIAssistantRecyclerAdapter;
import com.chengsheng.cala.htcm.adapter.BannerAdapter;
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
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateAIAssisont;
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
public class MainPageFragment extends Fragment implements UpdateAIAssisont {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Retrofit retrofit;

    private OnFragmentInteractionListener mListener;


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

    public MainPageFragment() {

    }

    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        app = HTCMApp.create(getContext());
        CallBackDataAuth.setUpdateAIAssisont(this);
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

        Log.e("TAG", app.getTokenType() + " " + app.getAccessToken());

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
//                Intent intent = new Intent(getContext(), AIAssistantActivity.class);
                Intent intent = new Intent(getContext(), TestActivity.class);
                startActivity(intent);
            }
        });

        //跳转到 “我的体检”
        myExamMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyExamActivity.class);
                startActivity(intent);
            }
        });

        //跳转到“体检报告”
        examEeportMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExamReportActivity.class);
                startActivity(intent);
            }
        });

        //刷新主页面
        refreshPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateServiceSMS();
                updateNews();
                updateAIAssistant();
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
                Intent intent = new Intent(getContext(), ServiceMessageActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateResult(boolean status) {
        if (status) {
            updateAIAssistant();
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void updateServiceSMS() {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getUnreadMessageNum(app.getTokenType() + " " + app.getAccessToken())
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

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getAIAssistants(app.getTokenType() + " " + app.getAccessToken(), "1")
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
