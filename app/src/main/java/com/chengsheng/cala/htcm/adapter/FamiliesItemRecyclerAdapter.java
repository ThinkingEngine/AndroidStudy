package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.module.activitys.FamiliesDetailsActivity;
import com.chengsheng.cala.htcm.module.activitys.FamilyManageActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.widget.ImmediatelyDialogView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class FamiliesItemRecyclerAdapter extends RecyclerView.Adapter<FamiliesItemRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<FamiliesListItem> datas;

    public FamiliesItemRecyclerAdapter(Context context, List<FamiliesListItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder;

        if (datas.isEmpty()) {
            viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.families_list_null_bg_layout, null));
        } else {
            viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fmailies_item_bg_layout, null));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {

        if (!datas.isEmpty()) {
            final FamiliesListItem data = datas.get(i);
            if (data.isIs_auth()) {
                viewHolder.certification.setVisibility(View.INVISIBLE);
                viewHolder.familiesQR.setVisibility(View.VISIBLE);
                viewHolder.familiesID.setVisibility(View.VISIBLE);
                viewHolder.note.setVisibility(View.INVISIBLE);

                viewHolder.familiesID.setText(data.getId_card_no());
            } else {
                viewHolder.certification.setVisibility(View.VISIBLE);
                viewHolder.familiesQR.setVisibility(View.INVISIBLE);
                viewHolder.familiesID.setVisibility(View.INVISIBLE);
                viewHolder.note.setVisibility(View.VISIBLE);
            }

            if (data.getOwner_relationship().equals("")) {
                viewHolder.mark.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.mark.setText(data.getOwner_relationship());
            }

            viewHolder.familiesName.setText(data.getFullname());
            viewHolder.familiesTel.setText(data.getMobile());


            viewHolder.familiesIcon.setImageURI(data.getAvatar_path());

            //跳转到含二维码的页面.
            viewHolder.familiesQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserCardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FAMILIES_INFO", data);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });


            viewHolder.certification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImmediatelyDialogView immediatelyDialogView = new ImmediatelyDialogView(context, data.getId());
                    immediatelyDialogView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    immediatelyDialogView.show();
                }
            });


            //获取家人详细信息
            viewHolder.familiesItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FamiliesDetailsActivity.class);
                    String familiesID = String.valueOf(data.getId());
                    intent.putExtra("FAMILIES_ID", familiesID);
                    context.startActivity(intent);
//                if(data.getId() == 9){
//                    Intent intent = new Intent(context,FamiliesDetailsActivity.class);
//                    String familiesID = String.valueOf(data.getId());
//                    intent.putExtra("FAMILIES_ID",familiesID);
//                    context.startActivity(intent);
//                }else{
//                    Toast.makeText(context,"id:"+data.getId()+" 无效",Toast.LENGTH_SHORT).show();
//                }

                }
            });
        } else {
            viewHolder.addNewFamilies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,FamilyManageActivity.class);
                    intent.putExtra("ADD_MARK",true);
                    context.startActivity(intent);
                    
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (datas.size() == 0) {
            return 1;
        } else {
            return datas.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout familiesItem;
        SimpleDraweeView familiesIcon;
        TextView familiesName;
        TextView familiesTel;
        TextView familiesID;
        ImageView familiesQR;
        Button certification;
        TextView note;
        TextView mark;

        Button addNewFamilies;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            if (datas.size() != 0) {
                familiesItem = itemView.findViewById(R.id.families_item);
                familiesIcon = itemView.findViewById(R.id.families_header_icon);
                familiesName = itemView.findViewById(R.id.families_name_text);
                familiesTel = itemView.findViewById(R.id.families_tel_text);
                familiesID = itemView.findViewById(R.id.families_id_text);
                familiesQR = itemView.findViewById(R.id.families_qr);
                certification = itemView.findViewById(R.id.families_certification_button);
                note = itemView.findViewById(R.id.certification_note_text);
                mark = itemView.findViewById(R.id.families_mark);
            } else {
                addNewFamilies = itemView.findViewById(R.id.add_new_families);
            }
        }
    }


}
