package com.example.fastapp;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    static public Context getContext() {
        return context;
    }
}
