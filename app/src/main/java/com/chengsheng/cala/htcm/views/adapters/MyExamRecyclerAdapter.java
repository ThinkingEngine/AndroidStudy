package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.BeforeExamActivity;

import java.util.List;

public class MyExamRecyclerAdapter extends RecyclerView.Adapter<MyExamRecyclerAdapter.MyExamRecyclerViewHolder> {

    private Context context;
    private List<String> datas;

    public MyExamRecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyExamRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyExamRecyclerViewHolder holder = new MyExamRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.my_exam_list_item_layout,null));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyExamRecyclerViewHolder viewHolder, final int i) {
        viewHolder.myExamItemName.setText(datas.get(i));
        viewHolder.examInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BeforeExamActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyExamRecyclerViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout myExamContainer;
        TextView examTargetName;
        TextView appointmentNum;
        ImageView examItemStats;
        TextView myExamItemName;
        TextView appointmentDate;
        ImageView examItemCode;
        TextView examInfo;

        public MyExamRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            myExamContainer = itemView.findViewById(R.id.my_exam_container);
            examTargetName = itemView.findViewById(R.id.exam_target_name);
            appointmentNum = itemView.findViewById(R.id.appointment_num);
            examItemStats = itemView.findViewById(R.id.exam_item_stats);
            myExamItemName = itemView.findViewById(R.id.my_exam_item_name);
            appointmentDate = itemView.findViewById(R.id.appointment_date);
            examItemCode = itemView.findViewById(R.id.exam_item_code);
            examInfo = itemView.findViewById(R.id.exam_info);
        }
    }
}
