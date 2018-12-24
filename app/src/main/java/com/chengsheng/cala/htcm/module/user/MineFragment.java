package com.chengsheng.cala.htcm.module.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseFragment;
import com.chengsheng.cala.htcm.data.repository.UserRepository;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.module.activitys.AccountSettingActivity;
import com.chengsheng.cala.htcm.module.activitys.ExamOrderFormActivity;
import com.chengsheng.cala.htcm.module.activitys.FamilyManageActivity;
import com.chengsheng.cala.htcm.module.activitys.MyCollectionActivity;
import com.chengsheng.cala.htcm.module.activitys.MyDevicesActivity;
import com.chengsheng.cala.htcm.module.activitys.ServiceMessageActivity;
import com.chengsheng.cala.htcm.module.activitys.ServiceOrderActivity;
import com.chengsheng.cala.htcm.module.activitys.SettingActivity;
import com.chengsheng.cala.htcm.module.user.card.MemberCardActivity;
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import io.reactivex.observers.DefaultObserver;

/**
 * Author: 蔡浪
 * CreateDate:
 * Description: 我的模块
 */
public class MineFragment extends BaseFragment {

    private SuperTextView medicalExamOrderText;
    private TextView userNameText;
    private TextView userCellphoneNum;
    private SimpleDraweeView userIcon;
    private FrameLayout messageIconContainerMine;
    private RelativeLayout layoutUpdateUserInfo;
    private ImageView currentHasMessageMine;
    private SuperTextView stv_family_manager;
    private SuperTextView stv_collection;
    private SuperTextView stv_device;

    private UserInfo userInfo;

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
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View rootView) {
        medicalExamOrderText = rootView.findViewById(R.id.stv_medical_order);//体检订单文本
        SuperTextView serviceOrderText = rootView.findViewById(R.id.stv_service_order);
        userNameText = rootView.findViewById(R.id.user_name_text);
        userCellphoneNum = rootView.findViewById(R.id.user_cellphone_num);
        userIcon = rootView.findViewById(R.id.user_icon);
        messageIconContainerMine = rootView.findViewById(R.id.message_icon_container_mine);
        currentHasMessageMine = rootView.findViewById(R.id.current_has_message_mine);
        layoutUpdateUserInfo = rootView.findViewById(R.id.layout_update_user_info);
        stv_family_manager = rootView.findViewById(R.id.stv_family_manager);
        SuperTextView stv_contact = rootView.findViewById(R.id.stv_contact);
        stv_device = rootView.findViewById(R.id.stv_device);
        SuperTextView stv_setting = rootView.findViewById(R.id.stv_setting);
        stv_collection = rootView.findViewById(R.id.stv_collection);
        LinearLayout layoutMember = rootView.findViewById(R.id.layoutMember);
        LinearLayout layoutCoupon = rootView.findViewById(R.id.layoutCoupon);

        //修改用户信息
        layoutUpdateUserInfo.setOnClickListener(view -> {
            if (!UserUtil.isLogin()) {
                LoginActivity.start(context);
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER_INFO", userInfo);
                startActivity(new AccountSettingActivity(), bundle);
            }
        });

        //会员卡
        layoutMember.setOnClickListener(view ->
                startActivityWithLoginStatus(new MemberCardActivity()));

        //优惠券
        layoutCoupon.setOnClickListener(view ->
                startActivityWithLoginStatus(new CouponActivity()));

        //家人管理
        stv_family_manager.setOnClickListener(view ->
                startActivityWithLoginStatus(new FamilyManageActivity()));

        //我的收藏
        stv_collection.setOnClickListener(view ->
                startActivityWithLoginStatus(new MyCollectionActivity()));

        //我的设备
        stv_device.setOnClickListener(view ->
                startActivityWithLoginStatus(new MyDevicesActivity()));

        //设置
        stv_setting.setOnClickListener(view ->
                startActivity(new SettingActivity()));

        //联系客服
        stv_contact.setOnClickListener(view -> contactService());

        //查看消息
        messageIconContainerMine.setOnClickListener(v ->
                startActivityWithLoginStatus(new ServiceMessageActivity()));

        //体检订单
        medicalExamOrderText.setOnClickListener(v -> {
            if (UserUtil.isLogin()) {
                Intent intent = new Intent(getContext(), ExamOrderFormActivity.class);
                intent.putExtra("CUSTOMER_ID", String.valueOf(userInfo.getId()));
                context.startActivity(intent);
            } else {
                startActivity(new LoginActivity());
            }
        });

        //服务订单
        serviceOrderText.setOnClickListener(v ->
                startActivityWithLoginStatus(new ServiceOrderActivity()));

    }

    @Override
    public void getData() {

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        if (!UserUtil.isLogin()) {
            return;
        }

        UserRepository.Companion.getDefault().getUserInfo()
                .subscribe(new DefaultObserver<UserInfo>() {
                    @Override
                    public void onNext(UserInfo user) {
                        userInfo = user;
                        setUserInfo(user);
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

    /**
     * 设置用户信息
     */
    void setUserInfo(final UserInfo userInfo) {
        userIcon.setImageURI(userInfo.getAvatar_url());
        userNameText.setText(userInfo.getNickname());
        String phoneNumber = userInfo.getPhone_number().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        userCellphoneNum.setText(phoneNumber);
    }

    /**
     * 联系客服
     */
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

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtil.isLogin()) {
            stv_family_manager.setVisibility(View.VISIBLE);
            stv_collection.setVisibility(View.VISIBLE);
            stv_device.setVisibility(View.VISIBLE);
            getUserInfo();
        } else {
            stv_family_manager.setVisibility(View.GONE);
            stv_collection.setVisibility(View.GONE);
            stv_device.setVisibility(View.GONE);
            userNameText.setText("");
            userCellphoneNum.setText("");
            userIcon.setImageResource(R.mipmap.morentouxiang_wode);
        }
    }
}
