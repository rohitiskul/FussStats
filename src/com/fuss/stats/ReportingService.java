package com.fuss.stats;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class ReportingService extends Service
{
	    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() 
	{
		if (isFirstBoot()) 
		{
            promptUser();
            Log.d(Constants.TAG, "Prompting user for opt-in.");
        } 
		else if (canReport() == true) 
		{
            Log.d(Constants.TAG, "User has opted in -- reporting.");
            Thread thread = new Thread() 
            {
                @Override
                public void run() 
                {
                    report();
                }
            };
            thread.start();
        } else 
        {
            Log.d(Constants.TAG, "User has not opted in -- skipping reporting.");
            stopSelf();
        }
	}
	
	 private boolean isFirstBoot() 
	 {
	        SharedPreferences settings = getSharedPreferences(Constants.PREF_NAME, 0);
	        boolean firstboot = settings.getBoolean(Constants.FIRST_BOOT, true);
	        return firstboot;	    
	 }
	    
	    
	 private void setCheckedIn(boolean checkedin)
	 {
	        SharedPreferences settings = getSharedPreferences(Constants.PREF_NAME, 0);
	        SharedPreferences.Editor editor = settings.edit();
	        
	        editor.putBoolean(Constants.CHECKED_IN, checkedin);
	        editor.commit();
	        Log.d(Constants.TAG, "SERVICE: setting checkedin=" + checkedin);	 
	 }

		    
	private boolean canReport()  
	{
		SharedPreferences settings = getSharedPreferences(Constants.PREF_NAME, 0);		        
		// Determine whether or not we have already checked in.
		boolean checkedin = settings.getBoolean(Constants.CHECKED_IN, false);		
		// Determine opt-in status (opt-in by default)
		boolean optin = settings.getBoolean(Constants.OPT_IN, true);		
		if (optin && !checkedin) 
		{
			return true;
		} 
		else 
		{
			return false;
		}		    
	}
	    
	private void report()
	{
		ArrayList<RomData> romValuesArray = new ArrayList<RomData>();
		romValuesArray = new ValuesReader().readValues(getApplicationContext());
		XMLGenerator xmlgen = new XMLGenerator(romValuesArray);
		String xmlData=null;
		xmlData = xmlgen.createXML();
		WebRequest wr = new WebRequest(Constants.SERVER_URL);
		boolean checkedin = wr.postData(getApplicationContext(), xmlData);
		setCheckedIn(checkedin);
		stopSelf();
	}
	
	
	private void promptUser() 
	{
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.ic_launcher,
		        getString(R.string.notification_ticker), System.currentTimeMillis());
		Intent nI = new Intent(this, MainActivity.class);
		PendingIntent pI = PendingIntent.getActivity(getApplicationContext(), 0, nI, 0);
		n.setLatestEventInfo(getApplicationContext(),
		        getString(R.string.notification_title),
		        getString(R.string.notification_desc), pI);
		nm.notify(1, n);
	}
}
