package com.chengsheng.cala.htcm.module.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.BeforeExamAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.childmodelb.BeforeExam;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.QRCodeUtil;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

/**
 * 检前须知
 */
public class BeforeExamActivity extends BaseActivity {

    private TextView examDateDetailBefore, examAddressDetailBefore;
    private ImageView userCode;
    private TextView userNameBeforeExam, userSomeInfo;
    private MyRecyclerView beforeExamItems;

    private Retrofit retrofit;
    private HTCMApp app;

    @Override
    public int getLayoutId() {
        return R.layout.activity_before_exam;
    }

    @Override
    public void initView() {
        String examID = getIntent().getStringExtra("EXAM_ID");
        app = HTCMApp.create(getApplicationContext());

        initViews();

        getExamBeforeNotice(examID);
    }

    @Override
    public void getData() {

    }

    private void initViews() {

        examAddressDetailBefore = findViewById(R.id.exam_address_detail_before);
        examDateDetailBefore = findViewById(R.id.exam_date_detail_before);
        userCode = findViewById(R.id.user_code);
        userNameBeforeExam = findViewById(R.id.user_name_before_exam);
        userSomeInfo = findViewById(R.id.user_some_info);
        beforeExamItems = findViewById(R.id.before_exam_items);
    }

    private void setViews(BeforeExam beforeExam) {
        BeforeExamAdapter adapter = new BeforeExamAdapter(this, beforeExam.getItems());
        beforeExamItems.setNestedScrollingEnabled(false);
        beforeExamItems.setLayoutManager(new LinearLayoutManager(this));
        beforeExamItems.setAdapter(adapter);
        examDateDetailBefore.setText(beforeExam.getMeta().getExam_customer().getExam_or_registration().getDate());
        examAddressDetailBefore.setText(beforeExam.getMeta().getOrganization().getAddress());
        userNameBeforeExam.setText(beforeExam.getMeta().getExam_customer().getName());
        if (beforeExam.getMeta().getExam_customer().getSex().equals("female")) {
            userSomeInfo.setText("女" + " " + beforeExam.getMeta().getExam_customer().getAge() + "岁");
        } else {
            userSomeInfo.setText("男" + " " + beforeExam.getMeta().getExam_customer().getAge() + "岁");
        }

        userCode.setImageBitmap(QRCodeUtil.createQRImage(beforeExam.getMeta().getExam_customer().getExam_or_registration().getId(),
                FuncUtils.dip2px(150), FuncUtils.dip2px(150)));
    }

    private void getExamBeforeNotice(String examId) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        showLoading();
        NetService service = retrofit.create(NetService.class);
        service.getBeforeExamNotice(app.getTokenType() + " " + app.getAccessToken(), examId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BeforeExam>() {
                    @Override
                    public void onNext(BeforeExam beforeExam) {
                        setViews(beforeExam);
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            hideLoading();
                        }
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }
}
