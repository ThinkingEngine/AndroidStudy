package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodelb.IntelligentCheck;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;
import com.chengsheng.cala.htcm.adapter.IntelligentCheckARecyclerAdapter;
import com.chengsheng.cala.htcm.adapter.IntelligentCheckBRecyclerAdapter;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class IntelligentCheckActivity extends BaseActivity {
    private ImageView back;
    private TextView title;
    private ImageView barCodeMarkIntelligent;
    private TextView numberBarCodeIntelligent;
    private TextView itemPersonName, itemPersonSex, itemPersonAge;
    private MyRecyclerView intelligentCheckRecyclerA, intelligentCheckRecyclerB;
    private TextView checkedLine;

    private Retrofit retrofit;
    private HTCMApp app;
    private ZLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = HTCMApp.create(getApplicationContext());
        String orderID = getIntent().getStringExtra("EXAM_ID");
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintText("加载中");
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));

        setContentView(R.layout.activity_intelligent_check);

        initViews();
        getIntelligentCheckInfo(orderID);

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

    private void setViews(IntelligentCheck intelligentCheck){
        String id = intelligentCheck.getExam_customer().getExam_or_registration().getId();
        barCodeMarkIntelligent.setImageBitmap(QRCodeUtil.createBarcode(id,FuncUtils.dip2px(280),FuncUtils.dip2px(74)));
        numberBarCodeIntelligent.setText(id);
        itemPersonName.setText(intelligentCheck.getExam_customer().getName());

        if(intelligentCheck.getExam_customer().getSex().equals("female")){
            itemPersonSex.setText("女");
        }else{
            itemPersonSex.setText("男");
        }

        itemPersonAge.setText(intelligentCheck.getExam_customer().getAge()+"岁");

        IntelligentCheckARecyclerAdapter adapter1 = new IntelligentCheckARecyclerAdapter(this, intelligentCheck.getUnexamined().getItems());
        IntelligentCheckBRecyclerAdapter adapter2 = new IntelligentCheckBRecyclerAdapter(this, intelligentCheck.getExamined().getItems());
        intelligentCheckRecyclerA.setLayoutManager(new LinearLayoutManager(this));
        intelligentCheckRecyclerB.setLayoutManager(new LinearLayoutManager(this));
        intelligentCheckRecyclerA.setSaveEnabled(false);
        intelligentCheckRecyclerB.setSaveEnabled(false);
        intelligentCheckRecyclerA.setAdapter(adapter1);
        intelligentCheckRecyclerB.setAdapter(adapter2);
    }

    private void getIntelligentCheckInfo(String orderId){

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.getIntelligentCheckInfo(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.INTELLIGENT_CHECK+ orderId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<IntelligentCheck>() {
                    @Override
                    public void onNext(IntelligentCheck userExamDetail) {
                        setViews(userExamDetail);
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "智能体检数据请求失败:" + body.string());
                                loadingDialog.cancel();
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
