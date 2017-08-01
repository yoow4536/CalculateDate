package com.widget.yoo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/1.
 */

public class MyApplication extends Application {

    private final String TAG = "yoo_log";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: this it the application");
        SharedPreferences preferences = getSharedPreferences("yoo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("targetDate", "please set targetDate");
        editor.commit();
    }
}
