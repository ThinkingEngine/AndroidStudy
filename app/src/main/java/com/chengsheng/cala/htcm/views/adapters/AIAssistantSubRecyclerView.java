package com.chengsheng.cala.htcm.views.adapters;

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

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.AssistantItem;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.views.activitys.BarCodeActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamDetailsActivity;
import com.chengsheng.cala.htcm.views.activitys.UserCardActivity;
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

            viewHolder.itemBigNotes.setText("您预约了"+data.getCustomer().getReservation_or_registration().getDate()+"的体检");
            viewHolder.aiAssistantItemDate.setText(data.getOrder().getUpdated_at());

            if (data.getRecommend_exam_item().getExam_item_id() == 0) {
                viewHolder.userNotifition.setVisibility(View.VISIBLE);
            } else {
                viewHolder.userNotifition.setVisibility(View.INVISIBLE);
            }
            //预约
            if (stats.equals(GlobalConstant.RESERVATION)) {
                viewHolder.dayNum.setVisibility(View.VISIBLE);
                viewHolder.showWaitNum.setVisibility(View.INVISIBLE);
                String day = "";
                String dayNumber = "";
                try {
                    dayNumber = String.valueOf(FuncUtils.dayNum(data.getCustomer().getReservation_or_registration().getDate()));
                    day = "距检查日" + "\n" + dayNumber + "天";
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!day.equals("")) {
                    SpannableString sp = new SpannableString(day);
                    sp.setSpan(new AbsoluteSizeSpan(10, true), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sp.setSpan(new AbsoluteSizeSpan(14, true), 5, 5 + dayNumber.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sp.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 5, 5 + dayNumber.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 5, 5 + dayNumber.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.dayNum.setText(sp);

                    viewHolder.dayNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ExamDetailsActivity.class);
                            intent.putExtra("ORDER_ID", String.valueOf(data.getOrder().getId()));
                            context.startActivity(intent);
                        }
                    });
                }

                viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                viewHolder.userNotifition.setVisibility(View.INVISIBLE);
            } else if (stats.equals(GlobalConstant.CHECKING)) {
                viewHolder.showWaitNum.setVisibility(View.VISIBLE);
                String tempNum = String.valueOf(data.getRecommend_exam_item().getWait_person());
                String waitNum = "当前排队" + "\n" + tempNum + "人";
                SpannableString sp = new SpannableString(waitNum);
                sp.setSpan(new AbsoluteSizeSpan(12, true), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new AbsoluteSizeSpan(20, true), 5, 5 + tempNum.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 5, 5 + tempNum.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 5, 5 + tempNum.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                viewHolder.showWaitNum.setText(sp);
                viewHolder.showWaitNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ExamDetailsActivity.class);
                        intent.putExtra("ORDER_ID", String.valueOf(data.getOrder().getId()));
                        context.startActivity(intent);
                    }
                });

                viewHolder.dayNum.setVisibility(View.INVISIBLE);
                viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                viewHolder.userNotifition.setVisibility(View.INVISIBLE);
            } else if (stats.equals(GlobalConstant.CHECKED)) {
                viewHolder.showWaitNum.setVisibility(View.INVISIBLE);
                if (data.getReport().isIssued()) {
                    viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                    viewHolder.dayNum.setVisibility(View.INVISIBLE);
                }
            }

            viewHolder.aiAssistantListItem.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.deleteExamItem.setBackground(context.getResources().getDrawable(R.drawable.red_dot));
            viewHolder.userNameAiAssistant.setText(data.getCustomer().getName());
            viewHolder.aiAssistantItemDate.setText(data.getCustomer().getReservation_or_registration().getDate());
            viewHolder.userHeaderIconAiAssistant.setImageURI(data.getCustomer().getAvatar());



            if (data.getOrder().getExam_status().equals(GlobalConstant.RESERVATION)) {
                viewHolder.userBitmapMark.setImageResource(R.mipmap.erweima);
                viewHolder.examNum.setText("预约号：" + data.getOrder().getId());
            } else {
                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getOrder().getId());
            }

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
        } else {
            viewHolder.noContents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBackDataAuth.doUpdateStateInterface(true);
                }
            });
        }

//        viewHolder.aiAssistantItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, IntelligentCheckActivity.class);
//                context.startActivity(intent);
//            }
//        });

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
        ImageView itemBigNotesMark;
        LinearLayout userNotifition;
        TextView dayNum;
        TextView checkText, backMainPageText;
        Button unscrambleMark;
        TextView showWaitNum;

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
                itemBigNotesMark = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.item_big_notes_mark);
                userNotifition = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.user_notifition);
                dayNum = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.day_num);
                unscrambleMark = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.unscramble_mark);
                showWaitNum = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.show_wait_num);
            }else{
                noContents = itemView.findViewById(R.id.no_contents);
            }

        }
    }
}
