package com.android.ll.znns;

import android.app.Application;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/12/14.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLog();
        initBmob();


    }

    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "6a7e5655fd3de79cfa88a6fb4c4d958e");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }

    private void initLog() {

        Logger.initialize(
                new Settings()
//                        .setStyle(new LogPrintStyle())
                        .isShowMethodLink(true)
                        .isShowThreadInfo(false)
                        .setMethodOffset(0)
                        .setLogPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
        );
    }
}
