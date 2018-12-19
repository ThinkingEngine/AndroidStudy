package com.chengsheng.cala.htcm.module.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.module.activitys.AccountSettingActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamOrderFormActivity;
import com.chengsheng.cala.htcm.module.activitys.FamilyManageActivity;
import com.chengsheng.cala.htcm.module.activitys.MyCollectionActivity;
import com.chengsheng.cala.htcm.module.activitys.MyDevicesActivity;
import com.chengsheng.cala.htcm.module.activitys.ServiceMessageActivity;
import com.chengsheng.cala.htcm.module.activitys.ServiceOrderActivity;
import com.chengsheng.cala.htcm.module.activitys.SettingActivity;
import com.chengsheng.cala.htcm.network.AccountService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Author: 蔡浪
 * CreateDate:
 * Description: 我的模块
 */
public class MineFragment extends Fragment {

    private TextView medicalExamOrderText;
    private TextView userNameText;
    private TextView userCellphoneNum;
    private SimpleDraweeView userIcon;
    private FrameLayout messageIconContainerMine;
    private RelativeLayout layoutUpdateUserInfo;
    private ImageView currentHasMessageMine;
    private SuperTextView stv_family_manager;
    private SuperTextView stv_collection;
    private SuperTextView stv_device;
    private SuperTextView stv_setting;
    private SuperTextView stv_contact;

    private Retrofit retrofit;
    private HTCMApp app;

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("p1", param1);
        args.putString("p2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("p1");
            String mParam2 = getArguments().getString("p2");
        }

        app = HTCMApp.create(getContext());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        medicalExamOrderText = rootView.findViewById(R.id.medical_exam_order_text);//体检订单文本
        TextView serviceOrderText = rootView.findViewById(R.id.service_order_text);
        userNameText = rootView.findViewById(R.id.user_name_text);
        userCellphoneNum = rootView.findViewById(R.id.user_cellphone_num);
        userIcon = rootView.findViewById(R.id.user_icon);
        messageIconContainerMine = rootView.findViewById(R.id.message_icon_container_mine);
        currentHasMessageMine = rootView.findViewById(R.id.current_has_message_mine);
        layoutUpdateUserInfo = rootView.findViewById(R.id.layout_update_user_info);
        stv_family_manager = rootView.findViewById(R.id.stv_family_manager);
        stv_contact = rootView.findViewById(R.id.stv_contact);
        stv_device = rootView.findViewById(R.id.stv_device);
        stv_setting = rootView.findViewById(R.id.stv_setting);
        stv_collection = rootView.findViewById(R.id.stv_collection);

        //修改用户信息
        layoutUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserUtil.isLogin()) {
                    ActivityUtil.Companion.startActivity(getContext(), new LoginActivity());
                }
            }
        });

        //家人管理
        stv_family_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new FamilyManageActivity());
            }
        });

        //我的收藏
        stv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new MyCollectionActivity());
            }
        });

        //我的设备
        stv_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new MyDevicesActivity());
            }
        });

        //设置
        stv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new SettingActivity());
            }
        });

        //联系客服
        stv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactService();
            }
        });

        updateUserInfo();

        //查看消息
        messageIconContainerMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.Companion.startActivityWithLoginStatus(getContext(), new ServiceMessageActivity());
            }
        });

        //服务订单
        serviceOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ServiceOrderActivity.class);
                getContext().startActivity(intent);
            }
        });

        return rootView;
    }

    private void contactService() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("拨号提示");
        builder.setMessage("确认向客服中心拨打电话?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "400-028-3020");
                intent.setData(data);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("暂不", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateUserInfo() {
        if (!UserUtil.isLogin()) {
            return;
        }

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        AccountService service = retrofit.create(AccountService.class);
        service.getUserInfo(app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserInfo>() {
                    @Override
                    public void onNext(final UserInfo userInfo) {
                        setViews(userInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "用户信息请求失败" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void setViews(final UserInfo userInfo) {
        userIcon.setImageURI(userInfo.getAvatar_url());
        userNameText.setText(userInfo.getNickname());
        String phoneNumber = userInfo.getPhone_number().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        userCellphoneNum.setText(phoneNumber);

        medicalExamOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExamOrderFormActivity.class);
                intent.putExtra("CUSTOMER_ID", String.valueOf(userInfo.getId()));
                getContext().startActivity(intent);
            }
        });

        //修改用户信息
        layoutUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserUtil.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("USER_INFO", userInfo);
                    ActivityUtil.Companion.startActivity(getContext(),
                            new AccountSettingActivity(), bundle);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtil.isLogin()) {
            stv_family_manager.setVisibility(View.VISIBLE);
            stv_collection.setVisibility(View.VISIBLE);
            stv_device.setVisibility(View.VISIBLE);
            updateUserInfo();
        } else {
            stv_family_manager.setVisibility(View.GONE);
            stv_collection.setVisibility(View.GONE);
            stv_device.setVisibility(View.GONE);
            userNameText.setText("");
            userCellphoneNum.setText("");
        }
    }
}
