package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamItem;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamPackages;


import java.util.List;

public class OrderDetailExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ExamPackages> datas;


    public OrderDetailExpandableListViewAdapter(Context context,List<ExamPackages> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getGroupCount() {
        return datas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas.get(groupPosition).getExam_item_charges().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition).getExam_item_charges().get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.order_detail_expandable_header_layout,null);
            vh = new GroupVH();
            vh.orderDetailItemName = convertView.findViewById(R.id.order_detail_item_name);
            vh.orderDetailItemValue = convertView.findViewById(R.id.order_detail_item_value);

            convertView.setTag(vh);
        }else{
            vh = (GroupVH) convertView.getTag();
        }

        ExamPackages data = datas.get(groupPosition);
        vh.orderDetailItemName.setText(data.getName());
        if(groupPosition == 0){
            vh.orderDetailPayStateMark.setVisibility(View.VISIBLE);
        }else{
            vh.orderDetailPayStateMark.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildVH vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_order_form_child_item_layout,null);
            vh = new ChildVH();
            vh.childOrderItemName = convertView.findViewById(R.id.child_order_item_name);
            vh.childOrderItemValue = convertView.findViewById(R.id.child_order_item_value);

            convertView.setTag(vh);
        }else{
            vh = (ChildVH) convertView.getTag();
        }

        ExamItem data = datas.get(groupPosition).getExam_item_charges().get(childPosition);
        vh.childOrderItemName.setText(data.getName());
        if(data.isPayment_status()){
            vh.childOrderItemValue.setText("已付");
            vh.childOrderItemValue.setTextColor(context.getResources().getColor(R.color.colorText));
        }else{
            vh.childOrderItemValue.setText("未付");
            vh.childOrderItemValue.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class GroupVH{
        TextView orderDetailItemName;
        ImageView orderDetailPayStateMark;
        TextView orderDetailItemValue;
    }

    public class ChildVH{
        TextView childOrderItemName;
        TextView childOrderItemValue;
    }
}
