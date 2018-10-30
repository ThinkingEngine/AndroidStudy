package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.FamiliesItemRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFamilyListInteractionListener mListener;

    public FamilyListFragment() {
    }

    public static FamilyListFragment newInstance(String param1, String param2) {
        FamilyListFragment fragment = new FamilyListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootViews = inflater.inflate(R.layout.fragment_family_list, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootViews.findViewById(R.id.fresh_family_recycler);
        RecyclerView recyclerView = (RecyclerView) rootViews.findViewById(R.id.families_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final FamiliesItemRecyclerAdapter familiesItemRecyclerAdapter = new FamiliesItemRecyclerAdapter(getContext(),tempDatas());
        recyclerView.setAdapter(familiesItemRecyclerAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                data.add("new");
                Toast.makeText(getContext(),"下拉刷新--",Toast.LENGTH_SHORT).show();
                familiesItemRecyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootViews;
    }

    public void onButtonPressed(Bundle data) {
        if (mListener != null) {
            mListener.onFamilyListInteraction(data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFamilyListInteractionListener) {
            mListener = (OnFamilyListInteractionListener) context;
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

    public interface OnFamilyListInteractionListener {
        // TODO: Update argument type and name
        void onFamilyListInteraction(Bundle data);
    }
    //临时测试数据（测试完成后删除）
    private List<Map<String,String>> tempDatas(){
        List<Map<String,String>> datas = new ArrayList<>();
        Map<String,String> a = new HashMap<>();
        Map<String,String> b = new HashMap<>();
        Map<String,String> c = new HashMap<>();
        a.put("FAMILIES_NAME","王树彤");
        a.put("FAMILIES_MARK","本人");
        a.put("FAMILIES_TEL","199****7068");
        a.put("FAMILIES_ID","51061419980808****");
        a.put("AUTHENTICATION","true");
        b.put("FAMILIES_NAME","王洛已");
        b.put("FAMILIES_MARK","女儿");
        b.put("FAMILIES_TEL","199****7068");
        b.put("FAMILIES_ID","51061419808****");
        b.put("AUTHENTICATION","true");
        c.put("FAMILIES_NAME","李凯旋");
        c.put("FAMILIES_MARK","丈夫");
        c.put("FAMILIES_TEL","199****7068");
        c.put("FAMILIES_ID","51061419808****");
        c.put("AUTHENTICATION","false");

        datas.add(a);
        datas.add(b);
        datas.add(c);

        return datas;
    }
}
