package com.chengsheng.cala.htcm.views.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.AppointmentDetail;
import com.chengsheng.cala.htcm.model.datamodel.Organization;
import com.chengsheng.cala.htcm.model.datamodel.PackageTag;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.adapters.ExamItemExpandableListViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;
import com.chengsheng.cala.htcm.views.customviews.TextViewBorder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ComboDetailActivity extends BaseActivity {

    private TextView title, immediateAppointment;
    private ImageView collect, share, back;
    private MyExpandableListView examItemComboExpandable;
    private TextView comboNameDetail, comboPrice, comboHasNum;
    private ImageView comboHotMark;
    private TagFlowLayout comboMarks;
    private TextView groupName, groupMark, groupTel, groupAddress;
    //    private GridView keyIllnessScreeningItem;
    private TextView comboBriefText;
    private TextView userNeedNote;
    private TextView onlineServiceTel;
    private TextView examItemComboNum;

    private Dialog dialog;
    private View dialogView;
    private TranslateAnimation ta;
    private List<String> shareList;
    private String id;
    private int[] images = {R.mipmap.pengyouquan,
            R.mipmap.weixinhaoyou,
            R.mipmap.qqhaoyou,
            R.mipmap.qqkongjian,
            R.mipmap.fuzhilianjie};

    private boolean isCollect = false;
    private ZLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final HTCMApp app = HTCMApp.create(this);
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_combo_detail);

        initViews();


        id = getIntent().getStringExtra("COMBO_ID");
        String comboID = "/" + id;
        String comboUrl = "api/physical-exam-item/exam-packages" + comboID;
        String comboUrlIsCollect = "api/physical-exam-item/collector/exam-packages" + comboID;
        final String cancelCollectUrl = "api/physical-exam-item/collector/exam-packages" + comboID;

        final MyRetrofit myRetrofit = MyRetrofit.createInstance();
        Retrofit collectRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        Retrofit retrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        NetService service = retrofit.create(NetService.class);
        NetService service1 = collectRetrofit.create(NetService.class);
        service1.checkIsCollect(comboUrlIsCollect, app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            if (str.contains("true")) {
                                collect.setSelected(true);
                                isCollect = true;
                            } else {
                                collect.setSelected(false);
                                isCollect = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        loadingDialog.setHintText("加载中....");
        loadingDialog.show();
        examItemComboExpandable.setFocusable(false);
        service.getCombonDetail(comboUrl, app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AppointmentDetail>() {
                    @Override
                    public void onNext(AppointmentDetail appointmentDetail) {
                        setViews(appointmentDetail);
                        ExamItemExpandableListViewAdapter adapter = new ExamItemExpandableListViewAdapter(ComboDetailActivity.this, appointmentDetail.getExam_items());
                        examItemComboExpandable.setAdapter(adapter);
                        examItemComboExpandable.setIndicatorBounds(examItemComboExpandable.getWidth() - 140, examItemComboExpandable.getWidth() - 10);
                        Log.e("TEST", appointmentDetail.toString());
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TEST", "ComboDetail onError!" + e);
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //收藏按钮
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCollect) {
                    //取消收藏
                    isCollect = false;
                    collect.setSelected(false);

                    loadingDialog.setHintText("加载中.....");
                    loadingDialog.show();
                    Retrofit cancelCollect = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
                    NetService cancelCollectService = cancelCollect.create(NetService.class);
                    cancelCollectService.cancelCollect(cancelCollectUrl, app.getTokenType() + " " + app.getAccessToken())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody o) {
                                    loadingDialog.cancel();
                                    Toast.makeText(ComboDetailActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    loadingDialog.cancel();
                                    Toast.makeText(ComboDetailActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    isCollect = true;
                                    collect.setSelected(true);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                } else {
                    //收藏
                    isCollect = true;
                    collect.setSelected(true);
                    Retrofit collectRetrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
                    NetService serviceCollect = collectRetrofit.create(NetService.class);

                    loadingDialog.setHintText("正在收藏");
                    loadingDialog.show();
                    serviceCollect.collectCombo(cancelCollectUrl, app.getTokenType() + " " + app.getAccessToken())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody o) {
                                    loadingDialog.cancel();
                                    Toast.makeText(ComboDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    loadingDialog.cancel();
                                    Toast.makeText(ComboDetailActivity.this, "收藏失败:" + "原因" + e, Toast.LENGTH_SHORT).show();
                                    isCollect = false;
                                    collect.setSelected(false);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });

        //分享该套餐
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareList = new ArrayList<>();
                shareList.add("朋友圈");
                shareList.add("微信好友");
                shareList.add("QQ好友");
                shareList.add("QQ空间");
                shareList.add("复制链接");
                shareSelection();
            }
        });


        //立即预约
        immediateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboDetailActivity.this, AffirmAppointmentActivity.class);
                intent.putExtra("COMBO_ID", id);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        title = findViewById(R.id.title_header_combo_detail).findViewById(R.id.menu_bar_title);
        collect = findViewById(R.id.title_header_combo_detail).findViewById(R.id.collect_icon);
        share = findViewById(R.id.title_header_combo_detail).findViewById(R.id.share);//分享按钮
        back = findViewById(R.id.title_header_combo_detail).findViewById(R.id.back_login);
        immediateAppointment = findViewById(R.id.immediate_appointment);//立即预约按钮
        examItemComboExpandable = findViewById(R.id.exam_item_combo_expandable);
        comboNameDetail = findViewById(R.id.model_a).findViewById(R.id.combo_name_detail);//套餐名称
        comboPrice = findViewById(R.id.model_a).findViewById(R.id.combo_price);//套餐价格
        comboHasNum = findViewById(R.id.combo_has_num).findViewById(R.id.combo_has_num);//套餐实际人数
        comboHotMark = findViewById(R.id.model_a).findViewById(R.id.combo_hot_mark);//套餐是否为热点
        comboMarks = findViewById(R.id.model_a).findViewById(R.id.combo_marks);
        examItemComboNum = findViewById(R.id.exam_item_combo_num);
        groupName = findViewById(R.id.model_b).findViewById(R.id.group_name);//机构名称
        groupMark = findViewById(R.id.model_b).findViewById(R.id.group_mark);//机构资质
        groupTel = findViewById(R.id.model_b).findViewById(R.id.group_tel);//机构电话
        groupAddress = findViewById(R.id.model_b).findViewById(R.id.group_address);//机构地址
//        keyIllnessScreeningItem = findViewById(R.id.model_c).findViewById(R.id.key_illness_screening_item);
        comboBriefText = findViewById(R.id.model_d).findViewById(R.id.combo_brief_text);//套餐简介
        userNeedNote = findViewById(R.id.model_f).findViewById(R.id.user_need_note);//体检须知
        onlineServiceTel = findViewById(R.id.online_service_tel);

        title.setText("套餐详情");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onlineServiceTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ComboDetailActivity.this);
                builder.setTitle("在线客服");
                builder.setMessage("您确定拨打在线客服电话!");
                builder.setNegativeButton("暂不", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + "400-028-3020");
                        intent.setData(data);
                        startActivity(intent);
                    }
                });

                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setViews(AppointmentDetail datas) {
        Organization organization = datas.getOrganization();

        comboNameDetail.setText(datas.getName());
        if (datas.isIs_hot()) {
            comboHotMark.setVisibility(View.VISIBLE);
        } else {
            comboHotMark.setVisibility(View.INVISIBLE);
        }
        comboHasNum.setText(String.valueOf(datas.getCurrent_sales_num()) + "人已检");
        comboPrice.setText(String.valueOf(datas.getPrice()));

        userNeedNote.setText(datas.getExam_notice());
        comboBriefText.setText(datas.getIntro());
        examItemComboNum.setText("("+datas.getExam_items().size()+")");

        groupName.setText(organization.getName());
        if (organization.getQualification().equals("")) {
            groupMark.setVisibility(View.INVISIBLE);
        } else {
            groupMark.setText(organization.getQualification());
        }
        groupTel.setText(organization.getContact_telephone());
        groupAddress.setText(organization.getAddress());
        comboMarks.setAdapter(new TagAdapter(datas.getPackage_tag()));
    }

    private void shareSelection() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog_bottom);
            dialogView = LayoutInflater.from(this).inflate(R.layout.share_combo_bg_layout, null);
            dialog.setContentView(dialogView);
        }

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


        GridView gridView = dialogView.findViewById(R.id.share_way);
        Button dissen = dialogView.findViewById(R.id.share_down);


        ta = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_PARENT, 0);
        ta.setInterpolator(new AccelerateInterpolator());
        ta.setDuration(200);


        dissen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ShareAdapter adapter = new ShareAdapter();
        gridView.setAdapter(adapter);


    }

    public class ShareAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return shareList.size();
        }

        @Override
        public Object getItem(int position) {
            return shareList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ComboDetailActivity.this).inflate(R.layout.share_way_icon_bg_layout, null);
            }

            ImageView shareWayIcon = convertView.findViewById(R.id.share_way_icon);
            TextView textView = convertView.findViewById(R.id.share_way_text);
            shareWayIcon.setImageResource(images[position]);
            textView.setText(shareList.get(position));
            return convertView;
        }

    }

    class TagAdapter extends com.zhy.view.flowlayout.TagAdapter<PackageTag> {


        public TagAdapter(List<PackageTag> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, PackageTag packageTag) {
            View rootView = LayoutInflater.from(ComboDetailActivity.this).inflate(R.layout.tag_text_item_layout, null);
            TextViewBorder textViewBorder = rootView.findViewById(R.id.tag_text);
            textViewBorder.setText(packageTag.getName());
            textViewBorder.setTextColor(Color.parseColor(packageTag.getColor()));
            textViewBorder.setBorderColor(Color.parseColor(packageTag.getColor()));
            return rootView;
        }

    }


}
