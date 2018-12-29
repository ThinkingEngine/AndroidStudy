package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseFragment;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.module.activitys.BarADActivity;
import com.chengsheng.cala.htcm.module.activitys.BarCodeActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamDetailsActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamReportDetailActivity;
import com.chengsheng.cala.htcm.module.activitys.ModePaymentActivity;
import com.chengsheng.cala.htcm.module.activitys.RegisterDetailActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.simple.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class AIAssistantAdapter extends RecyclerView.Adapter<AIAssistantAdapter.AssistantViewHolder> {

    private Context context;
    private BaseFragment baseFragment;
    private List<AssistantItem> datas;
    private int type;

    private Retrofit retrofit;
    private HTCMApp app;


    public AIAssistantAdapter(Context context, BaseFragment baseFragment, List<AssistantItem> datas,
                              int type, String error) {
        this.context = context;
        this.baseFragment = baseFragment;
        this.datas = datas;
        this.type = type;
        app = HTCMApp.create(context);
    }

    @NonNull
    @Override
    public AssistantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AssistantViewHolder holder;
        if (datas.isEmpty() && type == 0) {
            holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_no_content_layout, null));
        } else if (datas.isEmpty() && type == -1) {
            holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.single_text_layout, null));
        } else {
            holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_item_layout, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssistantViewHolder viewHolder, final int i) {

        if (!datas.isEmpty()) {
            final AssistantItem data = datas.get(i);
            String stats = data.getOrder().getExam_status();
            Log.e("TAG", "STATUS:" + stats);

            viewHolder.userNameAIAssistant.setText(data.getCustomer().getName());
            viewHolder.userHeaderIconAIAssistant.setImageURI(data.getCustomer().getAvatar());

            //删除首页智能助理卡片
            viewHolder.deleteExamItem.setOnClickListener(v -> removeData(i));
            //跳转到详情页面
            viewHolder.aiAssistantItem.setOnClickListener(v -> {
                Intent intent = new Intent(context, ExamDetailsActivity.class);
                FamiliesListItem familiesListItem = new FamiliesListItem();
                familiesListItem.setFullname(data.getCustomer().getName());
                familiesListItem.setAvatar_path(data.getCustomer().getAvatar());
                familiesListItem.setHealth_card_no(data.getCustomer().getReservation_or_registration().getId());
                intent.putExtra("ORDER_ID", String.valueOf(data.getOrder().getId()));
                context.startActivity(intent);
            });

            viewHolder.userBitmapMark.setOnClickListener(v -> {
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
            });

            //预约
            if (stats.equals(GlobalConstant.RESERVATION)) {

                viewHolder.userBitmapMark.setImageResource(R.mipmap.erweima);
                viewHolder.examNum.setText("预约号：" + data.getCustomer().getReservation_or_registration().getId());
                viewHolder.itemBigNotes.setText("预约了" + data.getCustomer().getReservation_or_registration().getDate() + "的体检");

                Float money = Float.valueOf(data.getOrder().getDiscount_receivable());
                if (money > 0) {
                    viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                    viewHolder.unscrambleMark.setText("立即支付");
                    viewHolder.itemSecNotes.setText("待支付金额：" + money + "元");

                    viewHolder.unscrambleMark.setOnClickListener(v -> {
                        Intent intent = new Intent(context, ModePaymentActivity.class);
                        intent.putExtra("ORDER_ID", String.valueOf(data.getOrder().getId()));
                        intent.putExtra("ORDER_VAL", data.getOrder().getDiscount_receivable());
                        context.startActivity(intent);

                    });
                } else {
                    if (data.getOrder().isCan_autonomous()) {
                        viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                        viewHolder.unscrambleMark.setText("自助登记");
                        viewHolder.itemSecNotes.setText("您可前往中心登记台或自助完成登记");

                        viewHolder.unscrambleMark.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putString("ORDER_ID", String.valueOf(data.getOrder().getId()));
                            ActivityUtil.Companion.startActivity(context, new RegisterDetailActivity(), bundle);
                        });
                    } else {
                        viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                        viewHolder.itemSecNotes.setText("请前往中心登记台完成登记");
                    }

                }

            } else if (stats.equals(GlobalConstant.CHECKING)) {//正在检查
                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getCustomer().getReservation_or_registration().getId());
                viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                viewHolder.itemBigNotes.setText("点击查看体检进度详情");
                viewHolder.itemSecNotes.setText("项目检查完成后，请前往中心登记台确认");

            } else if (stats.equals(GlobalConstant.CHECKED)) {//已检查

                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getCustomer().getReservation_or_registration().getId());
                viewHolder.itemSecNotes.setText("体检日期：" + data.getCustomer().getReservation_or_registration().getDate());

                if (data.getReport().isIssued()) {
                    viewHolder.unscrambleMark.setVisibility(View.VISIBLE);
                    viewHolder.unscrambleMark.setText("查看报告");
                    viewHolder.itemBigNotes.setText("体检报告已生成");
                    viewHolder.unscrambleMark.setOnClickListener(v -> {
                        Intent intent = new Intent(context, ExamReportDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(GlobalConstant.EXAM_REPORT_ID, String.valueOf(data.getOrder().getId()));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    });
                } else {
                    viewHolder.itemBigNotes.setText("体检报告预计2-5个工作日内生成");
                    viewHolder.unscrambleMark.setVisibility(View.INVISIBLE);
                }
            } else {
                viewHolder.userBitmapMark.setImageResource(R.mipmap.tianxingma);
                viewHolder.examNum.setText("体检号：" + data.getCustomer().getReservation_or_registration().getId());
            }

        } else if (datas.isEmpty() && type == -1) {

        } else if (datas.isEmpty() && type == -2) {
            viewHolder.examNum.setText("未登录");
            viewHolder.itemBigNotes.setText("新手指引");
            viewHolder.unscrambleMark.setText("立即登录");
            viewHolder.userBitmapMark.setVisibility(View.INVISIBLE);
            viewHolder.deleteExamItem.setVisibility(View.INVISIBLE);

            viewHolder.unscrambleMark.setOnClickListener(v -> ActivityUtil.Companion.startActivity(context, new LoginActivity()));

            viewHolder.aiAssistantItem.setOnClickListener(v -> ActivityUtil.Companion.startActivity(context, new LoginActivity()));

        } else if (datas.isEmpty() && type == 0) {
            viewHolder.aiAssistantGroupDetail.setOnClickListener(v -> {
                Intent intent = new Intent(context, BarADActivity.class);
                intent.putExtra("NUM", 0);
                context.startActivity(intent);
            });
        }
    }


    public void removeData(int i) {
        updateAIAssistant(i);
    }

    @Override
    public int getItemCount() {
        if (type <= 0) {
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
        Button unscrambleMark;


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
            unscrambleMark = itemView.findViewById(R.id.unscramble_mark);

            aiAssistantGroupDetail = itemView.findViewById(R.id.ai_assistant_group_detail);
        }
    }

    private void updateAIAssistant(final int i) {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        baseFragment.showLoading();
        NetService service = retrofit.create(NetService.class);
        service.closeRecommended(app.getTokenType() + " " + app.getAccessToken(), datas.get(i).getOrder().getExam_status(), String.valueOf(datas.get(i).getOrder().getId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {

                        if (datas.size() == 1) {
                            datas.remove(i);
                            EventBus.getDefault().post("", GlobalConstant.UPDATE_AI_ASSISTANT_DATA);
                        } else {
                            datas.remove(i);
                            notifyItemRemoved(i);
                            notifyDataSetChanged();
                        }

                        baseFragment.showShortToast("已关闭");
                        baseFragment.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseFragment.hideLoading();
                        Toast.makeText(context, "关闭失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
