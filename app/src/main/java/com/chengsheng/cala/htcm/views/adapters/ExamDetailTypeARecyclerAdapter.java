package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.childmodela.singleItemResult;
import com.chengsheng.cala.htcm.views.customviews.MyRecyclerView;

import java.util.List;

public class ExamDetailTypeARecyclerAdapter extends RecyclerView.Adapter<ExamDetailTypeARecyclerAdapter.ExamDetailViewholder> {
    private Context context;
    private List<singleItemResult> datas;
    private int type;

    public ExamDetailTypeARecyclerAdapter(Context context, List<singleItemResult> datas, int type) {
        this.context = context;
        this.datas = datas;
        this.type = type;

    }

    @NonNull
    @Override
    public ExamDetailViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamDetailViewholder viewholder;
        if (type == 1) {
            viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_a_layout, null));
        } else if (type == 2) {
            if (i == 1) {
                viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_a_layout, null));
            } else if (i == 2) {
                viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_b_header_layout, null));
            } else {
                viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_b_header_layout, null));
            }

        } else if (type == 3) {
            viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.image_recycler_layout, null));
        }else{
            viewholder = new ExamDetailViewholder(LayoutInflater.from(context).inflate(R.layout.single_text_layout, null));
        }
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamDetailViewholder viewHolder, int i) {
        singleItemResult data = datas.get(i);
        if (type == 1) {
            viewHolder.examItemNameDetail.setText(data.getName());
            viewHolder.examItemAdvice.setText(data.getExamine_saws());

            if (data.isIs_exception()) {
                viewHolder.examItemAdvice.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
        } else if (type == 2) {
            if (data.getType() == 1) {
                viewHolder.examItemNameDetail.setText(data.getName());
                viewHolder.examItemAdvice.setText(data.getExamine_saws());
                if (data.isIs_exception()) {
                    viewHolder.examItemAdvice.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
            } else {
                viewHolder.itemUnit.setText(data.getName());
                viewHolder.floatValue.setText(data.getExamine_saws());
                viewHolder.lowLimit.setText(String.valueOf(data.getLow_limit()));
                viewHolder.upperLimit.setText(String.valueOf(data.getUpper_limit()));
                if (data.isIs_exception()) {
                    viewHolder.warningMark.setVisibility(View.VISIBLE);
                }
            }
        }else if(type == 3){
            ExamResultImageRecyclerAdapter adapter = new ExamResultImageRecyclerAdapter(context,data.getImages());
            viewHolder.imageTypeExamResult.setLayoutManager(new GridLayoutManager(context,2));
            Log.e("TAG",data.getImages()[0]);
            viewHolder.imageTypeExamResult.setAdapter(adapter);

        }

    }

    @Override
    public int getItemViewType(int position) {

        if (datas.get(position).getType() == 1) {
            return 1;
        } else if (datas.get(position).getType() == 2) {
            return 2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            return datas.size();
        } else {
            return datas.size();
        }

    }

    public class ExamDetailViewholder extends RecyclerView.ViewHolder {
        RelativeLayout detailsItemBg;
        TextView examItemNameDetail;
        TextView examItemAdvice;

        ImageView warningMark;
        TextView itemUnit;
        TextView floatValue;
        TextView lowLimit;
        TextView upperLimit;

        MyRecyclerView imageTypeExamResult;

        public ExamDetailViewholder(@NonNull View itemView) {
            super(itemView);

            examItemNameDetail = itemView.findViewById(R.id.exam_item_name_detail);
            examItemAdvice = itemView.findViewById(R.id.exam_item_advice);
            detailsItemBg = itemView.findViewById(R.id.details_item_bg);

            warningMark = itemView.findViewById(R.id.warning_mark);
            itemUnit = itemView.findViewById(R.id.item_unit);
            floatValue = itemView.findViewById(R.id.float_value);
            lowLimit = itemView.findViewById(R.id.low_limit);
            upperLimit = itemView.findViewById(R.id.upper_limit);

            imageTypeExamResult = itemView.findViewById(R.id.image_type_exam_result);
        }
    }
}
