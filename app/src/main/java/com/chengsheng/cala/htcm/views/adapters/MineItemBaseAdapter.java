package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.activitys.FAQActivity;
import com.chengsheng.cala.htcm.views.activitys.MyDevicesActivity;
import com.chengsheng.cala.htcm.views.activitys.SettingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineItemBaseAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,String>> dataList;

    public MineItemBaseAdapter(Context context,List<Map<String,String>> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.mine_linst_item_layout,null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.mine_list_item_icon);
            holder.title = (TextView) convertView.findViewById(R.id.mine_list_item_text);
            holder.input = (ImageView) convertView.findViewById(R.id.input_mine_item_icon);
            holder.cellphone = (TextView) convertView.findViewById(R.id.service_cellphone_num);
            holder.bottom = (LinearLayout) convertView.findViewById(R.id.mine_item_bottom_cut_off);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Map<String,String> data =dataList.get(position);
        if(position != (dataList.size())){
            holder.icon.setImageResource(Integer.valueOf(data.get("ICON")));
            holder.title.setText(data.get("TITLE"));
        }

        if(data.get("TYPE").equals("TEL")){
            holder.cellphone.setText("028-05056666");
            holder.input.setVisibility(View.INVISIBLE);
        }else{
            holder.cellphone.setVisibility(View.INVISIBLE);
        }

        if(position == dataList.size()-1){
            holder.bottom.setVisibility(View.INVISIBLE);
        }

        holder.input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get("TITLE").equals("设置")){
                    Intent intent = new Intent(context,SettingActivity.class);
                    context.startActivity(intent);
                }else if(data.get("TITLE").equals("常见问题")){
                    Intent intent = new Intent(context,FAQActivity.class);
                    context.startActivity(intent);
                }else if(data.get("TITLE").equals("我的设备")){
                    Intent intent = new Intent(context,MyDevicesActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }



    public static class ViewHolder{
        public ImageView icon;
        public TextView title;
        public ImageView input;
        public TextView cellphone;
        public LinearLayout bottom;
    }
}
