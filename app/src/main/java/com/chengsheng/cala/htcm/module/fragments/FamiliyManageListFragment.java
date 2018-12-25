package com.chengsheng.cala.htcm.module.fragments;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.FamilyMemberAdapter;
import com.chengsheng.cala.htcm.base.BaseRefreshFragment;
import com.chengsheng.cala.htcm.data.repository.MemberRepository;
import com.chengsheng.cala.htcm.protocol.FamiliesList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-25 9:11
 * Description:
 */
public class FamiliyManageListFragment extends BaseRefreshFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_family_list;
    }

    @Override
    public void initViews(@NotNull View view) {
        getSmartRefreshLayout().setEnableLoadmore(false);
    }

    @Override
    public void getData(int page) {

        MemberRepository.Companion.getDefault().getMember().subscribe(new DefaultObserver<FamiliesList>() {
            @Override
            public void onNext(FamiliesList familiesList) {
                fillData(familiesList.getItems());
            }

            @Override
            public void onError(Throwable e) {
                showError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Nullable
    @Override
    public BaseQuickAdapter getCurrentAdapter() {
        return new FamilyMemberAdapter(R.layout.fmailies_item_bg_layout, new ArrayList<>(),getContext());
    }
}
