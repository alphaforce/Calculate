package com.seon.calculate.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.seon.calculate.service.CalculateService;

/**
 * Created by Songful on 2014/8/17.
 */
public class BootReceiver extends BroadcastReceiver {
    private final static String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, CalculateService.class));
            Log.i(TAG, "CalculateService started");
    }
}
