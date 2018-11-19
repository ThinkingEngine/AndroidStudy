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
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamAppointment;
import com.chengsheng.cala.htcm.views.activitys.ComboDetailActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamAppointmentActivity;

import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class ExamAppointmentRecyclerAdapter extends RecyclerView.Adapter<ExamAppointmentRecyclerAdapter.ExamAppointmentViewHolder> {

    private Context context;
    private List<ExamAppointment> datas;

    public ExamAppointmentRecyclerAdapter(Context context,List<ExamAppointment> datas){
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
        final ExamAppointment dataItem = datas.get(i);

        viewHolder.examItemName.setText(dataItem.getName());
        if(dataItem.isIs_hot()){
            viewHolder.examHotDegreeMark.setVisibility(View.VISIBLE);
        }else{
            viewHolder.examHotDegreeMark.setVisibility(View.INVISIBLE);
        }
        viewHolder.examPriceNum.setText("¥"+dataItem.getPrice());
        viewHolder.examHadNum.setText(String.valueOf(dataItem.getActual_sales_num())+"人已检");
        final String comboId = String.valueOf(dataItem.getId());

        viewHolder.itemMark.setTags(dataItem.getPackage_tag());

        viewHolder.examItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataItem.getId() == 2){
                    Intent intent = new Intent(context,ComboDetailActivity.class);
                    intent.putExtra("COMBO_ID",comboId);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,"当前条目，后台数据不支持！",Toast.LENGTH_SHORT).show();
                }

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
        TagGroup itemMark;
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
