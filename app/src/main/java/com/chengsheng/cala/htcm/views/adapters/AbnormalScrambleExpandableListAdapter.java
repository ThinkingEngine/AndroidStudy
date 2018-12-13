package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class AbnormalScrambleExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;


    public AbnormalScrambleExpandableListAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupVH vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.abnormal_unscramble_header_layout,null);
            vh = new GroupVH();
            vh.abnormalName = convertView.findViewById(R.id.abnormal_name);
            vh.abnormalMark = convertView.findViewById(R.id.abnormal_mark);
            vh.abnormalNameUnit = convertView.findViewById(R.id.abnormal_name_unit);
            vh.abnormalNum = convertView.findViewById(R.id.abnormal_num);
            vh.abnormalWarn = convertView.findViewById(R.id.abnormal_warn);

            convertView.setTag(vh);
        }else {
            vh = (GroupVH) convertView.getTag();
        }

        if(groupPosition == 1){
            vh.abnormalName.setText("肝脏触及触及");
            vh.abnormalNameUnit.setVisibility(View.INVISIBLE);
            vh.abnormalMark.setVisibility(View.INVISIBLE);
            vh.abnormalNum.setVisibility(View.INVISIBLE);
        }
        if(groupPosition == 2){
            vh.abnormalWarn.setVisibility(View.INVISIBLE);
            vh.abnormalNameUnit.setVisibility(View.VISIBLE);
            vh.abnormalMark.setVisibility(View.VISIBLE);
            vh.abnormalNum.setVisibility(View.VISIBLE);
            vh.abnormalName.setText("白细胞计数");
            vh.abnormalMark.setSelected(true);
            vh.abnormalNum.setText("20.9");
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AbnormalScrambleExpandableListAdapter.ChildViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout,null);
            holder = new AbnormalScrambleExpandableListAdapter.ChildViewHolder();
            holder.itemAdvices = convertView.findViewById(R.id.item_advices);
            holder.itemExplain = convertView.findViewById(R.id.item_explain);
            holder.checkAllAdvice = convertView.findViewById(R.id.check_all_advice);

            convertView.setTag(holder);
        }else{
            holder = (AbnormalScrambleExpandableListAdapter.ChildViewHolder)convertView.getTag();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupVH{
        TextView abnormalName;
        TextView abnormalNameUnit;
        TextView abnormalWarn;
        TextView abnormalNum;
        ImageView abnormalMark;
    }

    public class ChildViewHolder{
        TextView itemExplain;
        TextView itemAdvices;
        TextView checkAllAdvice;
    }
}
