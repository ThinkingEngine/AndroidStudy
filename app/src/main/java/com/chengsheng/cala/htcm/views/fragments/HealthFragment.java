package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
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

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.FMRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

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

        final List<String> data = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            data.add("input_data");
        }

        peopleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        final FMRecyclerAdapter fmRecyclerAdapter = new FMRecyclerAdapter(getContext(),data);
        peopleRecycler.setAdapter(fmRecyclerAdapter);

        freahPeopleRecycler.setColorSchemeColors(Color.BLUE);

        freahPeopleRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.add("new");
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
}
