package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.customviews.MyGridView;

import java.util.List;

public class CompareReportRecyclerAdapter extends RecyclerView.Adapter<CompareReportRecyclerAdapter.CompareReportVH> {
    private int TYPE = 0;
    private Context context;
    private List<String> dataList;

    public CompareReportRecyclerAdapter(Context context,int TYPE,List<String> dataList){
        this.context = context;
        this.TYPE = TYPE;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public CompareReportVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CompareReportVH vh = new CompareReportVH(LayoutInflater.from(context).inflate(R.layout.compare_table_item_layout,null));

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CompareReportVH viewHolder, int i) {
        if(TYPE != 0){
            viewHolder.compareItemTitleContainer.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.compareItemTitleContainer.setVisibility(View.VISIBLE);
            viewHolder.compareItemTitle.setText(dataList.get(i));
        }
        CompareItemBaseAdapter adapter = new CompareItemBaseAdapter(context);

        viewHolder.compareTable.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if(TYPE == 0){
            return dataList.size();
        }else{
            return 1;
        }

    }

    public class CompareReportVH extends RecyclerView.ViewHolder{
        RelativeLayout compareItemTitleContainer;
        TextView compareItemTitle;
        MyGridView compareTable;

        public CompareReportVH(@NonNull View itemView) {
            super(itemView);

            compareItemTitleContainer = itemView.findViewById(R.id.compare_item_title_container);
            compareItemTitle = itemView.findViewById(R.id.compare_item_title);
            compareTable = itemView.findViewById(R.id.compare_table);
        }
    }


}
