package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.activitys.BarCodeActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamDetailsActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamReportDetailActivity;
import com.chengsheng.cala.htcm.module.activitys.ModePaymentActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-20 18:00
 * Description:重写智能助理适配器，添加分页功能
 */
public class AIAssistantNewAdapter extends BaseQuickAdapter<AssistantItem> {
    private Context context;

    public AIAssistantNewAdapter(int layoutResId, List<AssistantItem> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, AssistantItem assistantItem) {

        String stats = assistantItem.getOrder().getExam_status();//获取智能助理卡片的状态

       baseViewHolder.setText(R.id.item_big_notes,"您预约了" + assistantItem.getCustomer()
               .getReservation_or_registration().getDate() + "的体检");
       baseViewHolder.setText(R.id.user_name_ai_assistant,assistantItem.getCustomer().getName());
       baseViewHolder.setText(R.id.ai_assistant_item_date,assistantItem.getCustomer()
               .getReservation_or_registration().getDate());
       SimpleDraweeView userHeader = baseViewHolder.itemView
               .findViewById(R.id.user_header_icon_ai_assistant);
       userHeader.setImageURI(assistantItem.getCustomer().getAvatar());

        //预约
        if (stats.equals(GlobalConstant.RESERVATION)) {
            baseViewHolder.setImageResource(R.id.user_bitmap_mark,R.mipmap.erweima);
            baseViewHolder.setText(R.id.exam_num,"预约号：" + assistantItem.getOrder().getId());

            Float money = Float.valueOf(assistantItem.getOrder().getDiscount_receivable());
            if (money > 0) {
                baseViewHolder.setText(R.id.unscramble_mark,"立即支付");
                baseViewHolder.setVisible(R.id.unscramble_mark,true);
                baseViewHolder.setText(R.id.item_sec_notes,"待支付金额：" + money + "元");

                baseViewHolder.setOnClickListener(R.id.unscramble_mark, v -> {
                    Intent intent = new Intent(context, ModePaymentActivity.class);
                    intent.putExtra("ORDER_ID", String.valueOf(assistantItem.getOrder().getId()));
                    intent.putExtra("ORDER_VAL", assistantItem.getOrder().getDiscount_receivable());
                    context.startActivity(intent);
                });

            } else {
                baseViewHolder.setVisible(R.id.unscramble_mark,false);
                baseViewHolder.setText(R.id.item_sec_notes,"请前往中心登记台完成登记");
            }

        } else if (stats.equals(GlobalConstant.CHECKING)) {
            baseViewHolder.setImageResource(R.id.user_bitmap_mark,R.mipmap.tianxingma);
            baseViewHolder.setText(R.id.exam_num,"体检号：" + assistantItem.getOrder().getId());

            if (assistantItem.getOrder().isCan_autonomous()) {
                baseViewHolder.setVisible(R.id.unscramble_mark,true);
                baseViewHolder.setText(R.id.unscramble_mark,"自主登记");
                baseViewHolder.setText(R.id.item_sec_notes,"您可前往中心登记台或自主完成登记");
            } else {
                baseViewHolder.setVisible(R.id.unscramble_mark,false);
                baseViewHolder.setText(R.id.item_sec_notes,"项目检查完成后，请前往中心登记台确认");
            }

        } else if (stats.equals(GlobalConstant.CHECKED)) {
            baseViewHolder.setImageResource(R.id.user_bitmap_mark,R.mipmap.tianxingma);
            baseViewHolder.setText(R.id.exam_num,"体检号：" + assistantItem.getOrder().getId());
            baseViewHolder.setText(R.id.item_sec_notes,"体检日期：" +
                    assistantItem.getCustomer().getReservation_or_registration().getDate());

            if (assistantItem.getReport().isIssued()) {
                baseViewHolder.setVisible(R.id.unscramble_mark,true);
                baseViewHolder.setText(R.id.unscramble_mark,"查看报告");
                baseViewHolder.setOnClickListener(R.id.unscramble_mark, v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalConstant.EXAM_REPORT_ID, String.valueOf(assistantItem.getOrder().getId()));
                    ActivityUtil.Companion.startActivity(context,new ExamReportDetailActivity(),bundle);
                });

            } else {
                baseViewHolder.setVisible(R.id.unscramble_mark,false);
            }
        } else {
            baseViewHolder.setImageResource(R.id.user_bitmap_mark,R.mipmap.tianxingma);
            baseViewHolder.setText(R.id.exam_num,"体检号：" + assistantItem.getOrder().getId());
        }

        baseViewHolder.setOnClickListener(R.id.user_bitmap_mark, v -> {
            Bundle bundle = new Bundle();
            if (assistantItem.getCustomer().getReservation_or_registration().getStatus().equals(GlobalConstant.RESERVATION)) {
                Intent intent = new Intent(context, UserCardActivity.class);
                FamiliesListItem familiesListItem = new FamiliesListItem();
                familiesListItem.setFullname(assistantItem.getCustomer().getName());
                familiesListItem.setAvatar_path(assistantItem.getCustomer().getAvatar());
                familiesListItem.setHealth_card_no(assistantItem.getCustomer().getReservation_or_registration().getId());
                bundle.putSerializable("FAMILIES_INFO", familiesListItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, BarCodeActivity.class);
                bundle.putString("FAMILIES_INFO", assistantItem.getCustomer().getReservation_or_registration().getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //智能助手 卡片点击事件，跳转到 体检详情 （ExamDetailsActivity）需要传递一个 FamiliesListItem对象
        baseViewHolder.setOnClickListener(R.id.ai_assistant_item, v -> {
            Intent intent = new Intent(context, ExamDetailsActivity.class);
            FamiliesListItem familiesListItem = new FamiliesListItem();
            familiesListItem.setFullname(assistantItem.getCustomer().getName());
            familiesListItem.setAvatar_path(assistantItem.getCustomer().getAvatar());
            familiesListItem.setHealth_card_no(assistantItem.getCustomer().getReservation_or_registration().getId());
            intent.putExtra("ORDER_ID", String.valueOf(assistantItem.getOrder().getId()));
            context.startActivity(intent);
        });

    }
}
