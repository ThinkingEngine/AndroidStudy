package com.chengsheng.cala.htcm.base;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.FeatureServiceAdapter;
import com.chengsheng.cala.htcm.data.repository.ProjectRepository;
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-20 14:14
 * Description:
 */
public class TestFragment extends BaseRefreshFragment<RecommendProProtocol.ItemsBean.RecommendBean> {


    @Override
    public void getData(int page) {
        ProjectRepository.Companion.getDefault().getRecommendPro()
                .subscribe(new DefaultObserver<RecommendProProtocol>() {
                    @Override
                    public void onNext(RecommendProProtocol recommendProProtocol) {
                        fillData(recommendProProtocol.getItems().get(0).getRecommend());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Nullable
    @Override
    public BaseQuickAdapter<RecommendProProtocol.ItemsBean.RecommendBean> getCurrentAdapter() {
        return new FeatureServiceAdapter(new ArrayList());
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }
}
