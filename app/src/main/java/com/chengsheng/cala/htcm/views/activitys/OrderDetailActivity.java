package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.OrderDetailExpandableListViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.HeaderScrollView;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back;
    private TextView paymentStateText;
    private TextView orderDetailExamPersonName,orderDetailAppointmentDate,orderDetailAppointmentNumDate;
    private TextView copyAppointmentNum;
    private TextView orderDetailGroup,orderDetailGroupAddress,orderDetailCellphone,orderDetailCreateTime;
    private MyExpandableListView orderItemExpandable;
    private TextView payTotalMoney,realPayMoney,waitingPayMoney;
    private TextView payFunctionA,payFunctionB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initViews();

        OrderDetailExpandableListViewAdapter adapter = new OrderDetailExpandableListViewAdapter(this);
        orderItemExpandable.setAdapter(adapter);

        payFunctionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this,ModePaymentActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initViews(){
        title = findViewById(R.id.title_header_order_detail).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_order_detail).findViewById(R.id.back_login);
        paymentStateText = findViewById(R.id.payment_state_text);
        orderDetailExamPersonName = findViewById(R.id.order_detail_exam_person_name);
        orderDetailAppointmentDate = findViewById(R.id.order_detail_appointment_date);
        orderDetailAppointmentNumDate = findViewById(R.id.order_detail_appointment_num_date);
        copyAppointmentNum = findViewById(R.id.copy_appointment_num);
        orderDetailGroup = findViewById(R.id.order_detail_group);
        orderDetailGroupAddress = findViewById(R.id.order_detail_group_address);
        orderDetailCellphone = findViewById(R.id.order_detail_cellphone);
        orderDetailCreateTime = findViewById(R.id.order_detail_create_time);
        orderItemExpandable = findViewById(R.id.order_item_expandable);
        payTotalMoney = findViewById(R.id.pay_total_money);
        realPayMoney = findViewById(R.id.real_pay_money);
        waitingPayMoney = findViewById(R.id.waiting_pay_money);
        payFunctionA = findViewById(R.id.pay_function_a);
        payFunctionB = findViewById(R.id.pay_function_b);

        title.setText("体检订单详情");
    }
}
