package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class OrderDetailExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    //模拟数据
    private String[] groups = {"入职体检套餐","自选项目（3）"};
    private String[][] childs = {{"一般检查","耳鼻喉","心电图"},{"血常规","血压检查","肝功能二项"}};

    public OrderDetailExpandableListViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs[groupPosition][childPosition];
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
            vh.orderDetailPayStateMark = convertView.findViewById(R.id.order_detail_pay_state_mark);

            convertView.setTag(vh);
        }else{
            vh = (GroupVH) convertView.getTag();
        }

        vh.orderDetailItemName.setText(groups[groupPosition]);
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

        vh.childOrderItemName.setText(childs[groupPosition][childPosition]);
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
