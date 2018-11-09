package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.AIAssistantActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamAppointmentActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamReportActivity;
import com.chengsheng.cala.htcm.views.activitys.MyExamActivity;
import com.chengsheng.cala.htcm.views.adapters.AIAssistantRecyclerAdapter;
import com.chengsheng.cala.htcm.views.adapters.BannerAdapter;
import com.chengsheng.cala.htcm.views.adapters.NewsRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    int[] images = new int[]{R.mipmap.bannera,R.mipmap.bannerb,R.mipmap.bannerc};
    Handler mHandler = new Handler();

    private ViewPager bodyBanner;
    private RecyclerView appointmentRecyclerView,newsRecyclerView;
    private SwipeRefreshLayout refreshPage;
    private ImageView appointmentExamMark;
    private ImageView myExamMark;
    private ImageView examEeportMark;
    private RelativeLayout aiAssistant;
    private LinearLayout pointGroup;

    private int[] barImages = {R.mipmap.bannera,R.mipmap.bannerb,R.mipmap.bannerc};//bar图片数据
    private int[] newsImages = new int[]{R.mipmap.tuijianzixun_imga,R.mipmap.tuijianzixun_imgb,R.mipmap.tuijianzixun_imgc,R.mipmap.tuijianzixun_imgd};

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
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
        examEeportMark = rootView.findViewById(R.id.exam_report_mark);

        pointGroup = rootView.findViewById(R.id.point_group);

        //为“智能助理”列表注入数据
        List<Map<String,String>> datas = tempNewsDatas();
        AIAssistantRecyclerAdapter appointment = new AIAssistantRecyclerAdapter(getContext());
        NewsRecyclerAdapter newsAppointment = new NewsRecyclerAdapter(getContext(),datas);


        final List<ImageView> data = new ArrayList<>();
        for(int i = 0;i < barImages.length;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(barImages[i]);
            data.add(imageView);
        }
        for(int i = 0;i<barImages.length;i++){
            ImageView point = new ImageView(getContext());
            point.setImageResource(R.drawable.selecter_white_dot);
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize,pointSize);
            if(i > 0){
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.point);
                point.setSelected(false);
            }else{
                point.setSelected(true);
            }

            point.setLayoutParams(params);
            pointGroup.addView(point);
        }

        BannerAdapter bannerAdapter = new BannerAdapter(getContext(),data);
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
                if(currentPosition == bodyBanner.getAdapter().getCount() - 1){
                    bodyBanner.setCurrentItem(0);
                }else{
                    bodyBanner.setCurrentItem(currentPosition+1);
                }

                mHandler.postDelayed(this,5000);
            }
        },5000);

        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentRecyclerView.setAdapter(appointment);
        appointmentRecyclerView.setNestedScrollingEnabled(false);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setAdapter(newsAppointment);
        newsRecyclerView.setNestedScrollingEnabled(false);

        //跳转到“智能助理”
        aiAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AIAssistantActivity.class);
                getContext().startActivity(intent);
            }
        });
        //跳转到 “我的体检”
        myExamMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MyExamActivity.class);
                getContext().startActivity(intent);
            }
        });
        //跳转到“体检报告”
        examEeportMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ExamReportActivity.class);
                getContext().startActivity(intent);
            }
        });

        //刷新主页面
        refreshPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                data.add("new");
                Toast.makeText(getContext(),"下拉刷新--",Toast.LENGTH_SHORT).show();
//                appointment.notifyDataSetChanged();
                refreshPage.setRefreshing(false);
            }
        });

        //跳转到“体检预约”
        appointmentExamMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ExamAppointmentActivity.class);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private List<Map<String,String>> tempNewsDatas(){
        List<Map<String,String>> result = new ArrayList<>();
        for(int i = 0;i < newsImages.length;i++){
            Map<String,String> data = new HashMap<>();
            data.put("ICON",String.valueOf(newsImages[i]));
            data.put("TITLE","新闻:"+i);
            data.put("BROWSE",String.valueOf(i*1000));
            result.add(data);
        }

        return result;
    }

}
