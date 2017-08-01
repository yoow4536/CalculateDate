package com.widget.yoo.mwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.widget.yoo.R;
import com.widget.yoo.aty.SecondActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private String TAG = "yoo_log";
    private final Intent intent = new Intent("android.appwidget.action.CALCULATETIME_SERVICE");
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        Intent startActivityIntent = new Intent(context, SecondActivity.class);
        PendingIntent pendingIntent = android.app.PendingIntent.getActivity(context, 0, startActivityIntent, 0);
        views.setOnClickPendingIntent(R.id.btn_1,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private ArrayList ids=new ArrayList();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.i(TAG, "onUpdate: this is the onUpdate"+"+appWidgetIds.length :"+appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            ids.add(appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i(TAG, "onDeleted: appWidgetIds.length:"+appWidgetIds.length);
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "onEnabled: this is onEnable");
        // Enter relevant functionality for when the first widget is created
        context.startService(intent);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.stopService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            String result = intent.getStringExtra("result");
            Log.i(TAG, "onReceive: get the broadcast ,"+"result is :"+result);
            if (result != null) {
                ComponentName componentName = new ComponentName(context, NewAppWidget.class);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
                views.setTextViewText(R.id.tv,result);
                AppWidgetManager.getInstance(context).updateAppWidget(componentName,views);
            }
        }
        super.onReceive(context, intent);
    }
}

