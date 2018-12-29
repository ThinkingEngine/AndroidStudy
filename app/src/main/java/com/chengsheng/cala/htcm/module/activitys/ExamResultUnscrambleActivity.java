package com.chengsheng.cala.htcm.module.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodela.examItemResult;
import com.chengsheng.cala.htcm.adapter.ExamDetailTypeARecyclerAdapter;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;
import com.facebook.drawee.view.SimpleDraweeView;


public class ExamResultUnscrambleActivity extends BaseActivity {

    private AppTitleBar atExamDetail;
    private TextView examDetailName, examAbnormalTips;
    private TextView docResultText;
    private SimpleDraweeView docSignatureMarkB;
    private MyRecyclerView examDetailTypeA;
    private TextView tips;


    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_result_unscramble;
    }

    @Override
    public void initView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        HTCMApp.EXACT_SCREEN_WIDTH = metrics.widthPixels;
        HTCMApp.EXACT_SCREEN_HEIGHT = metrics.heightPixels;

        examItemResult result = (examItemResult) getIntent().getBundleExtra("mesg").getSerializable("ExamResult");

        initViews();
        examDetailName.setText(result.getName());
        if (result.getException_count() == 0) {
            examAbnormalTips.setVisibility(View.INVISIBLE);
        } else {
            examAbnormalTips.setVisibility(View.VISIBLE);
            examAbnormalTips.setText(result.getException_count() + "项异常数");
        }

        docResultText.setText("小结:" + result.getSummary());

        docSignatureMarkB.setImageURI(result.getDoctor_sign());

        ExamDetailTypeARecyclerAdapter adapter = new ExamDetailTypeARecyclerAdapter(this, result.getSingle_item_result(), result.getType());
        examDetailTypeA.setLayoutManager(new LinearLayoutManager(this));
        examDetailTypeA.setAdapter(adapter);
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        atExamDetail = findViewById(R.id.at_exam_detail);
        examDetailName = findViewById(R.id.exam_detail_name);
        examAbnormalTips = findViewById(R.id.exam_abnormal_tips);
        docResultText = findViewById(R.id.doc_result_text);
        docSignatureMarkB = findViewById(R.id.doc_signature_mark_b);
        examDetailTypeA = findViewById(R.id.exam_detail_type_a);
        tips = findViewById(R.id.tips);

        atExamDetail.setTitle("检查详情");
        atExamDetail.setFinishClickListener(() -> finish());

        examDetailTypeA.setFocusable(false);

    }
}
