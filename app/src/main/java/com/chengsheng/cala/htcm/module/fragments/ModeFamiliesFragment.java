package com.chengsheng.cala.htcm.module.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.module.activitys.AddExamPersonActivity;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.protocol.Message;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.TimeUtilKt;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class ModeFamiliesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView title;
    private ImageView back;
    private TextView childTitle;
    private EditText inputNewName;

    private boolean backMark = false;
    private String familiesMark = "";
    private String familiesTag = "";
    private String newName = "";
    private String id = "";

    private String mParam1;
    private FamiliesDetailInfo mParam2;

    private Retrofit retrofit;
    private HTCMApp app;
    private ZLoadingDialog loadingDialog;

    private String[] mark;//标签

    private OnFragmentInteractionListener mListener;

    public ModeFamiliesFragment() {
        // Required empty public constructor
    }


    public static ModeFamiliesFragment newInstance(String param1, FamiliesDetailInfo param2) {
        ModeFamiliesFragment fragment = new ModeFamiliesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = (FamiliesDetailInfo) getArguments().getSerializable(ARG_PARAM2);
        }

        app = HTCMApp.create(getContext());

        mark = mParam1.split(",");

        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("上传中...");
        loadingDialog.setHintTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getContext().getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        if (mark[0].equals("CELLPHONE")) {
            rootView = inflater.inflate(R.layout.mode_cellphone_layout, null);
            title = rootView.findViewById(R.id.title_header_mode_families).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_families).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_families).findViewById(R.id.message_mark_text);
            Button getCodeSmsButtonMode = rootView.findViewById(R.id.get_code_sms_button_mode);
            Button makeModeButton = rootView.findViewById(R.id.make_mode_button);
            final EditText getCodeFormSmsMode = rootView.findViewById(R.id.get_code_form_sms_mode);
            final EditText getNumberSmsMode = rootView.findViewById(R.id.get_number_sms_mode);

            childTitle.setVisibility(View.INVISIBLE);
            back.setOnClickListener(v -> backMark = true);

            if (mark.length > 0 && mark[1].equals("MODE_FAM")) {
                title.setText("编辑家人信息");
                getNumberSmsMode.setText(mParam2.getMobile());
                getNumberSmsMode.setEnabled(false);
                TimeUtilKt.initCaptchaTimer(getCodeSmsButtonMode);

                makeModeButton.setOnClickListener(v -> {
                    String uuid = FuncUtils.getString("TEMP_UUID", "");
                    if (getNumberSmsMode.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "电话号码不能为空!", Toast.LENGTH_SHORT).show();
                    } else if (!FuncUtils.isMobileNO(getNumberSmsMode.getText().toString())) {
                        Toast.makeText(getContext(), "请输入正确的电话号码!", Toast.LENGTH_SHORT).show();
                    } else if (uuid.equals("")) {
                        Toast.makeText(getContext(), "您的号码还未验证!", Toast.LENGTH_SHORT).show();
                    } else if (getCodeFormSmsMode.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "验证码不能为空!", Toast.LENGTH_SHORT).show();
                    } else {
                        ActivityUtil.Companion.startActivity(getContext(),new AddExamPersonActivity());
                    }
                });

            } else {
                title.setText("修改手机号码");

                makeModeButton.setOnClickListener(v -> {
                    String uuid = FuncUtils.getString("TEMP_UUID", "");
                    if (getNumberSmsMode.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "电话号码不能为空!", Toast.LENGTH_SHORT).show();
                    } else if (!FuncUtils.isMobileNO(getNumberSmsMode.getText().toString())) {
                        Toast.makeText(getContext(), "请输入正确的电话号码!", Toast.LENGTH_SHORT).show();
                    } else if (uuid.equals("")) {
                        Toast.makeText(getContext(), "您的号码还未验证!", Toast.LENGTH_SHORT).show();
                    } else if (getCodeFormSmsMode.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "验证码不能为空!", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("new_mobile", getNumberSmsMode.getText().toString());
                        map.put("uuid", uuid);
                        map.put("code", getCodeFormSmsMode.getText().toString());
                        map.put("fullname", mParam2.getFullname());

                        modeFamiliesTel(map);
                    }
                });
            }

            getCodeSmsButtonMode.setOnClickListener(v -> {
                if (getNumberSmsMode.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "电话号码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!FuncUtils.isMobileNO(getNumberSmsMode.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确的电话号码!", Toast.LENGTH_SHORT).show();
                } else {
                    getCode(getNumberSmsMode.getText().toString());
                }
            });

        } else if (mark[0].equals("RELATION")) {

            rootView = inflater.inflate(R.layout.mode_relation_layout, null);
            title = rootView.findViewById(R.id.title_header_mode_relation).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_relation).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_relation).findViewById(R.id.message_mark_text);
            final TagFlowLayout familiesRelationSelecterMode = rootView.findViewById(R.id.families_relation_selecter_mode);
            final LinearLayout addFamiliesMarksBoxMode = rootView.findViewById(R.id.add_families_marks_box_mode);
            final EditText newFamiliesMarkInputMode = rootView.findViewById(R.id.new_families_mark_input_mode);
            Button addFamiliesMarksButtonMode = rootView.findViewById(R.id.add_families_marks_button_mode);
            addFamiliesMarksBoxMode.setVisibility(View.INVISIBLE);

            childTitle.setText("完成");
            title.setText("修改家人关系");

            final List<String> dataSource = new ArrayList<>();
            dataSource.add("本人");
            dataSource.add("父亲");
            dataSource.add("母亲");
            dataSource.add("儿子");
            dataSource.add("女儿");
            dataSource.add("老婆");
            dataSource.add("老公");
            dataSource.add("其他");

            final TagAdapter adapter = new TagAdapter(dataSource);
            familiesRelationSelecterMode.setAdapter(adapter);
            familiesRelationSelecterMode.setOnTagClickListener((view, position, parent) -> {
                if (position != dataSource.size() - 1) {
                    familiesTag = dataSource.get(position);
                }
                TextView textView = view.findViewById(R.id.select_families_mark);
                if (position == dataSource.size() - 1 && textView.isSelected()) {
                    addFamiliesMarksBoxMode.setVisibility(View.VISIBLE);
                } else {
                    addFamiliesMarksBoxMode.setVisibility(View.INVISIBLE);
                }
                return true;
            });

            addFamiliesMarksButtonMode.setOnClickListener(v -> {
                if (newFamiliesMarkInputMode.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "请输入新的标签", Toast.LENGTH_SHORT).show();
                } else {
                    String newMark = newFamiliesMarkInputMode.getText().toString();
                    dataSource.add(0, newMark);
                    adapter.notifyDataChanged();
                    familiesRelationSelecterMode.setAdapter(adapter);
                    newFamiliesMarkInputMode.setText("");
                }
            });

            childTitle.setOnClickListener(v -> {
                if (familiesTag.equals("") || familiesTag == null) {
                    Toast.makeText(getContext(), "你还没选择家人的关系!", Toast.LENGTH_SHORT).show();
                } else {
                    mParam2.setOwner_relationship(familiesTag);
                    uploadModeInfo(mParam2);
                }

            });

            back.setOnClickListener(v -> backMark = true);
        } else if (mark[0].equals("NAME")) {
            rootView = inflater.inflate(R.layout.mode_name_layout, null);
            title = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.message_mark_text);
            inputNewName = rootView.findViewById(R.id.input_new_name);
            ImageView cleanInput = rootView.findViewById(R.id.clean_input);
            childTitle.setText("完成");
            title.setText("修改姓名");

            cleanInput.setOnClickListener(v -> inputNewName.setText(""));
            childTitle.setOnClickListener(v -> {
                newName = inputNewName.getText().toString();
                if (newName.equals("") || newName == null) {
                    Toast.makeText(getContext(), "名字不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    mParam2.setFullname(newName);
                    uploadModeInfo(mParam2);
                }
            });


            back.setOnClickListener(v -> backMark = true);
        } else {
            rootView = inflater.inflate(R.layout.mode_name_layout, null);
            title = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.menu_bar_title);
            back = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.back_login);
            childTitle = rootView.findViewById(R.id.title_header_mode_name).findViewById(R.id.message_mark_text);
            inputNewName = rootView.findViewById(R.id.input_new_name);
            ImageView cleanInput = rootView.findViewById(R.id.clean_input);
            childTitle.setText("完成");
            title.setText("修改身份证号码");

            cleanInput.setOnClickListener(v -> inputNewName.setText(""));
            childTitle.setOnClickListener(v -> {
                id = inputNewName.getText().toString();
                if (id.equals("") || id == null) {
                    Toast.makeText(getContext(), "身份证号不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!FuncUtils.isIdentityCode(id, getContext())) {
                    Toast.makeText(getContext(), "身份证号格式不对!", Toast.LENGTH_SHORT).show();
                } else {
                    mParam2.setId_card_no(id);
                    uploadModeInfo(mParam2);
                }
            });


            back.setOnClickListener(v -> backMark = true);
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Boolean isBack) {
        if (mListener != null) {
            mListener.onFragmentInteraction(isBack);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Boolean isBack);
    }

    private void uploadModeInfo(FamiliesDetailInfo info) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        Map<String, String> map = new HashMap<>();
        map.put("mobile", info.getMobile());
        map.put("fullname", info.getFullname());
        map.put("owner_relationship", info.getOwner_relationship());
        map.put("avatar_path", info.getAvatar_path());
        map.put("sex", info.getSex());
        map.put("birthday", info.getBirthday());
        map.put("id_card_no", info.getId_card_no());
        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.modeFamiliesInfo(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.MODE_FAMILIES + String.valueOf(mParam2.getId()), map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "修改关系成功!", Toast.LENGTH_SHORT).show();
                        CallBackDataAuth.doUpdateStateInterface(true);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Log.e("TAG", "测试:" + e.toString());
                        Toast.makeText(getContext(), "修改失败！请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCode(String phone) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        loadingDialog.show();
        service.getCodeModeFamiliesPhone(app.getTokenType() + " " + app.getAccessToken(), String.valueOf(mParam2.getId()), phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "请求成功，请等待验证码短信", Toast.LENGTH_SHORT).show();
                        FuncUtils.putString("TEMP_UUID", message.getUuid());
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "请求失败，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

    private void modeFamiliesTel(Map<String, String> map) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        loadingDialog.show();
        service.modeFamiliesTel(app.getTokenType() + " " + app.getAccessToken(), String.valueOf(mParam2.getId()), map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "您已成功修改", Toast.LENGTH_SHORT).show();
                        CallBackDataAuth.doUpdateStateInterface(true);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Toast.makeText(getContext(), "修改失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
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
