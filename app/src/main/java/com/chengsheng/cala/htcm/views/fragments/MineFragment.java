package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.businesslogic.DataFlow;
import com.chengsheng.cala.htcm.views.activitys.AccountSettingActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamOrderFormActivity;
import com.chengsheng.cala.htcm.views.adapters.MineItemBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private int[] iconsA = new int[]{R.mipmap.jiarengaunli,R.mipmap.shoucang,R.mipmap.tuikuanjilu,R.mipmap.wodeshebei};
    private int[] iconsB = new int[]{R.mipmap.changjianwenti,R.mipmap.shezhi,R.mipmap.lianxikefu};
    private String[] titleA = new String[]{"家人管理","收藏","退款记录","我的设备"};
    private String[] titleB = new String[]{"常见问题","设置","联系客服"};
    private String[] typeA = new String[]{"GENERAL","GENERAL","GENERAL","GENERAL"};
    private String[] typeB = new String[]{"GENERAL","GENERAL","TEL"};

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        ListView groupA = (ListView) rootView.findViewById(R.id.mine_group_a);
        ListView groupB = (ListView) rootView.findViewById(R.id.mine_group_b);
        TextView medicalExamOrderText = rootView.findViewById(R.id.medical_exam_order_text);//体检订单文本
        ImageView inputPersonalInfo = (ImageView) rootView.findViewById(R.id.input_personal_info);

        List<Map<String,String>> dataGroupA = DataFlow.minePageItemModelA(4,iconsA,titleA,typeA);
        List<Map<String,String>> dataGroupB = DataFlow.minePageItemModelA(3,iconsB,titleB,typeB);

        groupA.setAdapter(new MineItemBaseAdapter(getContext(),dataGroupA));
        groupB.setAdapter(new MineItemBaseAdapter(getContext(),dataGroupB));

        inputPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AccountSettingActivity.class);
                getContext().startActivity(intent);
            }
        });

        medicalExamOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ExamOrderFormActivity.class);
                getContext().startActivity(intent);
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

//    private void setListViewHeightBasedOnChildren(ListView listView) {
//        // 获取ListView对应的Adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//            // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i,null, listView);
//            // 计算子项View 的宽高
//            listItem.measure(0, 0);
//            // 统计所有子项的总高度
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() *
//                (listAdapter.getCount() - 1));
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
//        listView.invalidate();
//    }
}
