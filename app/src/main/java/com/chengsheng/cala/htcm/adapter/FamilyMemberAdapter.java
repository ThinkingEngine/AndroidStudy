package com.chengsheng.cala.htcm.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.module.activitys.FamiliesDetailsActivity;
import com.chengsheng.cala.htcm.module.activitys.UserCardActivity;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-25 9:54
 * Description:家庭成员列表
 */
public class FamilyMemberAdapter extends BaseQuickAdapter<FamiliesListItem> {

    private SimpleDraweeView familiesIcon;
    private Context context;


    public FamilyMemberAdapter(int layoutResId, List<FamiliesListItem> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, FamiliesListItem familiesListItem) {

        baseViewHolder.setText(R.id.families_name_text, familiesListItem.getFullname());
        baseViewHolder.setText(R.id.families_tel_text, familiesListItem.getMobile());
        baseViewHolder.setText(R.id.families_id_text,familiesListItem.getId_card_no());
        familiesIcon = baseViewHolder.itemView.findViewById(R.id.families_header_icon);
        familiesIcon.setImageURI(familiesListItem.getAvatar_path());


        baseViewHolder.setOnClickListener(R.id.families_item,v -> {
            Intent intent = new Intent(context, FamiliesDetailsActivity.class);
            String familiesID = String.valueOf(familiesListItem.getId());
            intent.putExtra("FAMILIES_ID", familiesID);
            context.startActivity(intent);
        });

        baseViewHolder.setOnClickListener(R.id.families_qr, v -> {
            Intent intent = new Intent(context,UserCardActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("FAMILIES_INFO", familiesListItem);
            intent.putExtras(bundle);
            context.startActivity(intent);
//            ActivityUtil.Companion.startActivity(context,new UserCardActivity(),bundle);
        });

        if (familiesListItem.getOwner_relationship().equals("")) {
            baseViewHolder.setVisible(R.id.families_mark, false);
        } else {
            baseViewHolder.setText(R.id.families_mark, familiesListItem.getOwner_relationship());
            baseViewHolder.setVisible(R.id.families_mark, true);
        }

    }
}
