package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodela.ExamReportDetial;
import com.chengsheng.cala.htcm.protocol.childmodela.HAItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ExamAdviceExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ExamReportDetial item;

    public ExamAdviceExpandableListAdapter(Context context, ExamReportDetial item) {
        this.context = context;
        this.item = item;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_advice_header_layout, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_advice_child_layout, null);
            holder.docSignatureMark = convertView.findViewById(R.id.doc_signature_mark);
            holder.docAdvices = convertView.findViewById(R.id.doc_advices);

            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }


        StringBuffer sb = new StringBuffer();
        List<HAItem> items = item.getAdvice().getItems();
        for (int i = 0; i < items.size(); i++) {
            sb.append((i + 1) + ",");
            sb.append(items.get(i).getDisease_name());
            if (items.get(i).getHealthy_advice() != null) {
                sb.append("\n" + items.get(i).getHealthy_advice() + "\n");
            }else{
                sb.append("\n");
            }

        }

        holder.docAdvices.setText(sb.toString());
        holder.docSignatureMark.setImageURI(item.getDoctor_sign());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class HeaderViewHolder {

    }

    public class ChildViewHolder {
        TextView docAdvices;
        SimpleDraweeView docSignatureMark;
    }
}
