package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class OtherSelectionExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
//    private List<Map<String,String>> datasHeader;
//    private List<List<Map<String,String>>> datasChild;

    private String[] groups;
    private String[][] childs;

   public OtherSelectionExpandableListViewAdapter(Context context,String[] groups,String[][] childs){
       this.context = context;
       this.groups = groups;
       this.childs = childs;
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
       GroupViewHolder holder;
       if(convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.expandable_other_item_header_layout,null);
           holder = new GroupViewHolder();
           holder.otherItemSelecter = convertView.findViewById(R.id.other_item_selecter);
           holder.otherItemName = convertView.findViewById(R.id.other_item_name);
           holder.otherItemPrice = convertView.findViewById(R.id.other_item_price);

           convertView.setTag(holder);
       }else{
           holder = (GroupViewHolder) convertView.getTag();
       }

       holder.otherItemName.setText(groups[groupPosition]);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       ChildViewHolder holder;
       if(convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_child_layout,null);
           holder = new ChildViewHolder();

           holder.examTargetExpandable = convertView.findViewById(R.id.exam_target_expandable);
           holder.examTargetDetailExpandable = convertView.findViewById(R.id.exam_target_detail_expandable);


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

    public class GroupViewHolder{
        ImageView otherItemSelecter;
        TextView otherItemName;
        TextView otherItemPrice;
    }

    public class ChildViewHolder{
        TextView examTargetExpandable;
        TextView examTargetDetailExpandable;

    }
}
