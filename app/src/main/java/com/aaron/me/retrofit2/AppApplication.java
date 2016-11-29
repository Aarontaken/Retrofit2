package com.aaron.me.retrofit2;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aaron Wang on 2016/11/29.
 */
public class AppApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
