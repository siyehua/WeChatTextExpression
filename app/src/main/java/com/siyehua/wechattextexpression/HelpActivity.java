package com.siyehua.wechattextexpression;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();
    }

    public void playVideo(View view) {
        view.setEnabled(false);
        startActivity(new Intent(this, PlayVideoActivity.class));
        view.setEnabled(true);
    }
}
