package com.chengsheng.cala.htcm.module.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.AppointmentBody;
import com.chengsheng.cala.htcm.protocol.AppointmentDetail;
import com.chengsheng.cala.htcm.protocol.FamiliesList;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.protocol.OrderID;
import com.chengsheng.cala.htcm.protocol.TextMessage;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.ExamDateInterface;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.adapter.AffirmAppointmentExamPersonAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class AffirmAppointmentActivity extends BaseActivity implements ExamDateInterface {

    private TextView title;
    private ImageView back;
    private TextView test, immediatePay;
    private RecyclerView examPersonRecycler;//, otherSelectItemRecycler;
    private RelativeLayout keyIllnessScreeningCoatiner;
    private SimpleDraweeView appointmentIcon;
    private TextView groupName;
    private TextView appointmentTotalPrice;
    private TextView examTotalPrice;
    private TextView appointmentDateText;
    private LinearLayout selectAppointmentDate;

    private List<FamiliesListItem> families;
    private AppointmentBody uploadBody;//数据上传体

    private Calendar calendar;

    private Retrofit retrofit;
    private NetService service;

    private HTCMApp app;
    private ZLoadingDialog loadingDialog;

    private String comboUrl;


    private void setView(AppointmentDetail detail) {
        test.setText(detail.getName());
        appointmentIcon.setImageURI(detail.getBanner_photo());
        groupName.setText("体检机构：" + detail.getOrganization().getName());
        appointmentTotalPrice.setText("¥ " + detail.getPrice());
        examTotalPrice.setText("合计：¥"+detail.getPrice()+".00");
        uploadBody.setExam_package_id(detail.getId());
        uploadBody.setReserve_date(appointmentDateText.getText().toString());
    }

    @Override
    public void getExamDate(int id) {
        uploadBody.setCustomer_id(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_affirm_appointment;
    }

    @Override
    public void initView() {
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("加载中....");
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));

        CallBackDataAuth.setExamDateInterface(this);
        final String comboID = getIntent().getStringExtra("COMBO_ID");
        app = HTCMApp.create(getApplicationContext());


        if (uploadBody == null) {
            uploadBody = new AppointmentBody();
        }
        uploadBody.setCustomer_id(-1);
        uploadBody.setReserve_date("");

        title = findViewById(R.id.title_header_affirm_appointment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_affirm_appointment).findViewById(R.id.back_login);
        test = findViewById(R.id.affirm_appointment_model_a).findViewById(R.id.appointment_name);
        appointmentIcon = findViewById(R.id.affirm_appointment_model_a).findViewById(R.id.appointment_icon);
        groupName = findViewById(R.id.affirm_appointment_model_a).findViewById(R.id.group_name);
        appointmentTotalPrice = findViewById(R.id.affirm_appointment_model_a).findViewById(R.id.appointment_total_price);
        examPersonRecycler = findViewById(R.id.affirm_appointment_model_b).findViewById(R.id.exam_person_recycler);
        selectAppointmentDate = findViewById(R.id.affirm_appointment_model_c).findViewById(R.id.select_appointment_date);
        appointmentDateText = findViewById(R.id.affirm_appointment_model_c).findViewById(R.id.appointment_date_text);
        keyIllnessScreeningCoatiner = findViewById(R.id.affirm_appointment_model_b).findViewById(R.id.key_illness_screening_coatiner);
        examTotalPrice = findViewById(R.id.exam_total_price);
        immediatePay = findViewById(R.id.immediate_pay);

        title.setText("确认预约");

        selectAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(appointmentDateText);
            }
        });

        keyIllnessScreeningCoatiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmAppointmentActivity.this, AddExamPersonActivity.class);
                intent.putExtra("COMBO_ID", comboID);
                startActivity(intent);
                finish();
            }
        });

        //获取套餐详情
        getComboDetail(comboID);
        //获取选择的家人list。
        getFamiliesList();

        //立即支付
        immediatePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadBody.getCustomer_id() == -1) {
                    Toast.makeText(AffirmAppointmentActivity.this, "对不起，你还没有选择家人的!", Toast.LENGTH_LONG).show();
                } else if (uploadBody.getReserve_date().equals("") || uploadBody.getReserve_date().equals("请选择体检日期")) {
                    Toast.makeText(AffirmAppointmentActivity.this, "对不起，你还没有选择体检日期的!", Toast.LENGTH_LONG).show();
                } else {
                    loadingDialog.setHintText("正在进行支付准备......");
                    loadingDialog.show();
                    final Gson gson = new Gson();
                    Map<String, String> params = new ArrayMap<>();
                    params.put("customer_id", String.valueOf(uploadBody.getCustomer_id()));
                    params.put("reserve_date", uploadBody.getReserve_date());
                    params.put("exam_package_id", FuncUtils.getString("COMBO", "0"));
                    params.put("exam_item_ids", "");
                    service.putAppointment(app.getTokenType() + " " + app.getAccessToken(), params)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<OrderID>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(OrderID orderID) {
                                    app.setOrderID(orderID.getOrder_id());
                                    Intent intent = new Intent(AffirmAppointmentActivity.this, ModePaymentActivity.class);
                                    intent.putExtra("ORDER_ID",String.valueOf(orderID.getOrder_id()));
                                    intent.putExtra("ORDER_VAL",FuncUtils.getString("COMBO_CAL",""));
                                    startActivity(intent);
                                    loadingDialog.cancel();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (e instanceof HttpException) {
                                        ResponseBody body = ((HttpException) e).response().errorBody();
                                        try {
                                            String s = body.string();
                                            TextMessage textMessage = gson.fromJson(s, TextMessage.class);
                                            loadingDialog.cancel();
                                            Toast.makeText(AffirmAppointmentActivity.this, "很遗憾，支付准备失败！", Toast.LENGTH_SHORT).show();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onComplete() {

                                }
                            });


                }
            }
        });

    }

    @Override
    public void getData() {

    }

    private void getFamiliesList(){
        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        service.getFamiliesList(app.getTokenType()+" "+app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FamiliesList>() {
                    @Override
                    public void onNext(FamiliesList familiesList) {
                        AffirmAppointmentExamPersonAdapter adapter = new AffirmAppointmentExamPersonAdapter(AffirmAppointmentActivity.this, familiesList.getItems());
                        examPersonRecycler.setLayoutManager(new LinearLayoutManager(AffirmAppointmentActivity.this));
                        examPersonRecycler.setAdapter(adapter);
                        examPersonRecycler.setNestedScrollingEnabled(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getComboDetail(String comboId){
        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        service = retrofit.create(NetService.class);
        loadingDialog.show();
        service.getCombonDetail(GlobalConstant.EXAM_PACKAGES+comboId, app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AppointmentDetail>() {
                    @Override
                    public void onNext(AppointmentDetail appointmentDetail) {
                        FuncUtils.putString("COMBO", String.valueOf(appointmentDetail.getId()));
                        FuncUtils.putString("COMBO_CAL", appointmentDetail.getPrice());
                        setView(appointmentDetail);
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Log.e("TEST", "ComboDetail onError!" + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getDate(final TextView textView) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                uploadBody.setReserve_date(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
