package com.siyehua.wechattextexpression;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 可自定义字体的TextView
 */
public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);
        setCustomFont();
    }


    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont();
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setCustomFont();
    }


    public void setCustomFont() {
        try {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "abc.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "Could not get typeface: " + e.getMessage());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 描外层
        Paint m_TextPaint = getPaint();
        int tmpColor = m_TextPaint.getColor();
        setTextColor(Color.parseColor("#EBEBEB"));
        m_TextPaint.setStrokeWidth(10);  // 描边宽度
        m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE); //描边种类
        m_TextPaint.setFakeBoldText(true); // 外层text采用粗体
//        m_TextPaint.setShadowLayer(0, 0, 0, 0); //字体的阴影效果，可以忽略
        super.onDraw(canvas);


        // 描内层，恢复原先的画笔
        setTextColor(tmpColor);
        m_TextPaint.setStrokeWidth(0);
        m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        m_TextPaint.setFakeBoldText(true);
//        m_TextPaint.setShadowLayer(0, 0, 0, 0);
        super.onDraw(canvas);
    }

}