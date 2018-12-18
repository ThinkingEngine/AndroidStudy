package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.PackageTag;
import com.chengsheng.cala.htcm.widget.TextViewBorder;

import java.util.List;

public class TagBaseAdapter extends BaseAdapter {
    private Context context;
    private List<PackageTag> datas;

    public TagBaseAdapter(Context context,List<PackageTag> datas){
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
        View rootView = LayoutInflater.from(context).inflate(R.layout.tag_text_item_layout,null);
        PackageTag data = datas.get(position);
        TextViewBorder textViewBorder = rootView.findViewById(R.id.tag_text);
        textViewBorder.setText(data.getName());
        textViewBorder.setTextColor(Color.parseColor(data.getColor()));
        textViewBorder.setBorderColor(Color.parseColor(data.getColor()));

        return rootView;
    }
}
