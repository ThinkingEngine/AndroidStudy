package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class CompareItemBaseAdapter extends BaseAdapter {

    private Context context;


    public CompareItemBaseAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position < 3){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_text_layout,null);
            TextView textView = convertView.findViewById(R.id.text_mark);

            if(position == 0){
                textView.setBackgroundColor(context.getResources().getColor(R.color.colorItemBg));
                textView.setText("甲胎蛋白定性");
            }

        }else{
            convertView = LayoutInflater.from(context).inflate(R.layout.compare_child_item_layout,null);
            ImageView indexMark = convertView.findViewById(R.id.index_mark);
            TextView  index = convertView.findViewById(R.id.index);
            TextView indexScope = convertView.findViewById(R.id.index_scope);
            RelativeLayout compareElementBg = convertView.findViewById(R.id.compare_element_bg);

            if(position % 3 == 0 && position != 0){
                compareElementBg.setBackground(context.getDrawable(R.color.colorItemBg));
            }
        }
        return convertView;
    }
}
