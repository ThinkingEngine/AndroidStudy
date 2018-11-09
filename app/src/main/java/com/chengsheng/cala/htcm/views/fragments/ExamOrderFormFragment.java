package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamOrderFormRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ExamOrderFormFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView orderList;
    private SwipeRefreshLayout refreshExamOrder;

    public ExamOrderFormFragment() {
        // Required empty public constructor
    }


    public static ExamOrderFormFragment newInstance(String param1, String param2) {
        ExamOrderFormFragment fragment = new ExamOrderFormFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_exam_order_form, container, false);

        orderList = rootView.findViewById(R.id.order_list);
        refreshExamOrder = rootView.findViewById(R.id.refresh_exam_order);

        refreshExamOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                data.add("new");
                Toast.makeText(getContext(),"下拉刷新--",Toast.LENGTH_SHORT).show();
//                appointment.notifyDataSetChanged();
                refreshExamOrder.setRefreshing(false);
            }
        });

        List<String> datas = new ArrayList<>();
        datas.add("testq");
        datas.add("testq");
        datas.add("testq");
        ExamOrderFormRecyclerAdapter adapter = new ExamOrderFormRecyclerAdapter(getContext(),datas);
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList.setAdapter(adapter);

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
