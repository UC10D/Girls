package com.android.ll.znns.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.ll.znns.R;
import com.android.ll.znns.ui.splashImage.widget.WowSplashView;
import com.android.ll.znns.ui.splashImage.widget.WowView;
import com.android.ll.znns.utils.SharedPreferencesUtil;
import com.bumptech.glide.Glide;

/**
 * Created by LeeMy on 16/12/20.
 * 欢迎页
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView imgSplash;
    private WowSplashView mWowSplashView;
    private WowView mWowView;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        imgSplash = (ImageView) findViewById(R.id.img_splash);
        mWowSplashView = (WowSplashView) findViewById(R.id.wowSplash);
        mWowView = (WowView) findViewById(R.id.wowView);

        Glide.with(this)
                .load(R.drawable.guide_up_2)
                .asGif() //判断加载的url资源是否为gif格式的资源
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.guide_up_2)
                .into(imgSplash);

        if (!SharedPreferencesUtil.getBoolean(SplashActivity.this, "isGuideShowed", false)) {
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                        if (!SharedPreferencesUtil.getBoolean(SplashActivity.this,"isGuideShowed", false)) {
//                            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
//                            startActivity(intent);
//                        } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
//                        }
                    finish();
                }
            }, 3000);
//            mWowSplashView.startAnimate();
//
//            mWowSplashView.setOnEndListener(new WowSplashView.OnEndListener() {
//                @Override
//                public void onEnd(final WowSplashView wowSplashView) {
//                    mWowSplashView.setVisibility(View.GONE);
//                    mWowView.setVisibility(View.VISIBLE);
//                    mWowView.startAnimate(wowSplashView.getDrawingCache());
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                        if (!SharedPreferencesUtil.getBoolean(SplashActivity.this,"isGuideShowed", false)) {
////                            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
////                            startActivity(intent);
////                        } else {
//                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                            startActivity(intent);
////                        }
//                            finish();
//                        }
//                    }, 1000);
//                }
//            });
        }
    }
}
