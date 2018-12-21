package com.chengsheng.cala.htcm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.AssistantItem;

import java.util.List;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-20 18:00
 * Description:重写智能助理适配器，添加分页功能
 */
public class AIAssistantNewAdapter extends BaseQuickAdapter<AssistantItem> {


    public AIAssistantNewAdapter(List<AssistantItem> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AssistantItem assistantItem) {
        baseViewHolder.setText(R.id.ai_assistant_item_date,assistantItem.getCustomer().getReservation_or_registration().getDate());
        baseViewHolder.setText(R.id.user_name_ai_assistant,assistantItem.getCustomer().getName());
//        baseViewHolder.setText(R.id)
    }
}
