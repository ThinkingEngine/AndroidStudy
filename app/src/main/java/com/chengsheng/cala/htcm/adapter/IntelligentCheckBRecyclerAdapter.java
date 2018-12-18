package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamItem;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

public class IntelligentCheckBRecyclerAdapter extends RecyclerView.Adapter<IntelligentCheckBRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<ExamItem> datas;

    public IntelligentCheckBRecyclerAdapter(Context context,List<ExamItem> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.intelligent_check_item_b_layout,null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        ExamItem data = datas.get(i);

        viewHolder.checkItemName.setText(data.getName());
        viewHolder.checkItemChild.setText(data.getExam_address());

        viewHolder.backOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击撤销",Toast.LENGTH_SHORT).show();
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
        TextView checkedMarkB;
        SwipeLayout swiplayoutModel;
        TextView backOutButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkItemName = itemView.findViewById(R.id.check_item_name_c);
            checkItemChild = itemView.findViewById(R.id.check_item_child_c);
            checkedMarkB = itemView.findViewById(R.id.checked_mark_c);
            swiplayoutModel = itemView.findViewById(R.id.swiplayout_model);
            backOutButton = itemView.findViewById(R.id.back_out_button);
        }
    }
}
