package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamReportItem;
import com.chengsheng.cala.htcm.views.activitys.ExamReportDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ExamReportRecyclerAdapter extends RecyclerView.Adapter<ExamReportRecyclerAdapter.ExamReportViewHolder> {

    private Context context;
    private List<ExamReportItem> datas;


    private List<String> exams = new ArrayList<>();
    private HTCMApp app;

    public ExamReportRecyclerAdapter(Context context, List<ExamReportItem> datas) {
        this.context = context;
        this.datas = datas;

        app = HTCMApp.create(context);
    }


    @NonNull
    @Override
    public ExamReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamReportViewHolder holder;
        if (datas.isEmpty() || datas.size() == 0) {
            holder = new ExamReportViewHolder(LayoutInflater.from(context).inflate(R.layout.no_content_layout, null));
        } else {
            holder = new ExamReportViewHolder(LayoutInflater.from(context).inflate(R.layout.exam_report_item_layout, null));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final ExamReportViewHolder viewHolder, final int i) {

        if (!datas.isEmpty()) {
            final ExamReportItem data = datas.get(i);
            viewHolder.examReportTitle.setText(data.getName());
            viewHolder.examReportDate.setText(data.getIssued_date());
            viewHolder.examReportNum.setText(data.getId());

            viewHolder.reportRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.reportRadio.isSelected() || app.getExams().size() < 2) {
                        if (viewHolder.reportRadio.isSelected()) {
                            viewHolder.reportRadio.setSelected(false);
                            app.delExamsID(data);
                        } else {
                            viewHolder.reportRadio.setSelected(true);
                            exams.add(String.valueOf(data.getOrderId()));
                            app.addExamsID(data);
                        }

                    }
                }
            });


            if (data.isSelect()) {
                viewHolder.reportRadio.setVisibility(View.VISIBLE);
                viewHolder.reportDetail.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.reportRadio.setVisibility(View.INVISIBLE);
                viewHolder.reportRadio.setSelected(false);
                viewHolder.reportDetail.setVisibility(View.VISIBLE);
            }
            viewHolder.reportDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "体检报告细节", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ExamReportDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalConstant.EXAM_REPORT_ID, String.valueOf(data.getOrderId()));
                    bundle.putString(GlobalConstant.EXAM_REPORT_NAME, data.getName());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
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


    public class ExamReportViewHolder extends RecyclerView.ViewHolder {
        TextView examReportDate;
        TextView examReportTitle;
        TextView examReportNum;
        ImageView reportRadio;
        ImageView reportDetail;

        public ExamReportViewHolder(@NonNull View itemView) {
            super(itemView);
            examReportDate = itemView.findViewById(R.id.exam_report_date);
            examReportTitle = itemView.findViewById(R.id.exam_report_title);
            examReportNum = itemView.findViewById(R.id.exam_report_num);
            reportRadio = itemView.findViewById(R.id.report_radio);
            reportDetail = itemView.findViewById(R.id.report_detail);
        }
    }
}
