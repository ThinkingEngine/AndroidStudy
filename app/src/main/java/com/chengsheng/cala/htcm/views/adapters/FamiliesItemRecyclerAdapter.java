package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.chengsheng.cala.htcm.views.activitys.FamiliesDetailsActivity;
import com.chengsheng.cala.htcm.views.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.views.dialog.ImmediatelyDialogView;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamiliesItemRecyclerAdapter extends RecyclerView.Adapter<FamiliesItemRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<Map<String,String>> datas;

    public FamiliesItemRecyclerAdapter(Context context,List<Map<String,String>> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fmailies_item_bg_layout,null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {

        Map<String,String> data = datas.get(i);
        if(data.get("AUTHENTICATION").equals("true")){
            viewHolder.certification.setVisibility(View.INVISIBLE);
            viewHolder.familiesQR.setVisibility(View.VISIBLE);
            viewHolder.familiesID.setVisibility(View.VISIBLE);
            viewHolder.note.setVisibility(View.INVISIBLE);

            viewHolder.familiesID.setText(data.get("FAMILIES_ID"));
        }else{
            viewHolder.certification.setVisibility(View.VISIBLE);
            viewHolder.familiesQR.setVisibility(View.INVISIBLE);
            viewHolder.familiesID.setVisibility(View.INVISIBLE);
            viewHolder.note.setVisibility(View.VISIBLE);
        }

        viewHolder.familiesName.setText(data.get("FAMILIES_NAME"));
        viewHolder.familiesTel.setText(data.get("FAMILIES_TEL"));
        viewHolder.mark.setText(data.get("FAMILIES_MARK"));

        viewHolder.familiesQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserCardActivity.class);
                context.startActivity(intent);
            }
        });

        viewHolder.certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImmediatelyDialogView immediatelyDialogView = new ImmediatelyDialogView(context,R.style.aert_dialog);
                immediatelyDialogView.show();
            }
        });

        viewHolder.familiesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FamiliesDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout familiesItem;
        CircleImageView familiesIcon;
        TextView familiesName;
        TextView familiesTel;
        TextView familiesID;
        ImageView familiesQR;
        Button certification;
        TextView note;
        Button mark;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            familiesItem = itemView.findViewById(R.id.families_item);
            familiesIcon = itemView.findViewById(R.id.families_header_icon);
            familiesName = itemView.findViewById(R.id.families_name_text);
            familiesTel = itemView.findViewById(R.id.families_tel_text);
            familiesID = itemView.findViewById(R.id.families_id_text);
            familiesQR = itemView.findViewById(R.id.families_qr);
            certification = itemView.findViewById(R.id.families_certification_button);
            note = itemView.findViewById(R.id.certification_note_text);
            mark = itemView.findViewById(R.id.families_mark);
        }
    }
}
