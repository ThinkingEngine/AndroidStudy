package com.chengsheng.cala.htcm.views.customviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.ArrayList;
import java.util.List;

public class ConditionPopupWindow extends PopupWindow {

    private Context context;
    private ListView listView;

    public ConditionPopupWindow(Context context){
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.condition_screening_model_a_layout,null);

        listView = rootView.findViewById(R.id.condition_screening_list);
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("T");
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

    public class MyBaseAdapter extends BaseAdapter{
        private List<String> datas;

        public MyBaseAdapter(List<String> datas){
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
            TextView selectConditionText;
            ImageView selectMark;
            TextView okButton;

            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.condition_screening_item_layout,null);
            }
            selectConditionText = convertView.findViewById(R.id.select_condition_text);
            selectMark = convertView.findViewById(R.id.select_mark);
            okButton = convertView.findViewById(R.id.ok_button);


            if(position == (datas.size()-1)){
                okButton.setVisibility(View.VISIBLE);
                selectConditionText.setVisibility(View.INVISIBLE);
                selectMark.setVisibility(View.INVISIBLE);
            }else{
                okButton.setVisibility(View.INVISIBLE);
                selectConditionText.setVisibility(View.VISIBLE);
                selectMark.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

    }

}
