package com.chengsheng.cala.htcm.views.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextViewBorder extends TextView {

    private static final int STROKE_WIDTH = 2;
    private int borderColor;

    private Paint borderPanit;


    public TextViewBorder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewBorder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextViewBorder(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(0 == this.getText().toString().length()){
            return;
        }

        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();

        borderPanit = new Paint();
        borderPanit.setColor(borderColor);
        borderPanit.setStyle(Paint.Style.STROKE);
        borderPanit.setStrokeWidth(4);
        borderPanit.setAntiAlias(true);
        canvas.drawRoundRect(2.5f,2.5f,w-2.5f,h-2.5f,8,8,borderPanit);

        super.onDraw(canvas);
    }

    public void setBorderColor(int newColor){
        borderColor = newColor;
        invalidate();
        requestLayout();
    }
}
