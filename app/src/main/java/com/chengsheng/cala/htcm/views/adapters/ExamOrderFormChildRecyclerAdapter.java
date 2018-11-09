package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;

public class ExamOrderFormChildRecyclerAdapter extends RecyclerView.Adapter<ExamOrderFormChildRecyclerAdapter.ExamOrderFormChildVH> {
    private Context context;
    private List<String> datas;

    public ExamOrderFormChildRecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamOrderFormChildVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamOrderFormChildVH vh = new ExamOrderFormChildVH(LayoutInflater.from(context).inflate(R.layout.exam_order_form_child_item_layout,null));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamOrderFormChildVH viewHolder, int i) {
        viewHolder.childOrderItemName.setText(datas.get(i));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ExamOrderFormChildVH extends RecyclerView.ViewHolder{

        TextView childOrderItemName;
        TextView childOrderItemValue;
        public ExamOrderFormChildVH(@NonNull View itemView) {
            super(itemView);
            childOrderItemName = itemView.findViewById(R.id.child_order_item_name);
            childOrderItemValue = itemView.findViewById(R.id.child_order_item_value);
        }
    }
}
