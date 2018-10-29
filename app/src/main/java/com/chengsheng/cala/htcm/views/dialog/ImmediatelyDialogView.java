package com.chengsheng.cala.htcm.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;

public class ImmediatelyDialogView extends Dialog {
    public ImmediatelyDialogView(Context context) {
        super(context);
    }

    public ImmediatelyDialogView(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ImmediatelyDialogView(Context context, boolean cancelable,DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.dialog_immediately_certification_layout,null);
        setContentView(rootView);

        TextView certificationText = (TextView) rootView.findViewById(R.id.certification_text);
        Button cancelCer = (Button) rootView.findViewById(R.id.cancel_certification_button);
        Button certification = (Button) rootView.findViewById(R.id.certification_button);


        String str_start = "\"认证码\"是本软件中将体检人和体检数据相互关联的唯一标识码，需要体检人到体检机构进行认证以后获得。" +
                           "为了保护体检人的健康隐私，只有在体检人成功认证后，用户才可以查看其体检数据。若您还未取得“认证码”，" +
                           "请前往体检登记处或拨打客服电话";
        String str_tagert = "028-7654321";
        TelClickableSpan telClickableSpan = new TelClickableSpan(str_tagert,getContext());
        SpannableString spannTagert = new SpannableString(str_tagert);
        spannTagert.setSpan(telClickableSpan,0,str_tagert.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        certificationText.setText(str_start);
        certificationText.append(spannTagert);
        certificationText.append("进行咨询。");
        certificationText.setMovementMethod(LinkMovementMethod.getInstance());

        cancelCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setCancelable(true);
    }

    class TelClickableSpan extends ClickableSpan{
        private String str;
        private Context context;

        public TelClickableSpan(String str,Context context){
            super();
            this.context = context;
            this.str = str;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
           ds.setColor(context.getResources().getColor(R.color.colorPrimary));
        }

        @Override
        public void onClick(View widget) {

            Toast.makeText(context,"点击测试",Toast.LENGTH_SHORT).show();

        }
    }
}
