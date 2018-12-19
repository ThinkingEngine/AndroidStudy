package com.chengsheng.cala.htcm.module.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.constant.GlobalConstant;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.Message;
import com.chengsheng.cala.htcm.protocol.URLResult;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Author: 蔡浪
 * CreateDate: 2018/12/17 8:50 PM
 * Description:添加家人页面
 */

public class AddFamilyFragment extends Fragment {

    private SimpleDraweeView selectHeaderIcon;
    private EditText inputFamiliesName;
    private Button maleMark, femaleMark;
    private TextView inputFamiliesAge;
    private EditText inputFamiliesIdNum;
    private EditText inputFamiliesTel;
    private EditText inputFamiliesCode;
    private Button getCode;
    private TagFlowLayout familiesRelationSelecter;
    private Button commitFamiliesInfoButton;
    private LinearLayout addFamiliesMarksBox;
    private EditText newFamiliesMarkInput;
    private Button addFamiliesMarksButton;


    private String[] relations = new String[]{"本人", "父亲", "母亲", "儿子", "女儿", "妻子", "丈夫", "其他"};//默认家人关系标签
    private String phoneHasCode = "";
    private Uri headerImageUri;
    private boolean isSelectHeader = false;
    private String sex;
    private String familiesTag = "本人";//家人关系标签

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MyRetrofit myRetrofit;
    private Retrofit getCodeRetrofit;
    private ZLoadingDialog loadingDialog;
    private Calendar calendar;

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

        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("请求中....");
        loadingDialog.setDialogBackgroundColor(getContext().getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        myRetrofit = MyRetrofit.createInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
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

        inputFamiliesAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calendar == null){
                    calendar = Calendar.getInstance();
                }
                DatePickerDialog timePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String current = FuncUtils.getCurrentTimeDay();
                        String selectDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        if(FuncUtils.isDate2Bigger(current,selectDate)){
                            Toast.makeText(getContext(),"出生日期选择有误！请重新选择",Toast.LENGTH_SHORT).show();
                            inputFamiliesAge.setText("");
                        }else{
                            inputFamiliesAge.setText(selectDate);
                        }

                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                timePickerDialog.show();
            }
        });

        //头像选择按钮
        selectHeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
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
                   getCodeFromNet(phone);
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
                } else if (inputFamiliesAge.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "您还未确认你的年龄!", Toast.LENGTH_SHORT).show();
                } else if (!inputFamiliesTel.getText().toString().equals("") && !FuncUtils.isMobileNO(inputFamiliesTel.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确的电话号码!", Toast.LENGTH_SHORT).show();
                } else if (inputFamiliesCode.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "手机验证码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (uuid.equals("") || uuid == null) {
                    Toast.makeText(getContext(), "手机号码验证异常!请重新验证", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.show();
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
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableObserver<URLResult>() {
                                    @Override
                                    public void onNext(URLResult urlResult) {
                                        map.put("avatar_path", urlResult.getFile_url());
                                        commitFamilies(getCodeRetrofit, map);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        loadingDialog.cancel();
                                        Log.e("UP", "上传图片失败:" + e);
                                        Toast.makeText(getContext(), "上传图片失败！", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    } else {
                        map.put("avatar_path", "");
                        Log.e("UP", "上传数据检查" + map.toString());
                        commitFamilies(getCodeRetrofit, map);
                    }

                }
            }
        });

        return rootViews;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle data,boolean isAdd) {
        if (mListener != null) {
            mListener.onAddFamilyFragmentInteraction(data,isAdd);
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
        void onAddFamilyFragmentInteraction(Bundle bundle,boolean isAdd);
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
        familiesRelationSelecter = rootViews.findViewById(R.id.families_relation_selecter);//家人关系组件
        commitFamiliesInfoButton = rootViews.findViewById(R.id.commit_families_info_button);
        addFamiliesMarksBox = rootViews.findViewById(R.id.add_families_marks_box);
        newFamiliesMarkInput = rootViews.findViewById(R.id.new_families_mark_input);
        addFamiliesMarksButton = rootViews.findViewById(R.id.add_families_marks_button);

        maleMark.setSelected(true);
        femaleMark.setSelected(false);
        maleMark.setText("男");
        femaleMark.setText("女");
        maleMark.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        femaleMark.setTextColor(getContext().getResources().getColor(R.color.colorThrText));
        sex = "male";
        addFamiliesMarksBox.setVisibility(View.INVISIBLE);

        selectHeaderIcon.setBackground(getResources().getDrawable(R.mipmap.tianjiatouxiang));

        final List<String> data = new ArrayList<>();
        for(int i = 0;i < relations.length;i++){
            data.add(relations[i]);
        }
        final TagAdapter tagAdapter = new TagAdapter(data);
        familiesRelationSelecter.setAdapter(tagAdapter);
        tagAdapter.onSelected(0,tagAdapter.getView(familiesRelationSelecter,0,data.get(0)));
        familiesRelationSelecter.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Toast.makeText(getContext(),"选中:"+data.get(position),Toast.LENGTH_SHORT).show();
                if(position != data.size()-1){
                    familiesTag = data.get(position);
                }
                TextView textView = view.findViewById(R.id.select_families_mark);
                if(position == data.size()-1 && textView.isSelected()){
                    addFamiliesMarksBox.setVisibility(View.VISIBLE);
                }else{
                    addFamiliesMarksBox.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });

        //添加新的家人关系标签按钮.
        addFamiliesMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newFamiliesMarkInput.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请输入新的标签",Toast.LENGTH_SHORT).show();
                }else{
                    String newMark = newFamiliesMarkInput.getText().toString();
                    data.add(0,newMark);
                    tagAdapter.setSelected(0,newMark);
                    tagAdapter.notifyDataChanged();
                    familiesRelationSelecter.setAdapter(tagAdapter);
                    newFamiliesMarkInput.setText("");
                }
            }
        });

        //当失去焦点的时候，体检输入的标签
        newFamiliesMarkInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!newFamiliesMarkInput.getText().toString().equals("")){
                        String newMark = newFamiliesMarkInput.getText().toString();
                        data.add(0,newMark);
                        tagAdapter.setSelected(0,newMark);
                        tagAdapter.notifyDataChanged();
                        familiesRelationSelecter.setAdapter(tagAdapter);
                        familiesTag = newFamiliesMarkInput.getText().toString();
                        newFamiliesMarkInput.setText("");
                    }
                }
            }
        });
    }

    private void getCodeFromNet(final String num){

        if(getCodeRetrofit == null){
            getCodeRetrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        loadingDialog.setHintText("请求验证....");
        loadingDialog.show();
        NetService service = getCodeRetrofit.create(NetService.class);
        service.addFamiliesCodeRequest(mParam1 + " " + mParam2, num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        Log.e("FAMILIES", "获取验证码请求成功" + message.toString());
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "获取验证码请求成功", Toast.LENGTH_SHORT).show();
                        phoneHasCode = num;
                        uuid = message.getUuid();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "获取验证码请求失败!", Toast.LENGTH_SHORT).show();
//                        Log.e("FAMILIES", "获取验证码请求失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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

    //提交家人信息
    private void commitFamilies(Retrofit uploadRetrofit, Map<String, String> map) {
        if (uploadRetrofit == null) {
            uploadRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = uploadRetrofit.create(NetService.class);
        service.upLoadFamiliesInfo(mParam1 + " " + mParam2, map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    loadingDialog.cancel();
                    Log.e("UP", "添加家庭成员成功:" + responseBody.string());
                    Toast.makeText(getContext(), "添加家庭成员成功!", Toast.LENGTH_SHORT).show();
                    CallBackDataAuth.doAuthStateCallBack(true);
//                    Intent intent = new Intent(getContext(), HomePageActivity.class);
//                    getContext().startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString("STATE","manage");
                    onButtonPressed(bundle,true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                loadingDialog.cancel();
                Toast.makeText(getContext(), "添加家庭成员失败!", Toast.LENGTH_SHORT);
                Log.e("UP", "添加家庭成员失败:" + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    class TagAdapter extends com.zhy.view.flowlayout.TagAdapter<String> {

        public TagAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public void onSelected(int position, View view) {
            TextView textView = view.findViewById(R.id.select_families_mark);
            textView.setSelected(true);
            textView.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void unSelected(int position, View view) {
            TextView textView = view.findViewById(R.id.select_families_mark);
            textView.setSelected(false);
            textView.setTextColor(getContext().getResources().getColor(R.color.colorThrText));
        }

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.families_select_mark_bg_layout, null);
            TextView textView = rootView.findViewById(R.id.select_families_mark);
            textView.setText(s);
            return rootView;
        }

    }


}
