package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;

import java.util.List;
import java.util.Map;

public class OtherSelectionItemAdapter extends RecyclerView.Adapter<OtherSelectionItemAdapter.OtherSelectionItemViewHolder> {
    private  Context context;
    private List<Map<String,String>> datas;

    private OtherSelectionExpandableListViewAdapter adapter;
    //临时数据.
    String[] groupa = new String[]{"甲状腺彩超"};
    String[][] childsa = new String[][]{{"a"}};
    String[] groupb = new String[]{"神经元特异性烯醇化酶","癌胚抗原定量(CEA)","细胞角蛋白(Cyfra21-1)","癌胚抗原定量(CEA)"};
    String[][] childsb = new String[][]{{"a"},{"a"},{"a"},{"a"}};

    public OtherSelectionItemAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public OtherSelectionItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OtherSelectionItemViewHolder holder = new OtherSelectionItemViewHolder(LayoutInflater.from(context).inflate(R.layout.other_select_item_bg_layout,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OtherSelectionItemViewHolder viewHolder, int i) {

        if(i == 0){
            adapter = new OtherSelectionExpandableListViewAdapter(context,groupa,childsa);
            viewHolder.otherItemExpandable.setAdapter(adapter);
        }else{
            adapter = new OtherSelectionExpandableListViewAdapter(context,groupb,childsb);
            viewHolder.otherItemExpandable.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class OtherSelectionItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemGenericTerm;
        TextView detailsRules;
        MyExpandableListView otherItemExpandable;

        public OtherSelectionItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemGenericTerm = itemView.findViewById(R.id.item_generic_term);
            detailsRules = itemView.findViewById(R.id.details_rules);
            otherItemExpandable = itemView.findViewById(R.id.other_item_expandable);
        }
    }
}
