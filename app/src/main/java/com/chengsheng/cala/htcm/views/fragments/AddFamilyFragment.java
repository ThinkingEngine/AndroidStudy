package com.chengsheng.cala.htcm.views.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.Message;
import com.chengsheng.cala.htcm.model.datamodel.URLResult;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.lujun.androidtagview.TagContainerLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.gujun.android.taggroup.TagGroup;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.Url;

import static android.app.Activity.RESULT_OK;


public class AddFamilyFragment extends Fragment {

    private SimpleDraweeView selectHeaderIcon;
    private EditText inputFamiliesName;
    private Button maleMark, femaleMark;
    private EditText inputFamiliesAge;
    private EditText inputFamiliesIdNum;
    private EditText inputFamiliesTel;
    private EditText inputFamiliesCode;
    private Button getCode;
    private TagGroup familiesRelationSelecter;
    private Button commitFamiliesInfoButton;


    private String[] relations = new String[]{"本人", "父亲", "母亲", "儿子", "女儿", "妻子", "丈夫", "其他"};
    private String phoneHasCode = "";
    private Uri headerImageUri;
    private boolean isSelectHeader = false;
    private String sex;
    private String familiesTag = "";//家人关系标签

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MyRetrofit myRetrofit;
    private Retrofit getCodeRetrofit;

    private String uuid;

    private String mParam1;
    private String mParam2;

    private OnAddFamilyFragmentInteractionListener mListener;

    public AddFamilyFragment() {
        // Required empty public constructor
    }

