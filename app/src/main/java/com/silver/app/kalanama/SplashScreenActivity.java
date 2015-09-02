package com.silver.app.kalanama;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;


import com.silver.app.kalanama.utility.ActivityBase;

public class SplashScreenActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
