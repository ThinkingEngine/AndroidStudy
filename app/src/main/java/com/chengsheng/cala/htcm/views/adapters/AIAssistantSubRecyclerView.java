package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.IntelligentCheckActivity;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

public class AIAssistantSubRecyclerView extends RecyclerView.Adapter<AIAssistantSubRecyclerView.AIAssistantSubViewHolder> {
    private Context context;
    private List<String> datas;

    public AIAssistantSubRecyclerView(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public AIAssistantSubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AIAssistantSubViewHolder holder = new AIAssistantSubViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_list_bg_layout,null));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AIAssistantSubViewHolder viewHolder, final int i) {
        viewHolder.aiAssistantListItem.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.deleteExamItem.setVisibility(View.INVISIBLE);
        viewHolder.userNameAiAssistant.setText(datas.get(i));

        viewHolder.aiAssistantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,IntelligentCheckActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class AIAssistantSubViewHolder extends RecyclerView.ViewHolder{
        SwipeLayout aiAssistantListItem;
        LinearLayout bottomWrapper;
        ImageView deleteExamItem;
        TextView userNameAiAssistant;
        RelativeLayout aiAssistantItem;

        public AIAssistantSubViewHolder(@NonNull View itemView) {
            super(itemView);
            aiAssistantListItem = itemView.findViewById(R.id.ai_assistant_list_item);
            bottomWrapper = itemView.findViewById(R.id.bottom_wrapper);
            deleteExamItem = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.delete_exam_item);
            userNameAiAssistant = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.user_name_ai_assistant);
            aiAssistantItem = itemView.findViewById(R.id.ai_assistant_sub_item).findViewById(R.id.ai_assistant_item);
        }
    }
}
