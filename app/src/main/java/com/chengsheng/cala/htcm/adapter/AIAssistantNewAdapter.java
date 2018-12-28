package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    public AIAssistantNewAdapter(int layoutResId, List<AssistantItem> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, AssistantItem assistantItem) {

        String stats = assistantItem.getOrder().getExam_status();//获取智能助理卡片的状态

        baseViewHolder.setText(R.id.item_big_notes, "您预约了" + assistantItem.getCustomer()
                .getReservation_or_registration().getDate() + "的体检");

        baseViewHolder.setText(R.id.user_name_ai_assistant, assistantItem.getCustomer().getName());
        baseViewHolder.setText(R.id.ai_assistant_item_date, assistantItem.getCustomer()
                .getReservation_or_registration().getDate());
        SimpleDraweeView userHeader = baseViewHolder.itemView
                .findViewById(R.id.user_header_icon_ai_assistant);
        userHeader.setImageURI(assistantItem.getCustomer().getAvatar());

        Log.e("TAG","STATUS:"+stats);
        //预约卡片
        if (stats.equals(GlobalConstant.RESERVATION)) {
            baseViewHolder.setImageResource(R.id.user_bitmap_mark, R.mipmap.erweima);//设置右边小图为二维码
            baseViewHolder.setText(R.id.exam_num, "预约号：" + assistantItem.getCustomer()
                    .getReservation_or_registration().getId());//为预约号

            //获取卡片信息中是否包含未支付的金额数
            Float money = Float.valueOf(assistantItem.getOrder().getDiscount_receivable());
            //如果未支付金额大于0
            if (money > 0) {
                baseViewHolder.setText(R.id.unscramble_mark, "立即支付");
                baseViewHolder.setVisible(R.id.unscramble_mark, true);
                baseViewHolder.setText(R.id.item_sec_notes, "待支付金额：" + money + "元");

                //设置 右边按钮的点击事件为 跳转到支付页面，并传递支付需要的数据
                baseViewHolder.setOnClickListener(R.id.unscramble_mark, v -> {
                    Intent intent = new Intent(context, ModePaymentActivity.class);
                    intent.putExtra("ORDER_ID", String.valueOf(assistantItem.getOrder().getId()));
                    intent.putExtra("ORDER_VAL", assistantItem.getOrder().getDiscount_receivable());
                    context.startActivity(intent);
                });

            } else {//如果未支付金额小于0，表示当前已支付。

                //是否可以自主登记
                if (assistantItem.getOrder().isCan_autonomous()) {
                    baseViewHolder.setVisible(R.id.unscramble_mark, true);
                    baseViewHolder.setText(R.id.unscramble_mark, "自助登记");
                    baseViewHolder.setText(R.id.item_sec_notes, "您可前往中心登记台或自主完成登记");
                } else {
                    baseViewHolder.setVisible(R.id.unscramble_mark, false);
                    baseViewHolder.setText(R.id.item_sec_notes, "项目检查完成后，请前往中心登记台确认");
                }

                baseViewHolder.setVisible(R.id.unscramble_mark, false);
                baseViewHolder.setText(R.id.item_sec_notes, "请前往中心登记台完成登记");
            }

            //
        } else if (stats.equals(GlobalConstant.CHECKING)) {
            baseViewHolder.setImageResource(R.id.user_bitmap_mark, R.mipmap.tianxingma);//卡片状态为待体检,设置小图为条形码
            baseViewHolder.setText(R.id.exam_num, "体检号：" +
                    assistantItem.getCustomer().getReservation_or_registration().getId());
            baseViewHolder.setVisible(R.id.unscramble_mark, false);
            baseViewHolder.setText(R.id.item_sec_notes, "项目检查完成后，请前往中心登记台确认");

        } else if (stats.equals(GlobalConstant.CHECKED)) {

            baseViewHolder.setImageResource(R.id.user_bitmap_mark, R.mipmap.tianxingma);
            baseViewHolder.setText(R.id.exam_num, "体检号：" +
                    assistantItem.getCustomer().getReservation_or_registration().getId());
            baseViewHolder.setText(R.id.item_sec_notes, "体检日期：" +
                    assistantItem.getCustomer().getReservation_or_registration().getDate());

            if (assistantItem.getReport().isIssued()) {

                baseViewHolder.setVisible(R.id.unscramble_mark, true);
                baseViewHolder.setText(R.id.unscramble_mark, "查看报告");
                baseViewHolder.setText(R.id.item_sec_notes, "体检报告已生成");

                baseViewHolder.setOnClickListener(R.id.unscramble_mark, v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalConstant.EXAM_REPORT_ID, String.valueOf(assistantItem.getOrder().getId()));
                    ActivityUtil.Companion.startActivity(context, new ExamReportDetailActivity(), bundle);
                });

            } else {
                baseViewHolder.setVisible(R.id.unscramble_mark, false);
                baseViewHolder.setText(R.id.item_sec_notes, "体检报告预计2-5个工作日内生成");
            }
        } else {

            baseViewHolder.setVisible(R.id.unscramble_mark, false);
            baseViewHolder.setImageResource(R.id.user_bitmap_mark, R.mipmap.tianxingma);
            baseViewHolder.setText(R.id.exam_num, "体检号：" +
                    assistantItem.getCustomer().getReservation_or_registration().getId());
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
