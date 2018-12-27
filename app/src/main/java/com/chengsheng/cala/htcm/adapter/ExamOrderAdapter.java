package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.activitys.ModePaymentActivity;
import com.chengsheng.cala.htcm.module.activitys.OrderDetailActivity;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrderItem;
import com.chengsheng.cala.htcm.protocol.childmodelb.PackageAndOptional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-27 13:49
 * Description:体检订单 条目适配器
 */
public class ExamOrderAdapter extends BaseQuickAdapter<ExamOrderItem> {
    private Context context;

    RecyclerView childOrderItem;


    public ExamOrderAdapter(int layoutResId, List<ExamOrderItem> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ExamOrderItem examOrderItem) {
        baseViewHolder.setText(R.id.obligation_value,"¥" + examOrderItem.getAmount().getDiscount_receivable());
        baseViewHolder.setText(R.id.exam_order_name,"体检人: " + examOrderItem.getCustomer().getName());
        baseViewHolder.setText(R.id.exam_order_date,"预约日期: " + examOrderItem.getCustomer().getReservation_or_registration().getDate());
        baseViewHolder.setText(R.id.exam_order_num,"预约号: " + examOrderItem.getCustomer().getReservation_or_registration().getId());

        baseViewHolder.setTextColor(R.id.obligation_value,R.color.colorThrText);
        baseViewHolder.setTextColor(R.id.obligation_text,R.color.colorThrText);

        if (examOrderItem.getOrder_status().equals(GlobalConstant.MODE_RECEIVABLE)) {
            baseViewHolder.setImageResource(R.id.payment_state,R.mipmap.dingdan_daifukuan);
            baseViewHolder.setText(R.id.continue_pay,"立即支付");
            baseViewHolder.setTextColor(R.id.obligation_value,R.color.colorPrimaryDark);
            baseViewHolder.setTextColor(R.id.obligation_text,R.color.colorPrimaryDark);
        } else if (examOrderItem.getOrder_status().equals(GlobalConstant.MODE_RECEIVED)) {
            baseViewHolder.setImageResource(R.id.payment_state,R.mipmap.dingdan_yifukuan);
            baseViewHolder.setVisible(R.id.continue_pay,false);
        } else if (examOrderItem.getOrder_status().equals(GlobalConstant.MODE_COMMENT)) {
            baseViewHolder.setImageResource(R.id.payment_state,R.mipmap.dingdan_daipingjia);
            baseViewHolder.setVisible(R.id.continue_pay,false);

        } else if (examOrderItem.getOrder_status().equals(GlobalConstant.MODE_CANCEL)) {
            baseViewHolder.setImageResource(R.id.payment_state,R.mipmap.yitijian);
            baseViewHolder.setVisible(R.id.continue_pay,false);

        }

        baseViewHolder.setOnClickListener(R.id.continue_pay, v -> {
//            app.setOrderID(Integer.valueOf(examOrderItem.getId()));
            Intent intent = new Intent(context, ModePaymentActivity.class);
            context.startActivity(intent);
        });

        baseViewHolder.setOnClickListener(R.id.into_exam_order_detail, v -> {
            if (!examOrderItem.getOrder_status().equals(GlobalConstant.MODE_CANCEL)) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID", String.valueOf(examOrderItem.getId()));
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "该订单已取消", Toast.LENGTH_SHORT).show();
            }
        });

        List<PackageAndOptional> childDatas = new ArrayList<>();
        for (int j = 0; j < examOrderItem.getPackage_and_optional().size(); j++) {
            childDatas.add(examOrderItem.getPackage_and_optional().get(j));
        }
        ExamOrderFormChildRecyclerAdapter adapter = new ExamOrderFormChildRecyclerAdapter(context, childDatas);

        childOrderItem = baseViewHolder.itemView.findViewById(R.id.child_order_item);
        childOrderItem.setLayoutManager(new LinearLayoutManager(context));
        childOrderItem.setAdapter(adapter);
    }
}
