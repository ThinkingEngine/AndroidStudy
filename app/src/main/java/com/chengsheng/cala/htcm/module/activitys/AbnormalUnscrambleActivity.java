package com.chengsheng.cala.htcm.module.activitys;

import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.AbnormalScrambleExpandableAdapter;

public class AbnormalUnscrambleActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private TextView abnormalHeaderTips;
    private ExpandableListView abnormalUnscrambleExpandable;

    @Override
    public int getLayoutId() {
        return R.layout.activity_abnormal_unscramble;
    }

    @Override
    public void initView() {
        title = findViewById(R.id.title_header_abnormal_unscramble).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_abnormal_unscramble).findViewById(R.id.back_login);
        abnormalHeaderTips = findViewById(R.id.abnormal_header_tips);
        abnormalUnscrambleExpandable = findViewById(R.id.abnormal_unscramble_expandable);

        title.setText("异常解读");

        AbnormalScrambleExpandableAdapter adapter = new AbnormalScrambleExpandableAdapter(this);

        abnormalUnscrambleExpandable.setAdapter(adapter);
    }

    @Override
    public void getData() {

    }
}
