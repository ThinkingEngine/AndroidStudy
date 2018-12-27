package com.chengsheng.cala.htcm.module.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.data.repository.ExamOrderRepository;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrderDetail;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.adapter.OrderDetailExpandableListViewAdapter;
import com.chengsheng.cala.htcm.protocol.childmodelb.UserExamDetail;
import com.chengsheng.cala.htcm.widget.MyExpandableListView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * 体检订单详情
 */

public class OrderDetailActivity extends BaseActivity {

    private TextView paymentStateText;
    private TextView orderDetailExamPersonName, orderDetailAppointmentDate, orderDetailAppointmentNumDate;
    private TextView orderDetailGroup, orderDetailGroupAddress, orderDetailCellphone, orderDetailCreateTime;
    private MyExpandableListView orderItemExpandable;
    private TextView payTotalMoney, realPayMoney, waitingPayMoney;
    private TextView tvPay;


    private Retrofit retrofit;
    private HTCMApp app;
    private String id;


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        id = getIntent().getStringExtra("ORDER_ID");

        initViews();
        getOrderDetail(id);

    }

    @Override
    public void getData() {

    }

    private void initViews() {

        paymentStateText = findViewById(R.id.payment_state_text);
        orderDetailExamPersonName = findViewById(R.id.order_detail_exam_person_name);
        orderDetailAppointmentDate = findViewById(R.id.order_detail_appointment_date);
        orderDetailAppointmentNumDate = findViewById(R.id.order_detail_appointment_num_date);
        orderDetailGroup = findViewById(R.id.order_detail_group);
        orderDetailGroupAddress = findViewById(R.id.order_detail_group_address);
        orderDetailCellphone = findViewById(R.id.order_detail_cellphone);
        orderDetailCreateTime = findViewById(R.id.order_detail_create_time);
        orderItemExpandable = findViewById(R.id.order_item_expandable);
        payTotalMoney = findViewById(R.id.pay_total_money);
        realPayMoney = findViewById(R.id.real_pay_money);//实付金额
        waitingPayMoney = findViewById(R.id.waiting_pay_money);//待付金额
        tvPay = findViewById(R.id.tv_pay);

    }

    private void setViews(final ExamOrderDetail examOrderDetail) {
        if (examOrderDetail.getStatus().equals(GlobalConstant.MODE_RECEIVABLE)) {
            paymentStateText.setText("待支付");
            paymentStateText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        } else if (examOrderDetail.getStatus().equals(GlobalConstant.MODE_RECEIVED)) {
            paymentStateText.setText("已付款");
            paymentStateText.setTextColor(getResources().getColor(R.color.colorText));
        } else if (examOrderDetail.getStatus().equals(GlobalConstant.MODE_COMMENT)) {
            paymentStateText.setText("待评价");
            paymentStateText.setTextColor(getResources().getColor(R.color.colorText));
        }

        orderDetailExamPersonName.setText(examOrderDetail.getCustomer().getName());
        orderDetailAppointmentDate.setText(examOrderDetail.getCustomer().getReservation_or_registration().getDate());
        orderDetailAppointmentNumDate.setText(examOrderDetail.getCustomer().getReservation_or_registration().getId());
        orderDetailGroup.setText(examOrderDetail.getOrganization().getName());
        orderDetailGroupAddress.setText(examOrderDetail.getOrganization().getAddress());
        realPayMoney.setText("¥ " + examOrderDetail.getAmount().getReceived());
        waitingPayMoney.setText("¥ " + examOrderDetail.getAmount().getDiscount_receivable());

        OrderDetailExpandableListViewAdapter adapter = new OrderDetailExpandableListViewAdapter(this, examOrderDetail.getExam_packages());
        orderItemExpandable.setAdapter(adapter);

        //支付按钮
        tvPay.setOnClickListener(v -> {
            app.setOrderID(Integer.valueOf(examOrderDetail.getId()));
            Intent intent = new Intent(OrderDetailActivity.this, ModePaymentActivity.class);
            startActivity(intent);
        });
    }

    private void getOrderDetail(String id) {
        ExamOrderRepository.Companion.getDefault().getOrders(id).subscribe(new DefaultObserver<ExamOrderDetail>() {
            @Override
            public void onNext(ExamOrderDetail examOrderDetail) {
                setViews(examOrderDetail);
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

    private void showDialog(final String id) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认取消订单!");
        builder.setMessage("删除订单后，若要继续购买只能重新下单，请确认后取消!");
        builder.setNegativeButton("取消", (dialog12, which) -> {

        });

        builder.setPositiveButton("确认", (dialog1, which) -> {
            if (retrofit == null) {
                retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
            }

            NetService service = retrofit.create(NetService.class);
            service.cancelReservation(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.CANCEL_RESERVATION + id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.e("TAG", "预约删除成功!");
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG", "预约删除失败!:" + e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });

        dialog = builder.create();
        dialog.show();
    }
}
