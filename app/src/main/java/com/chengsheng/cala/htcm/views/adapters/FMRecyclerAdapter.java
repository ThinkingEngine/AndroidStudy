package com.chengsheng.cala.htcm.views.adapters;

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
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.views.activitys.FamilyManageActivity;
import com.chengsheng.cala.htcm.views.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.views.dialog.ImmediatelyDialogView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class FMRecyclerAdapter extends RecyclerView.Adapter<FMRecyclerAdapter.FMRViewHolder> {

    private List<FamiliesListItem> datas;
    private Context context;

    public FMRecyclerAdapter(Context context,List<FamiliesListItem> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public FMRViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FMRViewHolder holder;
        if(datas.isEmpty()){
             holder = new FMRViewHolder(LayoutInflater.from(context).inflate(R.layout.families_list_null_bg_layout,viewGroup,false));
        }else{
            holder = new FMRViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item_layout,viewGroup,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FMRViewHolder viewHolder, final int i) {

        if(!datas.isEmpty()){
            final FamiliesListItem data = datas.get(i);
            if(!data.isIs_auth()){
                viewHolder.peopleID.setVisibility(View.INVISIBLE);
                viewHolder.qcCode.setVisibility(View.INVISIBLE);
                viewHolder.immediatelyCertification.setVisibility(View.VISIBLE);
                viewHolder.immediatelyCertification.setEnabled(true);
                viewHolder.qcCode.setEnabled(false);
                viewHolder.peopleID.setText(data.getId_card_no());
            }else{
                viewHolder.peopleID.setVisibility(View.VISIBLE);
                viewHolder.qcCode.setVisibility(View.VISIBLE);
                viewHolder.immediatelyCertification.setVisibility(View.INVISIBLE);
                viewHolder.immediatelyCertification.setEnabled(false);
                viewHolder.qcCode.setEnabled(true);

//            PeopleInfoRecyclerAdapter pa;
//            if(data.get("NAME").equals("王树彤")){
//                 pa = new PeopleInfoRecyclerAdapter(context,a);
//            }else if(data.get("NAME").equals("王树同")){
//                 pa = new PeopleInfoRecyclerAdapter(context,b);
//            }else{
//                pa = null;
//            }
//            viewHolder.chileList.setLayoutManager(new LinearLayoutManager(context));
//            viewHolder.chileList.setAdapter(pa);
            }


            viewHolder.peopleIcon.setImageURI(data.getAvatar_path());
            viewHolder.peopleName.setText(data.getFullname());
            if(data.getOwner_relationship().equals("")){
                viewHolder.peopleMark.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.peopleMark.setVisibility(View.VISIBLE);
                viewHolder.peopleMark.setText(data.getOwner_relationship());
            }

            final ImmediatelyDialogView immediatelyDialogView = new ImmediatelyDialogView(context,data.getId());

            viewHolder.qcCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,UserCardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FAMILIES_INFO",data);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            viewHolder.immediatelyCertification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    immediatelyDialogView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    immediatelyDialogView.show();
                }
            });
        }else{
            viewHolder.addNewFamilies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,"测试",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,FamilyManageActivity.class);
                    intent.putExtra("ADD_MARK",true);
                    context.startActivity(intent);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        //返回数据长度
        if(datas.isEmpty()){
            return 1;
        }else{
            return datas.size();
        }

    }

    public class FMRViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView peopleIcon;
        TextView peopleName;
        Button peopleMark;
        Button immediatelyCertification;
        ImageView qcCode;
        TextView peopleID;
        RecyclerView chileList;
        Button addNewFamilies;

        public FMRViewHolder(@NonNull View itemView) {
            super(itemView);

            peopleIcon = itemView.findViewById(R.id.people_icon);
            peopleName = itemView.findViewById(R.id.people_name_text);
            peopleMark = itemView.findViewById(R.id.people_mark);
            immediatelyCertification = itemView.findViewById(R.id.immediately_certification_button);
            qcCode = itemView.findViewById(R.id.people_qr_code_mark);
            peopleID = itemView.findViewById(R.id.people_id);
            chileList = itemView.findViewById(R.id.people_has_info);
            addNewFamilies = itemView.findViewById(R.id.add_new_families);
        }
    }


    public boolean addItem(int position, FamiliesListItem msg) {
        if (position < datas.size() && position >= 0) {
            datas.add(position, msg);
            notifyItemInserted(position);
            return true;
        }
        return false;
    }

    public boolean removeItem(int position) {
        if (position < datas.size() && position >= 0) {
            datas.remove(position);
            notifyItemRemoved(position);
            return true;
        }
        return false;
    }

    public void clearAll() {
        datas.clear();
        notifyDataSetChanged();
    }

}