    public static AddFamilyFragment newInstance(String param1, String param2) {
        AddFamilyFragment fragment = new AddFamilyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        myRetrofit = MyRetrofit.createInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootViews = inflater.inflate(R.layout.fragment_add_family, container, false);
        initViews(rootViews);

        maleMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleMark.setSelected(true);
                maleMark.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
                femaleMark.setSelected(false);
                femaleMark.setTextColor(getContext().getResources().getColor(R.color.colorThrText));
                sex = "male";
            }
        });
        femaleMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleMark.setSelected(false);
                maleMark.setTextColor(getContext().getResources().getColor(R.color.colorThrText));
                femaleMark.setSelected(true);
                femaleMark.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
                sex = "female";
            }
        });

        //头像选择按钮
        selectHeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });

        //获取家人关系标签
        familiesRelationSelecter.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Toast.makeText(getContext(),"标签为"+tag,Toast.LENGTH_SHORT).show();
                familiesTag = tag;
            }
        });


        //获取验证码
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = inputFamiliesTel.getText().toString();
                if (phone == null) {
                    Toast.makeText(getContext(), "手机号码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!FuncUtils.isMobileNO(phone)) {
                    Toast.makeText(getContext(), "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else if (phone.equals(phoneHasCode)) {
                    Toast.makeText(getContext(), "当前号码已验证!", Toast.LENGTH_SHORT).show();
                } else {
                    getCodeRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
                    NetService service = getCodeRetrofit.create(NetService.class);
                    service.addFamiliesCodeRequest(mParam1 + " " + mParam2, phone)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<Message>() {
                                @Override
                                public void onNext(Message message) {
                                    Log.e("FAMILIES", "获取验证码请求成功" + message.toString());
                                    phoneHasCode = phone;
                                    uuid = message.getUuid();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("FAMILIES", "获取验证码请求失败");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });

        //提交家人信息。
        commitFamiliesInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputFamiliesName.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "请输入姓名!", Toast.LENGTH_SHORT).show();
                } else if (inputFamiliesTel.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "电话号码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!inputFamiliesTel.getText().toString().equals("") && !FuncUtils.isMobileNO(inputFamiliesTel.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确的电话号码!", Toast.LENGTH_SHORT).show();
                } else if (inputFamiliesCode.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "手机验证码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (uuid.equals("") || uuid == null) {
                    Toast.makeText(getContext(), "手机号码尚未验证!", Toast.LENGTH_SHORT).show();
                } else {
                    final Map<String, String> map = new HashMap<>();
                    map.put("fullname", inputFamiliesName.getText().toString());
                    map.put("mobile", phoneHasCode);
                    map.put("sex", sex);
                    map.put("id_card_no", inputFamiliesIdNum.getText().toString());
                    map.put("birthday", inputFamiliesAge.getText().toString());
                    map.put("owner_relationship", familiesTag);
                    map.put("uuid", uuid);
                    map.put("code", inputFamiliesCode.getText().toString());

                    if (isSelectHeader) {
                        String path = FuncUtils.getReal(getContext(), headerImageUri);
                        Log.e("UP", "文件路径:" + path);
                        File file = new File(path);

                        RequestBody descriptionString = RequestBody.create(MediaType.parse("multipart/form-data"), "avatar");
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        Map<String, RequestBody> mapa = new HashMap<>();
                        mapa.put("bucket_name", descriptionString);

                        getCodeRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
                        NetService service = getCodeRetrofit.create(NetService.class);
                        service.uploadFile(mParam1 + mParam2, mapa, body)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<URLResult>() {
                            @Override
                            public void onNext(URLResult urlResult) {
                                Log.e("UP", "上传图片成功" + urlResult.toString());
                                Log.e("UP", "检查地址" + urlResult.getFile_url());
                                Log.e("UP", "上传数据检查" + map.toString());
                                map.put("avatar_path",urlResult.getFile_url());
                                commitFamilies(getCodeRetrofit,map);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("UP", "上传图片失败:" + e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }else{
                        map.put("avatar_path","");
                        Log.e("UP", "上传数据检查" + map.toString());
                        commitFamilies(getCodeRetrofit,map);
                    }

                }
            }
        });

        return rootViews;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle data) {
        if (mListener != null) {
            mListener.onAddFamilyFragmentInteraction(data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFamilyFragmentInteractionListener) {
            mListener = (OnAddFamilyFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnAddFamilyFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddFamilyFragmentInteraction(Bundle bundle);
    }


    private void initViews(View rootViews) {
        selectHeaderIcon = rootViews.findViewById(R.id.select_header_icon);//头像按钮
        inputFamiliesName = rootViews.findViewById(R.id.input_families_name);//家人姓名输入栏
        maleMark = rootViews.findViewById(R.id.male_mark);
        femaleMark = rootViews.findViewById(R.id.female_mark);
        inputFamiliesAge = rootViews.findViewById(R.id.input_families_age);
        inputFamiliesIdNum = rootViews.findViewById(R.id.input_families_id_num);
        inputFamiliesTel = rootViews.findViewById(R.id.input_families_tel);//手机号码
        inputFamiliesCode = rootViews.findViewById(R.id.input_families_code);
        getCode = rootViews.findViewById(R.id.get_code);
        familiesRelationSelecter = rootViews.findViewById(R.id.families_relation_selecter);
        commitFamiliesInfoButton = rootViews.findViewById(R.id.commit_families_info_button);

        maleMark.setSelected(true);
        femaleMark.setSelected(false);
        maleMark.setText("男");
        femaleMark.setText("女");
        maleMark.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        femaleMark.setTextColor(getContext().getResources().getColor(R.color.colorThrText));
        sex = "male";


        selectHeaderIcon.setBackground(getResources().getDrawable(R.mipmap.tianjiatouxiang));


        familiesRelationSelecter.setTags(relations);
    }

    //照片选则栏.
    private void showPopwindow() {
        final Dialog dialog = new Dialog(getContext(), R.style.dialog_bottom);
        View view = View.inflate(getContext(), R.layout.bottom_select_layout, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.get_photo_camera).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                File outputImage = new File(getContext().getExternalCacheDir(), "user_header.jpg");
                if (outputImage.exists()) {
                    outputImage.delete();
                }

                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    headerImageUri = FileProvider.getUriForFile(getContext(), "com.example.cameraalbumtest.fileprovider", outputImage);
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
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
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
                    selectHeaderIcon.setImageURI(headerImageUri);
                    isSelectHeader = true;
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    headerImageUri = data.getData();
                    selectHeaderIcon.setImageURI(headerImageUri);
                    isSelectHeader = true;
                }
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(getContext(), "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void commitFamilies(Retrofit uploadRetrofit, Map<String, String> map) {
        if(uploadRetrofit == null){
            uploadRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = uploadRetrofit.create(NetService.class);
        service.upLoadFamiliesInfo(mParam1+" "+mParam2,map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                Toast.makeText(getContext(),"添加家庭成员成功!",Toast.LENGTH_SHORT);
                try {
                    Log.e("UP","添加家庭成员成功:"+responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(),"添加家庭成员失败!",Toast.LENGTH_SHORT);
                Log.e("UP","添加家庭成员失败:"+e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
