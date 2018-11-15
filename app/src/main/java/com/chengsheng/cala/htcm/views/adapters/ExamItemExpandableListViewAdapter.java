package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExamItemExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
//    private List<Map<String,String>> datasHeader;
//    private List<List<Map<String,String>>> datasChild;
    private List<ExamItem> datas;

    String[] a;
    String[][] b;


    public ExamItemExpandableListViewAdapter(Context context, List<ExamItem> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getGroupCount() {
        return datas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datas.get(groupPosition).getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition);
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
        GroupViewHolder groupViewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_header_layout,null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.examItemStatsExpandable.setVisibility(View.INVISIBLE);
//        Log.e("TEST",);
        groupViewHolder.examItemNameExpandable.setText(datas.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_child_layout,null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.examTargetDetailExpandable.setText(datas.get(groupPosition).getExam_purpose());
        childViewHolder.examNotesDetailExpandable.setText(datas.get(groupPosition).getPrecautions());
        childViewHolder.examIncludeDetailExpandable.setText(datas.get(groupPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupViewHolder{
        TextView examItemNameExpandable;
        TextView examItemStatsExpandable;

        public GroupViewHolder(View convertView){
            examItemNameExpandable = convertView.findViewById(R.id.exam_item_name_expandable);
            examItemStatsExpandable = convertView.findViewById(R.id.exam_item_stats_expandable);
        }
    }

    public class ChildViewHolder{
        TextView examTargetExpandable;
        TextView examTargetDetailExpandable;
        TextView examNotesExpandable;
        TextView examNotesDetailExpandable;
        TextView examIncludeExpandable;
        TextView examIncludeDetailExpandable;

        public ChildViewHolder(View convertView){
            examTargetExpandable = convertView.findViewById(R.id.exam_target_expandable);
            examTargetDetailExpandable = convertView.findViewById(R.id.exam_target_detail_expandable);
            examNotesExpandable = convertView.findViewById(R.id.exam_notes_expandable);
            examNotesDetailExpandable = convertView.findViewById(R.id.exam_notes_detail_expandable);
            examIncludeExpandable = convertView.findViewById(R.id.exam_include_expandable);
            examIncludeDetailExpandable = convertView.findViewById(R.id.exam_include_detail_expandable);
        }
    }
}
