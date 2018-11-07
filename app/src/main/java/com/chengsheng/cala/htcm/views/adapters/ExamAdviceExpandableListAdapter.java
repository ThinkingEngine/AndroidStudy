package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.chengsheng.cala.htcm.R;

public class ExamAdviceExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    public ExamAdviceExpandableListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return 1;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return 1;
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_advice_header_layout,null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if(convertView == null){
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_advice_child_layout,null);
            holder.docSignatureMark = convertView.findViewById(R.id.doc_signature_mark);

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

    public class HeaderViewHolder{

    }

    public class ChildViewHolder{
        ImageView docSignatureMark;
    }
}
