package com.chengsheng.cala.htcm.adapter;

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
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamItem;

import java.util.List;

public class IntelligentCheckARecyclerAdapter extends RecyclerView.Adapter<IntelligentCheckARecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<ExamItem> datas;

    public IntelligentCheckARecyclerAdapter(Context context,List<ExamItem> datas){
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
        ExamItem data = datas.get(i);

        viewHolder.currentWaitingNumber.setText(String.valueOf(data.getWait_person()));
        viewHolder.checkItemName.setText(data.getName());
        viewHolder.checkItemChild.setText(data.getExam_address());

//        viewHolder.itemSelectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(viewHolder.itemSelectButton.isSelected()){
//                    viewHolder.itemSelectButton.setSelected(false);
//                }else{
//                    viewHolder.itemSelectButton.setSelected(true);
//                }
//            }
//        });

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
