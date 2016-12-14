package com.android.ll.znns;

import android.app.Application;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

/**
 * Created by Administrator on 2016/12/14.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
