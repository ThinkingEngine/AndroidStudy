package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamItem;


import java.util.List;

public class ExamItemExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ExamItem> datas;

    public ExamItemExpandableListViewAdapter(Context context, List<ExamItem> datas) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_header_layout, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        if (groupPosition == datas.size() - 1) {
            groupViewHolder.examResultContainer.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        if (groupPosition == 0 && datas.size() > 1) {
            groupViewHolder.examResultContainer.setBackground(context.getResources().getDrawable(R.drawable.has_bottom_line_item_bg));
        }

//        groupViewHolder.examItemStatsExpandable.setVisibility(View.INVISIBLE);
        groupViewHolder.examItemNameExpandable.setText(datas.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_exam_item_child_layout, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.examTargetExpandable.setText("包含内容");

        childViewHolder.examTargetDetailExpandable.setText(datas.get(groupPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupViewHolder {
        RelativeLayout examResultContainer;
        TextView examItemNameExpandable;
        TextView examItemStatsExpandable;

        public GroupViewHolder(View convertView) {
            examResultContainer = convertView.findViewById(R.id.exam_result_container);
            examItemNameExpandable = convertView.findViewById(R.id.exam_item_name_expandable);
            examItemStatsExpandable = convertView.findViewById(R.id.exam_item_stats_expandable);
        }
    }

    public class ChildViewHolder {
        TextView examTargetExpandable;
        TextView examTargetDetailExpandable;


        public ChildViewHolder(View convertView) {
            examTargetExpandable = convertView.findViewById(R.id.exam_target_expandable);
            examTargetDetailExpandable = convertView.findViewById(R.id.exam_target_detail_expandable);

        }
    }
}
