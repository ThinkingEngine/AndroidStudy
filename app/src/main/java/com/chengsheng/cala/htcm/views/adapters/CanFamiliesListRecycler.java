package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;

public class CanFamiliesListRecycler extends RecyclerView.Adapter<CanFamiliesListRecycler.CanFamiliesListViewHolder> {
    private Context context;
    private List<String> datas;

    public CanFamiliesListRecycler(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public CanFamiliesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CanFamiliesListViewHolder holder = new CanFamiliesListViewHolder(LayoutInflater.from(context).inflate(R.layout.can_add_families_tem_layout,null));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CanFamiliesListViewHolder viewHolder, int i) {
        viewHolder.examPersonName.setText(datas.get(i));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CanFamiliesListViewHolder extends RecyclerView.ViewHolder{

        ImageView addFamiliesButton;
        TextView examPersonName;
        ImageView authenticationMarkA;
        TextView authenticationTextA;
        TextView examPersonTel,examPersonId;

        public CanFamiliesListViewHolder(@NonNull View itemView) {
            super(itemView);
            addFamiliesButton = itemView.findViewById(R.id.add_families_button);
            examPersonName = itemView.findViewById(R.id.exam_person_name);
            authenticationMarkA = itemView.findViewById(R.id.authentication_mark_a);
            authenticationTextA = itemView.findViewById(R.id.authentication_text_a);
            examPersonTel = itemView.findViewById(R.id.exam_person_tel);
            examPersonId = itemView.findViewById(R.id.exam_person_id);
        }
    }
}
