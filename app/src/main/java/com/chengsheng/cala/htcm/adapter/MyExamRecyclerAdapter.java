package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.ExamItems;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.module.activitys.BarCodeActivity;
import com.chengsheng.cala.htcm.module.activitys.BeforeExamActivity;
import com.chengsheng.cala.htcm.module.activitys.IntelligentCheckActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;

import java.util.List;

public class MyExamRecyclerAdapter extends RecyclerView.Adapter<MyExamRecyclerAdapter.MyExamRecyclerViewHolder> {

    private Context context;
    private List<ExamItems> datas;


    public MyExamRecyclerAdapter(Context context, List<ExamItems> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyExamRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyExamRecyclerViewHolder holder;
        if (datas.isEmpty()) {
            holder = new MyExamRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.no_contants_layout, null));
        } else {
            holder = new MyExamRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.my_exam_list_item_layout, null));
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyExamRecyclerViewHolder viewHolder, final int i) {

        if (!datas.isEmpty()) {


            final ExamItems data = datas.get(i);
            viewHolder.myExamItemName.setText(data.getName());
            viewHolder.text4.setText("3-5" + "\n" + "天");
            if (data.getExam_status().equals(GlobalConstant.CHECKING)) {
                viewHolder.examItemStats.setImageResource(R.mipmap.tijianzhongbiaoqian);
                viewHolder.examInfo.setText("进入导检");
            } else if (data.getExam_status().equals(GlobalConstant.CHECKED)) {
                viewHolder.examItemStats.setImageResource(R.mipmap.yitijian);
                viewHolder.examInfo.setText("查看报告");
                if (data.getReport().isIssued()) {
                    viewHolder.examInfo.setBackground(context.getResources().getDrawable(R.drawable.gray_gradient_button_bg));
                    viewHolder.examInfo.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    viewHolder.examWaitNum.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.examInfo.setBackground(context.getResources().getDrawable(R.drawable.code_button_bg));
                    viewHolder.examWaitNum.setVisibility(View.INVISIBLE);
                }
                viewHolder.examItemCode.setVisibility(View.INVISIBLE);
            } else if (data.getExam_status().equals(GlobalConstant.RESERVATION)) {
                viewHolder.examItemStats.setImageResource(R.mipmap.daitijian);
                viewHolder.examInfo.setText("查看检前须知");
            } else {
                viewHolder.examItemStats.setVisibility(View.INVISIBLE);
            }

            if (data.getExam_status().equals(GlobalConstant.CHECKING)) {
                viewHolder.examItemCode.setImageResource(R.mipmap.tianxingma);

            } else {
                viewHolder.examItemCode.setImageResource(R.mipmap.erweima);
            }

            viewHolder.examItemCode.setOnClickListener(v -> {
                if (data.getExam_status().equals(GlobalConstant.CHECKING)) {
                    Intent intent = new Intent(context, BarCodeActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, UserCardActivity.class);
                    FamiliesListItem familiesListItem = new FamiliesListItem();
                    familiesListItem.setFullname(data.getCustomer().getName());
                    familiesListItem.setHealth_card_no(data.getCustomer().getReservation_or_registration().getId());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FAMILIES_INFO", familiesListItem);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            if (data.getExam_status().equals(GlobalConstant.RESERVATION)) {
                viewHolder.appointmentDate.setText("预约日期：" + data.getCustomer().getReservation_or_registration().getDate());
            } else {
                viewHolder.appointmentDate.setText("体检日期：" + data.getCustomer().getReservation_or_registration().getDate());
            }

            viewHolder.examTargetName.setText(data.getCustomer().getName());
            viewHolder.appointmentNum.setText(data.getCustomer().getReservation_or_registration().getId());


            viewHolder.examInfo.setOnClickListener(v -> {
                if (data.getExam_status().equals(GlobalConstant.RESERVATION)) {
                    Intent intent = new Intent(context, BeforeExamActivity.class);
                    intent.putExtra("EXAM_ID", String.valueOf(data.getId()));
                    context.startActivity(intent);
                } else if (data.getExam_status().equals(GlobalConstant.CHECKING)) {
                    Intent intent = new Intent(context, IntelligentCheckActivity.class);
                    intent.putExtra("EXAM_ID", String.valueOf(data.getId()));
                    context.startActivity(intent);
                }
            });
        }else{
            viewHolder.noContents.setOnClickListener(v -> CallBackDataAuth.doUpdateStateInterface(true));
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


    public class MyExamRecyclerViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout myExamContainer;
        TextView examTargetName;
        TextView appointmentNum;
        ImageView examItemStats;
        TextView myExamItemName;
        TextView appointmentDate;
        ImageView examItemCode;
        TextView examInfo;
        TextView text4;
        LinearLayout examWaitNum;

        ImageView noContents;


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
            examWaitNum = itemView.findViewById(R.id.exam_wait_num);
            text4 = itemView.findViewById(R.id.text4);

            noContents = itemView.findViewById(R.id.no_contents);

        }
    }
}
