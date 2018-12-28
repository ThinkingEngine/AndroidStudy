package com.chengsheng.cala.htcm.module.fragments;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.MyExamAdapter;
import com.chengsheng.cala.htcm.base.BaseRefreshFragment;
import com.chengsheng.cala.htcm.data.repository.MyExamRepository;
import com.chengsheng.cala.htcm.protocol.ExamItemsList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 9:28
 * Description:我的体检页面 (展示用户所有的体检信息)
 */
public class MyExamFragment extends BaseRefreshFragment {

    private String mode;

    @Override
    public void initViews(@NotNull View view) {

    }

    @Override
    public void getData(int page) {
        MyExamRepository
                .Companion.getDefault()
                .getExamList(mode, "", String.valueOf(page))
                .subscribe(new DefaultObserver<ExamItemsList>() {
                    @Override
                    public void onNext(ExamItemsList examItemsList) {
                        fillData(examItemsList.getItems());
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
        return new MyExamAdapter(R.layout.my_exam_list_item_layout, new ArrayList<>(), getContext());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_exam_all;
    }

    public void setMode(String currentMode) {
        if (currentMode.equals("全部")) {
            mode = "valid";
        } else if (currentMode.equals("待体检")) {
            mode = "reservation";
        } else if (currentMode.equals("体检中")) {
            mode = "checking";
        } else {
            mode = "checked";
        }
    }
}
