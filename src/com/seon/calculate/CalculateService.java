package com.seon.calculate;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public class CalculateService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private Notification mFlow;
    Notification.Builder mBuilder;
    NotificationManager mNotificationManager;

	public CalculateService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mParam1 = prefs.getString(Params.PARAMS_1, "1");
        String mParam2 = prefs.getString(Params.PARAMS_2, "2");



	}

    public void sendNotification() {
        RemoteViews view_custom;
        // 先设定RemoteViews
        view_custom = new RemoteViews(getPackageName(), R.layout.notify_layout);
        view_custom.setTextViewText(R.id.textView, "流量监控");
        view_custom.setTextViewText(R.id.textView2, "10M/300M");
        mBuilder = new Notification.Builder(this);
        mBuilder.setContent(view_custom).setWhen(System.currentTimeMillis()) // 通知产生的时间，会在通知信息里显示
                .setTicker("开启流量监控").setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                ;
        mFlow = mBuilder.build();
        mFlow.flags = Notification.FLAG_ONGOING_EVENT
                | Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(NOTIFICATION_ID, mFlow);
    }


}
