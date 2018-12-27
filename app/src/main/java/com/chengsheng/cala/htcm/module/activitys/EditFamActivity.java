package com.chengsheng.cala.htcm.module.activitys;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.data.repository.MemberRepository;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.protocol.URLResult;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.CellPhoneInterface;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.StringUtils;
import com.chengsheng.cala.htcm.utils.ToastUtil;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

/**
 * Author: 蔡浪
 * CreateDate: 2018/12/26 9:44 AM
 * Description: 编辑家人信息
 */
public class EditFamActivity extends BaseActivity implements UpdateStateInterface,CellPhoneInterface {

    private AppTitleBar appTitleBar;
    private SimpleDraweeView header;
    private TextView nameMod, modAge, modeId, modTel;
    private Button femaleMod, maleMod;
    private TagFlowLayout famReMark;
    private EditText newMark;
    private Button saveMod;

    private RelativeLayout addItemB, addItemF, addItemD;

    private Calendar calendar;

    private String famID;
    private String[] relations = new String[]{"本人", "父亲", "母亲", "儿子", "女儿", "妻子", "丈夫", "其他"};//默认家人关系标签组
    private TagAdapter adapter;
    private boolean hasMark = true;
    private String currentMark = "";
    private String currentSex = "";
    private String currentAge = "";
    private String headerUri = "";
    private String currentCell = "";

    private Uri headerImageUri;

    private List<String> marks;

