package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.FuncUtils;

public class FeedbackActivity extends BaseActivity {
    private TextView titleText;

    private int max = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        titleText = findViewById(R.id.title_header_feedback).findViewById(R.id.menu_bar_title);
        final TextView inputTextNum = findViewById(R.id.input_text_num);
        final EditText textArea = findViewById(R.id.text_area);
        final EditText userContactInfo = findViewById(R.id.user_contact_info);
        Button commitFeedbackButton = findViewById(R.id.commit_feedback_button);

        textArea.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = max - s.length();
                inputTextNum.setText(number + "/100");
                selectionStart = textArea.getSelectionStart();
                selectionEnd = textArea.getSelectionEnd();
                if (temp.length() > max) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    textArea.setText(s);
                    textArea.setSelection(tempSelection);
                }
            }
        });

        commitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userContactInfo.getText().toString().equals("")){
                    Toast.makeText(FeedbackActivity.this,"请留下您的联系方式!",Toast.LENGTH_SHORT).show();
                }else if(!FuncUtils.isMobileNO(userContactInfo.getText().toString())){
                    Toast.makeText(FeedbackActivity.this,"请留下您的正确联系方式，以方便我们联系您",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FeedbackActivity.this,"请的反馈信息已经提交，谢谢你的支持!",Toast.LENGTH_SHORT).show();
                    inputTextNum.setText( "100/100");
                    textArea.setText("");
                }
            }
        });

        titleText.setText("意见反馈");
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }
}
