package com.chengsheng.cala.htcm.module.activitys;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.adapter.ExamAppointmentRecyclerAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.data.repository.ComboRepository;
import com.chengsheng.cala.htcm.protocol.ExamApponitments;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DefaultObserver;


public class SearchActivity extends BaseActivity {

    private TagFlowLayout searchMarks;
    private TextView backText;
    private ImageView clearSearchBox;
    private EditText globalSearchBox;
    private RecyclerView searchRecord, comboSearchResult;
    private LinearLayout searchModelBox;

    private RecyclerAdapter adapter;
    private ExamAppointmentRecyclerAdapter examAdapter;//

    private String[] marks = new String[]{"青少年", "老年", "华美丽人", "中年", "加班族", "熬夜族", "优雅绅士", "婴幼儿"};
    private List<String> searchRecordData = new ArrayList<>();
    private String currentMark = "";//当前搜索的标签
    private String filtersPre = "price:between=;name:like=";//搜索条件的前缀

    private ZLoadingDialog loadingDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintText("搜索中...");
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setCancelable(false);

        initViews();

        String record = FuncUtils.getString("SEARCH_RECORD", "");
        if (!record.equals("")) {
            String[] strs = record.split(",");
            for (String d : strs) {
                searchRecordData.add(d);
            }
        }

        globalSearchBox.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!globalSearchBox.getText().toString().equals("")) {
                    currentMark = globalSearchBox.getText().toString();
                    searchRecordData.add(currentMark);
                    getSearchResult(currentMark);
                }
            }
        });


        final List<String> datas = new ArrayList<>();
        for (String mark : marks) {
            datas.add(mark);
        }
        TagAdapter tagAdapter = new TagAdapter(datas);
        searchMarks.setAdapter(tagAdapter);

        searchRecord.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter();
        searchRecord.setAdapter(adapter);

        searchMarks.setOnTagClickListener((view, position, parent) -> {
            currentMark = datas.get(position);

            getSearchResult(currentMark);

            if (!searchRecordData.contains(currentMark)) {
                searchRecordData.add(currentMark);
            }
            adapter.notifyDataSetChanged();
            return true;
        });

        backText.setOnClickListener(v -> finish());

    }

    @Override
    public void getData() {

    }

    //获取搜索的结果
    private void getSearchResult(String currentMark) {

        loadingDialog.show();
        ComboRepository
                .Companion
                .getDefault()
                .getComboInfoFilters(filtersPre + currentMark, "1", GlobalConstant.COMBO_TYPE_A)
                .subscribe(new DefaultObserver<ExamApponitments>() {
                    @Override
                    public void onNext(ExamApponitments examApponitments) {
                        loadingDialog.cancel();
                        searchModelBox.setVisibility(View.INVISIBLE);
                        examAdapter = new ExamAppointmentRecyclerAdapter(SearchActivity.this, examApponitments.getItems());
                        comboSearchResult.setVisibility(View.VISIBLE);
                        comboSearchResult.setAdapter(examAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        showError(e);
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

    private void initViews() {
        searchMarks = findViewById(R.id.search_marks);
        backText = findViewById(R.id.back_text);
        searchRecord = findViewById(R.id.search_record);
        globalSearchBox = findViewById(R.id.global_search_box);
        clearSearchBox = findViewById(R.id.clear_search_box);
        comboSearchResult = findViewById(R.id.combo_search_result);
        searchModelBox = findViewById(R.id.search_model_box);

        clearSearchBox.setOnClickListener(v -> {
            globalSearchBox.setText("");
            comboSearchResult.setVisibility(View.INVISIBLE);
            searchModelBox.setVisibility(View.VISIBLE);
        });
        comboSearchResult.setLayoutManager(new LinearLayoutManager(this));
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

            viewHolder.deleteSearchRecord.setOnClickListener(v -> removeData(i));
            viewHolder.hasSearchItem.setOnClickListener(v -> {
                currentMark = viewHolder.searchText.getText().toString();
                getSearchResult(currentMark);
            });

        }

        @Override
        public int getItemCount() {
            return searchRecordData.size();
        }

        public void removeData(int i) {
            searchRecordData.remove(i);
            notifyItemRemoved(i);
            notifyDataSetChanged();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView searchText;

        ImageView deleteSearchRecord;
        TextView textMark;
        RelativeLayout hasSearchItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            searchText = itemView.findViewById(R.id.search_text);
            deleteSearchRecord = itemView.findViewById(R.id.delete_search_record);
            hasSearchItem = itemView.findViewById(R.id.has_search_item);

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

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            View rootView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.families_select_mark_bg_layout, null);
            TextView textView = rootView.findViewById(R.id.select_families_mark);
            textView.setText(s);
            return rootView;
        }
    }

}
