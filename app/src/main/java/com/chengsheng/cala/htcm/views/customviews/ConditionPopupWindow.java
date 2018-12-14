package com.chengsheng.cala.htcm.views.customviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConditionPopupWindow extends PopupWindow {

    private Context context;
    private List<Map<String, String>> datas, selectedData;

    private ListView listView;


    public ConditionPopupWindow(Context context, List<Map<String, String>> datas) {
        super(context);
        this.context = context;
        this.datas = datas;
        selectedData = new ArrayList<>();
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.condition_screening_model_a_layout, null);

        listView = rootView.findViewById(R.id.condition_screening_list);
        listView.setAdapter(new MyBaseAdapter(datas));
        setContentView(rootView);

        this.setOutsideTouchable(true);
        this.setTouchable(true);

        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(dw);
//        backgroundAlpha((Activity)context,0.8f);
//        this.setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha((Activity)context,1f);
//            }
//        });
    }

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public class MyBaseAdapter extends BaseAdapter {
        private List<Map<String, String>> datas;

        public MyBaseAdapter(List<Map<String, String>> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            if (datas.isEmpty()) {
                return 2;
            } else {
                return datas.size() + 1;
            }

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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            TextView selectConditionText;
            final ImageView selectMark;
            TextView okButton;
            RelativeLayout selectItemBg;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.condition_screening_item_layout, null);
            }

            selectConditionText = convertView.findViewById(R.id.select_condition_text);
            selectMark = convertView.findViewById(R.id.select_mark);
            okButton = convertView.findViewById(R.id.ok_button);
            selectItemBg = convertView.findViewById(R.id.select_item_bg);


            if (!datas.isEmpty()) {
                if (position < datas.size()) {
                    Map<String, String> map = datas.get(position);
                    if (map.get("SELECT").equals("true")) {
                        selectMark.setSelected(true);
                    } else if (map.get("SELECT").equals("false")) {
                        selectMark.setSelected(false);
                    }
                    selectConditionText.setText(map.get("DATA"));
                }

                if (position == (datas.size())) {
                    okButton.setVisibility(View.VISIBLE);
                    selectConditionText.setVisibility(View.INVISIBLE);
                    selectMark.setVisibility(View.INVISIBLE);
                } else if (position == 0) {
                    okButton.setVisibility(View.INVISIBLE);
                    selectConditionText.setVisibility(View.VISIBLE);
                    selectMark.setVisibility(View.VISIBLE);
                    selectConditionText.setText("全部");
                } else {
                    okButton.setVisibility(View.INVISIBLE);
                    selectConditionText.setVisibility(View.VISIBLE);
                    selectMark.setVisibility(View.VISIBLE);

                }
            } else {
                if (position == 0) {
                    okButton.setVisibility(View.INVISIBLE);
                    selectConditionText.setVisibility(View.VISIBLE);
                    selectConditionText.setText("无数据");
                    selectMark.setVisibility(View.INVISIBLE);
                } else {
                    okButton.setVisibility(View.VISIBLE);
                    selectConditionText.setVisibility(View.INVISIBLE);
                    selectMark.setVisibility(View.INVISIBLE);
                }
            }


            selectItemBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectMark.isSelected()) {
                        selectMark.setSelected(false);
                        if (position == 0) {
                            for (int i = 0; i < datas.size(); i++) {
                                datas.get(i).put("SELECT", "false");
                                selectedData.clear();
                            }
                            notifyDataSetChanged();
                        } else if (position != 0 && position < datas.size()) {
                            if (datas.get(0).get("SELECT").equals("true")) {
                                datas.get(0).put("SELECT", "false");
                                datas.get(position).put("SELECT", "false");
                                notifyDataSetChanged();
                            }else{
                                datas.get(position).put("SELECT", "false");
                                notifyDataSetChanged();
                            }
                            selectedData.remove(datas.get(position));
                        }
                    } else {
                        selectMark.setSelected(true);
                        if (position == 0) {
                            for (int i = 0; i < datas.size(); i++) {
                                datas.get(i).put("SELECT", "true");
                                selectedData.clear();
                                selectedData.addAll(datas);
                            }
                            notifyDataSetChanged();
                        } else if (position != 0 && position < datas.size()) {
                            selectedData.add(datas.get(position));
                            if (selectedData.size() == (datas.size() - 1)) {
                                for (int i = 0; i < datas.size(); i++) {
                                    datas.get(i).put("SELECT", "true");
                                }
                                notifyDataSetChanged();
                            }
                        }
                    }


                }
            });

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBackDataAuth.doUpdateConditionInterface(selectedData, true);
                    selectedData.clear();
                    dismiss();
                }
            });


            return convertView;
        }

    }

}
