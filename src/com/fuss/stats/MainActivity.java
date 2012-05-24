package com.fuss.stats;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity 
{

    private CheckBox mCheckbox = null;
    private TextView thanku = null;
    private Button previewData= null,viewStats=null;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        thanku = (TextView) findViewById(R.id.thankutext);
        mCheckbox = (CheckBox) findViewById(R.id.main_optin);
        mCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
            {
            	if(isChecked) thanku.setText(getResources().getString(R.string.thxoptin));
            	else thanku.setText("");
            	
                SharedPreferences settings = getSharedPreferences(Constants.PREF_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(Constants.OPT_IN, isChecked);
                editor.putBoolean(Constants.FIRST_BOOT, false);
                editor.commit();
                startReportingService();                
            }
        });
        
        previewData = (Button) findViewById(R.id.previewBtn);
        previewData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(MainActivity.this,PreviewActivity.class));
			}
		});
        
        viewStats = (Button) findViewById(R.id.viewstatsBtn);
        viewStats.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.team-blockbuster.com/stats/viewstats.php"));
				startActivity(intent);
			}
		});
        
        SharedPreferences settings = getSharedPreferences(Constants.PREF_NAME, 0);
        boolean optin = settings.getBoolean(Constants.OPT_IN, true);

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.FIRST_BOOT, false);
        editor.commit();

        mCheckbox.setChecked(optin);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(1);
    }

    private void startReportingService()
    {
        ComponentName cmp = new ComponentName(getPackageName(), ReportingService.class.getName());
        startService(new Intent().setComponent(cmp));
    }
}
