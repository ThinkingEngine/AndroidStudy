package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;
import java.util.Map;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private Context context;
    private List<Map<String,String>> datas;

    public NewsRecyclerAdapter(Context context,List<Map<String,String>> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        NewsViewHolder holder = new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.recommedn_news_item_bg_layout,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder viewHolder, int i) {

        Map<String,String> data = datas.get(i);
        if(i == 0){
            viewHolder.newsItemTopLine.setVisibility(View.VISIBLE);
        }else{
            viewHolder.newsItemTopLine.setVisibility(View.INVISIBLE);
        }

        viewHolder.newsHeaderIcon.setImageResource(Integer.valueOf(data.get("ICON")));
        viewHolder.newsTitle.setText(data.get("TITLE"));
        viewHolder.browseNum.setText(data.get("BROWSE"));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout commendNewsItem;
        LinearLayout newsItemTopLine;
        ImageView newsHeaderIcon;
        TextView newsTitle;
        TextView browseNum;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            commendNewsItem = itemView.findViewById(R.id.commend_news_item);
            newsHeaderIcon = itemView.findViewById(R.id.news_header_icon);
            newsTitle = itemView.findViewById(R.id.news_title);
            browseNum = itemView.findViewById(R.id.browse_num);
        }
    }
}
