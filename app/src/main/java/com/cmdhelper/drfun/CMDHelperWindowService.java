package com.cmdhelper.drfun;

import com.cmdhelper.drfun.CommandList;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.io.*;

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
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        // 背景
        GradientDrawable buttonDrawable = new GradientDrawable();
        buttonDrawable.setColor(Color.parseColor("#FFFFFF"));
        // 获取屏幕宽高
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        if (Settings.canDrawOverlays(this)) {
            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackground(buttonDrawable);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenHeight));
            LinearLayout topLayout = new LinearLayout(this);
            topLayout.setOrientation(LinearLayout.HORIZONTAL);
            topLayout.setGravity(Gravity.CENTER);
            topLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, new Double(screenHeight * 0.06).intValue()));
            topLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            topLayout.setElevation(10f);
            topLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    windowManager.removeView(linearLayout);
                    isStarted = false;
                    MainActivity.cmdhelperOpen = false;
                    stopSelf();

                }
            }
            );
            TextView textView = new TextView(this);
            textView.setText("CMDHelper");
            // 指令列表

            LinearLayout cmdListLayout = new LinearLayout(this);
            cmdListLayout.setOrientation(LinearLayout.VERTICAL);
            cmdListLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, new Double(screenHeight * 0.88).intValue()));
            ListView cmdListView = new ListView(this);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CommandList.commandList());
            cmdListView.setAdapter(arrayAdapter);
            // 下方搜索框
            LinearLayout bottomLayout = new LinearLayout(this);
            bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
            bottomLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, new Double(screenHeight * 0.06).intValue()));
            bottomLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            bottomLayout.setElevation(10f);
            // 搜索框
            EditText searchEditText = new EditText(this);
            searchEditText.setLayoutParams(new LinearLayout.LayoutParams(new Double(screenWidth * 0.8).intValue(), new Double(screenHeight * 0.06).intValue()));
            searchEditText.setHint("Type command here");
            searchEditText.setBackgroundColor(Color.parseColor("#FFFFFF"));
            // 监听事件
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    arrayAdapter.getFilter().filter(charSequence);
                }
                @Override
                public void afterTextChanged(Editable editable) {
                }
            }
            );
            // 监听事件
            cmdListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String result = ((TextView) view).getText().toString();
                    searchEditText.setText(result);
                }
            }
            );
            // 添加布局
            topLayout.addView(textView);
            cmdListLayout.addView(cmdListView);
            bottomLayout.addView(searchEditText);
            linearLayout.addView(topLayout);
            linearLayout.addView(cmdListLayout);
            linearLayout.addView(bottomLayout);
            windowManager.addView(linearLayout, layoutParams);
        }
    }


}
