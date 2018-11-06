package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class PayResultActivity extends AppCompatActivity {
    private TextView title,payResultText,orderFormPayMode,orderFormDate;
    private ImageView back,payResultMark;
    private Button lookOrderForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);

        title = findViewById(R.id.title_header_pay_result).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_pay_result).findViewById(R.id.back_login);

        payResultText = findViewById(R.id.pay_result_text);
        orderFormPayMode = findViewById(R.id.order_form_pay_mode);
        orderFormDate = findViewById(R.id.order_form_date);
        payResultMark = findViewById(R.id.pay_result_mark);
        lookOrderForm = findViewById(R.id.look_order_form);

        title.setText("支付成功");
    }
}
