package com.chengsheng.cala.htcm.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamAppointment;
import com.chengsheng.cala.htcm.model.datamodel.ExamApponitments;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.views.adapters.ExamAppointmentRecyclerAdapter;
import com.chengsheng.cala.htcm.views.customviews.HeaderScrollView;
import com.chengsheng.cala.htcm.views.customviews.MyRecyclerView;
import com.chengsheng.cala.htcm.views.dialog.CustomComboDialog;
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

    private PopupWindow window;

    private ExamAppointmentRecyclerAdapter adapter;
    private ExamApponitments examApponitmentsDatas;
    private ZLoadingDialog loadDialog;
    private Retrofit retrofit;

    private String currentFilter = GlobalConstant.COMBO_TYPE_A;
    private boolean value = true;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CallBackDataAuth.setUpdateStateInterface(this);
        setContentView(R.layout.activity_exam_appointment);

        loadDialog = new ZLoadingDialog(this);
        loadDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadDialog.setHintText("加载中......");
        loadDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));

        title = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.back_login);
        search = findViewById(R.id.title_header_exam_appointment).findViewById(R.id.search_button);
        headerScrollView = findViewById(R.id.scroll_view_header);
        imageView = findViewById(R.id.rl1);
        textView = findViewById(R.id.rl2);
        examAppointmentItem = findViewById(R.id.exam_appointment_recycler);//体检预约列表。
        refreshExamAppointmentPage = findViewById(R.id.refresh_exam_appointment_page);
        comboRecommendButton = findViewById(R.id.combo_recommend_button);

        title.setText("体检预约");//修改标题

        allPriceA = findViewById(R.id.price_model_a).findViewById(R.id.all_price);
        allPriceB = findViewById(R.id.price_model_b).findViewById(R.id.all_price);
        allPriceStateA = findViewById(R.id.price_model_a).findViewById(R.id.all_price_state);
        allPriceStateB = findViewById(R.id.price_model_b).findViewById(R.id.all_price_state);
        comboSynthesizeA = findViewById(R.id.price_model_a).findViewById(R.id.combo_synthesize);
        comboSynthesizeB = findViewById(R.id.price_model_b).findViewById(R.id.combo_synthesize);
        comboSalesVolumeA = findViewById(R.id.price_model_a).findViewById(R.id.combo_sales_volume);
        comboSalesVolumeB = findViewById(R.id.price_model_b).findViewById(R.id.combo_sales_volume);

        setTextState(true, false, false);
        //获取网络数据。
        updataComboInfo(String.valueOf(1), currentFilter, true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamAppointmentActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        //下拉刷新
        refreshExamAppointmentPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updataComboInfo(String.valueOf(1), currentFilter, false);
            }
        });

        allPriceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        allPriceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        comboSynthesizeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = GlobalConstant.COMBO_TYPE_A;
                updataComboInfo(String.valueOf(1), currentFilter, true);
                setTextState(true, false, false);
            }
        });
        comboSynthesizeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = GlobalConstant.COMBO_TYPE_A;
                updataComboInfo(String.valueOf(1), currentFilter, true);
                setTextState(true, false, false);
            }
        });

        comboSalesVolumeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = GlobalConstant.COMBO_TYPE_D;
                updataComboInfo(String.valueOf(1), currentFilter, true);
                setTextState(false, false, true);
            }
        });
        comboSalesVolumeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = GlobalConstant.COMBO_TYPE_D;
                updataComboInfo(String.valueOf(1), currentFilter, true);
                setTextState(false, false, true);
            }
        });

        //测试数据
//        List<Map<String, Object>> listadata = new ArrayList<>();
//        final String[] d = new String[]{"100元以内", "100元-200元", "100元-200元", "100元-200元", "1000元-2000元", "10000元-200000元"};
//        final boolean[] select = {false,false,false,false,false,false};
//        for (int i = 0; i < d.length; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("text", d[i]);
//            listadata.add(map);
//        }
//        String[] from = {"text"};
//        int[] to = {R.id.price_text};
//
//        View popupView = LayoutInflater.from(this).inflate(R.layout.condition_selection_combo_bg_layout, null);
//        window = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
//
//        TextView isok = popupView.findViewById(R.id.complete_price_select);
//        GridView gridView = popupView.findViewById(R.id.price_selecter);
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listadata, R.layout.price_bg_layout, from, to);
//        gridView.setAdapter(simpleAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView price = view.findViewById(R.id.price_text);
//                if(select[position]){
//                    select[position] = false;
//                    price.setTextColor(getResources().getColor(R.color.colorSecText));
//                    price.setSelected(false);
//                }else{
//                    select[position] = true;
//                    price.setSelected(true);
//                    price.setTextColor(getResources().getColor(R.color.colorPrimary));
//                }
//            }
//        });
//
//        window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
//        window.setOutsideTouchable(true);
//        window.setTouchable(true);


//        isok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ExamAppointmentActivity.this, "nih", Toast.LENGTH_SHORT).show();
//            }
//        });

        final CustomComboDialog dialog = new CustomComboDialog(ExamAppointmentActivity.this);

        //定制套餐
        comboRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });
        headerScrollView.setCallback(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

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
//        window.dismiss();
        super.onDestroy();
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


}
