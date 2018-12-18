package com.chengsheng.cala.htcm.module.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodela.MessageItem;
import com.chengsheng.cala.htcm.protocol.childmodela.MessageList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.CheckServiceInterface;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.adapter.ServiceMessageRecyclerViewAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ServiceMessageActivity extends BaseActivity implements UpdateStateInterface, CheckServiceInterface {

    private Retrofit retrofit;
    private int currentPage = 1;
    private boolean addMode = false;
    private List<MessageItem> dataCollect;

    private XRecyclerView serviceMessageList;
    private TextView childTitle;

    private boolean hasNoRead = false;

    private HTCMApp app;
    private ZLoadingDialog loadingDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_service_message;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        CallBackDataAuth.setUpdateStateInterface(this);
        CallBackDataAuth.setCheckServiceInterface(this);
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintText("加载中....");
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));


        ImageView back = findViewById(R.id.title_header_service_messages).findViewById(R.id.back_login);
        TextView title = findViewById(R.id.title_header_service_messages).findViewById(R.id.menu_bar_title);
        childTitle = findViewById(R.id.title_header_service_messages).findViewById(R.id.message_mark_text);
        serviceMessageList = findViewById(R.id.service_message_list);

        childTitle.setText("全部已读");
        childTitle.setTextColor(getResources().getColor(R.color.colorThrText));
        childTitle.setSelected(false);
        getMessageList(currentPage, true);

        serviceMessageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getMessageList(1, false);
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                addMode = true;
                getMessageList(currentPage, false);
                serviceMessageList.loadMoreComplete();
            }
        });

        title.setText("服务通知");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getData() {

    }


    private void getMessageList(int page, final boolean loading) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        if (loading) {
            loadingDialog.show();
        }
        NetService service = retrofit.create(NetService.class);
        service.getMessageList(app.getTokenType() + " " + app.getAccessToken(), String.valueOf(page))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MessageList>() {
                    @Override
                    public void onNext(MessageList messageList) {
                        if (!addMode) {
                            dataCollect = messageList.getItems();
                            for (MessageItem messageItem : dataCollect) {
                                if (!messageItem.isIs_read()) {
                                    childTitle.setText("标为已读");
                                    childTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    childTitle.setSelected(true);
                                }
                            }
                            serviceMessageList.setLayoutManager(new LinearLayoutManager(ServiceMessageActivity.this));
                            serviceMessageList.setAdapter(new ServiceMessageRecyclerViewAdapter(ServiceMessageActivity.this, dataCollect));
                            serviceMessageList.refreshComplete();
                        } else {
                            dataCollect.addAll(messageList.getItems());
                            serviceMessageList.setAdapter(new ServiceMessageRecyclerViewAdapter(ServiceMessageActivity.this, dataCollect));
                        }

                        if (loading) {
                            loadingDialog.cancel();
                        }
                        childTitle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (childTitle.isSelected()) {
                                    childTitle.setText("全部已读");
                                    childTitle.setTextColor(getResources().getColor(R.color.colorThrText));
                                    childTitle.setSelected(false);
                                    List<String> check = new ArrayList<>();
                                    for (MessageItem item : dataCollect) {
                                        if (!item.isIs_read()) {
                                            check.add(String.valueOf(item.getId()));
                                        }
                                    }

                                    postCheckSMS(check);
                                }
                            }
                        });

                        serviceMessageList.loadMoreComplete();
                        serviceMessageList.refreshComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        serviceMessageList.loadMoreComplete();
                        serviceMessageList.refreshComplete();
                    }

                    @Override
                    public void onComplete() {
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        serviceMessageList.loadMoreComplete();
                        serviceMessageList.refreshComplete();
                    }

                });
    }

    private void postCheckSMS(List<String> checks) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        service.markMessageReaded(app.getTokenType() + " " + app.getAccessToken(), checks)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e("TAG", "Service check success!");
                        getMessageList(1, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "Service check fal!");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            getMessageList(1, true);
        }
    }

    @Override
    public void clickSMS(List<String> clicks) {
        if (!clicks.isEmpty()) {
            postCheckSMS(clicks);
        } else {
            Log.e("TAG", "service data abnormal!");
        }

    }
}
