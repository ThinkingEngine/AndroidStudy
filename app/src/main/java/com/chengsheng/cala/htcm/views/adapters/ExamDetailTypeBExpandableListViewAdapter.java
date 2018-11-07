package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class ExamDetailTypeBExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private String[] group = {"白细胞计数(10^g/L)","总蛋白(g/L)","总蛋白(g/L)"};
    private String[][] child = {{"白细胞总数"},{"白细胞总数偏高多见于急性化脓性感染、尿毒症、白血病、组织损伤、"},{"多见于急性化脓性感"}};

    public ExamDetailTypeBExpandableListViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child[groupPosition][childPosition];
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
        GroupViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_b_header_layout,null);
            holder = new GroupViewHolder();
            holder.floatImage = convertView.findViewById(R.id.float_image);
            holder.floatValue = convertView.findViewById(R.id.float_value);
            holder.itemUnit = convertView.findViewById(R.id.item_unit);
            holder.warningMark = convertView.findViewById(R.id.warning_mark);
            holder.floatMark = convertView.findViewById(R.id.float_mark);

            convertView.setTag(holder);
        }else{
            holder = (GroupViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_detail_item_type_b_child_layout,null);
            holder = new ChildViewHolder();
            holder.itemAdvices = convertView.findViewById(R.id.item_advices);
            holder.itemExplain = convertView.findViewById(R.id.item_explain);
            holder.checkAllAdvice = convertView.findViewById(R.id.check_all_advice);

            convertView.setTag(holder);
        }else{
            holder = (ChildViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class GroupViewHolder {
        TextView itemUnit;
        ImageView warningMark;
        LinearLayout floatMark;
        TextView floatValue;
        ImageView floatImage;
    }

    public class ChildViewHolder{
        TextView itemExplain;
        TextView itemAdvices;
        TextView checkAllAdvice;
    }
}
