package com.fuss.stats;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

public class ReportingServiceManager extends BroadcastReceiver 
{

    @Override
    public void onReceive(Context ctx, Intent intent) 
    {
        SharedPreferences settings = ctx.getSharedPreferences(Constants.PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) 
        {
            editor.putBoolean(Constants.CHECKED_IN, false);
            editor.commit();
            Log.d(Constants.TAG, "BOOT_COMPLETED: Setting checkedin=false");
        }
        
        if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) 
        {
            editor.putBoolean(Constants.CHECKED_IN, false);
            editor.commit();
            Log.d(Constants.TAG, "ACTION_SHUTDOWN: Setting checkedin=false");
        }
        
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) 
        {
            boolean checkedin = settings.getBoolean(Constants.CHECKED_IN, false);
            
            Bundle extras = intent.getExtras();
            boolean noConnectivity = extras.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            
            Log.d(Constants.TAG, "CONNECTIVITY_ACTION: noConnectivity = " + noConnectivity);
            
            if (!checkedin && !noConnectivity) 
            {	
            	//start service which will send data to server
                Log.d(Constants.TAG, "CONNECTIVITY_ACTION: starting service");
                ComponentName cmp = new ComponentName(ctx.getPackageName(), ReportingService.class.getName());
                ctx.startService(new Intent().setComponent(cmp));
            }
        }
    }

}
