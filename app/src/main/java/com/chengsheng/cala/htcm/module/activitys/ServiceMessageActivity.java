package com.chengsheng.cala.htcm.module.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.data.repository.MessageRepository;
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

/**
 * 服务通知类
 */

public class ServiceMessageActivity extends BaseActivity implements UpdateStateInterface, CheckServiceInterface {

    private Retrofit retrofit;
    private int currentPage = 1;
    private boolean addMode = false;
    private List<MessageItem> dataCollect;

    private XRecyclerView serviceMessageList;
    private TextView childTitle;

    private boolean hasNoRead = false;
    private ServiceMessageRecyclerViewAdapter adapter;

    private HTCMApp app;


    @Override
    public int getLayoutId() {
        return R.layout.activity_service_message;
    }

    @Override
    public void initView() {

        initActivityParam();
        initViews();

        serviceMessageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                addMode = false;//设置是否为加载下一页还是刷新最新数据，addMode = true：加载下一页 addMode = false：加载最新数据
                getMessageList(1, false);
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                addMode = true;
                getMessageList(currentPage, false);
            }
        });


    }

    @Override
    public void getData() {
        getMessageList(currentPage, true);
    }


    //获取服务消息列表
    private void getMessageList(int page, final boolean loading) {

        if (loading) {
            showLoading();
        }

        MessageRepository
                .Companion.getDefault()
                .getMessageList(String.valueOf(page))
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
                            adapter = new ServiceMessageRecyclerViewAdapter(ServiceMessageActivity.this, dataCollect);
                            serviceMessageList.setAdapter(adapter);
                            serviceMessageList.refreshComplete();
                            currentPage = 0;

                        } else {
                            if (messageList.getItems().isEmpty()) {
                                showShortToast("已无更多数据了");
                                currentPage--;
                            } else {

                                for (int i = 0; i < messageList.getItems().size(); i++) {
                                    dataCollect.add(messageList.getItems().get(i));
                                }
                                adapter.notifyDataSetChanged();
                            }
                            serviceMessageList.loadMoreComplete();
                        }

                        if (loading) {
                            hideLoading();
                        }


                        childTitle.setOnClickListener(v -> {
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
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "MessageList:" + e.toString());
                        if (loading) {
                            hideLoading();
                        }
                        serviceMessageList.loadMoreComplete();
                        serviceMessageList.refreshComplete();
                    }

                    @Override
                    public void onComplete() {
                        if (loading) {
                            hideLoading();
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

    private void initViews() {
        ImageView back = findViewById(R.id.title_header_service_messages).findViewById(R.id.back_login);
        TextView title = findViewById(R.id.title_header_service_messages).findViewById(R.id.menu_bar_title);
        childTitle = findViewById(R.id.title_header_service_messages).findViewById(R.id.message_mark_text);
        serviceMessageList = findViewById(R.id.service_message_list);

        childTitle.setText("全部已读");
        childTitle.setTextColor(getResources().getColor(R.color.colorThrText));
        childTitle.setSelected(false);

        serviceMessageList.setHasFixedSize(true);
        serviceMessageList.setFocusable(false);
        serviceMessageList.setFocusableInTouchMode(false);

        title.setText("服务通知");
        serviceMessageList.setLayoutManager(new LinearLayoutManager(this));

        back.setOnClickListener(v -> finish());
    }

    private void initActivityParam() {
        app = HTCMApp.create(getApplicationContext());
        CallBackDataAuth.setUpdateStateInterface(this);
        CallBackDataAuth.setCheckServiceInterface(this);
    }
}
