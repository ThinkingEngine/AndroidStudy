package com.chengsheng.cala.htcm.module.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.data.repository.AIAssistantRepository;
import com.chengsheng.cala.htcm.protocol.childmodelb.IntelligentCheck;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;
import com.chengsheng.cala.htcm.adapter.IntelligentCheckARecyclerAdapter;
import com.chengsheng.cala.htcm.adapter.IntelligentCheckBRecyclerAdapter;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;
import io.reactivex.observers.DefaultObserver;

public class IntelligentCheckActivity extends BaseActivity {
    private ImageView back;
    private TextView title;
    private ImageView barCodeMarkIntelligent;
    private TextView numberBarCodeIntelligent;
    private TextView itemPersonName, itemPersonSex, itemPersonAge;
    private MyRecyclerView intelligentCheckRecyclerA, intelligentCheckRecyclerB;
    private TextView checkedLine;

    private HTCMApp app;
    private String orderID;


    @Override
    public int getLayoutId() {
        return R.layout.activity_intelligent_check;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        orderID = getIntent().getStringExtra("EXAM_ID");

        initViews();

    }

    @Override
    public void getData() {
        getIntelligentCheckInfo(orderID);
    }

    private void initViews() {
        back = findViewById(R.id.title_header_intelligent_check).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_intelligent_check).findViewById(R.id.menu_bar_title);
        barCodeMarkIntelligent = findViewById(R.id.bar_code_mark_intelligent);
        numberBarCodeIntelligent = findViewById(R.id.number_bar_code_intelligent);
        itemPersonName = findViewById(R.id.item_person_name);
        itemPersonSex = findViewById(R.id.item_person_sex);
        itemPersonAge = findViewById(R.id.item_person_age);
        intelligentCheckRecyclerA = findViewById(R.id.intelligent_check_recycler_a);
        intelligentCheckRecyclerB = findViewById(R.id.intelligent_check_recycler_b);
        checkedLine = findViewById(R.id.checked_line);

        title.setText("智能导检");
    }

    private void setViews(IntelligentCheck intelligentCheck) {
        String id = intelligentCheck.getExam_customer().getExam_or_registration().getId();
        barCodeMarkIntelligent.setImageBitmap(QRCodeUtil.createBarcode(id, FuncUtils.dip2px(280), FuncUtils.dip2px(74)));
        numberBarCodeIntelligent.setText(id);
        itemPersonName.setText(intelligentCheck.getExam_customer().getName());

        if (intelligentCheck.getExam_customer().getSex().equals("female")) {
            itemPersonSex.setText("女");
        } else {
            itemPersonSex.setText("男");
        }

        itemPersonAge.setText(intelligentCheck.getExam_customer().getAge() + "岁");

        IntelligentCheckARecyclerAdapter adapter1 = new IntelligentCheckARecyclerAdapter(this, intelligentCheck.getUnexamined().getItems());
        IntelligentCheckBRecyclerAdapter adapter2 = new IntelligentCheckBRecyclerAdapter(this, intelligentCheck.getExamined().getItems());
        intelligentCheckRecyclerA.setLayoutManager(new LinearLayoutManager(this));
        intelligentCheckRecyclerB.setLayoutManager(new LinearLayoutManager(this));
        intelligentCheckRecyclerA.setSaveEnabled(false);
        intelligentCheckRecyclerB.setSaveEnabled(false);
        intelligentCheckRecyclerA.setAdapter(adapter1);
        intelligentCheckRecyclerB.setAdapter(adapter2);
    }

    private void getIntelligentCheckInfo(String orderId) {

        showLoading();

        AIAssistantRepository.Companion.getDefault().aiCheck(orderId).subscribe(new DefaultObserver<IntelligentCheck>() {
            @Override
            public void onNext(IntelligentCheck intelligentCheck) {
                setViews(intelligentCheck);
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                showError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
