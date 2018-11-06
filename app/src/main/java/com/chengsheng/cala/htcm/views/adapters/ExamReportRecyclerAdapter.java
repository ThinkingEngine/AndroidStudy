package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.ExamReportDetailActivity;

import java.util.List;

public class ExamReportRecyclerAdapter extends RecyclerView.Adapter<ExamReportRecyclerAdapter.ExamReportViewHolder> {

    private Context context;
    private List<String> datas;

    public ExamReportRecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }


    @NonNull
    @Override
    public ExamReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamReportViewHolder holder = new ExamReportViewHolder(LayoutInflater.from(context).inflate(R.layout.exam_report_item_layout,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(ExamReportViewHolder viewHolder, final int i) {
        viewHolder.examReportTitle.setText(datas.get(i));
        viewHolder.reportRadio.setVisibility(View.INVISIBLE);
        viewHolder.reportDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"体检报告细节",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ExamReportDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ExamReportViewHolder extends RecyclerView.ViewHolder{
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
