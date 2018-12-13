package com.chengsheng.cala.htcm.views.customviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.RecommendedItem;
import com.chengsheng.cala.htcm.views.activitys.NewsDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;


public class FooterAdapter extends RecyclerView.Adapter<FooterAdapter.NewsViewHolder> {

    private RecyclerView mRecyclerView;

    private List<RecommendedItem> datas;
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    public FooterAdapter(List<RecommendedItem> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new NewsViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new NewsViewHolder(VIEW_HEADER);
        } else {
            return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recommedn_news_item_bg_layout, null, false));
        }
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            final RecommendedItem data = datas.get(position);
            holder.newsHeaderIcon.setImageURI(data.getCover_photo_path());
            holder.newsTitle.setText(data.getTitle());
            holder.browseNum.setText(String.valueOf(data.getBasic_read_num()));
            List<String> tags = new ArrayList<>();
            tags.add(data.getArticle_type_name());
            holder.newsMarks.setTags(tags);
            holder.collectNum.setText(String.valueOf(data.getCurrent_collected_num()));

            holder.commendNewsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,NewsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("NEWS_DETAIL",data);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ? ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        TextView recyclerFooter;

        public FooterHolder(@NonNull View itemView) {
            super(itemView);

            recyclerFooter = itemView.findViewById(R.id.recycler_footer);
        }
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout commendNewsItem;
        LinearLayout newsItemTopLine;
        SimpleDraweeView newsHeaderIcon;
        TextView newsTitle;
        TextView browseNum;
        TextView collectNum;
        TagGroup newsMarks;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            commendNewsItem = itemView.findViewById(R.id.commend_news_item);
            newsHeaderIcon = itemView.findViewById(R.id.news_header_icon);
            newsTitle = itemView.findViewById(R.id.news_title);
            browseNum = itemView.findViewById(R.id.browse_num);
            newsMarks = itemView.findViewById(R.id.news_marks);
            collectNum = itemView.findViewById(R.id.collect_num);
        }
    }

}
