package com.cmdhelper.drfun;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class CMDHelperWindowService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private LinearLayout linearLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取屏幕宽高
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = screenWidth;
        layoutParams.height = screenHeight;
        layoutParams.x = 0;
        layoutParams.y = 0;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        // 背景
        GradientDrawable buttonDrawable = new GradientDrawable();
        buttonDrawable.setColor(Color.parseColor("#FFFFFF"));
        // 获取屏幕宽高
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        if (Settings.canDrawOverlays(this)) {
            linearLayout = new LinearLayout(this);
            LinearLayout topLayout = new LinearLayout(this);
            topLayout.setOrientation(LinearLayout.HORIZONTAL);
            topLayout.setGravity(Gravity.CENTER);
            topLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

            linearLayout.setBackground(buttonDrawable);
            windowManager.addView(linearLayout, layoutParams);
        }
    }


}
