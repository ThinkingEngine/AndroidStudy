package com.chengsheng.cala.htcm.module.activitys;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.data.repository.MemberRepository;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.protocol.Message;
import com.chengsheng.cala.htcm.protocol.URLResult;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class FamiliesDetailsActivity extends BaseActivity implements UpdateStateInterface {
    private ImageView backButton;
    private TextView title;
    private TextView unbundle;
    private TextView healthCardNum;
    private ImageView authenticationMark;
    private SimpleDraweeView familiesHeaderIconHad;
    private TextView familiesNameHad;
    private Button sexSelecterMale, sexSelecterFemale;
    private TextView familiesAgeHad;
    private TextView familiesIdNumHad;
    private TextView familiesTelNumHad;
    private TextView familiesRelationHad;
    private Button defaultExam, editFamInfo;
    private RelativeLayout healthCardBt;

    private Uri headerImageUri;
    private String familiesID;

    private String sex = "male";
    private Calendar calendar;

    private HTCMApp app;
    private Retrofit familiesDetailRetrofit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_families_details;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());

        familiesID = getIntent().getStringExtra("FAMILIES_ID");

        CallBackDataAuth.setUpdateStateInterface(this);

        //初始化界面。
        initViews();

    }

    @Override
    public void getData() {
        //获取家人详细信息。
        getFamiliesInfo();
    }


    private void setViews(final FamiliesDetailInfo info) {

        familiesNameHad.setText(info.getFullname());
        familiesAgeHad.setText(info.getBirthday());
        familiesIdNumHad.setText(info.getId_card_no());
        familiesTelNumHad.setText(info.getMobile());
        familiesRelationHad.setText(info.getOwner_relationship());
        familiesHeaderIconHad.setImageURI(info.getAvatar_path());
        healthCardNum.setText(info.getHealth_card_no());

        setSexModel(info.getSex());

        if (info.isIs_default()) {
            defaultExam.setTextColor(getResources().getColor(R.color.colorThrText));
        } else {
            defaultExam.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        defaultExam.setOnClickListener(v -> {
            if (!info.isIs_default()) {
                setDefault(info);
            }
        });

        //解绑家人
        unbundle.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FamiliesDetailsActivity.this);
            builder.setTitle("提示");
            builder.setMessage("解绑后您将不能再为【" + info.getFullname() + "】购买套餐或查看其体检报告，确认解绑吗？");
            builder.setPositiveButton("确认", (dialog, which) -> unbundleFam());
            builder.setNegativeButton("取消", (dialog, which) -> Toast.makeText(FamiliesDetailsActivity.this, "取消操作", Toast.LENGTH_SHORT).show());
            builder.show();
        });

        editFamInfo.setOnClickListener(v -> modeInfoDialog(info));

        healthCardBt.setOnClickListener(v -> {
            FamiliesListItem familiesListItem = new FamiliesListItem();
            familiesListItem.setHealth_card_no(info.getHealth_card_no());
            familiesListItem.setAvatar_path(info.getAvatar_path());
            familiesListItem.setFullname(info.getFullname());
            Bundle bundle = new Bundle();
            bundle.putSerializable("FAMILIES_INFO", familiesListItem);
            ActivityUtil.Companion.startActivity(this, new UserCardActivity(), bundle);
        });

    }

    //获取家人信息
    private void getFamiliesInfo() {
        MemberRepository.Companion.getDefault().getFamInfo(familiesID).subscribe(new DefaultObserver<FamiliesDetailInfo>() {
            @Override
            public void onNext(FamiliesDetailInfo info) {
                app.setFamiliesDetailInfo(info);
                setViews(info);
                //修改家人头像
//                inputChangeHeaderIcon.setOnClickListener(v -> modeInfoDialog("头像", info));
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(FamiliesDetailsActivity.this, "信息请求失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });


    }

    private void modeInfoDialog(FamiliesDetailInfo info) {
        AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("当前家人已经绑定手机号，修改相关信息将先通过短信验证，是否发送验证码到" + info.getMobile() + "?");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("发送", (dialog1, which) -> {
            MemberRepository
                    .Companion.getDefault()
                    .sendModCode(familiesID)
                    .subscribe(new DefaultObserver<Message>() {
                        @Override
                        public void onNext(Message message) {
                            showShortToast("已成功发送验证请求！请耐心等待验证码短信");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("FAMILIES_INFO", info);
                            bundle.putString("MODE", "CELLPHONE" + ",MODE_FAM");
                            FuncUtils.putString("TEMP_UUID", message.getUuid());
                            ActivityUtil.Companion.startActivity(FamiliesDetailsActivity.this, new ModeFamiliesInfoActivity(), bundle);
                        }

                        @Override
                        public void onError(Throwable e) {
                            showShortToast("验证请求失败!请重试");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });

        dialog = builder.create();
        dialog.show();
    }


    //上传头像图片
    private void updateHeader() {

        if (familiesDetailRetrofit == null) {
            familiesDetailRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        String path = FuncUtils.getReal(this, headerImageUri);
        File file = new File(path);
        RequestBody descriptionString = RequestBody.create(MediaType.parse("multipart/form-data"), "avatar");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Map<String, RequestBody> mapa = new HashMap<>();
        mapa.put("bucket_name", descriptionString);

        NetService service = familiesDetailRetrofit.create(NetService.class);
        service.uploadFile(app.getTokenType() + " " + app.getAccessToken(), mapa, body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<URLResult>() {
                    @Override
                    public void onNext(URLResult urlResult) {
                        Toast.makeText(FamiliesDetailsActivity.this, "上传新头像成功！", Toast.LENGTH_SHORT).show();
                        updateFamiliesInfo(urlResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("UP", "上传图片失败:" + e);
                        Toast.makeText(FamiliesDetailsActivity.this, "上传图片失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    //设置默认就诊人
    private void setDefault(FamiliesDetailInfo info) {
        MemberRepository
                .Companion.getDefault()
                .setDefault(familiesID)
                .subscribe(new DefaultObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        defaultExam.setTextColor(getResources().getColor(R.color.colorThrText));
                        showShortToast("你已成功设置" + info.getFullname() + "为默认就诊人");
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

    //上传更新家人信息
    private void updateFamiliesInfo(URLResult urlResult) {
        if (familiesDetailRetrofit == null) {
            familiesDetailRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        FamiliesDetailInfo info = app.getFamiliesDetailInfo();
        NetService service = familiesDetailRetrofit.create(NetService.class);
        Map<String, String> data = daoTOmap(info, "avatar_path", urlResult.getFile_url());
        service.modeFamiliesInfo(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.MODE_FAMILIES + info.getId(), data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Toast.makeText(FamiliesDetailsActivity.this, "头像更新成功！", Toast.LENGTH_SHORT).show();
                        CallBackDataAuth.doAuthStateCallBack(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(FamiliesDetailsActivity.this, "头像更新失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            getData();
        }
    }


    private void unbundleFam() {
        MemberRepository.Companion.getDefault().delFam(familiesID).subscribe(new DefaultObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
//                CallBackDataAuth.doAuthStateCallBack(true);
                showShortToast("解绑操作完成");
                finish();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast("解绑操作失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void updateInfo(String key, String val) {
        if (familiesDetailRetrofit == null) {
            familiesDetailRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        FamiliesDetailInfo info = app.getFamiliesDetailInfo();
        Map<String, String> map = daoTOmap(info, key, val);

        NetService service = familiesDetailRetrofit.create(NetService.class);
        service.modeFamiliesInfo(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.MODE_FAMILIES + info.getId(), map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getFamiliesInfo();
                        Toast.makeText(FamiliesDetailsActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(FamiliesDetailsActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    private Map<String, String> daoTOmap(FamiliesDetailInfo info, String key, String val) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", info.getMobile());
        map.put("fullname", info.getFullname());
        map.put("owner_relationship", info.getOwner_relationship());
        map.put("avatar_path", info.getAvatar_path());
        map.put("sex", info.getSex());
        map.put("birthday", info.getBirthday());
        map.put("id_card_no", info.getId_card_no());

        map.put(key, val);
        return map;
    }

    private void setSexModel(String sex) {
        sexSelecterMale.setText("男");
        sexSelecterFemale.setText("女");
        if (sex.equals("male")) {
            sexSelecterMale.setSelected(true);
            sexSelecterMale.setTextColor(getResources().getColor(R.color.colorWhite));
            sexSelecterFemale.setSelected(false);
            sexSelecterFemale.setTextColor(getResources().getColor(R.color.colorThrText));

        } else {
            sexSelecterMale.setSelected(false);
            sexSelecterMale.setTextColor(getResources().getColor(R.color.colorThrText));
            sexSelecterFemale.setSelected(true);
            sexSelecterFemale.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void initViews() {
        backButton = findViewById(R.id.title_header_families_details).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_families_details).findViewById(R.id.menu_bar_title);
        unbundle = findViewById(R.id.title_header_families_details).findViewById(R.id.message_mark_text);
        healthCardNum = findViewById(R.id.health_card_num);
        authenticationMark = findViewById(R.id.authentication_mark);//
        familiesHeaderIconHad = findViewById(R.id.families_header_icon_had);
        familiesNameHad = findViewById(R.id.families_name_had);
        sexSelecterMale = findViewById(R.id.sex_selecter_male);
        sexSelecterFemale = findViewById(R.id.sex_selecter_female);
        familiesAgeHad = findViewById(R.id.families_age_had);
        familiesIdNumHad = findViewById(R.id.families_id_num_had);
        familiesTelNumHad = findViewById(R.id.families_tel_num_had);
        familiesRelationHad = findViewById(R.id.families_relation_had);
        editFamInfo = findViewById(R.id.edit_fam_info);
        defaultExam = findViewById(R.id.default_exam);

        healthCardBt = findViewById(R.id.health_card_bt);

        title.setText("家人详情");
        unbundle.setText("解绑");
        backButton.setOnClickListener(v -> finish());

    }
}
