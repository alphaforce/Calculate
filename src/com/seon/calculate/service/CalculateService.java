package com.seon.calculate.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import com.seon.calculate.R;
import com.seon.calculate.MainActivity;
import com.seon.calculate.common.Params;

public class CalculateService extends IntentService {
    private final static String TAG = "CalculateService";
    public static final int NOTIFICATION_ID = 18650;

    public CalculateService() {
        super("CalculateService");
    }

    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        int param1 = prefs.getInt(Params.PARAMS_1, 0);
        int param2 = prefs.getInt(Params.PARAMS_2, 1);

        sendNotification(param1, param2);
    }

    private void sendNotification(int param1, int param2) {
        int sum = param1 + param2;
        int sub = param1 - param2;
        long mul = 1l * param1 * param2;
        double dec = (double) param1 / param2;
        Log.i(TAG, sum + " " + sub + " " + mul + " " + dec);
        Notification.Builder mBuilder = new Notification.Builder(getApplication());

        RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.notify_layout);
        view_custom.setTextViewText(R.id.sum, String.valueOf(sum));
        view_custom.setTextViewText(R.id.sub, String.valueOf(sub));
        view_custom.setTextViewText(R.id.mul, String.valueOf(mul));
        view_custom.setTextViewText(R.id.dec, String.format("%.2f", dec));

        mBuilder.setContent(view_custom)
                .setTicker("计算结果")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle data = new Bundle();
        data.putBoolean(Params.NOTIFICATION_FLAG, true);
        intent.putExtras(data);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mBuilder.setContentIntent(PendingIntent.getActivity(getApplicationContext(),
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
