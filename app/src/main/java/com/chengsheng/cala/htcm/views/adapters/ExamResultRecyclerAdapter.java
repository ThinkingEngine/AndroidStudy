package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.ExamResultUnscrambleActivity;

import java.util.List;

public class ExamResultRecyclerAdapter extends RecyclerView.Adapter<ExamResultRecyclerAdapter.ExamResultViewHolder> {

    private Context context;
    private List<String> datas;

    public ExamResultRecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamResultViewHolder holder = new ExamResultViewHolder(LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_header_layout,null));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamResultViewHolder viewHolder, int i) {
        viewHolder.examItemNameExpandable.setText(datas.get(i));
        viewHolder.arrowMark.setImageResource(R.mipmap.liebiaojinru);
        if(i == 0 || i == 3){
            viewHolder.examItemStatsExpandable.setText("一项异常");
            viewHolder.examItemStatsExpandable.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            viewHolder.examItemStatsExpandable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE","table_a");
                    Intent intent = new Intent(context,ExamResultUnscrambleActivity.class);
                    intent.putExtra("mesg",bundle);
                    context.startActivity(intent);
                }
            });

        }else{
            viewHolder.examItemStatsExpandable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE","table_b");
                    Intent intent = new Intent(context,ExamResultUnscrambleActivity.class);
                    intent.putExtra("mesg",bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ExamResultViewHolder extends RecyclerView.ViewHolder{

        TextView examItemNameExpandable;
        TextView examItemStatsExpandable;
        ImageView arrowMark;

        public ExamResultViewHolder(@NonNull View itemView) {
            super(itemView);

            examItemNameExpandable = itemView.findViewById(R.id.exam_item_name_expandable);
            examItemStatsExpandable = itemView.findViewById(R.id.exam_item_stats_expandable);
            arrowMark = itemView.findViewById(R.id.arrow_mark);
        }
    }
}
