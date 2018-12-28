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
import com.chengsheng.cala.htcm.module.activitys.BeforeExamActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamReportCompareActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamReportDetailActivity;
import com.chengsheng.cala.htcm.module.activitys.IntelligentCheckActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.protocol.ExamItems;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.ToastUtil;

import java.util.List;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 9:32
 * Description:我的体检 的页面适配器
 */
public class MyExamAdapter extends BaseQuickAdapter<ExamItems> {
    private Context context;


    public MyExamAdapter(int layoutResId, List<ExamItems> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ExamItems examItems) {
        baseViewHolder.setText(R.id.my_exam_item_name, examItems.getName());
        baseViewHolder.setText(R.id.tv_wait_person, "3-5" + "\n" + "天");
        baseViewHolder.setText(R.id.exam_target_name, examItems.getCustomer().getName());
        baseViewHolder.setText(R.id.appointment_num, examItems.getCustomer()
                .getReservation_or_registration().getId());

        if (examItems.getExam_status().equals(GlobalConstant.RESERVATION)) {
            baseViewHolder.setText(R.id.appointment_date, "预约日期：" + examItems
                    .getCustomer().getReservation_or_registration().getDate());
        } else {
            baseViewHolder.setText(R.id.appointment_date, "体检日期：" + examItems
                    .getCustomer().getReservation_or_registration().getDate());
        }

        if (examItems.getExam_status().equals(GlobalConstant.CHECKING)) {
            baseViewHolder.setImageResource(R.id.exam_item_code, R.mipmap.tianxingma);
        } else {
            baseViewHolder.setImageResource(R.id.exam_item_code, R.mipmap.erweima);
        }

        baseViewHolder.setOnClickListener(R.id.exam_item_code, v -> {
            if(examItems.getExam_status().equals(GlobalConstant.CHECKING)){

                Bundle bundle = new Bundle();
                bundle.putString("FAMILIES_INFO", examItems.getCustomer().getReservation_or_registration().getId());
                ActivityUtil.Companion.startActivity(context,new BarCodeActivity(),bundle);
            }else{
                Bundle bundle = new Bundle();
                FamiliesListItem familiesListItem = new FamiliesListItem();
                familiesListItem.setFullname(examItems.getCustomer().getName());
                familiesListItem.setAvatar_path(examItems.getCustomer().getAvatar());
                familiesListItem.setHealth_card_no(examItems.getCustomer().getReservation_or_registration().getId());
                bundle.putSerializable("FAMILIES_INFO", familiesListItem);
                ActivityUtil.Companion.startActivity(context, new UserCardActivity(), bundle);
            }
        });

        if (examItems.getExam_status().equals(GlobalConstant.CHECKING)) {
            baseViewHolder.setImageResource(R.id.exam_item_stats, R.mipmap.tijianzhongbiaoqian);
            baseViewHolder.setText(R.id.exam_info, "进入导检");
        } else if (examItems.getExam_status().equals(GlobalConstant.CHECKED)) {
            baseViewHolder.setImageResource(R.id.exam_item_stats, R.mipmap.yitijian);
            baseViewHolder.setText(R.id.exam_info, "查看报告");
            if (examItems.getReport().isIssued()) {
                baseViewHolder.setBackgroundRes(R.id.exam_info, R.drawable.code_button_bg);
                baseViewHolder.setTextColor(R.id.exam_info, R.color.colorWhite);
                baseViewHolder.setVisible(R.id.tv_wait_person, true);

            } else {
                baseViewHolder.setBackgroundRes(R.id.exam_info, R.drawable.gray_gradient_button_bg);
                baseViewHolder.setVisible(R.id.tv_wait_person, false);
            }
            baseViewHolder.setVisible(R.id.exam_item_code, false);
            baseViewHolder.setVisible(R.id.tv_wait_person, false);
        } else if (examItems.getExam_status().equals(GlobalConstant.RESERVATION)) {
            baseViewHolder.setImageResource(R.id.exam_item_stats, R.mipmap.daitijian);
            baseViewHolder.setText(R.id.exam_info, "查看检前须知");
        } else {
            baseViewHolder.setVisible(R.id.exam_item_stats, false);
        }

        baseViewHolder.setOnClickListener(R.id.exam_item_stats, v -> {
            if (examItems.getExam_status().equals(GlobalConstant.CHECKING)) {
                Intent intent = new Intent(context, BarCodeActivity.class);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, UserCardActivity.class);
                FamiliesListItem familiesListItem = new FamiliesListItem();
                familiesListItem.setFullname(examItems.getCustomer().getName());
                familiesListItem.setHealth_card_no(examItems.getCustomer()
                        .getReservation_or_registration().getId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("FAMILIES_INFO", familiesListItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        baseViewHolder.setOnClickListener(R.id.exam_info, v -> {
            if (examItems.getExam_status().equals(GlobalConstant.RESERVATION)) {
                Intent intent = new Intent(context, BeforeExamActivity.class);
                intent.putExtra("EXAM_ID", String.valueOf(examItems.getId()));
                context.startActivity(intent);
            } else if (examItems.getExam_status().equals(GlobalConstant.CHECKING)) {
                Intent intent = new Intent(context, IntelligentCheckActivity.class);
                intent.putExtra("EXAM_ID", String.valueOf(examItems.getId()));
                context.startActivity(intent);
            } else if (examItems.getExam_status().equals(GlobalConstant.CHECKED)
                    && examItems.getReport().isIssued()) {
                Bundle bundle = new Bundle();
                bundle.putString(GlobalConstant.EXAM_REPORT_ID, String.valueOf(examItems.getId()));
                ActivityUtil.Companion.startActivity(context, new ExamReportDetailActivity(), bundle);
            } else if (examItems.getExam_status().equals(GlobalConstant.CHECKED)
                    && !examItems.getReport().isIssued()) {
                ToastUtil.showShortToast(context, "你还没有报告");
            }
        });

    }
}
