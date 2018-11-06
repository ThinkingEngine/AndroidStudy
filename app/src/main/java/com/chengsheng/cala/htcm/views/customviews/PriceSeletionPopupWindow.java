package com.chengsheng.cala.htcm.views.customviews;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceSeletionPopupWindow extends CommonPopupWindow {
    private GridView priceSelecter;
    private TextView reset,completePriceSelect;
    private EditText bestLowerPrice,bestHighPrice;
    private Context context;


    public PriceSeletionPopupWindow(Context c, int layoutRes, int w, int h) {

        super(c, layoutRes, w, h);
        this.context = c;
    }

    @Override
    protected void initView() {
        View rootView = getContentView();
        priceSelecter = rootView.findViewById(R.id.price_selecter);
        reset = rootView.findViewById(R.id.reset);
        completePriceSelect = rootView.findViewById(R.id.complete_price_select);
        bestLowerPrice = rootView.findViewById(R.id.best_lower_price);
        bestHighPrice = rootView.findViewById(R.id.best_high_price);
    }

    @Override
    protected void initEvent() {
//        String[] datas = new String[]{"100元以内","100元-200元","100元-200元","100元-200元","100元-200元","10000-200000元"};
//        List<Map<String,String>> d = new ArrayList<>();
//        for(int i = 0; i < datas.length;i++){
//            Map<String,String> map = new HashMap<>();
//            map.put("text",datas[i]);
//            d.add(map);
//        }
//        String[] from = new String[]{"text"};
//        int[] to = new int[]{R.id.price_text};
//        SimpleAdapter adapter = new SimpleAdapter(context,d,R.layout.price_bg_layout,from,to);
//        priceSelecter.setAdapter(adapter);
    }
}
