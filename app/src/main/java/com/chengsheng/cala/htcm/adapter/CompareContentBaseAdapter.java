package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.ExamReportModel.SingleItem;

import java.util.List;

public class CompareContentBaseAdapter extends BaseAdapter {

    private Context context;
    private List<SingleItem> datas;

    public CompareContentBaseAdapter(Context context, List<SingleItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CompareReportVH vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.compare_child_item_layout, null);
            vh = new CompareReportVH();
            vh.compareItemHeaderA = convertView.findViewById(R.id.compare_item_header_a);
            vh.compareItemHeaderB = convertView.findViewById(R.id.compare_item_header_b);
            vh.compareItemHeaderC = convertView.findViewById(R.id.compare_item_header_c);
            vh.compareTitleABox = convertView.findViewById(R.id.compare_title_a_box);
            vh.compareTitleBBox = convertView.findViewById(R.id.compare_title_b_box);
            vh.compareTitleCBox = convertView.findViewById(R.id.compare_title_c_box);
            vh.compareItemAMark = convertView.findViewById(R.id.compare_item_a_mark);
            vh.compareItemBMark = convertView.findViewById(R.id.compare_item_b_mark);
            vh.compareItemCMark = convertView.findViewById(R.id.compare_item_c_mark);
            vh.compareItemAText = convertView.findViewById(R.id.compare_item_a_text);
            vh.compareItemBText = convertView.findViewById(R.id.compare_item_b_text);
            vh.compareItemCText = convertView.findViewById(R.id.compare_item_c_text);
            vh.compareItemAUnit = convertView.findViewById(R.id.compare_item_a_unit);
            vh.compareItemBUnit = convertView.findViewById(R.id.compare_item_b_unit);
            vh.compareItemCUnit = convertView.findViewById(R.id.compare_item_c_unit);

            convertView.setTag(vh);
        } else {
            vh = (CompareReportVH) convertView.getTag();
        }

        SingleItem data = datas.get(position);

        vh.compareItemHeaderB.setText(data.getFirst().getExamine_saw());
        vh.compareItemHeaderC.setText(data.getSecond().getExamine_saw());
        vh.compareItemHeaderA.setText(data.getName());

        if (data.getType() == 2) {
            vh.compareItemAUnit.setVisibility(View.VISIBLE);
            vh.compareItemBUnit.setVisibility(View.VISIBLE);
            vh.compareItemCUnit.setVisibility(View.VISIBLE);
            vh.compareItemAUnit.setText("(" + data.getFirst().getLow_limit() + "~" + data.getFirst().getUpper_limit() + ")");
            vh.compareItemBUnit.setText("(" + data.getFirst().getLow_limit() + "~" + data.getFirst().getUpper_limit() + ")");
            vh.compareItemCUnit.setText("(" + data.getFirst().getLow_limit() + "~" + data.getFirst().getUpper_limit() + ")");

            vh.compareItemAUnit.setText(data.getMeasure_unit());
        }
        if (data.getFirst().isIs_exception()) {
            vh.compareTitleCBox.setVisibility(View.VISIBLE);
            if (data.getFirst().getException_type() == 1) {
                vh.compareItemCMark.setSelected(false);
            } else if (data.getFirst().getException_type() == 2) {
                vh.compareItemCMark.setSelected(true);
            }
        } else {
            vh.compareTitleBBox.setVisibility(View.INVISIBLE);
        }

        if (data.getSecond().isIs_exception()) {
            vh.compareTitleCBox.setVisibility(View.VISIBLE);
            vh.compareItemCText.setText(data.getSecond().getExamine_saw());
            if (data.getSecond().getException_type() == 1) {
                vh.compareItemCMark.setSelected(true);
            } else if (data.getSecond().getException_type() == 2) {
                vh.compareItemCMark.setSelected(false);
            }
        }


        return convertView;
    }

    public class CompareReportVH {

        TextView compareItemHeaderA, compareItemHeaderB, compareItemHeaderC;
        LinearLayout compareTitleABox, compareTitleBBox, compareTitleCBox;
        ImageView compareItemAMark, compareItemBMark, compareItemCMark;
        TextView compareItemAText, compareItemBText, compareItemCText;
        TextView compareItemAUnit, compareItemBUnit, compareItemCUnit;
    }
}
