package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FMRecyclerAdapter extends RecyclerView.Adapter<FMRecyclerAdapter.FMRViewHolder> {

    private List<String> data;
    private Context context;

    public FMRecyclerAdapter(Context context,List<String> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FMRViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FMRViewHolder holder = new FMRViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item_layout,viewGroup,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FMRViewHolder viewHolder, int i) {

        viewHolder.peopleName.setText("测试");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FMRViewHolder extends RecyclerView.ViewHolder{
        CircleImageView peopleIcon;
        TextView peopleName;
        Button peopleMark;
        Button immediatelyCertification;

        public FMRViewHolder(@NonNull View itemView) {
            super(itemView);

            peopleIcon = itemView.findViewById(R.id.people_icon);
            peopleName = itemView.findViewById(R.id.people_name_text);
            peopleMark = itemView.findViewById(R.id.people_mark);
            immediatelyCertification = itemView.findViewById(R.id.immediately_certification_button);
        }
    }


    public boolean addItem(int position, String msg) {
        if (position < data.size() && position >= 0) {
            data.add(position, msg);
            notifyItemInserted(position);
            return true;
        }
        return false;
    }

    public boolean removeItem(int position) {
        if (position < data.size() && position >= 0) {
            data.remove(position);
            notifyItemRemoved(position);
            return true;
        }
        return false;
    }

    public void clearAll() {
        data.clear();
        notifyDataSetChanged();
    }

}
