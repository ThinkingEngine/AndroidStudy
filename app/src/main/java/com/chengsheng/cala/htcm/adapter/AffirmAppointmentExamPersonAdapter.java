package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.module.activitys.FamilyManageActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.simple.eventbus.EventBus;

import java.util.List;

public class AffirmAppointmentExamPersonAdapter extends RecyclerView.Adapter<AffirmAppointmentExamPersonAdapter.ExamPersonAdapter> {

    private Context context;
    private List<FamiliesListItem> datas;

    private boolean select = false;//用户选择标签


    public AffirmAppointmentExamPersonAdapter(Context context, List<FamiliesListItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ExamPersonAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamPersonAdapter holder;
        if (i == 1) {
            holder = new ExamPersonAdapter(LayoutInflater.from(context).inflate(R.layout.add_families_item_layout, null));
        } else {
            holder = new ExamPersonAdapter(LayoutInflater.from(context).inflate(R.layout.exam_person_item_layout, null));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExamPersonAdapter viewHolder, final int i) {

        if (i < datas.size()) {
            final FamiliesListItem info = datas.get(i);
            viewHolder.familiesHeaderIconAppointment.setImageURI(info.getAvatar_path());
            viewHolder.appointmentFamiliesName.setText(info.getFullname());
            viewHolder.familiesMarkAppointment.setText(info.getOwner_relationship());


            if(info.isIs_default()){
                if(!select){
                    EventBus.getDefault().post(info.getId(),GlobalConstant.BOARD_EXAM_ID);
                    viewHolder.buttonSelectFamilies.setSelected(true);
                }
            }

            if (info.isIs_select()) {
                viewHolder.buttonSelectFamilies.setSelected(true);
            } else {
                viewHolder.buttonSelectFamilies.setSelected(false);
            }
            viewHolder.appointmentAddFamiliesBg.setOnClickListener(v -> {
                select = true;
                if (!viewHolder.buttonSelectFamilies.isSelected()) {
                    viewHolder.buttonSelectFamilies.setSelected(true);
                    EventBus.getDefault().post(info.getId(),GlobalConstant.BOARD_EXAM_ID);
//                    CallBackDataAuth.doExamDateInterface(info.getId());
                    for (int i1 = 0; i1 < datas.size(); i1++) {
                        if (datas.get(i1).getId() != info.getId()) {
                            datas.get(i1).setIs_select(false);
                            notifyItemChanged(i1);
                        }
                    }

                }
            });


        } else {
            //添加家人
            viewHolder.addFamiliesBox.setOnClickListener(v -> {
                Intent intent = new Intent(context, FamilyManageActivity.class);
                intent.putExtra("ADD_MARK", true);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == datas.size()) {
            return 1;
        } else {
            return 0;
        }

    }

    public class ExamPersonAdapter extends RecyclerView.ViewHolder {

        RelativeLayout appointmentAddFamiliesBg;
        SimpleDraweeView familiesHeaderIconAppointment;
        TextView appointmentFamiliesName;
        Button familiesMarkAppointment;
        Button buttonSelectFamilies;


        RelativeLayout addFamiliesBox;

        public ExamPersonAdapter(@NonNull View itemView) {
            super(itemView);
            appointmentAddFamiliesBg = itemView.findViewById(R.id.appointment_add_families_bg);
            familiesHeaderIconAppointment = itemView.findViewById(R.id.families_header_icon_appointment);
            appointmentFamiliesName = itemView.findViewById(R.id.appointment_families_name);
            familiesMarkAppointment = itemView.findViewById(R.id.families_mark_appointment);
            buttonSelectFamilies = itemView.findViewById(R.id.button_select_families);

            addFamiliesBox = itemView.findViewById(R.id.add_families_box);
        }
    }


}
