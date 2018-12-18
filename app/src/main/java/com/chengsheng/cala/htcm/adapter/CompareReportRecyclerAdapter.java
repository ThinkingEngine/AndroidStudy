package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.ExamReportModel.CompareItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.ConditionInterface;

import java.util.List;


public class CompareReportRecyclerAdapter extends RecyclerView.Adapter<CompareReportRecyclerAdapter.CompareHeader> implements ConditionInterface {
    private Context context;
    private List<CompareItem> datas;



    public CompareReportRecyclerAdapter(Context context, List<CompareItem> datas) {
        this.context = context;
        this.datas = datas;

        CallBackDataAuth.setConditionInterface(this);
    }


    @NonNull
    @Override
    public CompareHeader onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CompareHeader vh = new CompareHeader(LayoutInflater.from(context).inflate(R.layout.compare_table_item_layout, null));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CompareHeader viewHolder, int i) {
        CompareItem data = datas.get(i);

        viewHolder.compareItemTitle.setText(data.getCheck_item_name());
        viewHolder.compareItemContent.setAdapter(new CompareContentBaseAdapter(context,data.getSingle_item()));


    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void selectCondition(List<CompareItem> condition) {
        this.datas = condition;
        notifyDataSetChanged();

    }


    public class CompareHeader extends RecyclerView.ViewHolder {

        TextView compareItemTitle;
        ListView compareItemContent;

        public CompareHeader(@NonNull View itemView) {
            super(itemView);
            compareItemTitle = itemView.findViewById(R.id.compare_item_title);
            compareItemContent = itemView.findViewById(R.id.compare_item_content);
        }
    }


}
