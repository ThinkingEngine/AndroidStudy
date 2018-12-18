package com.chengsheng.cala.htcm.adapter;

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
import android.widget.Toast;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.module.activitys.BarADActivity;
import com.chengsheng.cala.htcm.module.activitys.BarCodeActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamDetailsActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.text.ParseException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class AIAssistantRecyclerAdapter extends RecyclerView.Adapter<AIAssistantRecyclerAdapter.AssistantViewHolder> {

    private Context context;
    private List<AssistantItem> datas;
    private String error;
    private int type;

    private Retrofit retrofit;
    private HTCMApp app;
    private ZLoadingDialog loadingDialog;

    private boolean mark = false;

    public AIAssistantRecyclerAdapter(Context context, List<AssistantItem> datas,int type,String error) {
        this.context = context;
        this.datas = datas;
        this.error = error;
        this.type = type;

        app = HTCMApp.create(context);
        loadingDialog = new ZLoadingDialog(context);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("正在删除.....");
        loadingDialog.setLoadingColor(context.getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(context.getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(context.getResources().getColor(R.color.colorText));
        loadingDialog.setCancelable(false);
    }

    @NonNull
    @Override
    public AssistantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AssistantViewHolder holder;
        if (datas.isEmpty() && type == 0) {
            holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_no_content_layout, null));
        } else if(datas.isEmpty() && type == -1){
            holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.single_text_layout, null));
        }else{
            holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_item_layout, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssistantViewHolder viewHolder, final int i) {
        if (!datas.isEmpty()) {
            final AssistantItem data = datas.get(i);
            String stats = data.getOrder().getExam_status();

            viewHolder.deleteExamItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(i);
                }
            });

            viewHolder.itemBigNotes.setText("您预约了"+data.getCustomer().getReservation_or_registration().getDate()+"的体检");

            viewHolder.aiAssistantItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, ExamDetailsActivity.class);
                    FamiliesListItem familiesListItem = new FamiliesListItem();
                    familiesListItem.setFullname(data.getCustomer().getName());
                    familiesListItem.setAvatar_path(data.getCustomer().getAvatar());
                    familiesListItem.setHealth_card_no(data.getCustomer().getReservation_or_registration().getId());
//                    bundle.putSerializable("FAMILIES_INFO", familiesListItem);
//                    intent.putExtras(bundle);
                    intent.putExtra("ORDER_ID", String.valueOf(data.getOrder().getId()));
                    context.startActivity(intent);
                }
            });

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

            viewHolder.userNameAIAssistant.setText(data.getCustomer().getName());
            viewHolder.userHeaderIconAIAssistant.setImageURI(data.getCustomer().getAvatar());


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
        } else if(datas.isEmpty() && type == -1){
            viewHolder.textMark.setText(error);
        }else if(datas.isEmpty() && type == 0){
            viewHolder.aiAssistantGroupDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,BarADActivity.class);
                    intent.putExtra("NUM",1);
                    context.startActivity(intent);
                }
            });
        }
    }


    public void removeData(int i) {
        updateAIAssistant(i);
    }

    @Override
    public int getItemCount() {
        if (datas.size() == 0) {
            return 1;
        } else {
            return datas.size();
        }
    }


    public class AssistantViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout aiAssistantItem;
        RelativeLayout aiAssistantGroupDetail;
        SimpleDraweeView userHeaderIconAIAssistant;
        TextView userNameAIAssistant;
        TextView examNum;
        ImageView userBitmapMark;
        ImageView deleteExamItem;
        TextView itemBigNotes;
        TextView itemSecNotes;
        TextView dayNum;
        LinearLayout userNotifition;
        Button unscrambleMark;
        TextView showWaitNum;

        TextView textMark;

        public AssistantViewHolder(@NonNull View itemView) {
            super(itemView);

            aiAssistantItem = itemView.findViewById(R.id.ai_assistant_item);
            userHeaderIconAIAssistant = itemView.findViewById(R.id.user_header_icon_ai_assistant);
            userNameAIAssistant = itemView.findViewById(R.id.user_name_ai_assistant);
            examNum = itemView.findViewById(R.id.exam_num);
            userBitmapMark = itemView.findViewById(R.id.user_bitmap_mark);
            deleteExamItem = itemView.findViewById(R.id.delete_exam_item);
            itemBigNotes = itemView.findViewById(R.id.item_big_notes);
            itemSecNotes = itemView.findViewById(R.id.item_sec_notes);
            dayNum = itemView.findViewById(R.id.day_num);
            userNotifition = itemView.findViewById(R.id.user_notifition);
            dayNum = itemView.findViewById(R.id.day_num);
            unscrambleMark = itemView.findViewById(R.id.unscramble_mark);
            showWaitNum = itemView.findViewById(R.id.show_wait_num);

            textMark = itemView.findViewById(R.id.text_mark);

            aiAssistantGroupDetail = itemView.findViewById(R.id.ai_assistant_group_detail);
        }
    }

    private void updateAIAssistant(final int i) {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.closeRecommended(app.getTokenType() + " " + app.getAccessToken(), datas.get(i).getOrder().getExam_status(), String.valueOf(datas.get(i).getOrder().getId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {

                        if (datas.size() == 1) {
                            datas.remove(i);
                            CallBackDataAuth.doUpdateAIAssisont(true);
                        } else {
                            datas.remove(i);
                            notifyItemRemoved(i);
                            notifyDataSetChanged();

                        }

                        loadingDialog.cancel();
                        Toast.makeText(context, "修改成功!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(context, "修改失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}