package com.chengsheng.cala.htcm.module.activitys;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.ExamOrderAdapter;
import com.chengsheng.cala.htcm.base.BaseRefreshFragment;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.data.repository.ExamOrderRepository;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-27 13:45
 * Description:体检订单
 */
public class ExamOrderFragment extends BaseRefreshFragment {

    private String marks = "";
    private String mode;

    @Override
    public void initViews(@NotNull View view) {

    }

    @Override
    public void getData(int page) {

        if (marks.equals("全部")) {
            mode = GlobalConstant.MODE_ALL;
        } else if (marks.equals("待付款")) {
            mode = GlobalConstant.MODE_RECEIVABLE;
        } else if (marks.equals("已付款")) {
            mode = GlobalConstant.MODE_RECEIVED;
        } else if (marks.equals("待评价")) {
            mode = GlobalConstant.MODE_COMMENT;
        } else if (marks.equals("已取消")) {
            mode = GlobalConstant.MODE_CANCEL;
        }

        ExamOrderRepository.Companion.getDefault().getExamOrder("", mode, String.valueOf(page)).subscribe(new DefaultObserver<ExamOrder>() {
            @Override
            public void onNext(ExamOrder examOrder) {
                fillData(examOrder.getItems());
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
        return new ExamOrderAdapter(R.layout.exam_order_form_item_layout, new ArrayList<>(), getContext());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_exam_order_form;
    }

    public void setMark(String mark) {
        this.marks = mark;
    }
}
