package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.ExamReportRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExamReprotListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private LinearLayout childButtonContainer;
    private TextView reportCompare,cancelReportCompare,startReportCompare;
    private RecyclerView targetExamReportList;

    public ExamReprotListFragment() {

    }

    public static ExamReprotListFragment newInstance(String param1, String param2) {
        ExamReprotListFragment fragment = new ExamReprotListFragment();
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

        View rootViews = inflater.inflate(R.layout.fragment_exam_reprot_list, container, false);
        childButtonContainer = rootViews.findViewById(R.id.child_button_container);
        reportCompare = rootViews.findViewById(R.id.report_compare);
        cancelReportCompare = rootViews.findViewById(R.id.cancel_report_compare);
        startReportCompare = rootViews.findViewById(R.id.start_report_compare);
        targetExamReportList = rootViews.findViewById(R.id.target_exam_report_list);

        childButtonContainer.setVisibility(View.INVISIBLE);

        List<String> datas = new ArrayList<>();
        datas.add("测试1");
        datas.add("测试2");
        datas.add("测试3");
        ExamReportRecyclerAdapter adapter = new ExamReportRecyclerAdapter(getContext(),datas);
        targetExamReportList.setLayoutManager(new LinearLayoutManager(getContext()));
        targetExamReportList.setAdapter(adapter);

        reportCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childButtonContainer.setVisibility(View.VISIBLE);
            }
        });

        cancelReportCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childButtonContainer.setVisibility(View.INVISIBLE);
            }
        });

        startReportCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"开始对比报告",Toast.LENGTH_SHORT).show();
            }
        });

        return rootViews;
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
        void onFragmentInteraction(Uri uri);
    }
}
