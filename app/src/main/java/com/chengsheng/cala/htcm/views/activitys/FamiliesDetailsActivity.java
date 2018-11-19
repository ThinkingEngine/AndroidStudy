package com.chengsheng.cala.htcm.views.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.AuthStateCallBack;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.views.dialog.ImmediatelyDialogView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FamiliesDetailsActivity extends AppCompatActivity implements AuthStateCallBack {
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

    private HTCMApp app;
    private Retrofit familiesDetailRetrofit;

    private FamiliesDetailInfo familiesDetailInfo;

    private String sexMark = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_families_details);

        CallBackDataAuth.setAuthStateCallBack(this);

        app = HTCMApp.create(this);
        String url = "api/family/account-family-members/";
        String familiesID = getIntent().getStringExtra("FAMILIES_ID");
        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        familiesDetailRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        NetService service = familiesDetailRetrofit.create(NetService.class);
        service.getFamiliesDetail(url + familiesID, app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<FamiliesDetailInfo>() {
            @Override
            public void onNext(FamiliesDetailInfo info) {
                Log.e("FAMILIES", "家人详细信息 数据请求成功:" + info.toString());
                familiesDetailInfo = info;
                setViews(info);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("FAMILIES", "家人详细信息 数据请求失败:" + e.toString());
            }

            @Override
            public void onComplete() {

            }
        });

        initViews();

        sexSelecterMale.setSelected(true);
        sexSelecterMale.setTextColor(getResources().getColor(R.color.colorWhite));
        sexSelecterMale.setText("男");
        sexSelecterFemale.setSelected(false);
        sexSelecterFemale.setTextColor(getResources().getColor(R.color.colorThrText));
        sexSelecterFemale.setText("女");
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

        unbundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FamiliesDetailsActivity.this);
                builder.setTitle("提示");
                builder.setMessage("解绑后您将不能再为【王树彤】购买套餐或查看其体检报告，确认解绑吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FamiliesDetailsActivity.this,"确认解绑!",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FamiliesDetailsActivity.this,"取消操作",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        authenticationMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImmediatelyDialogView immediatelyDialogView = new ImmediatelyDialogView(FamiliesDetailsActivity.this, familiesDetailInfo.getId());
                immediatelyDialogView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                immediatelyDialogView.show();
            }
        });

        //点击进入修改手机号码界面.
        inputChangeCellphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                intent.putExtra("MODE", "CELLPHONE");
                startActivity(intent);
            }
        });
        //点击进入修改家人关系页面
        inputChangeRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                intent.putExtra("MODE", "RELATION");
                startActivity(intent);
            }
        });
        //点击进入修改家人关系页面
        inputChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliesDetailsActivity.this, ModeFamiliesInfoActivity.class);
                intent.putExtra("MODE", "NAME");
                startActivity(intent);
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
    }

    private void setViews(FamiliesDetailInfo info) {
        if (info.isIs_auth()) {
            authenticationMark.setVisibility(View.INVISIBLE);
            inputChangeHeaderIcon.setVisibility(View.VISIBLE);
            inputChangeName.setVisibility(View.INVISIBLE);
            inputChangeAge.setVisibility(View.INVISIBLE);
            inputChangeIdNum.setVisibility(View.INVISIBLE);
            inputChangeCellphone.setVisibility(View.INVISIBLE);
//            inputChangeRelation.setVisibility(View.INVISIBLE);
            sexSelecterMale.setVisibility(View.INVISIBLE);
            sexSelecterFemale.setVisibility(View.INVISIBLE);
            familiesSexHad.setVisibility(View.VISIBLE);


            healthCardNum.setText(info.getHealth_card_no());
            familiesSexHad.setText(info.getSex());

        } else {
            authenticationMark.setVisibility(View.VISIBLE);
            inputChangeHeaderIcon.setVisibility(View.VISIBLE);
            inputChangeName.setVisibility(View.VISIBLE);
            inputChangeAge.setVisibility(View.VISIBLE);
            inputChangeIdNum.setVisibility(View.VISIBLE);
            inputChangeCellphone.setVisibility(View.VISIBLE);
//            inputChangeRelation.setVisibility(View.VISIBLE);
            sexSelecterMale.setVisibility(View.VISIBLE);
            sexSelecterFemale.setVisibility(View.VISIBLE);
            familiesSexHad.setVisibility(View.INVISIBLE);
            healthCardNum.setText("无");
        }

        familiesNameHad.setText(info.getFullname());
        familiesAgeHad.setText(String.valueOf(info.getAge()) + "岁");
        familiesIdNumHad.setText(info.getId_card_no());
        familiesTelNumHad.setText(info.getMobile());
        familiesRelationHad.setText(info.getOwner_relationship());
        familiesHeaderIconHad.setImageURI(info.getAvatar_path());

    }

    @Override
    public void authResult(Map<String, String> result) {
        Toast.makeText(this,result.get("STATE")+":"+result.get("REASON"),Toast.LENGTH_LONG).show();
    }
}
