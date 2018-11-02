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
import com.chengsheng.cala.htcm.views.activitys.ExamDetailsActivity;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AIAssistantRecyclerAdapter extends RecyclerView.Adapter<AIAssistantRecyclerAdapter.AssistantViewHolder>{

    private Context context;
    private List<Map<String,String>> datas;

    public AIAssistantRecyclerAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public AssistantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AssistantViewHolder holder = new AssistantViewHolder(LayoutInflater.from(context).inflate(R.layout.ai_assistant_main_page_item_layout,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssistantViewHolder viewHolder, final int i) {
        viewHolder.aiAssistantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ExamDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class AssistantViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout aiAssistantItem;
        CircleImageView userHeaderIconAIAssistant;
        TextView userNameAIAssistant;
        TextView examNum;
        ImageView userBitmapMark;
        ImageView deleteExamItem;
        TextView itemBigNotes;
        TextView itemSecNotes;
        TextView dayNum;
        LinearLayout userNotifition;

        public AssistantViewHolder(@NonNull View itemView) {
            super(itemView);

            aiAssistantItem = itemView.findViewById(R.id.ai_assistant_item);
            userHeaderIconAIAssistant = itemView.findViewById(R.id.user_header_icon_ai_assistant);
            userNameAIAssistant = itemView.findViewById(R.id.user_name_ai_assistant);
            examNum = itemView.findViewById(R.id.exam_num);
            userBitmapMark = itemView.findViewById(R.id.user_bitmap_mark);
            deleteExamItem = itemView.findViewById(R.id.delete_exam_item);
            itemBigNotes = itemView.findViewById(R.id.item_big_notes);
            itemSecNotes = itemView.findViewById(R.id.item_sec_notes);
            dayNum = itemView.findViewById(R.id.day_num);
            userNotifition = itemView.findViewById(R.id.user_notifition);
        }
    }
}
