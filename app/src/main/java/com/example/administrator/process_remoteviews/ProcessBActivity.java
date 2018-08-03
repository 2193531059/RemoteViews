package com.example.administrator.process_remoteviews;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.administrator.remoteviewstest.R;

public class ProcessBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_b);
        findViewById(R.id.send_broadcast_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadCast();
            }
        });
    }

    private void sendBroadCast(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_layout);
        remoteViews.setTextViewText(R.id.info_text, "message from : " + Process.myPid());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ProcessAActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentActivity = PendingIntent.getActivity(this, 0,
                new Intent(this, ProcessAActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.info_text, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.info_button, pendingIntentActivity);
        Intent intent = new Intent("com.package.remoteViews");
        intent.putExtra("remoteViews", remoteViews);
        sendBroadcast(intent);
    }
}
