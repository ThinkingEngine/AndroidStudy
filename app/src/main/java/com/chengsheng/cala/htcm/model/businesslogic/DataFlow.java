package com.chengsheng.cala.htcm.model.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFlow {

    public static List<Map<String,String>> minePageItemModelA(int dataItem, int[] res, String[] textItem, String[] type){
        List<Map<String,String>> datas = new ArrayList<>();
        for(int i = 0; i < dataItem;i++){
            Map<String,String> data = new HashMap<>();
            data.put("ICON",String.valueOf(res[i]));
            data.put("TITLE",textItem[i]);
            data.put("TYPE",type[i]);
            datas.add(data);
        }

        return datas;
    }
}
