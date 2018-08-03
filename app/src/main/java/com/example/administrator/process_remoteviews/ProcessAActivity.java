package com.example.administrator.process_remoteviews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.example.administrator.remoteviewstest.R;

public class ProcessAActivity extends AppCompatActivity {

    private LinearLayout mRemoteViewsContainer;
    private MyRemoteViewsReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_a);
        mRemoteViewsContainer = findViewById(R.id.remoteViews_container);
        receiver = new MyRemoteViewsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.package.remoteViews");
        registerReceiver(receiver, filter);
    }

    private void updateUI(RemoteViews remoteViews){
        int layoutID = getResources().getIdentifier("notify_layout", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutID, mRemoteViewsContainer, false);
        remoteViews.reapply(this, view);
        mRemoteViewsContainer.addView(view);


//        View view = remoteViews.apply(this, mRemoteViewsContainer);
//        mRemoteViewsContainer.addView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private class MyRemoteViewsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.package.remoteViews")) {
                RemoteViews remoteViews = intent.getParcelableExtra("remoteViews");
                if (remoteViews != null) {
                    updateUI(remoteViews);
                }
            }
        }
    }
}
