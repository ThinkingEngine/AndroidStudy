package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.ViewsUtils;
import com.chengsheng.cala.htcm.views.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.views.dialog.ImmediatelyDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FMRecyclerAdapter extends RecyclerView.Adapter<FMRecyclerAdapter.FMRViewHolder> {

    private List<Map<String,String>> datas;
    private Context context;

    public FMRecyclerAdapter(Context context,List<Map<String,String>> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public FMRViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FMRViewHolder holder = new FMRViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item_layout,viewGroup,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FMRViewHolder viewHolder, final int i) {

        Map<String,String> data = datas.get(i);

        //临时测试数据（测试完成后删除）
        Map<String,String> data_a = new HashMap<>();
        Map<String,String> data_b = new HashMap<>();
        Map<String,String> data_c = new HashMap<>();
        Map<String,String> data_d = new HashMap<>();
        data_a.put("NAME","体检报告");
        data_b.put("NAME","体检报告");
        data_c.put("NAME","家庭医生");
        data_d.put("NAME","基因检测");
        List<Map<String,String>> a = new ArrayList<>();
        List<Map<String,String>> b = new ArrayList<>();
        a.add(data_a);
        b.add(data_b);
        b.add(data_c);
        b.add(data_d);

        if(data.get("VERIFY").equals("FALSE")){
            viewHolder.peopleID.setVisibility(View.INVISIBLE);
            viewHolder.qcCode.setVisibility(View.INVISIBLE);
            viewHolder.immediatelyCertification.setVisibility(View.VISIBLE);
            viewHolder.immediatelyCertification.setEnabled(true);
            viewHolder.qcCode.setEnabled(false);
            viewHolder.peopleID.setText(data.get("USER_ID"));
        }else{
            viewHolder.peopleID.setVisibility(View.VISIBLE);
            viewHolder.qcCode.setVisibility(View.VISIBLE);
            viewHolder.immediatelyCertification.setVisibility(View.INVISIBLE);
            viewHolder.immediatelyCertification.setEnabled(false);
            viewHolder.qcCode.setEnabled(true);

            PeopleInfoRecyclerAdapter pa;
            if(data.get("NAME").equals("王树彤")){
                 pa = new PeopleInfoRecyclerAdapter(context,a);
            }else if(data.get("NAME").equals("王树同")){
                 pa = new PeopleInfoRecyclerAdapter(context,b);
            }else{
                pa = null;
            }
            viewHolder.chileList.setLayoutManager(new LinearLayoutManager(context));
            viewHolder.chileList.setAdapter(pa);
        }


        viewHolder.peopleName.setText(data.get("NAME"));
        viewHolder.peopleMark.setText(data.get("MARK"));
        final ImmediatelyDialogView immediatelyDialogView = new ImmediatelyDialogView(context,1);

        viewHolder.qcCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserCardActivity.class);
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

    }

    @Override
    public int getItemCount() {
        //返回数据长度
        return datas.size();
    }

    public class FMRViewHolder extends RecyclerView.ViewHolder{
        CircleImageView peopleIcon;
        TextView peopleName;
        Button peopleMark;
        Button immediatelyCertification;
        ImageView qcCode;
        TextView peopleID;
        RecyclerView chileList;

        public FMRViewHolder(@NonNull View itemView) {
            super(itemView);

            peopleIcon = itemView.findViewById(R.id.people_icon);
            peopleName = itemView.findViewById(R.id.people_name_text);
            peopleMark = itemView.findViewById(R.id.people_mark);
            immediatelyCertification = itemView.findViewById(R.id.immediately_certification_button);
            qcCode = itemView.findViewById(R.id.people_qr_code_mark);
            peopleID = itemView.findViewById(R.id.people_id);
            chileList = itemView.findViewById(R.id.people_has_info);
        }
    }


    public boolean addItem(int position, Map<String,String> msg) {
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
