package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.adapters.AbnormalScrambleExpandableListAdapter;

public class AbnormalUnscrambleActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private TextView abnormalHeaderTips;
    private ExpandableListView abnormalUnscrambleExpandable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal_unscramble);

        title = findViewById(R.id.title_header_abnormal_unscramble).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_abnormal_unscramble).findViewById(R.id.back_login);
        abnormalHeaderTips = findViewById(R.id.abnormal_header_tips);
        abnormalUnscrambleExpandable = findViewById(R.id.abnormal_unscramble_expandable);

        title.setText("异常解读");

        AbnormalScrambleExpandableListAdapter adapter = new AbnormalScrambleExpandableListAdapter(this);

        abnormalUnscrambleExpandable.setAdapter(adapter);
    }
}
