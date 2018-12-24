package com.chengsheng.cala.htcm.module.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.AssistantItem;
import com.chengsheng.cala.htcm.protocol.AssistantList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.adapter.AIAssistantSubAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AIAssistantActivity extends BaseActivity implements UpdateStateInterface {
    private TextView title;
    private TextView subhead;
    private XRecyclerView aiAssistantList;

    private AIAssistantSubAdapter adapter;
    private List<AssistantItem> dataCollection;

    private HTCMApp app;
    private Retrofit retrofit;
    private ZLoadingDialog loadingDialog;

    private int current = 1;//数据页 默认为一

    @Override
    public int getLayoutId() {
        return R.layout.activity_aiassistant;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        CallBackDataAuth.setUpdateStateInterface(this);
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintText("加载中....");

        initViews();


    }

    @Override
    public void getData() {
        updateData(true,false);

        aiAssistantList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                updateData(false,false);
            }

            @Override
            public void onLoadMore() {
                updateData(false,true);
            }
        });

    }

    //param loading 标记是否为初次进入页面 param addMore 加载更多
    private void updateData(final boolean loading, final boolean addMore) {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        if (loading) {
            loadingDialog.show();
        }

        if(addMore){
            current++;
        }

        NetService service = retrofit.create(NetService.class);
        service.getAIAssistants(app.getTokenType() + " " + app.getAccessToken(), "0", String.valueOf(current))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AssistantList>() {
                    @Override
                    public void onNext(AssistantList assistantList) {

                        if (!addMore) {
                            dataCollection = assistantList.getItems();
                            adapter = new AIAssistantSubAdapter(AIAssistantActivity.this, dataCollection);
                            aiAssistantList.setAdapter(adapter);
                        } else {
                            if (assistantList.getItems().isEmpty()) {
                                showShortToast("已无更多内容");
                                current--;
                            } else {
                                dataCollection.addAll(assistantList.getItems());
                                adapter.notifyDataSetChanged();
                            }

                        }

                        aiAssistantList.loadMoreComplete();
                        aiAssistantList.refreshComplete();

                        if (loading) {
                            loadingDialog.cancel();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        List<AssistantItem> noData = new ArrayList<>();
                        adapter = new AIAssistantSubAdapter(AIAssistantActivity.this, noData);
                        aiAssistantList.setAdapter(adapter);
                        if (loading) {
                            loadingDialog.cancel();
                        }

                        aiAssistantList.loadMoreComplete();
                        aiAssistantList.refreshComplete();
                    }

                    @Override
                    public void onComplete() {

                        if (loading) {
                            loadingDialog.cancel();
                        }
                    }
                });
    }

    private void initViews() {
        title = findViewById(R.id.title_header_ai_assistant).findViewById(R.id.menu_bar_title);
        subhead = findViewById(R.id.title_header_ai_assistant).findViewById(R.id.message_mark_text);
        aiAssistantList = findViewById(R.id.ai_assistant_list);
        subhead.setText("");
        title.setText("智能助理");

        aiAssistantList.setLoadingMoreEnabled(true);
        aiAssistantList.setLayoutManager(new LinearLayoutManager(AIAssistantActivity.this));
    }


    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            updateData(true,false);
        }
    }
}
