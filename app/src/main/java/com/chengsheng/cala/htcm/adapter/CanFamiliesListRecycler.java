package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;

import java.util.ArrayList;
import java.util.List;

public class CanFamiliesListRecycler extends RecyclerView.Adapter<CanFamiliesListRecycler.CanFamiliesListViewHolder> {
    private Context context;
    private List<FamiliesListItem> datas;
    private List<FamiliesListItem> result = new ArrayList<>();

    private int currentItem = -1;

    public CanFamiliesListRecycler(Context context, List<FamiliesListItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public CanFamiliesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CanFamiliesListViewHolder holder = new CanFamiliesListViewHolder(LayoutInflater.from(context).inflate(R.layout.can_add_families_tem_layout, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CanFamiliesListViewHolder viewHolder, int i) {
        final FamiliesListItem data = datas.get(i);

        if (data.isIs_auth()) {
            viewHolder.authenticationMarkA.setSelected(true);
            viewHolder.authenticationTextA.setTextColor(context.getResources().getColor(R.color.colorOrange));
        } else {
            viewHolder.authenticationMarkA.setSelected(false);
            viewHolder.authenticationTextA.setTextColor(context.getResources().getColor(R.color.colorThrText));
        }


        viewHolder.examPersonName.setText(data.getFullname());
        viewHolder.examPersonTel.setText("电话号码 " + data.getMobile());
        viewHolder.examPersonId.setText("身份证号 " + data.getId_card_no());
        if (data.getOwner_relationship().equals("")) {
            viewHolder.examPersonMark.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.examPersonMark.setVisibility(View.VISIBLE);
            viewHolder.examPersonMark.setText(data.getOwner_relationship());
        }

        viewHolder.canSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.addFamiliesButton.isSelected()) {
                    viewHolder.addFamiliesButton.setSelected(false);
                    result.remove(data);
                } else {
                    viewHolder.addFamiliesButton.setSelected(true);
                    result.add(data);
                }

            }
        });

        CallBackDataAuth.doExamPersonCallBack(result);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CanFamiliesListViewHolder extends RecyclerView.ViewHolder {

        ImageView addFamiliesButton;
        TextView examPersonName;
        ImageView authenticationMarkA;
        TextView authenticationTextA;
        TextView examPersonTel, examPersonId;
        TextView examPersonMark;
        RelativeLayout canSelectItem;

        public CanFamiliesListViewHolder(@NonNull View itemView) {
            super(itemView);
            addFamiliesButton = itemView.findViewById(R.id.add_families_button);
            examPersonName = itemView.findViewById(R.id.exam_person_name);
            authenticationMarkA = itemView.findViewById(R.id.authentication_mark_a);
            authenticationTextA = itemView.findViewById(R.id.authentication_text_a);
            examPersonTel = itemView.findViewById(R.id.exam_person_tel);
            examPersonId = itemView.findViewById(R.id.exam_person_id);
            examPersonMark = itemView.findViewById(R.id.exam_person_mark);
            canSelectItem = itemView.findViewById(R.id.can_select_item);
        }
    }
}
