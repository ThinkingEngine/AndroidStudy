package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;
import java.util.Map;

public class ExamItemExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Map<String,String>> datasHeader;
    private List<List<Map<String,String>>> datasChild;

    //临时数据
    String[] a = new String[]{"a","b","c","d","e"};
    String[][] b = new String[][]{{"a"},{"b"},{"c"},{"d"},{"e"}};


    public ExamItemExpandableListViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return a.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return b[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return a[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return b[groupPosition][childPosition];
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
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.examItemNameExpandable = convertView.findViewById(R.id.exam_item_expandable);
            groupViewHolder.examItemStatsExpandable = convertView.findViewById(R.id.exam_item_stats_expandable);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_child_layout,null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.examTargetExpandable = convertView.findViewById(R.id.exam_target_expandable);
            childViewHolder.examTargetDetailExpandable = convertView.findViewById(R.id.exam_target_detail_expandable);
            childViewHolder.examNotesExpandable = convertView.findViewById(R.id.exam_notes_expandable);
            childViewHolder.examNotesDetailExpandable = convertView.findViewById(R.id.exam_notes_detail_expandable);
            childViewHolder.examIncludeExpandable = convertView.findViewById(R.id.exam_include_expandable);
            childViewHolder.examIncludeDetailExpandable = convertView.findViewById(R.id.exam_include_detail_expandable);

            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupViewHolder{
        TextView examItemNameExpandable;
        TextView examItemStatsExpandable;

    }

    public class ChildViewHolder{
        TextView examTargetExpandable;
        TextView examTargetDetailExpandable;
        TextView examNotesExpandable;
        TextView examNotesDetailExpandable;
        TextView examIncludeExpandable;
        TextView examIncludeDetailExpandable;

    }
}
