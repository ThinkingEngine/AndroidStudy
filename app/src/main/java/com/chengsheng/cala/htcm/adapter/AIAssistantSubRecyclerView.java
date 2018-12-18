package com.chengsheng.cala.htcm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.module.activitys.ExamReportDetailActivity;
import com.chengsheng.cala.htcm.module.activitys.ModePaymentActivity;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.module.activitys.BarCodeActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamDetailsActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.daimajia.swipe.SwipeLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.List;


public class AIAssistantSubRecyclerView extends RecyclerView.Adapter<AIAssistantSubRecyclerView.AIAssistantSubViewHolder> {
    private Context context;
    private List<AssistantItem> datas;

    public AIAssistantSubRecyclerView(Context context, List<AssistantItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public AIAssistantSubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AIAssistantSubViewHolder holder;
        if (datas.isEmpty()) {
            holder = new AIAssistantSubViewHolder(LayoutInflater.from(context).inflate(R.layout.no_contants_layout, null));
        } else {
            holder = new AIAssistantSubViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_list_bg_layout, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AIAssistantSubViewHolder viewHolder, final int i) {
        if (!datas.isEmpty()) {
            final AssistantItem data = datas.get(i);

            String stats = data.getOrder().getExam_status();

            viewHolder.itemBigNotes.setText("您预约了" + data.getCustomer().getReservation_or_registration().getDate() + "的体检");
            viewHolder.aiAssistantItemDate.setText(data.getOrder().getUpdated_at());
            viewHolder.aiAssistantListItem.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.deleteExamItem.setBackground(context.getResources().getDrawable(R.drawable.red_dot));
            viewHolder.userNameAiAssistant.setText(data.getCustomer().getName());
            viewHolder.aiAssistantItemDate.setText(data.getCustomer().getReservation_or_registration().getDate());
            viewHolder.userHeaderIconAiAssistant.setImageURI(data.getCustomer().getAvatar());

            viewHolder.userBitmapMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    if (data.getCustomer().getReservation_or_registration().getStatus().equals(GlobalConstant.RESERVATION)) {
                        Intent intent = new Intent(context, UserCardActivity.class);
                        FamiliesListItem familiesListItem = new FamiliesListItem();
                        familiesListItem.setFullname(data.getCustomer().getName());
                        familiesListItem.setAvatar_path(data.getCustomer().getAvatar());
                        familiesListItem.setHealth_card_no(data.getCustomer().getReservation_or_registration().getId());
                        bundle.putSerializable("FAMILIES_INFO", familiesListItem);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, BarCodeActivity.class);
                        bundle.putString("FAMILIES_INFO", data.getCustomer().getReservation_or_registration().getId());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });

//            viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);

            //预约
            if (stats.equals(GlobalConstant.RESERVATION)) {
                viewHolder.userBitmapMark.setImageResource(R.mipmap.erweima);
                viewHolder.examNum.setText("预约号：" + data.getOrder().getId());

                Float money = Float.valueOf(data.getOrder().getDiscount_receivable());
                if (money > 0) {
                    viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                    viewHolder.unscrambleMark.setText("立即支付");
                    viewHolder.itemSecNotes.setText("待支付金额：" + money + "元");

                    viewHolder.unscrambleMark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,ModePaymentActivity.class);
                            intent.putExtra("ORDER_ID",String.valueOf(data.getOrder().getId()));
                            intent.putExtra("ORDER_VAL",data.getOrder().getDiscount_receivable());
                            context.startActivity(intent);
                        }
                    });
                } else {
                    viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                    viewHolder.itemSecNotes.setText("请前往中心登记台完成登记");
                }

            } else if (stats.equals(GlobalConstant.CHECKING)) {
                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getOrder().getId());

                if (data.getOrder().isCan_autonomous()) {
                    viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                    viewHolder.unscrambleMark.setText("自主登记");
                    viewHolder.itemSecNotes.setText("您可前往中心登记台或自主完成登记");
                } else {
                    viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                    viewHolder.itemSecNotes.setText("项目检查完成后，请前往中心登记台确认");
                }

            } else if (stats.equals(GlobalConstant.CHECKED)) {
                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getOrder().getId());
                viewHolder.itemSecNotes.setText("体检日期：" + data.getCustomer().getReservation_or_registration().getDate());

                if (data.getReport().isIssued()) {
                    viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                    viewHolder.unscrambleMark.setText("查看报告");
                    viewHolder.unscrambleMark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,ExamReportDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(GlobalConstant.EXAM_REPORT_ID,String.valueOf(data.getOrder().getId()));
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                }
            }else{
                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getOrder().getId());
            }
        } else {
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
        if (datas.isEmpty()) {
            return 1;
        } else {
            return datas.size();
        }
    }

    public class AIAssistantSubViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout aiAssistantListItem;
        LinearLayout bottomWrapper;
        ImageView deleteExamItem;
        TextView userNameAiAssistant;
        RelativeLayout aiAssistantItem;
        SimpleDraweeView userHeaderIconAiAssistant;
        ImageView userBitmapMark;
        TextView examNum;
        TextView aiAssistantItemDate;
        TextView itemBigNotes;
        TextView itemSecNotes;
        TextView checkText, backMainPageText;
        Button unscrambleMark;

        ImageView noContents;

        @SuppressLint("CutPasteId")
        public AIAssistantSubViewHolder(@NonNull View itemView) {
            super(itemView);
            if (!datas.isEmpty()) {
                aiAssistantListItem = itemView.findViewById(R.id.ai_assistant_list_item);
                bottomWrapper = itemView.findViewById(R.id.bottom_wrapper);
                checkText = itemView.findViewById(R.id.check_text);
                backMainPageText = itemView.findViewById(R.id.back_main_page_text);
                deleteExamItem = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.delete_exam_item);
                userNameAiAssistant = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.user_name_ai_assistant);
                aiAssistantItem = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.ai_assistant_item);
                userHeaderIconAiAssistant = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.user_header_icon_ai_assistant);
                userBitmapMark = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.user_bitmap_mark);
                examNum = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.exam_num);
                aiAssistantItemDate = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.ai_assistant_item_date);
                itemBigNotes = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.item_big_notes);
                itemSecNotes = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.item_sec_notes);
                unscrambleMark = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.unscramble_mark);
            } else {
                noContents = itemView.findViewById(R.id.no_contents);
            }

        }
    }
}
