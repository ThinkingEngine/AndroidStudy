package com.chengsheng.cala.htcm.module.activitys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.ExamAppointmentRecyclerAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.ExamAppointment;
import com.chengsheng.cala.htcm.protocol.ExamApponitments;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.widget.CustomComboDialog;
import com.chengsheng.cala.htcm.widget.HeaderScrollView;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ExamAppointmentActivity extends BaseActivity implements HeaderScrollView.StopCall, UpdateStateInterface {
    private HeaderScrollView headerScrollView;
    private RelativeLayout imageView;
    private RelativeLayout textView;

    private MyRecyclerView examAppointmentItem;//预约列表
    private SwipeRefreshLayout refreshExamAppointmentPage;

    private TextView title;
    private ImageView back, search;
    private TextView allPriceA, allPriceB;
    private ImageView allPriceStateA, allPriceStateB;
    private TextView comboSynthesizeA, comboSynthesizeB;
    private TextView comboSalesVolumeA, comboSalesVolumeB;
    private ImageView comboRecommendButton;

    private ExamAppointmentRecyclerAdapter adapter;
    private ExamApponitments examApponitmentsDatas;
    private ZLoadingDialog loadDialog;
    private Retrofit retrofit;

    private String currentFilter = GlobalConstant.COMBO_TYPE_A;
    private boolean value = true;

    @Override
    public void stopSlide(boolean isStop) {
        if (isStop) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_appointment;
    }

    @Override
    public void initView() {
        CallBackDataAuth.setUpdateStateInterface(this);

        loadDialog = new ZLoadingDialog(this);
        loadDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadDialog.setHintText("加载中...");
        loadDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));

        initViews();


        //下拉刷新
        refreshExamAppointmentPage.setOnRefreshListener(() -> updataComboInfo(String.valueOf(1), currentFilter, false));

        allPriceA.setOnClickListener(v -> {
            if (value) {
                currentFilter = GlobalConstant.COMBO_TYPE_B;
                value = false;
                allPriceStateA.setSelected(value);
                allPriceStateB.setSelected(value);
            } else {
                currentFilter = GlobalConstant.COMBO_TYPE_C;
                value = true;
                allPriceStateA.setSelected(value);
                allPriceStateB.setSelected(value);
            }

            updataComboInfo(String.valueOf(1), currentFilter, true);
            setTextState(false, true, false);
        });

        allPriceB.setOnClickListener(v -> {
            if (value) {
                currentFilter = GlobalConstant.COMBO_TYPE_B;
                value = false;
                allPriceStateA.setSelected(value);
                allPriceStateB.setSelected(value);
            } else {
                currentFilter = GlobalConstant.COMBO_TYPE_C;
                value = true;
                allPriceStateA.setSelected(value);
                allPriceStateB.setSelected(value);
            }

            currentFilter = GlobalConstant.COMBO_TYPE_B;
            updataComboInfo(String.valueOf(1), currentFilter, true);
            setTextState(false, true, false);
        });

        comboSynthesizeA.setOnClickListener(v -> {
            currentFilter = GlobalConstant.COMBO_TYPE_A;
            updataComboInfo(String.valueOf(1), currentFilter, true);
            setTextState(true, false, false);
        });
        comboSynthesizeB.setOnClickListener(v -> {
            currentFilter = GlobalConstant.COMBO_TYPE_A;
            updataComboInfo(String.valueOf(1), currentFilter, true);
            setTextState(true, false, false);
        });

        comboSalesVolumeA.setOnClickListener(v -> {
            currentFilter = GlobalConstant.COMBO_TYPE_D;
            updataComboInfo(String.valueOf(1), currentFilter, true);
            setTextState(false, false, true);
        });
        comboSalesVolumeB.setOnClickListener(v -> {
            currentFilter = GlobalConstant.COMBO_TYPE_D;
            updataComboInfo(String.valueOf(1), currentFilter, true);
            setTextState(false, false, true);
        });

        final CustomComboDialog dialog = new CustomComboDialog(ExamAppointmentActivity.this);

        //定制套餐
        comboRecommendButton.setOnClickListener(v -> {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        });
        HeaderScrollView.setCallback(this);


    }

    @Override
    public void getData() {
        //获取网络数据。
        updataComboInfo(String.valueOf(1), currentFilter, true);
    }

    private void setTextState(boolean Synthesize, boolean price, boolean sales) {
        allPriceA.setSelected(price);
        allPriceB.setSelected(price);

        comboSynthesizeA.setSelected(Synthesize);
        comboSynthesizeB.setSelected(Synthesize);
        comboSalesVolumeA.setSelected(sales);
        comboSalesVolumeB.setSelected(sales);
    }

    private void updataComboInfo(String page, String sortFields, final boolean loading) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        if (loading) {
            loadDialog.show();
        }
        NetService service = retrofit.create(NetService.class);
        service.getComboInfoFilters(GlobalConstant.COMBO_FILTER, page, sortFields)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamApponitments>() {
                    @Override
                    public void onNext(ExamApponitments examApponitments) {
                        examApponitmentsDatas = examApponitments;
                        adapter = new ExamAppointmentRecyclerAdapter(ExamAppointmentActivity.this, examApponitmentsDatas.getItems());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExamAppointmentActivity.this) {
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
                        examAppointmentItem.setLayoutManager(linearLayoutManager);
                        examAppointmentItem.setAdapter(adapter);
                        examAppointmentItem.setNestedScrollingEnabled(false);
                        if (loading) {
                            loadDialog.cancel();
                        }
                        refreshExamAppointmentPage.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loading) {
                            loadDialog.cancel();
                        }
                        refreshExamAppointmentPage.setRefreshing(false);
                        Toast.makeText(ExamAppointmentActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                        List<ExamAppointment> data = new ArrayList<>();
                        examAppointmentItem.setLayoutManager(new LinearLayoutManager(ExamAppointmentActivity.this));
                        ExamAppointmentRecyclerAdapter noContentAdapter = new ExamAppointmentRecyclerAdapter(ExamAppointmentActivity.this, data);
                        examAppointmentItem.setAdapter(noContentAdapter);
                        examAppointmentItem.setNestedScrollingEnabled(false);
                    }

                    @Override
                    public void onComplete() {
                        if (loading) {
                            loadDialog.cancel();
                        }
                        refreshExamAppointmentPage.setRefreshing(false);
                    }
                });
    }


    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            updataComboInfo(String.valueOf(1), currentFilter, true);
        }
    }

    private void initViews() {
        title = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.back_login);
        search = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.search_button);
        headerScrollView = findViewById(R.id.scroll_view_header);
        imageView = findViewById(R.id.rl1);
        textView = findViewById(R.id.rl2);
        examAppointmentItem = findViewById(R.id.exam_appointment_recycler);//体检预约列表。
        refreshExamAppointmentPage = findViewById(R.id.refresh_exam_appointment_page);
        comboRecommendButton = findViewById(R.id.combo_recommend_button);

        allPriceA = findViewById(R.id.price_model_a).findViewById(R.id.all_price);
        allPriceB = findViewById(R.id.price_model_b).findViewById(R.id.all_price);
        allPriceStateA = findViewById(R.id.price_model_a).findViewById(R.id.all_price_state);
        allPriceStateB = findViewById(R.id.price_model_b).findViewById(R.id.all_price_state);
        comboSynthesizeA = findViewById(R.id.price_model_a).findViewById(R.id.combo_synthesize);
        comboSynthesizeB = findViewById(R.id.price_model_b).findViewById(R.id.combo_synthesize);
        comboSalesVolumeA = findViewById(R.id.price_model_a).findViewById(R.id.combo_sales_volume);
        comboSalesVolumeB = findViewById(R.id.price_model_b).findViewById(R.id.combo_sales_volume);

        title.setText("体检预约");//修改标题
        setTextState(true, false, false);
        back.setOnClickListener(v -> finish());
        //进入搜索页面
        search.setOnClickListener(v -> startActivity(new SearchActivity()));
    }


}
