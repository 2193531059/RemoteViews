package com.example.administrator.remoteviewstest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MyAppWidgetprovider extends AppWidgetProvider{
    private static final String TAG = "MyAppWidgetprovider";
    public static final String CLICK_ACTION = "com.myappwidget.action.click";

    public MyAppWidgetprovider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive: action = " + intent.getAction());
        if (intent.getAction().equals(CLICK_ACTION)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i = 0;i<37;i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        remoteViews.setImageViewBitmap(R.id.image_1, rotateBitmap(context, srcBitmap, degree));
                        Intent intent_click = new Intent();
                        intent_click.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent_click, 0);
                        remoteViews.setOnClickPendingIntent(R.id.image_1, pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetprovider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate: ------------------");
        final int counter = appWidgetIds.length;
        Log.e(TAG, "onUpdate: counter = " + counter);
        for (int i = 0;i<counter;i++) {
            int appWidgetID = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetID);
        }
    }

    private void onWidgetUpdate(Context context, AppWidgetManager manager, int appWidgetID){
        Log.e(TAG, "onWidgetUpdate: appWidgetID = " + appWidgetID);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent_click = new Intent();
        intent_click.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent_click, 0);
        remoteViews.setOnClickPendingIntent(R.id.image_1, pendingIntent);
        manager.updateAppWidget(appWidgetID, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        return bitmap;
    }
}
