package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FamiliesDetailsActivity extends AppCompatActivity {
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
    private CircleImageView familiesHeaderIconHad;
    private TextView familiesNameHad;
    private Button sexSelecterMale, sexSelecterFemale;
    private TextView familiesSexHad;
    private TextView familiesAgeHad;
    private TextView familiesIdNumHad;
    private TextView familiesTelNumHad;
    private TextView familiesRelationHad;

    private HTCMApp app;
    private Retrofit familiesDetailRetrofit;

    private String sexMark = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_families_details);


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
                Log.e("FAMILIES", "数据请求成功:" + info.toString());
                setViews(info);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("FAMILIES", "数据请求失败:" + e.toString());
            }

            @Override
            public void onComplete() {

            }
        });

        initViews();

        sexSelecterMale.setSelected(true);
        sexSelecterMale.setTextColor(getResources().getColor(R.color.colorWhite));
        sexSelecterFemale.setSelected(false);
        sexSelecterFemale.setTextColor(getResources().getColor(R.color.colorThrText));
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

    private void initViews() {
        backButton = findViewById(R.id.title_header_families_details).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_families_details).findViewById(R.id.menu_bar_title);
        unbundle = findViewById(R.id.title_header_families_details).findViewById(R.id.message_mark_text);
        healthCardNum = findViewById(R.id.health_card_num);
        authenticationMark = findViewById(R.id.authentication_mark);//立即认证 按钮
        inputChangeHeaderIcon = findViewById(R.id.input_change_header_icon);//
        inputChangeName = findViewById(R.id.input_change_name);
        inputChangeAge = findViewById(R.id.input_change_age);
        inputChangeIdNum = findViewById(R.id.input_change_id_num);
        inputChangeCellphone = findViewById(R.id.input_change_cellphone);
        inputChangeRelation = findViewById(R.id.input_change_relation);
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
            inputChangeHeaderIcon.setVisibility(View.INVISIBLE);
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
        familiesAgeHad.setText(info.getAge());
        familiesIdNumHad.setText(info.getId_card_no());
        familiesTelNumHad.setText(info.getMobile());
        familiesRelationHad.setText(info.getOwner_relationship());

    }
}
