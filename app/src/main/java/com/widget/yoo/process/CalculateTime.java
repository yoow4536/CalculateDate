package com.widget.yoo.process;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.widget.yoo.util.Yoo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateTime extends Service {
    private String TAG = "yoo_log";
    private boolean debug = false;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private UpdateThread thread;

    @Override
    public void onCreate() {
        printLog("onCreate");
        if (thread == null) {
            thread = new UpdateThread();
        }
        thread.start();
        mContext = this.getApplicationContext();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        printLog("onDestroy");
        if (thread != null) {
            thread.interrupt();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printLog("onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void printLog(String msg) {
        if (debug == true) {
            Log.i(TAG, "printLog: " + msg);
        }
    }

    private int count = 0;
    private Context mContext;
    private String INTENT_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    private class UpdateThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                count = 0;
                while (true) {
                    Intent updateIntent = new Intent(INTENT_ACTION);

                    SharedPreferences preferences = getSharedPreferences("yoo", Context.MODE_PRIVATE);
                    String targetDate = preferences.getString("targetDate", "unset");
                    Log.i(TAG, "run: -------------------->targetDate:" + targetDate);
                    if (targetDate.equals("please set targetDate")) {
                        updateIntent.putExtra("result", targetDate);
                        mContext.sendBroadcast(updateIntent);
                    } else {
                        String nowDate = (new SimpleDateFormat(Yoo.FORMATE_DATE).format(new Date()));
                        String result = new Yoo().dateDiff(nowDate, targetDate, Yoo.FORMATE_DATE);
                        updateIntent.putExtra("result", result);
                        mContext.sendBroadcast(updateIntent);
                    }
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                // 将 InterruptedException 定义在while循环之外，意味着抛出 InterruptedException 异常时，终止线程。
                e.printStackTrace();
            }
        }
    }

}
