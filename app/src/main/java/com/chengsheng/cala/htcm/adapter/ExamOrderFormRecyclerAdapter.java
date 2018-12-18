package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrderItem;
import com.chengsheng.cala.htcm.protocol.childmodelb.PackageAndOptional;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.module.activitys.ModePaymentActivity;
import com.chengsheng.cala.htcm.module.activitys.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ExamOrderFormRecyclerAdapter extends RecyclerView.Adapter<ExamOrderFormRecyclerAdapter.ExamOrderFormVH> {
    private Context context;
    private List<ExamOrderItem> datas;
    private HTCMApp app;

    public ExamOrderFormRecyclerAdapter(Context context, List<ExamOrderItem> datas) {
        this.context = context;
        this.datas = datas;
        app = HTCMApp.create(context);
    }

    @NonNull
    @Override
    public ExamOrderFormVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamOrderFormVH orderFormVH;
        Log.e("TAG","onCreateViewHolder:"+datas.size());
        if (datas.isEmpty()) {
            orderFormVH = new ExamOrderFormVH(LayoutInflater.from(context).inflate(R.layout.no_contants_layout, null));
        } else {
            orderFormVH = new ExamOrderFormVH(LayoutInflater.from(context).inflate(R.layout.exam_order_form_item_layout, null));
        }

        return orderFormVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamOrderFormVH viewHolder, int i) {

        if (!datas.isEmpty()) {
            final ExamOrderItem data = datas.get(i);
            viewHolder.obligationValue.setText("¥" + data.getAmount().getDiscount_receivable());
            viewHolder.examOrderName.setText("体检人: " + data.getCustomer().getName());
            viewHolder.examOrderDate.setText("预约日期: " + data.getCustomer().getReservation_or_registration().getDate());
            if (data.getOrder_status().equals(GlobalConstant.MODE_RECEIVABLE)) {
                viewHolder.paymentState.setImageResource(R.mipmap.dingdan_daifukuan);
                viewHolder.obligationValue.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.obligationText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.continuePay.setText("立即支付");
            } else if (data.getOrder_status().equals(GlobalConstant.MODE_RECEIVED)) {
                viewHolder.paymentState.setImageResource(R.mipmap.dingdan_yifukuan);
                viewHolder.continuePay.setVisibility(View.INVISIBLE);
                viewHolder.obligationValue.setTextColor(context.getResources().getColor(R.color.colorThrText));
                viewHolder.obligationText.setTextColor(context.getResources().getColor(R.color.colorThrText));
            } else if (data.getOrder_status().equals(GlobalConstant.MODE_COMMENT)) {
                viewHolder.paymentState.setImageResource(R.mipmap.dingdan_daipingjia);
                viewHolder.continuePay.setText("去评价");
                viewHolder.obligationValue.setTextColor(context.getResources().getColor(R.color.colorThrText));
                viewHolder.obligationText.setTextColor(context.getResources().getColor(R.color.colorThrText));
            } else if (data.getOrder_status().equals(GlobalConstant.MODE_CANCEL)) {
                viewHolder.paymentState.setImageResource(R.mipmap.yitijian);
                viewHolder.continuePay.setVisibility(View.INVISIBLE);
                viewHolder.obligationValue.setTextColor(context.getResources().getColor(R.color.colorThrText));
                viewHolder.obligationText.setTextColor(context.getResources().getColor(R.color.colorThrText));
            }

            viewHolder.examOrderNum.setText("预约号: " + data.getCustomer().getReservation_or_registration().getId());
            List<PackageAndOptional> childDatas = new ArrayList<>();
            for (int j = 0; j < data.getPackage_and_optional().size(); j++) {
                childDatas.add(data.getPackage_and_optional().get(j));
            }
            ExamOrderFormChildRecyclerAdapter adapter = new ExamOrderFormChildRecyclerAdapter(context, childDatas);


            viewHolder.childOrderItem.setLayoutManager(new LinearLayoutManager(context));
            viewHolder.childOrderItem.setAdapter(adapter);

            viewHolder.intoExamOrderDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!data.getOrder_status().equals(GlobalConstant.MODE_CANCEL)) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("ORDER_ID", String.valueOf(data.getId()));
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "该订单已取消", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            viewHolder.continuePay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app.setOrderID(Integer.valueOf(data.getId()));
                    Intent intent = new Intent(context, ModePaymentActivity.class);
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
        if(datas.isEmpty()){
            return 1;
        }else{
            return datas.size();
        }

    }


    public class ExamOrderFormVH extends RecyclerView.ViewHolder {

        TextView examOrderName, examOrderDate, examOrderNum;
        ImageView paymentState;
        RecyclerView childOrderItem;
        TextView obligationText, obligationValue;
        Button continuePay;
        RelativeLayout intoExamOrderDetail;

        ImageView noContents;

        public ExamOrderFormVH(@NonNull View itemView) {
            super(itemView);
            examOrderName = itemView.findViewById(R.id.exam_order_name);
            examOrderDate = itemView.findViewById(R.id.exam_order_date);
            examOrderNum = itemView.findViewById(R.id.exam_order_num);
            paymentState = itemView.findViewById(R.id.payment_state);
            childOrderItem = itemView.findViewById(R.id.child_order_item);
            obligationText = itemView.findViewById(R.id.obligation_text);
            obligationValue = itemView.findViewById(R.id.obligation_value);
            continuePay = itemView.findViewById(R.id.continue_pay);
            intoExamOrderDetail = itemView.findViewById(R.id.into_exam_order_detail);

            noContents = itemView.findViewById(R.id.no_contents);
        }
    }
}
