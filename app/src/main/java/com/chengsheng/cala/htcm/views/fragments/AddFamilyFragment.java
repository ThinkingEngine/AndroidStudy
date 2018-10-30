package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;


public class AddFamilyFragment extends Fragment {

    private ImageView selectHeaderIcon;
    private EditText inputFamiliesName;
    private Button maleMark,femaleMark;
    private EditText  inputFamiliesAge;
    private EditText inputFamiliesIdNum;
    private EditText inputFamiliesTel;
    private EditText inputFamiliesCode;
    private Button getCode;
    private TagContainerLayout familiesRelationSelecter;
    private Button commitFamiliesInfoButton;

    private String[] relations = new String[]{"本人","父亲","母亲","儿子","女儿","妻子","丈夫","其他"};

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnAddFamilyFragmentInteractionListener mListener;

    public AddFamilyFragment() {
        // Required empty public constructor
    }

    public static AddFamilyFragment newInstance(String param1, String param2) {
        AddFamilyFragment fragment = new AddFamilyFragment();
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
        View rootViews = inflater.inflate(R.layout.fragment_add_family, container, false);
        initViews(rootViews);

        maleMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleMark.setSelected(true);
                femaleMark.setSelected(false);
            }
        });
        femaleMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleMark.setSelected(false);
                femaleMark.setSelected(true);
            }
        });

        return rootViews;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle data) {
        if (mListener != null) {
            mListener.onAddFamilyFragmentInteraction(data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFamilyFragmentInteractionListener) {
            mListener = (OnAddFamilyFragmentInteractionListener) context;
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


    public interface OnAddFamilyFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddFamilyFragmentInteraction(Bundle bundle);
    }


    private void initViews(View rootViews){
        selectHeaderIcon = rootViews.findViewById(R.id.select_header_icon);
        inputFamiliesName = rootViews.findViewById(R.id.input_families_name);
        maleMark = rootViews.findViewById(R.id.male_mark);
        femaleMark = rootViews.findViewById(R.id.female_mark);
        inputFamiliesAge = rootViews.findViewById(R.id.input_families_age);
        inputFamiliesIdNum = rootViews.findViewById(R.id.input_families_id_num);
        inputFamiliesTel = rootViews.findViewById(R.id.input_families_tel);
        inputFamiliesCode = rootViews.findViewById(R.id.input_families_code);
        getCode = rootViews.findViewById(R.id.get_code);
        familiesRelationSelecter = rootViews.findViewById(R.id.families_relation_selecter);
        commitFamiliesInfoButton = rootViews.findViewById(R.id.commit_families_info_button);

        maleMark.setSelected(true);
        femaleMark.setSelected(false);
        maleMark.setText("男");
        femaleMark.setText("女");

        List<String> dataRelta = new ArrayList<>();
        for(int i = 0;i < relations.length;i++){
            dataRelta.add(relations[i]);
        }

        familiesRelationSelecter.setTag(dataRelta);
    }
}
