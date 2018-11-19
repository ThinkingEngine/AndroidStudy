package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;
import java.util.Map;

public class IntelligentCheckARecyclerAdapter extends RecyclerView.Adapter<IntelligentCheckARecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<Map<String,String>> datas;

    public IntelligentCheckARecyclerAdapter(Context context,List<Map<String,String>> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.intellignet_check_item_a_layout,null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int i) {
        Map<String,String> data = datas.get(i);

        if(i == datas.size()-1){
            viewHolder.box.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        if(data.get("STATE").equals("TYPE_A")){
            viewHolder.currentWaitingNumberBox.setVisibility(View.INVISIBLE);
            viewHolder.checkedMark.setVisibility(View.INVISIBLE);
            viewHolder.itemSelectButton.setVisibility(View.VISIBLE);
        }else if(data.get("STATE").equals("TYPE_B")){
            viewHolder.currentWaitingNumberBox.setVisibility(View.VISIBLE);
            viewHolder.checkedMark.setVisibility(View.INVISIBLE);
            viewHolder.itemSelectButton.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.currentWaitingNumberBox.setVisibility(View.INVISIBLE);
            viewHolder.checkedMark.setVisibility(View.VISIBLE);
            viewHolder.itemSelectButton.setVisibility(View.INVISIBLE);
        }

        viewHolder.itemSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.itemSelectButton.isSelected()){
                    viewHolder.itemSelectButton.setSelected(false);
                }else{
                    viewHolder.itemSelectButton.setSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView checkItemName;
        TextView checkItemChild;
        Button itemSelectButton;
        LinearLayout currentWaitingNumberBox;
        TextView currentWaitingNumber;
        TextView checkedMark;
        RelativeLayout box;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkItemName = itemView.findViewById(R.id.check_item_name);
            checkItemChild = itemView.findViewById(R.id.check_item_child);
            itemSelectButton = itemView.findViewById(R.id.item_select_button);
            currentWaitingNumberBox = itemView.findViewById(R.id.current_waiting_number_box);
            currentWaitingNumber = itemView.findViewById(R.id.current_waiting_number);
            checkedMark = itemView.findViewById(R.id.checked_mark);
            box = itemView.findViewById(R.id.container_h);
        }
    }
}
