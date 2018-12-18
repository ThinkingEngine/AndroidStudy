package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodela.MessageItem;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.module.activitys.ExamDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class ServiceMessageRecyclerViewAdapter extends RecyclerView.Adapter<ServiceMessageRecyclerViewAdapter.ServiceMessageVH> {
    private Context context;
    private List<MessageItem> datas;


    public ServiceMessageRecyclerViewAdapter(Context context, List<MessageItem> datas) {
        this.context = context;
        this.datas = datas;

    }

    @NonNull
    @Override
    public ServiceMessageVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ServiceMessageVH vh;
        if (datas.isEmpty()) {
            vh = new ServiceMessageVH(LayoutInflater.from(context).inflate(R.layout.no_contants_layout, null));
        } else {
            vh = new ServiceMessageVH(LayoutInflater.from(context).inflate(R.layout.service_message_item_layout, null));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceMessageVH viewHolder, int i) {
        if (!datas.isEmpty()) {
            final MessageItem data = datas.get(i);
            if (!data.isIs_read()) {
                viewHolder.messageHasRead.setVisibility(View.VISIBLE);
            } else {
                viewHolder.messageHasRead.setVisibility(View.INVISIBLE);
            }
            viewHolder.serviceMessageItemDate.setText(data.getCreated_at());
            viewHolder.messageTitle.setText(data.getTitle());
            viewHolder.messageBody.setText(data.getContent());

            viewHolder.serviceSmsBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> check = new ArrayList<>();
                    check.add(String.valueOf(data.getId()));
                    CallBackDataAuth.doCheckServiceInterface(check);
                    String id = data.getPurpose_identifier();
                    Intent intent = new Intent(context, ExamDetailsActivity.class);
                    intent.putExtra("ORDER_ID", id);
                    context.startActivity(intent);
                }
            });

        } else {
            viewHolder.noContents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBackDataAuth.doUpdateStateInterface(true);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (datas.isEmpty()) {
            return 1;
        } else {
            return datas.size();
        }
    }

    public class ServiceMessageVH extends RecyclerView.ViewHolder {
        TextView serviceMessageItemDate;
        TextView messageTitle;
        TextView messageBody;
        ImageView messageHasRead;
        RelativeLayout serviceSmsBody;

        ImageView noContents;

        public ServiceMessageVH(@NonNull View itemView) {
            super(itemView);
            serviceMessageItemDate = itemView.findViewById(R.id.service_message_item_date);
            messageTitle = itemView.findViewById(R.id.message_title);
            messageBody = itemView.findViewById(R.id.message_body);
            messageHasRead = itemView.findViewById(R.id.message_has_read);
            serviceSmsBody = itemView.findViewById(R.id.service_sms_body);

            noContents = itemView.findViewById(R.id.no_contents);
        }
    }
}
