package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private TagFlowLayout searchMarks;
    private TextView backText;
    private ImageView clearSearchBox;
    private EditText globalSearchBox;
    private RecyclerView searchRecord;

    private String[] marks = new String[]{"青少年", "老年", "华美丽人", "中年", "加班族", "熬夜族", "优雅绅士", "婴幼儿"};
    private List<String> searchRecordData = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        searchMarks = findViewById(R.id.search_marks);
        backText = findViewById(R.id.back_text);
        searchRecord = findViewById(R.id.search_record);
        globalSearchBox = findViewById(R.id.global_search_box);
        clearSearchBox = findViewById(R.id.clear_search_box);

        String record = FuncUtils.getString("SEARCH_RECORD", "");
        if (!record.equals("")) {
            String[] strs = record.split(",");
            for (String d : strs) {
                searchRecordData.add(d);
            }
        }

        clearSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchBox.setText("");
            }
        });


        globalSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    searchRecordData.add(s.toString());
                }
            }
        });

        final List<String> datas = new ArrayList<>();
        for (String mark : marks) {
            datas.add(mark);
        }
        TagAdapter adapter = new TagAdapter(datas);
        searchMarks.setAdapter(adapter);

        searchRecord.setLayoutManager(new LinearLayoutManager(this));
        searchRecord.setAdapter(new RecyclerAdapter());

        searchMarks.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(SearchActivity.this, " " + datas.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void getData() {

    }

    class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            MyViewHolder viewHolder;
            if (searchRecordData.isEmpty()) {
                viewHolder = new MyViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.single_text_layout, null));
            } else {
                viewHolder = new MyViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_record_item_layout, null));
            }

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

            if (searchRecordData.isEmpty()) {
                viewHolder.textMark.setText("你还没有搜索记录的哟");
            } else {
                viewHolder.searchText.setText(searchRecordData.get(i));
            }
        }

        @Override
        public int getItemCount() {
            return searchRecordData.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView searchText;

        ImageView deleteSearchRecord;
        TextView textMark;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            searchText = itemView.findViewById(R.id.search_text);
            deleteSearchRecord = itemView.findViewById(R.id.delete_search_record);

            textMark = itemView.findViewById(R.id.text_mark);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StringBuffer sb = new StringBuffer();
        for (String str : searchRecordData) {
            if (str.equals(searchRecordData.size())) {
                sb.append(str);
            } else {
                sb.append(str + ",");
            }

        }
        FuncUtils.putString("SEARCH_RECORD", sb.toString());
    }

    class TagAdapter extends com.zhy.view.flowlayout.TagAdapter<String> {


        public TagAdapter(List<String> datas) {
            super(datas);
        }

//        @Override

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            View rootView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.families_select_mark_bg_layout, null);
            TextView textView = rootView.findViewById(R.id.select_families_mark);
            textView.setText(s);
            return rootView;
        }
        //        }
    }
    //        public void onSelected(int position, View view) {
    //            TextView textView = view.findViewById(R.id.select_families_mark);
    //            textView.setSelected(true);
    //            textView.setTextColor(SearchActivity.this.getResources().getColor(R.color.colorWhite));
    //        }
    //
    //        @Override
    //        public void unSelected(int position, View view) {
    //            TextView textView = view.findViewById(R.id.select_families_mark);
    //            textView.setSelected(false);
    //            textView.setTextColor(SearchActivity.this.getResources().getColor(R.color.colorThrText));
}
