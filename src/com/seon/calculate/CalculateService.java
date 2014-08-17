package com.seon.calculate;

import android.app.Service;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

public class CalculateService extends Service  {
    private final static  String TAG = "CalculateService";
    public static final int NOTIFICATION_ID = 18650;
    private Notification mFlow;

/*    public CalculateService() {
        super("CalculateService");
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        return Service.START_STICKY;
    }

    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String param1 = prefs.getString(Params.PARAMS_1, "1");
        String param2 = prefs.getString(Params.PARAMS_2, "2");

        double p1 = Double.valueOf(param1);
        double p2 = Double.valueOf(param2);

        sendNotification(p1 + p2, p1 - p2, p1 * p2, p1 / p2);
    }

    public void sendNotification(double sum, double sub, double mul, double dec) {
        Log.i(TAG, sum +" "+sub +" "+ mul +" "+ dec );
        Notification.Builder mBuilder = new Notification.Builder(getApplication());

        RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.notify_layout);
        view_custom.setTextViewText(R.id.sum, String.valueOf(sum));
        view_custom.setTextViewText(R.id.sub, String.valueOf(sub));
        view_custom.setTextViewText(R.id.mul, String.valueOf(mul));
        view_custom.setTextViewText(R.id.dec, String.valueOf(dec));

        mBuilder.setContent(view_custom)
                .setTicker("计算结果")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent(getApplicationContext(), MainActivity.class), 0));

        mFlow = mBuilder.build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mFlow);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
