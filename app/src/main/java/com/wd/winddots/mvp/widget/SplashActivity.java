package com.wd.winddots.mvp.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wd.winddots.R;
import com.wd.winddots.activity.login.LoginActivity;
import com.wd.winddots.utils.SpHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toElseActivity();
        SpHelper.getInstance(this).setInt("badge",0);
    }

    private void toElseActivity() {
        final String id = SpHelper.getInstance(getApplicationContext()).getString("id");
        final Intent intent = new Intent();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(id)) {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1500);
    }


    Handler mHandler = new Handler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
