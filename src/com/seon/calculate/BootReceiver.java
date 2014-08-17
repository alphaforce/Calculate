package com.seon.calculate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Songful on 2014/8/17.
 */
public class BootReceiver extends BroadcastReceiver {
    private final static String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(BOOT_ACTION)){
            context.startService(new Intent(context, CalculateService.class));
        }
    }
}
