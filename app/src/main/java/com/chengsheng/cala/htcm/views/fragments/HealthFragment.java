package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.FamilyManageActivity;
import com.chengsheng.cala.htcm.views.adapters.FMRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HealthFragment() {
        // Required empty public constructor
    }

    public static HealthFragment newInstance(String param1, String param2) {
        HealthFragment fragment = new HealthFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_health, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.title_header_health).findViewById(R.id.menu_bar_title);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.title_header_health).findViewById(R.id.back_login);
        TextView childTitle = (TextView) rootView.findViewById(R.id.title_header_health).findViewById(R.id.message_mark_text);
        RecyclerView peopleRecycler = (RecyclerView) rootView.findViewById(R.id.family_manage_recycler);
        final SwipeRefreshLayout freahPeopleRecycler = (SwipeRefreshLayout) rootView.findViewById(R.id.fresh_people_recycler);

        title.setText("健康");
        imageView.setVisibility(View.INVISIBLE);
        childTitle.setText("家人管理");

        childTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FamilyManageActivity.class);
                startActivity(intent);
            }
        });

        final List<String> data = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            data.add("input_data");
        }

        peopleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        final FMRecyclerAdapter fmRecyclerAdapter = new FMRecyclerAdapter(getContext(),tempDatas());
        peopleRecycler.setAdapter(fmRecyclerAdapter);

        freahPeopleRecycler.setColorSchemeColors(Color.BLUE);

        freahPeopleRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                data.add("new");
                Toast.makeText(getContext(),"下拉刷新--",Toast.LENGTH_SHORT).show();
                fmRecyclerAdapter.notifyDataSetChanged();
                freahPeopleRecycler.setRefreshing(false);
            }
        });

        return rootView;
    }

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

    //临时测试数据(完成测试后删除)
    private List<Map<String,String>> tempDatas(){
        List<Map<String,String>> datas = new ArrayList<>();
        Map<String,String> user_a = new HashMap<>();
        Map<String,String> user_b = new HashMap<>();
        Map<String,String> user_c = new HashMap<>();
        user_a.put("NAME","王树彤");
        user_a.put("MARK","本人");
        user_a.put("USER_ID","511 623 000 66");
        user_a.put("VERIFY","TRUE");

        user_b.put("NAME","王树同");
        user_b.put("MARK","女儿");
        user_b.put("USER_ID","511 623 000 66");
        user_b.put("VERIFY","TRUE");

        user_c.put("NAME","李凯旋");
        user_c.put("MARK","丈夫");
        user_c.put("VERIFY","FALSE");

        datas.add(user_a);
        datas.add(user_b);
        datas.add(user_c);
        return datas;
    }
}
