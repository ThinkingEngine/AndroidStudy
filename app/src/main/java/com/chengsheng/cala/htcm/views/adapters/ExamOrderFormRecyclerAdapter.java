package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ExamOrderFormRecyclerAdapter extends RecyclerView.Adapter<ExamOrderFormRecyclerAdapter.ExamOrderFormVH> {
    private Context context;
    private List<String> datas;

    public ExamOrderFormRecyclerAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamOrderFormVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamOrderFormVH orderFormVH = new ExamOrderFormVH(LayoutInflater.from(context).inflate(R.layout.exam_order_form_item_layout,null));

        return orderFormVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamOrderFormVH viewHolder, int i) {
        List<String> childDatas = new ArrayList<>();
        childDatas.add("测试2");
        childDatas.add("测试3");
        ExamOrderFormChildRecyclerAdapter adapter = new ExamOrderFormChildRecyclerAdapter(context,childDatas);
        viewHolder.childOrderItem.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.childOrderItem.setAdapter(adapter);

        viewHolder.intoExamOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OrderDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ExamOrderFormVH extends RecyclerView.ViewHolder{

        TextView examOrderName,examOrderDate,examOrderNum;
        ImageView paymentState;
        RecyclerView childOrderItem;
        TextView realPayText,realPayValue,obligationText,obligationValue;
        Button continuePay;
        RelativeLayout intoExamOrderDetail;

        public ExamOrderFormVH(@NonNull View itemView) {
            super(itemView);
            examOrderName = itemView.findViewById(R.id.exam_order_name);
            examOrderDate = itemView.findViewById(R.id.exam_order_date);
            examOrderNum = itemView.findViewById(R.id.exam_order_num);
            paymentState = itemView.findViewById(R.id.payment_state);
            childOrderItem = itemView.findViewById(R.id.child_order_item);
            realPayText = itemView.findViewById(R.id.real_pay_text);
            realPayValue = itemView.findViewById(R.id.real_pay_value);
            obligationText = itemView.findViewById(R.id.obligation_text);
            obligationValue = itemView.findViewById(R.id.obligation_value);
            continuePay = itemView.findViewById(R.id.continue_pay);
            intoExamOrderDetail = itemView.findViewById(R.id.into_exam_order_detail);
        }
    }
}
