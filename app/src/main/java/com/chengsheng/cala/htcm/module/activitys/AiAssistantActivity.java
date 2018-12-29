package com.chengsheng.cala.htcm.module.activitys;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.AIAssistantNewAdapter;
import com.chengsheng.cala.htcm.base.BaseRefreshActivity;
import com.chengsheng.cala.htcm.data.repository.AIAssistantRepository;
import com.chengsheng.cala.htcm.protocol.AssistantList;
import com.chengsheng.cala.htcm.widget.AppTitleBar;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 10:37
 * Description:AI智能助理
 */
public class AiAssistantActivity extends BaseRefreshActivity {

    private AppTitleBar header;

    @Override
    public void initViews() {
        header = findViewById(R.id.at_ai_header);
        header.setTitle("智能助理");
        header.setFinishClickListener(() -> finish());
    }

    @Override
    public void getData(int page) {

        AIAssistantRepository.Companion.getDefault()
                .getAIAssistants("0", String.valueOf(page))
                .subscribe(new DefaultObserver<AssistantList>() {
                    @Override
                    public void onNext(AssistantList assistantList) {
                        fillData(assistantList.getItems());
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
        return new AIAssistantNewAdapter(R.layout.ai_assistant_main_page_item_layout
                , new ArrayList<>(), this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_aiassistant;
    }
}
