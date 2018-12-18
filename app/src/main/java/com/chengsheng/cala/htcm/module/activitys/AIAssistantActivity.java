package com.chengsheng.cala.htcm.module.activitys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.chengsheng.cala.htcm.adapter.AIAssistantSubRecyclerView;
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
    private RecyclerView aiAssistantList;
    private SwipeRefreshLayout refreshAiAssistant;


    private HTCMApp app;
    private Retrofit retrofit;
    private ZLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = HTCMApp.create(getApplicationContext());
        CallBackDataAuth.setUpdateStateInterface(this);
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintText("加载中....");
        setContentView(R.layout.activity_aiassistant);

        title = findViewById(R.id.title_header_ai_assistant).findViewById(R.id.menu_bar_title);
        subhead = findViewById(R.id.title_header_ai_assistant).findViewById(R.id.message_mark_text);
        aiAssistantList = findViewById(R.id.ai_assistant_list);
        refreshAiAssistant = findViewById(R.id.refresh_ai_assistant);
        subhead.setText("");
        updateData(true);

        refreshAiAssistant.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData(false);
            }
        });
        title.setText("智能助理");
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }

    private void updateData(final boolean loading){

        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        if(loading){
            loadingDialog.show();
        }
        NetService service = retrofit.create(NetService.class);
        service.getAIAssistants(app.getTokenType()+" "+app.getAccessToken(),"0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AssistantList>() {
                    @Override
                    public void onNext(AssistantList assistantList) {
                        Log.e("TAG","AIAssistant_onNext:"+assistantList.toString());
                        AIAssistantSubRecyclerView adapter = new AIAssistantSubRecyclerView(AIAssistantActivity.this,assistantList.getItems());
                        aiAssistantList.setLayoutManager(new LinearLayoutManager(AIAssistantActivity.this));
                        aiAssistantList.setAdapter(adapter);
                        if(loading){
                            loadingDialog.cancel();
                        }
                        refreshAiAssistant.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","AIAssistant_onError:"+e.toString());
                        List<AssistantItem> noData = new ArrayList<>();
                        AIAssistantSubRecyclerView adapter = new AIAssistantSubRecyclerView(AIAssistantActivity.this,noData);
                        aiAssistantList.setLayoutManager(new LinearLayoutManager(AIAssistantActivity.this));
                        aiAssistantList.setAdapter(adapter);
                        if(loading){
                            loadingDialog.cancel();
                        }
                        refreshAiAssistant.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
//                        List<AssistantItem> noData = new ArrayList<>();
//                        AIAssistantSubRecyclerView adapter = new AIAssistantSubRecyclerView(AIAssistantActivity.this,noData);
//                        aiAssistantList.setLayoutManager(new LinearLayoutManager(AIAssistantActivity.this));
//                        aiAssistantList.setAdapter(adapter);
                        if(loading){
                            loadingDialog.cancel();
                        }
                        refreshAiAssistant.setRefreshing(false);
                    }
                });
    }


    @Override
    public void updateServiceMessage(boolean status) {
        if(status){
            updateData(true);
        }
    }
}
