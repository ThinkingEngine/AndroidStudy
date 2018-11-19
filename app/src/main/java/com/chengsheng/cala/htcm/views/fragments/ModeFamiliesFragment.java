package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;


public class ModeFamiliesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView title;
    private ImageView back;
    private TextView childTitle;

    private boolean backMark = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModeFamiliesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ModeFamiliesFragment newInstance(String param1, String param2) {
        ModeFamiliesFragment fragment = new ModeFamiliesFragment();
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
        // Inflate the layout for this fragment

        View rootView;
        if(mParam1.equals("CELLPHONE")){
            rootView = inflater.inflate(R.layout.mode_cellphone_layout,null);
            title = rootView.findViewById(R.id.title_header_mode_families).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_families).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_families).findViewById(R.id.message_mark_text);

            childTitle.setVisibility(View.INVISIBLE);
            title.setText("修改手机号码");

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backMark = true;
                }
            });

        }else if(mParam1.equals("RELATION")){
            rootView = inflater.inflate(R.layout.mode_relation_layout,null);
            title = rootView.findViewById(R.id.title_header_mode_relation).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_relation).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_relation).findViewById(R.id.message_mark_text);

            childTitle.setText("完成");
            title.setText("修改家人关系");

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backMark = true;
                }
            });
        }else{
            rootView = inflater.inflate(R.layout.mode_name_layout,null);
            title = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.message_mark_text);

            childTitle.setText("完成");
            title.setText("修改姓名");

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backMark = true;
                }
            });
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Boolean isBack) {
        if (mListener != null) {
            mListener.onFragmentInteraction(isBack);
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
        void onFragmentInteraction(Boolean isBack);
    }
}
