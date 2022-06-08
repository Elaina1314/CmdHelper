package com.cmdhelper.drfun.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class MIUIEditText extends AppCompatEditText {
    public MIUIEditText(Context context) {
        super(context);
    }

    public MIUIEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MIUIEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setSingleLine(true);
        setTextColor(Color.parseColor("#000000"));
        setHintTextColor(Color.parseColor("#88000000"));
        setGravity(16);
        @SuppressLint("DrawAllocation") GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(30.0f);
        gradientDrawable.setStroke(5, Color.parseColor("#FF1E88E5"));
        setBackground(gradientDrawable);
    }
}
