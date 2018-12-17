package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.childmodelb.ExamItem;

import java.util.List;

public class BeforeExamRecyclerViewAdapter extends RecyclerView.Adapter<BeforeExamRecyclerViewAdapter.BeforeExamVH> {
    private Context context;
    private List<ExamItem> datas;

    public BeforeExamRecyclerViewAdapter(Context context,List<ExamItem> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public BeforeExamVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BeforeExamVH vh = new BeforeExamVH(LayoutInflater.from(context).inflate(R.layout.before_exam_item_layout,null));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BeforeExamVH viewHolder, int i) {

        ExamItem data = datas.get(i);
        viewHolder.normalTitleA.setText(data.getName());
        viewHolder.bloodNormal.setText(data.getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class BeforeExamVH extends RecyclerView.ViewHolder{

        TextView normalTitleA,bloodNormal;
        public BeforeExamVH(@NonNull View itemView) {
            super(itemView);
            normalTitleA = itemView.findViewById(R.id.before_exam_item_title);
            bloodNormal = itemView.findViewById(R.id.blood_normal);
        }
    }
}