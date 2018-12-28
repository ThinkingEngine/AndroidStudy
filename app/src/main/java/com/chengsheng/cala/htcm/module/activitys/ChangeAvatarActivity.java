package com.chengsheng.cala.htcm.module.activitys;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.data.repository.UpLoadFileRepository;
import com.chengsheng.cala.htcm.protocol.URLResult;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.widget.ChangeAvatarDialog;
import com.chengsheng.cala.htcm.widget.IChoicePictureListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:54 AM
 * Description: 修改用户头像
 */
public class ChangeAvatarActivity extends BaseActivity {

    private SimpleDraweeView header;
    private Uri headerImageUri;
    private ChangeAvatarDialog changeAvatarDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_avatar;
    }

    @Override
    public void initView() {
        AppTitleBar titleBar = findViewById(R.id.titleBar);
        String uri = getIntent().getStringExtra("avatar_url");
        header = findViewById(R.id.user_icon_big);
        header.setImageURI(uri);

        titleBar.setRightTxtClickListener(this::showChoicePicturePop);
    }

    @Override
    public void getData() {

    }

    private void showChoicePicturePop() {
        if (changeAvatarDialog == null) {
            changeAvatarDialog = new ChangeAvatarDialog(this);
        }

        changeAvatarDialog.setChoiceListener(new IChoicePictureListener() {
            @Override
            public void onTakePhoto() {
                takePhoto();
            }

            @Override
            public void onChoiceFromGallery() {
                choiceFromGallery();
            }
        }).showDialog();
    }

    private void takePhoto() {
        File outputImage = new File(ChangeAvatarActivity.this.getExternalCacheDir(), "user_header.jpg");
        if (outputImage.exists()) {
            outputImage.delete();
        }

        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            headerImageUri = FileProvider.getUriForFile(ChangeAvatarActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            headerImageUri = Uri.fromFile(outputImage);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, headerImageUri);
        startActivityForResult(intent, 1);
    }

    private void choiceFromGallery() {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new DefaultObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent("android.intent.action.GET_CONTENT");
                            intent.setType("image/*");
                            startActivityForResult(intent, 2);
                        } else {
                            showShortToast("请先打开读写权限");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    header.setImageURI(headerImageUri);
                    String path = FuncUtils.getReal(this, headerImageUri);
                    upLoad(new File(path));
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    headerImageUri = data.getData();
                    header.setImageURI(headerImageUri);
                    String path = FuncUtils.getReal(this, headerImageUri);
                    upLoad(new File(path));
                }
        }
    }

    private void upLoad(File file) {
        showLoading();
        UpLoadFileRepository.Companion.getDefault().upLoad(file)
                .subscribe(new DefaultObserver<URLResult>() {
                    @Override
                    public void onNext(URLResult urlResult) {
                        hideLoading();
                        EventBus.getDefault().post(urlResult.getFile_url(), "updateAvatarSuc");
                        finish();
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
