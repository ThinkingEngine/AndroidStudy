package com.chengsheng.cala.htcm.views.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.model.datamodel.URLResult;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.views.dialog.ImmediatelyDialogView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class FamiliesDetailsActivity extends BaseActivity implements UpdateStateInterface {
    private ImageView backButton;
    private TextView title;
    private TextView unbundle;
    private TextView healthCardNum, authenticationMark;
    private ImageView inputChangeHeaderIcon,
            inputChangeName,
            inputChangeAge,
            inputChangeIdNum,
            inputChangeCellphone,
            inputChangeRelation;
    private SimpleDraweeView familiesHeaderIconHad;
    private TextView familiesNameHad;
    private Button sexSelecterMale, sexSelecterFemale;
    private TextView familiesSexHad;
    private TextView familiesAgeHad;
    private TextView familiesIdNumHad;
    private TextView familiesTelNumHad;
    private TextView familiesRelationHad;

    private Uri headerImageUri;
    private String familiesID;

    private String sex = "male";
    private Calendar calendar;

    private HTCMApp app;
    private Retrofit familiesDetailRetrofit;
    private ZLoadingDialog loadingDialog;

    private FamiliesDetailInfo familiesDetailInfo;

    private String unbundleFamilies = "api/family/account-family-members/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = HTCMApp.create(getApplicationContext());
        familiesID = getIntent().getStringExtra("FAMILIES_ID");
        CallBackDataAuth.setUpdateStateInterface(this);
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintText("更新中....");
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));

        setContentView(R.layout.activity_families_details);

        //获取家人详细信息。
        getFamiliesInfo();
        //初始化界面。
        initViews();


        //认证家人
        authenticationMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImmediatelyDialogView immediatelyDialogView = new ImmediatelyDialogView(FamiliesDetailsActivity.this, familiesDetailInfo.getId());
                immediatelyDialogView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                immediatelyDialogView.show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViews() {
        backButton = findViewById(R.id.title_header_families_details).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_families_details).findViewById(R.id.menu_bar_title);
        unbundle = findViewById(R.id.title_header_families_details).findViewById(R.id.message_mark_text);
        healthCardNum = findViewById(R.id.health_card_num);
        authenticationMark = findViewById(R.id.authentication_mark);//立即认证 按钮
        inputChangeHeaderIcon = findViewById(R.id.input_change_header_icon);//
        inputChangeName = findViewById(R.id.input_change_name);//修改家人姓名
        inputChangeAge = findViewById(R.id.input_change_age);
        inputChangeIdNum = findViewById(R.id.input_change_id_num);
        inputChangeCellphone = findViewById(R.id.input_change_cellphone);//修改手机号码按钮
        inputChangeRelation = findViewById(R.id.input_change_relation);//修改家人关系按钮
        familiesHeaderIconHad = findViewById(R.id.families_header_icon_had);
        familiesNameHad = findViewById(R.id.families_name_had);
        sexSelecterMale = findViewById(R.id.sex_selecter_male);
        sexSelecterFemale = findViewById(R.id.sex_selecter_female);
        familiesSexHad = findViewById(R.id.families_sex_had);
        familiesAgeHad = findViewById(R.id.families_age_had);
        familiesIdNumHad = findViewById(R.id.families_id_num_had);
        familiesTelNumHad = findViewById(R.id.families_tel_num_had);
        familiesRelationHad = findViewById(R.id.families_relation_had);

        title.setText("家人详情");
        unbundle.setText("解绑");

        setSexModel(sex);

        sexSelecterMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexSelecterMale.setSelected(true);
                sexSelecterMale.setTextColor(getResources().getColor(R.color.colorWhite));
                sexSelecterFemale.setSelected(false);
                sexSelecterFemale.setTextColor(getResources().getColor(R.color.colorThrText));
            }
        });

        sexSelecterFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexSelecterMale.setSelected(false);
                sexSelecterMale.setTextColor(getResources().getColor(R.color.colorThrText));
                sexSelecterFemale.setSelected(true);
                sexSelecterFemale.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });
    }

    private void setViews(final FamiliesDetailInfo info) {
        if (info.isIs_auth()) {
            authenticationMark.setVisibility(View.INVISIBLE);
            inputChangeHeaderIcon.setVisibility(View.VISIBLE);
            inputChangeName.setVisibility(View.INVISIBLE);
            inputChangeAge.setVisibility(View.INVISIBLE);
            inputChangeIdNum.setVisibility(View.INVISIBLE);
            inputChangeCellphone.setVisibility(View.INVISIBLE);
            sexSelecterMale.setVisibility(View.INVISIBLE);
            sexSelecterFemale.setVisibility(View.INVISIBLE);
            familiesSexHad.setVisibility(View.VISIBLE);
            healthCardNum.setText(info.getHealth_card_no());
            if (info.getSex().equals("male")) {
                familiesSexHad.setText("男");
            } else {
                familiesSexHad.setText("女");
            }
        } else {
            authenticationMark.setVisibility(View.VISIBLE);
            inputChangeHeaderIcon.setVisibility(View.VISIBLE);
            inputChangeName.setVisibility(View.VISIBLE);
            inputChangeAge.setVisibility(View.VISIBLE);
            inputChangeIdNum.setVisibility(View.VISIBLE);
            inputChangeCellphone.setVisibility(View.VISIBLE);
            sexSelecterMale.setVisibility(View.VISIBLE);
            sexSelecterFemale.setVisibility(View.VISIBLE);
            familiesSexHad.setVisibility(View.INVISIBLE);
            healthCardNum.setText("无");
        }

        familiesNameHad.setText(info.getFullname());
        familiesAgeHad.setText(info.getBirthday());
        familiesIdNumHad.setText(info.getId_card_no());
        familiesTelNumHad.setText(info.getMobile());
        familiesRelationHad.setText(info.getOwner_relationship());
        familiesHeaderIconHad.setImageURI(info.getAvatar_path());

        setSexModel(info.getSex());

        //解绑家人
        unbundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FamiliesDetailsActivity.this);
                builder.setTitle("提示");
                builder.setMessage("解绑后您将不能再为【" + info.getFullname() + "】购买套餐或查看其体检报告，确认解绑吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String familiesID = String.valueOf(familiesDetailInfo.getId());
                        NetService service1 = familiesDetailRetrofit.create(NetService.class);
                        service1.deleteFamilies(unbundleFamilies + familiesID, app.getTokenType() + " " + app.getAccessToken())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableObserver<ResponseBody>() {
                                    @Override
                                    public void onNext(ResponseBody responseBody) {
                                        CallBackDataAuth.doAuthStateCallBack(true);
                                        Toast.makeText(FamiliesDetailsActivity.this, "解绑操作完成", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (e instanceof HttpException) {
                                            ResponseBody body = ((HttpException) e).response().errorBody();
                                            try {
                                                Log.e("DETE", body.string());
                                                Toast.makeText(FamiliesDetailsActivity.this, "解绑操作失败", Toast.LENGTH_SHORT).show();
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
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FamiliesDetailsActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        //点击进入修改手机号码界面.
        inputChangeCellphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FAMILIES_INFO", info);
                intent.putExtras(bundle);
                intent.putExtra("MODE", "CELLPHONE");
                startActivity(intent);
            }
        });

        //点击进入修改家人关系页面
        inputChangeRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FAMILIES_INFO", info);
                intent.putExtras(bundle);
                intent.putExtra("MODE", "RELATION");
                startActivity(intent);
            }
        });

        inputChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FAMILIES_INFO", info);
                intent.putExtras(bundle);
                intent.putExtra("MODE", "NAME");
                startActivity(intent);
            }
        });

        inputChangeIdNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FAMILIES_INFO", info);
                intent.putExtras(bundle);
                intent.putExtra("MODE", "ID");
                startActivity(intent);
            }
        });

        sexSelecterMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sexSelecterMale.isSelected()) {
                    dialog("提示", "你确认修改性别!", "sex", "male");
                }
            }
        });

        sexSelecterFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sexSelecterFemale.isSelected()) {
                    dialog("提示", "你确认修改性别!", "sex", "female");
                }
            }
        });

        inputChangeAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(FamiliesDetailsActivity.this);
                builder.setTitle("提示");
                builder.setMessage("您确认要更改你的出生年月!");
                builder.setNegativeButton("暂不", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateBrithday();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

    }

    //获取家人信息
    private void getFamiliesInfo() {

        if (familiesDetailRetrofit == null) {
            familiesDetailRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        loadingDialog.show();
        NetService service = familiesDetailRetrofit.create(NetService.class);
        service.getFamiliesDetail(GlobalConstant.FAMILIES_INFO + familiesID, app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FamiliesDetailInfo>() {
                    @Override
                    public void onNext(final FamiliesDetailInfo info) {
                        Log.e("FAMILIES", "家人详细信息 数据请求成功:" + info.toString());
                        familiesDetailInfo = info;
                        app.setFamiliesDetailInfo(info);
                        setViews(info);
                        loadingDialog.cancel();
                        //修改家人头像
                        inputChangeHeaderIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modeInfoDialog("头像", info);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(FamiliesDetailsActivity.this, "信息请求失败！", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });

    }


    //
    private void modeInfoDialog(String message, FamiliesDetailInfo info) {
        AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("家人信息修改");
        builder.setMessage("你确定要修改" + info.getFullname() + "的" + message + "?");
        builder.setNegativeButton("暂不", null);
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showPopwindow();
            }
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
        loadingDialog.show();
        service.uploadFile(app.getTokenType() + " " + app.getAccessToken(), mapa, body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<URLResult>() {
                    @Override
                    public void onNext(URLResult urlResult) {
                        loadingDialog.cancel();
                        Toast.makeText(FamiliesDetailsActivity.this, "上传新头像成功！", Toast.LENGTH_SHORT).show();
                        updateFamiliesInfo(urlResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("UP", "上传图片失败:" + e);
                        loadingDialog.cancel();
                        Toast.makeText(FamiliesDetailsActivity.this, "上传图片失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

    //修改头像弹窗框.
    private void showPopwindow() {
        final Dialog dialog = new Dialog(this, R.style.dialog_bottom);
        View view = View.inflate(this, R.layout.bottom_select_layout, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.get_photo_camera).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                File outputImage = new File(FamiliesDetailsActivity.this.getExternalCacheDir(), "user_header.jpg");
                if (outputImage.exists()) {
                    outputImage.delete();
                }

                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    headerImageUri = FileProvider.getUriForFile(FamiliesDetailsActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
                } else {
                    headerImageUri = Uri.fromFile(outputImage);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, headerImageUri);
                startActivityForResult(intent, 1);
            }
        });

        //从相册获取头像
        dialog.findViewById(R.id.get_photo_gra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(FamiliesDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FamiliesDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
                    dialog.dismiss();
                }
            }
        });

        dialog.findViewById(R.id.get_photo_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    headerImageUri = data.getData();
                    familiesHeaderIconHad.setImageURI(headerImageUri);
                    updateHeader();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    headerImageUri = data.getData();
                    familiesHeaderIconHad.setImageURI(headerImageUri);
                    updateHeader();
                }
            default:
        }
    }

    //上传更新家人信息
    private void updateFamiliesInfo(URLResult urlResult) {
        if (familiesDetailRetrofit == null) {
            familiesDetailRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        FamiliesDetailInfo info = app.getFamiliesDetailInfo();
        NetService service = familiesDetailRetrofit.create(NetService.class);
        Map<String, String> data = daoTOmap(info, "avatar_path", urlResult.getFile_url());
        loadingDialog.show();
        service.modeFamiliesInfo(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.MODE_FAMILIES + info.getId(), data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loadingDialog.cancel();
                        Toast.makeText(FamiliesDetailsActivity.this, "头像更新成功！", Toast.LENGTH_SHORT).show();
                        CallBackDataAuth.doAuthStateCallBack(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(FamiliesDetailsActivity.this, "头像更新失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            getFamiliesInfo();
        }
    }

    private void dialog(String title, String message, final String key, final String val) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("暂不", null);
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateInfo(key, val);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }

    private void updateInfo(String key, String val) {
        if (familiesDetailRetrofit == null) {
            familiesDetailRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        FamiliesDetailInfo info = app.getFamiliesDetailInfo();
        Map<String, String> map = daoTOmap(info, key, val);

        NetService service = familiesDetailRetrofit.create(NetService.class);
        loadingDialog.show();
        service.modeFamiliesInfo(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.MODE_FAMILIES + info.getId(), map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loadingDialog.cancel();
                        getFamiliesInfo();
                        Toast.makeText(FamiliesDetailsActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(FamiliesDetailsActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });

    }

    private void updateBrithday() {

        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        DatePickerDialog timePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String brith = year + "-" + (month + 1) + "-" + dayOfMonth;
                updateInfo("birthday", brith);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog.show();
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
}
