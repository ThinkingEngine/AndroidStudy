package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.ComboDetailActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamAppointmentActivity;

import java.util.List;

public class ExamAppointmentRecyclerAdapter extends RecyclerView.Adapter<ExamAppointmentRecyclerAdapter.ExamAppointmentViewHolder> {

    private Context context;
    private List<String> datas;

    public ExamAppointmentRecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamAppointmentViewHolder holder = new ExamAppointmentViewHolder(LayoutInflater.from(context).inflate(R.layout.exam_appointment_item_bg_layout,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAppointmentViewHolder viewHolder, final int i) {
        viewHolder.examItemName.setText(datas.get(i));
        viewHolder.itemMark.setVisibility(View.INVISIBLE);
        viewHolder.examItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ComboDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ExamAppointmentViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout examItem;
        TextView examItemName;
        ImageView examHotDegreeMark;
        TextView examPriceNum;
        GridView itemMark;
        TextView examHadNum;

        public ExamAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            examItem = itemView.findViewById(R.id.exam_item);
            examItemName = itemView.findViewById(R.id.exam_item_name);
            examHotDegreeMark = itemView.findViewById(R.id.exam_hot_degree_mark);
            examPriceNum = itemView.findViewById(R.id.exam_price_num);
            itemMark = itemView.findViewById(R.id.item_mark);
            examHadNum = itemView.findViewById(R.id.exam_had_num);
        }
    }
}
