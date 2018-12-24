package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;
import java.util.Map;

public class PeopleInfoRecyclerAdapter extends RecyclerView.Adapter<PeopleInfoRecyclerAdapter.MyViewHoleder> {
    private Context context;
    private List<Map<String,String>> datas;

    public PeopleInfoRecyclerAdapter(Context context,List<Map<String,String>> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHoleder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHoleder myViewHoleder = new MyViewHoleder(LayoutInflater.from(context).inflate(R.layout.people_has_info_item_bg_layout,viewGroup,false));

        return myViewHoleder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoleder viewHolder, int i) {

        Map<String,String> data = datas.get(i);
        if(i != 0){
            viewHolder.divider.setVisibility(View.INVISIBLE);
        }

//        viewHolder.icon.setImageResource(Integer.valueOf(data.get("ICON")));
        viewHolder.typeName.setText(data.get("NAME"));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHoleder extends RecyclerView.ViewHolder{

        ImageView icon;
        ImageView intoButton;
        TextView typeName;
        LinearLayout divider;

        public MyViewHoleder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.people_has_info_icon);
            intoButton = itemView.findViewById(R.id.into_people_has_info_detail);
            typeName = itemView.findViewById(R.id.people_has_info_type);
            divider = itemView.findViewById(R.id.people_has_info_divider_up);
        }
    }
}
