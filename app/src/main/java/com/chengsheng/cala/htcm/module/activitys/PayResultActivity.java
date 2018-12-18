package com.chengsheng.cala.htcm.module.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.FuncUtils;

public class PayResultActivity extends BaseActivity {
    private TextView title, payResultText, orderFormPayMode, orderFormDate;
    private ImageView back, payResultMark;
    private Button lookOrderForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int payResult = bundle.getInt(GlobalConstant.PAY_MARK);
        Log.e("TAG", "结果：" + payResult);

        Toast.makeText(this, "支付结果" + payResult, Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.title_header_pay_result).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_pay_result).findViewById(R.id.back_login);

        payResultText = findViewById(R.id.pay_result_text);
        orderFormPayMode = findViewById(R.id.order_form_pay_mode);
        orderFormDate = findViewById(R.id.order_form_date);
        payResultMark = findViewById(R.id.pay_result_mark);
        lookOrderForm = findViewById(R.id.look_order_form);

        if (payResult == GlobalConstant.PAY_COMPLETE) {
            payResultMark.setImageResource(R.mipmap.zhineng_chenggong);
            title.setText("支付成功");
            lookOrderForm.setVisibility(View.VISIBLE);
            payResultText.setText("订单支付成功!");
            payResultText.setTextColor(getResources().getColor(R.color.colorPaySuccess));
        } else {
            payResultMark.setImageResource(R.mipmap.zhineng_shibai);
            title.setText("支付失败");
            lookOrderForm.setVisibility(View.INVISIBLE);
            payResultText.setText("订单支付失败!");
            payResultText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        orderFormDate.setText("下单时间：" + FuncUtils.getCurrentTime());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
}