    private boolean hasMod = false;//默认没修改家人信息


    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_fam;
    }

    @Override
    public void initView() {
        CallBackDataAuth.setUpdateStateInterface(this);
        CallBackDataAuth.setCellPhoneInterface(this);
        famID = getIntent().getExtras().getString("FAM_ID");
        initViews();
    }

    @Override
    public void getData() {

        //获取家人信息
        MemberRepository
                .Companion.getDefault()
                .getFamInfo(famID)
                .subscribe(new DefaultObserver<FamiliesDetailInfo>() {
                    @Override
                    public void onNext(FamiliesDetailInfo familiesDetailInfo) {
                        setViews(familiesDetailInfo);
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

    private void initViews() {
        appTitleBar = findViewById(R.id.e_f_header);
        header = findViewById(R.id.header_mod);
        nameMod = findViewById(R.id.name_mod);
        modAge = findViewById(R.id.mod_age);
        modeId = findViewById(R.id.mode_id);
        modTel = findViewById(R.id.mod_tel);
        femaleMod = findViewById(R.id.female_mod);
        maleMod = findViewById(R.id.male_mod);
        famReMark = findViewById(R.id.fam_re_mark);
        newMark = findViewById(R.id.new_mark);
        saveMod = findViewById(R.id.save_mod);
        addItemB = findViewById(R.id.add_item_b);
        addItemF = findViewById(R.id.add_item_f);
        addItemD = findViewById(R.id.add_item_d);

        appTitleBar.setTitle("编辑家人信息");
        appTitleBar.setFinishClickListener(() -> finish());

        marks = new ArrayList<>();
        for (int i = 0; i < relations.length; i++) {
            marks.add(relations[i]);
        }
        adapter = new TagAdapter(marks);
        famReMark.setMaxSelectCount(1);

        famReMark.setAdapter(adapter);

    }

    private void setViews(FamiliesDetailInfo familiesDetailInfo) {
        header.setImageURI(familiesDetailInfo.getAvatar_path());
        nameMod.setText(familiesDetailInfo.getFullname());
        modAge.setText(familiesDetailInfo.getBirthday());

        setSexModel(familiesDetailInfo.getSex());

        currentSex = familiesDetailInfo.getSex();
        currentAge = familiesDetailInfo.getBirthday();
        currentMark = familiesDetailInfo.getOwner_relationship();
        headerUri = familiesDetailInfo.getAvatar_path();

        if (familiesDetailInfo.getId_card_no().isEmpty()) {
            modeId.setText("暂无");
        } else {
            modeId.setText(familiesDetailInfo.getId_card_no());
        }

        if(currentCell.equals("")){
            modTel.setText(familiesDetailInfo.getMobile());
            currentCell = familiesDetailInfo.getMobile();
        }else{
            modTel.setText(currentCell);
        }


        //
        femaleMod.setOnClickListener(v -> {
            if (!femaleMod.isSelected()) {
                setSexModel("female");
                familiesDetailInfo.setSex("female");
                hasMod = true;
            }
        });

        maleMod.setOnClickListener(v -> {
            if (!maleMod.isSelected()) {
                setSexModel("male");
                familiesDetailInfo.setSex("male");
                hasMod = true;
            }
        });

        for (int i = 0; i < relations.length - 1; i++) {
            if (relations[i].equals(familiesDetailInfo.getOwner_relationship())) {
                adapter.setSelectedList(i);
                hasMark = false;
            }
        }

        if (hasMark) {
            adapter.setSelectedList(relations.length - 1);
            newMark.setVisibility(View.VISIBLE);
            newMark.setText(familiesDetailInfo.getOwner_relationship());
        }

        //修改名字
        addItemB.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("MODE", "NAME");
            bundle.putSerializable("FAMILIES_INFO", familiesDetailInfo);
            ActivityUtil.Companion.startActivity(this, new ModeFamiliesInfoActivity(), bundle);
        });

        //修改身份证号码
        modeId.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("FAMILIES_INFO", familiesDetailInfo);
            bundle.putString("MODE", "ID");
            ActivityUtil.Companion.startActivity(this, new ModeFamiliesInfoActivity(), bundle);
        });

        //修改电话号码
        addItemF.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("MODE", "CELLPHONE,MODE_TEL");
            bundle.putSerializable("FAMILIES_INFO", familiesDetailInfo);
            ActivityUtil.Companion.startActivity(this, new ModeFamiliesInfoActivity(), bundle);
        });

        //修改出生年月
        addItemD.setOnClickListener(v -> setDate());

        //修改关系标签
        famReMark.setOnTagClickListener((view, position, parent) -> {
            if (position != (relations.length - 1)) {
                if (!relations[position].equals(currentMark)) {
                    currentMark = relations[position];
                    hasMod = true;
                }
            } else {
                newMark.setVisibility(View.VISIBLE);
                hasMod = true;
            }
            return true;
        });


        newMark.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !StringUtils.getText(newMark).isEmpty()) {
                marks.add(marks.size(), StringUtils.getText(newMark));
                adapter.setSelectedList(marks.size() - 2);
                currentMark = StringUtils.getText(newMark);
            }
        });

        //提交修改信息
        saveMod.setOnClickListener(v -> {
            if (hasMod) {
                if (!StringUtils.getText(newMark).isEmpty()) {
                    familiesDetailInfo.setOwner_relationship(StringUtils.getText(newMark));
                }
                familiesDetailInfo.setOwner_relationship(currentMark);
                uploadModeInfo(familiesDetailInfo);
            } else {
                CallBackDataAuth.doUpdateStateInterface(true);
                finish();
            }

        });

        header.setOnClickListener(v -> showPopwindow());

    }

    private void setSexModel(String sex) {
        maleMod.setText("男");
        femaleMod.setText("女");
        if (sex.equals("male")) {
            maleMod.setSelected(true);
            maleMod.setTextColor(getResources().getColor(R.color.colorWhite));
            femaleMod.setSelected(false);
            femaleMod.setTextColor(getResources().getColor(R.color.colorThrText));

        } else {
            maleMod.setSelected(false);
            maleMod.setTextColor(getResources().getColor(R.color.colorThrText));
            femaleMod.setSelected(true);
            femaleMod.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }


    private void setDate() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        DatePickerDialog timePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert, (view, year, month, dayOfMonth) -> {
            String current = FuncUtils.getCurrentTimeDay();
            currentAge = year + "-" + (month + 1) + "-" + dayOfMonth;
            if (FuncUtils.isDate2Bigger(current, currentAge)) {
                Toast.makeText(this, "出生日期选择有误 请重新选择", Toast.LENGTH_SHORT).show();
            } else {
                modAge.setText(currentAge);
                hasMod = true;
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        timePickerDialog.show();
    }

    //上传修改后的家人信息
    private void uploadModeInfo(FamiliesDetailInfo info) {

        Map<String, String> map = new HashMap<>();
        map.put("mobile", currentCell);
        map.put("fullname", info.getFullname());
        map.put("owner_relationship", info.getOwner_relationship());
        map.put("avatar_path", headerUri);
        map.put("sex", info.getSex());
        map.put("birthday", info.getBirthday());
        map.put("id_card_no", info.getId_card_no());

        MemberRepository
                .Companion.getDefault()
                .putFamInfo(String.valueOf(info.getId()), map)
                .subscribe(new DefaultObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        CallBackDataAuth.doUpdateStateInterface(true);
                        Toast.makeText(EditFamActivity.this, "修改关系成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToast(EditFamActivity.this, "提交失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //照片选则栏.
    private void showPopwindow() {
        final Dialog dialog = new Dialog(this, R.style.dialog_bottom);
        View view = View.inflate(this, R.layout.bottom_select_layout, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.get_photo_camera).setOnClickListener(view1 -> {
            File outputImage = new File(getExternalCacheDir(), "user_header.jpg");
            if (outputImage.exists()) {
                outputImage.delete();
            }

            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= 24) {
                headerImageUri = FileProvider.getUriForFile(this, "com.example.cameraalbumtest.fileprovider", outputImage);
            } else {
                headerImageUri = Uri.fromFile(outputImage);
            }

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, headerImageUri);
            startActivityForResult(intent, 1);
        });

        //从相册获取头像
        dialog.findViewById(R.id.get_photo_gra).setOnClickListener(view13 -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, 2);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.get_photo_cancel).setOnClickListener(view12 -> dialog.dismiss());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    header.setImageURI(headerImageUri);
                    updateHeader();
                    hasMod = true;
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    headerImageUri = data.getData();
                    header.setImageURI(headerImageUri);
                    updateHeader();
                    hasMod = true;
                }
            default:
        }
    }

    private void updateHeader() {

        String path = FuncUtils.getReal(this, headerImageUri);
        File file = new File(path);
        RequestBody descriptionString = RequestBody.create(MediaType.parse("multipart/form-data"), "avatar");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Map<String, RequestBody> mapa = new HashMap<>();
        mapa.put("bucket_name", descriptionString);

        Retrofit retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        HTCMApp app = HTCMApp.create(this);

        NetService service = retrofit.create(NetService.class);
        service.uploadFile(app.getTokenType() + " " + app.getAccessToken(), mapa, body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<URLResult>() {
                    @Override
                    public void onNext(URLResult urlResult) {
                        headerUri = urlResult.getFile_url();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showShortToast("上传图片失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    public void updateServiceMessage(boolean status) {
        if(status){
            getData();
        }
    }

    @Override
    public void setCellphone(String cell) {
        hasMod = true;
        currentCell = cell;
        modTel.setText(cell);
    }


    class TagAdapter extends com.zhy.view.flowlayout.TagAdapter<String> {

        public TagAdapter(List<String> datas) {

            super(datas);
        }

        @Override
        public void
        onSelected(int position, View view) {
            TextView textView = view.findViewById(R.id.select_families_mark);
            textView.setSelected(true);
            textView.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void unSelected(int position, View view) {
            TextView textView = view.findViewById(R.id.select_families_mark);
            textView.setSelected(false);
            textView.setTextColor(getResources().getColor(R.color.colorThrText));
        }

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            View rootView = LayoutInflater.from(EditFamActivity.this).inflate(R.layout.families_select_mark_bg_layout, null);
            TextView textView = rootView.findViewById(R.id.select_families_mark);
            textView.setText(s);
            return rootView;
        }

    }
}
