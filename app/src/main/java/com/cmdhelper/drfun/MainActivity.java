package com.cmdhelper.drfun;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmdhelper.drfun.Services.FloatingWindowService;

public class MainActivity extends AppCompatActivity {
    public static final int CODE_WINDOW = 0; // 标识
    public static boolean windowOpen = false; // 判断悬浮窗是否已经打开
    public static boolean cmdhelperOpen = false; // 判断cmdhelperservice是否已经打开
    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 背景布局
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER);
        // 关于
        TextView about = new TextView(this);
        about.setText("CMDHelper 开源且免费的命令助手\n开源链接:https://github.com/Elaina1314/CmdHelper");
        about.setGravity(Gravity.CENTER);
        // 申请悬浮窗权限
        Button getPermission = new Button(this);
        getPermission.setText("点击我进行申请权限");
        //点击事件
        getPermission.setOnClickListener(v -> {
            // 进行申请权限
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), CODE_WINDOW);
            Toast.makeText(MainActivity.this, "请给予权限！", Toast.LENGTH_SHORT).show();
        }
        );
        // 拉起悬浮窗
        Button startFloat = new Button(this);
        startFloat.setText("点击我拉起悬浮窗");
        startFloat.setOnClickListener(v -> {
            try {
                if (!Settings.canDrawOverlays(MainActivity.this)) {
                    throw new Exception("没有悬浮窗权限");
                }
                if (windowOpen) {
                    throw new Exception("重复打开悬浮窗");
                }
                startService(new Intent(MainActivity.this, FloatingWindowService.class));
                windowOpen = true;
                Toast.makeText(MainActivity.this, "已拉起悬浮窗", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        // 添加到布局
        mainLayout.addView(about);
        mainLayout.addView(getPermission);
        mainLayout.addView(startFloat);
        setContentView(mainLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_WINDOW) {
            if (Settings.canDrawOverlays(this)) {
                // 权限申请成功
                Toast.makeText(MainActivity.this, "权限申请成功！", Toast.LENGTH_SHORT).show();
            } else {
                // 权限申请失败
                Toast.makeText(MainActivity.this, "权限申请失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}