package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamAppointment;
import com.chengsheng.cala.htcm.model.datamodel.PackageTag;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.views.activitys.ComboDetailActivity;
import com.chengsheng.cala.htcm.views.customviews.TextViewBorder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


public class ExamAppointmentRecyclerAdapter extends RecyclerView.Adapter<ExamAppointmentRecyclerAdapter.ExamAppointmentViewHolder> {

    private Context context;
    private List<ExamAppointment> datas;

    private ExamAppointment dataItem;

    public ExamAppointmentRecyclerAdapter(Context context, List<ExamAppointment> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamAppointmentViewHolder holder;
        if (datas.isEmpty()) {
            holder = new ExamAppointmentViewHolder(LayoutInflater.from(context).inflate(R.layout.no_contants_layout, null));
        } else {
            holder = new ExamAppointmentViewHolder(LayoutInflater.from(context).inflate(R.layout.exam_appointment_item_bg_layout, null));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAppointmentViewHolder viewHolder, int i) {
        if (!datas.isEmpty()) {
            dataItem = datas.get(i);
            viewHolder.examItemName.setText(dataItem.getName());
            if (dataItem.isIs_hot()) {
                viewHolder.examHotDegreeMark.setVisibility(View.VISIBLE);
            } else {
                viewHolder.examHotDegreeMark.setVisibility(View.INVISIBLE);
            }
            viewHolder.examPriceNum.setText(dataItem.getPrice());
            viewHolder.examHadNum.setText(String.valueOf(dataItem.getCurrent_sales_num()) + "人已检");
            final String comboId = String.valueOf(dataItem.getId());
//            viewHolder.itemMark.setAdapter(new TagBaseAdapter(context, dataItem.getPackage_tag()));
            viewHolder.itemMark.setAdapter(new TagAdapter(dataItem.getPackage_tag()));
            viewHolder.examItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ComboDetailActivity.class);
                    intent.putExtra("COMBO_ID", comboId);
                    context.startActivity(intent);
                }
            });
        }else{
            viewHolder.noContents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBackDataAuth.doUpdateStateInterface(true);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (datas.isEmpty()) {
            return 1;
        } else {
            return datas.size();
        }

    }

    public class ExamAppointmentViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout examItem;
        TextView examItemName;
        ImageView examHotDegreeMark;
        TextView examPriceNum;
        TagFlowLayout itemMark;
        TextView examHadNum;

        ImageView noContents;

        public ExamAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            examItem = itemView.findViewById(R.id.exam_item);
            examItemName = itemView.findViewById(R.id.exam_item_name);
            examHotDegreeMark = itemView.findViewById(R.id.exam_hot_degree_mark);
            examPriceNum = itemView.findViewById(R.id.exam_price_num);
            itemMark = itemView.findViewById(R.id.item_mark);
            examHadNum = itemView.findViewById(R.id.exam_had_num);

            noContents = itemView.findViewById(R.id.no_contents);
        }
    }

    class TagAdapter extends com.zhy.view.flowlayout.TagAdapter<PackageTag>{


        public TagAdapter(List<PackageTag> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, PackageTag packageTag) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.tag_text_item_layout,null);
            TextViewBorder textViewBorder = rootView.findViewById(R.id.tag_text);
            textViewBorder.setText(packageTag.getName());
            textViewBorder.setTextColor(Color.parseColor(packageTag.getColor()));
            textViewBorder.setBorderColor(Color.parseColor(packageTag.getColor()));
            return rootView;
        }

    }
}
