package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;


public class SMSCodeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSMSCodeFragmentInteractionListener mListener;

    public SMSCodeFragment() {
        // Required empty public constructor
    }


    public static SMSCodeFragment newInstance(String param1, String param2) {
        SMSCodeFragment fragment = new SMSCodeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_smscode, container, false);

        TextView nextButton = rootView.findViewById(R.id.next_button);
        ImageView backLoginPdButton = rootView.findViewById(R.id.top_panel_sms).findViewById(R.id.back_login);
        EditText getNumberSms = rootView.findViewById(R.id.get_number_sms);
        EditText getCodeFormSms = rootView.findViewById(R.id.get_code_form_sms);
        final Button getCodeSmsButton = rootView.findViewById(R.id.get_code_sms_button);

        getCodeSmsButton.setBackground(getResources().getDrawable(R.drawable.code_button_bg));
        getCodeSmsButton.setTextColor(getResources().getColor(R.color.colorPrimary));

        getNumberSms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    getCodeSmsButton.setBackground(getResources().getDrawable(R.drawable.code_button_gray_box));
                    getCodeSmsButton.setTextColor(getResources().getColor(R.color.colorThrText));
                }
                if(s.length() == 0){
                    getCodeSmsButton.setBackground(getResources().getDrawable(R.drawable.code_button_bg));
                    getCodeSmsButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("EVENT","click");
                bundle.putString("SOURCE","next");
                onButtonPressed(bundle);
            }
        });

        backLoginPdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("EVENT","click");
                bundle.putString("SOURCE","backParent");
                onButtonPressed(bundle);
            }
        });

        return rootView;
    }


    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onSMSCodeFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSMSCodeFragmentInteractionListener) {
            mListener = (OnSMSCodeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnSMSCodeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSMSCodeFragmentInteraction(Bundle bundle);
    }
}
