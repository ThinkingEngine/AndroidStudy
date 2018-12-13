package com.chengsheng.cala.htcm.views.activitys;

import android.Manifest;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;

public class HeaderIconActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private TextView childTitle;
    private SimpleDraweeView header;


    private Uri headerImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_icon);

        String uri = getIntent().getStringExtra("USER_INFO");

        title = findViewById(R.id.title_header_header_icon).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_header_icon).findViewById(R.id.back_login);
        childTitle = findViewById(R.id.title_header_header_icon).findViewById(R.id.message_mark_text);
        header = findViewById(R.id.user_icon_big);

        title.setText("头像显示");
        childTitle.setText("更新头像");
        childTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header.setImageURI(uri);


    }

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
                File outputImage = new File(HeaderIconActivity.this.getExternalCacheDir(), "user_header.jpg");
                if (outputImage.exists()) {
                    outputImage.delete();
                }

                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    headerImageUri = FileProvider.getUriForFile(HeaderIconActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
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
                if (ContextCompat.checkSelfPermission(HeaderIconActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HeaderIconActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
                    header.setImageURI(headerImageUri);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    headerImageUri = data.getData();
                    header.setImageURI(headerImageUri);
                }
            default:
        }
    }
}
