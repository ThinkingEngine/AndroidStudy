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


public class SetNewPWFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSetNewPWFFragmentInteractionListener mListener;

    public SetNewPWFragment() {
        // Required empty public constructor
    }


    public static SetNewPWFragment newInstance(String param1, String param2) {
        SetNewPWFragment fragment = new SetNewPWFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_set_new_pw,container,false);

        TextView title = rootView.findViewById(R.id.top_panel_sms).findViewById(R.id.menu_bar_title);
        ImageView backLoginNpdButton = rootView.findViewById(R.id.top_panel_sms).findViewById(R.id.back_login);
        TextView completeButton = rootView.findViewById(R.id.complete_button);

        title.setText("设置新密码");

        backLoginNpdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("EVENT","click");
                bundle.putString("SOURCE","back");
                onButtonPressed(bundle);
            }
        });
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("EVENT","click");
                bundle.putString("SOURCE","complete");
                onButtonPressed(bundle);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onSetNewPWFFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetNewPWFFragmentInteractionListener) {
            mListener = (OnSetNewPWFFragmentInteractionListener) context;
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

    public interface OnSetNewPWFFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSetNewPWFFragmentInteraction(Bundle bundle);
    }
}
