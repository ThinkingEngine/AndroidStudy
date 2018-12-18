package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ServiceOrderRecyclerViewAdapter extends RecyclerView.Adapter<ServiceOrderRecyclerViewAdapter.ServiceOrderVH> {

    private Context context;

    public ServiceOrderRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceOrderVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ServiceOrderVH vh = new ServiceOrderVH(LayoutInflater.from(context).inflate(R.layout.service_order_item_layout,null));

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOrderVH viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ServiceOrderVH extends RecyclerView.ViewHolder{

        TextView serviceName;
        ImageView serviceStateMark;
        SimpleDraweeView serviceIcon;
        TextView serviceMainTitle;
        TextView serviceDetail;
        TextView serviceSingleVal;
        TextView serviceTotalVal;
        Button payService;

        public ServiceOrderVH(@NonNull View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.service_name);
            serviceStateMark = itemView.findViewById(R.id.service_state_mark);
            serviceIcon = itemView.findViewById(R.id.service_icon);
            serviceMainTitle = itemView.findViewById(R.id.service_main_title);
            serviceDetail = itemView.findViewById(R.id.service_detail);
            serviceSingleVal = itemView.findViewById(R.id.service_single_val);
            serviceTotalVal = itemView.findViewById(R.id.service_total_val);
            payService = itemView.findViewById(R.id.pay_service);
        }
    }
}
