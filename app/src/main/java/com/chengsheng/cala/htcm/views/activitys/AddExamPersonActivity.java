package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesList;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesListItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.ExamPersonInterface;
import com.chengsheng.cala.htcm.views.adapters.CanFamiliesListRecycler;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.Serializable;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AddExamPersonActivity extends BaseActivity implements ExamPersonInterface {
    private TextView title;
    private ImageView back;
    private RelativeLayout familiesManageBox;
    private TextView admitAdd;
    private RecyclerView familiesListRecycler;

    private MyRetrofit myRetrofit;
    private Retrofit getFamiliesList;

    private List<FamiliesListItem> items;
    private List<FamiliesListItem> selected;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_person);

        final ZLoadingDialog loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintText("努力加载中.....");
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        HTCMApp app = HTCMApp.create(getApplicationContext());
        myRetrofit = MyRetrofit.createInstance();

        CallBackDataAuth.setExamPersonInterface(this);

        id = getIntent().getStringExtra("COMBO_ID");
        initViews();


        familiesListRecycler.setLayoutManager(new LinearLayoutManager(this));
        loadingDialog.show();
        getFamiliesList = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        NetService service = getFamiliesList.create(NetService.class);
        service.getFamiliesList(app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FamiliesList>() {
                    @Override
                    public void onNext(FamiliesList list) {
                        items = list.getItems();
                        CanFamiliesListRecycler adapter = new CanFamiliesListRecycler(AddExamPersonActivity.this, items);
                        familiesListRecycler.setAdapter(adapter);
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ADD", "onNext" + e.toString());
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        familiesManageBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExamPersonActivity.this, FamilyManageActivity.class);
                startActivity(intent);
            }
        });

        admitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AddExamPersonActivity.this,"选择的人数:"+selected.size(),Toast.LENGTH_LONG).show();
                if(selected.size() == 0){
                    Toast.makeText(AddExamPersonActivity.this,"你还没有选择家人进行体检的，请选择你想添加的家人",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(AddExamPersonActivity.this,AffirmAppointmentActivity.class);
                    intent.putExtra("SELECT",(Serializable)selected);
                    intent.putExtra("COMBO_ID",id);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void initViews() {
        title = findViewById(R.id.title_header_add_exam_person).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_add_exam_person).findViewById(R.id.back_login);
        familiesManageBox = findViewById(R.id.families_manage_box);
        admitAdd = findViewById(R.id.admit_add);
        familiesListRecycler = findViewById(R.id.families_list_recycler);

        title.setText("添加体检人");
    }

    @Override
    public void examPersonData(List<FamiliesListItem> datas) {
        selected = datas;
    }
}
