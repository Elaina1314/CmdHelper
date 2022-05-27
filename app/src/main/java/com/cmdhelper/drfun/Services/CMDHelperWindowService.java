package com.cmdhelper.drfun.Services;

import com.cmdhelper.drfun.Util.ClipboardUtil;
import com.cmdhelper.drfun.Util.CommandList;
import com.cmdhelper.drfun.MainActivity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CMDHelperWindowService extends Service {

    public static boolean isStarted = false;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private LinearLayout linearLayout;

    @SuppressLint("RtlHardcoded")
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

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        // 背景
        GradientDrawable buttonDrawable = new GradientDrawable();
        buttonDrawable.setColor(Color.parseColor("#FFFFFF"));
        // 获取屏幕宽高
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackground(buttonDrawable);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenHeight));
        LinearLayout topLayout = new LinearLayout(this);
        topLayout.setOrientation(LinearLayout.HORIZONTAL);
        topLayout.setGravity(Gravity.CENTER);
        topLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, Double.valueOf(screenHeight * 0.06).intValue()));
        topLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        topLayout.setElevation(10f);
        topLayout.setOnClickListener(v -> {
            windowManager.removeView(linearLayout);
            isStarted = false;
            MainActivity.cmdhelperOpen = false;
            stopSelf();
        }
        );
        TextView textView = new TextView(this);
        textView.setText("CMDHelper");
        // 指令列表
        LinearLayout cmdListLayout = new LinearLayout(this);
        cmdListLayout.setOrientation(LinearLayout.VERTICAL);
        cmdListLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, Double.valueOf(screenHeight * 0.88).intValue()));
        ListView cmdListView = new ListView(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CommandList.commandList());
//      ArrayAdapter commandTargetSelectorAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CommandList.commandTargetSelector()); //选择器列表
//      ArrayAdapter commandTargetSelectorParametersAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CommandList.commandTargetSelectorParameters()); //选择器参数列表
        cmdListView.setAdapter(arrayAdapter);
        // 下方搜索框
        LinearLayout bottomLayout = new LinearLayout(this);
        bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
        bottomLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, Double.valueOf(screenHeight * 0.06).intValue()));
        bottomLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        bottomLayout.setElevation(10f);
        // 搜索框
        EditText searchEditText = new EditText(this);
        searchEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        searchEditText.setMaxLines(1);
        searchEditText.setLines(1);
        searchEditText.setLayoutParams(new LinearLayout.LayoutParams(Double.valueOf(screenWidth * 0.8).intValue(), Double.valueOf(screenHeight * 0.06).intValue()));
        searchEditText.setHint("Type command here");
        searchEditText.setBackgroundColor(Color.parseColor("#FFFFFF"));
        // 左侧菜单按钮
        Button settingButton = new Button(this);
        settingButton.setText("🏆");
        settingButton.setBackground(buttonDrawable);
        settingButton.setLayoutParams(new LinearLayout.LayoutParams(Double.valueOf(screenWidth * 0.1).intValue(), Double.valueOf(screenHeight * 0.06).intValue()));
        // 复制按钮
        Button copyButton = new Button(this);
        copyButton.setText("📋");
        copyButton.setBackground(buttonDrawable);
        copyButton.setLayoutParams(new LinearLayout.LayoutParams(Double.valueOf(screenWidth * 0.1).intValue(), Double.valueOf(screenHeight * 0.06).intValue()));
        copyButton.setOnClickListener(v -> ClipboardUtil.putTextIntoClip(this, searchEditText.getText().toString()));
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
        cmdListView.setOnItemClickListener((parent, view, position, id) -> {
            String result = ((TextView) view).getText().toString();
            searchEditText.setText(result);
        }
        );
        // 添加布局
        topLayout.addView(textView);
        cmdListLayout.addView(cmdListView);
        bottomLayout.addView(settingButton);
        bottomLayout.addView(searchEditText);
        bottomLayout.addView(copyButton);
        linearLayout.addView(topLayout);
        linearLayout.addView(cmdListLayout);
        linearLayout.addView(bottomLayout);
        windowManager.addView(linearLayout, layoutParams);
    }


}