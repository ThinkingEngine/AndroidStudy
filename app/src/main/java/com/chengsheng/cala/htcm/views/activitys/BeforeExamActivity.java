package com.chengsheng.cala.htcm.views.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.childmodelb.BeforeExam;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;
import com.chengsheng.cala.htcm.views.adapters.BeforeExamRecyclerViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyRecyclerView;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class BeforeExamActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private TextView examDateDetailBefore, examAddressDetailBefore;
    private ImageView userCode;
    private TextView userNameBeforeExam, userSomeInfo;
    private MyRecyclerView beforeExamItems;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HTCMApp app = HTCMApp.create(getApplicationContext());
        setContentView(R.layout.activity_before_exam);

        String examID = getIntent().getStringExtra("EXAM_ID");
        initViews();

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        Log.e("TAG","id:"+examID);
        service.getBeforeExamNotice(app.getTokenType() + " " + app.getAccessToken(), examID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BeforeExam>() {
                    @Override
                    public void onNext(BeforeExam beforeExam) {
                        Log.e("TAG", "检前须知数据请求成功:" + beforeExam.toString());
                        setViews(beforeExam);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "检前须知数据请求失败:" + body.string());
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

    private void initViews() {
        title = findViewById(R.id.title_header_before_exam).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_before_exam).findViewById(R.id.back_login);

        examAddressDetailBefore = findViewById(R.id.exam_address_detail_before);
        examDateDetailBefore = findViewById(R.id.exam_date_detail_before);
        userCode = findViewById(R.id.user_code);
        userNameBeforeExam = findViewById(R.id.user_name_before_exam);
        userSomeInfo = findViewById(R.id.user_some_info);
        beforeExamItems = findViewById(R.id.before_exam_items);

        title.setText("检前须知");
    }

    private void setViews(BeforeExam beforeExam) {
        BeforeExamRecyclerViewAdapter adapter = new BeforeExamRecyclerViewAdapter(this, beforeExam.getItems());
        beforeExamItems.setNestedScrollingEnabled(false);
        beforeExamItems.setLayoutManager(new LinearLayoutManager(this));
        beforeExamItems.setAdapter(adapter);
        examDateDetailBefore.setText(beforeExam.getMeta().getExam_customer().getExam_or_registration().getDate());
        examAddressDetailBefore.setText(beforeExam.getMeta().getOrganization().getAddress());
        userNameBeforeExam.setText(beforeExam.getMeta().getExam_customer().getName());
        if(beforeExam.getMeta().getExam_customer().getSex().equals("female")){
            userSomeInfo.setText("女"+" "+beforeExam.getMeta().getExam_customer().getAge()+"岁");
        }else{
            userSomeInfo.setText("男"+" "+beforeExam.getMeta().getExam_customer().getAge()+"岁");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userCode.setImageBitmap(QRCodeUtil.createQRImage(beforeExam.getMeta().getExam_customer().getExam_or_registration().getId(), 80, 80));
    }
}
