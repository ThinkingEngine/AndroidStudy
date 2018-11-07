package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;

public class ExamDetailTypeARecyclerAdapter extends RecyclerView.Adapter<ExamDetailTypeARecyclerAdapter.ExamDetailViewholder> {
    private Context context;
    private List<String> datas;

    public ExamDetailTypeARecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamDetailViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamDetailViewholder viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_a_layout,null));
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamDetailViewholder viewHolder, int i) {
        viewHolder.examItemNameDetail.setText(datas.get(i));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ExamDetailViewholder extends RecyclerView.ViewHolder{
        TextView examItemNameDetail;
        TextView examItemAdvice;

        public ExamDetailViewholder(@NonNull View itemView) {
            super(itemView);

            examItemNameDetail = itemView.findViewById(R.id.exam_item_name_detail);
            examItemAdvice = itemView.findViewById(R.id.exam_item_advice);
        }
    }
}
