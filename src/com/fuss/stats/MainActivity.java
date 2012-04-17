package com.fuss.stats;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	ListView romValuesList; 	Button sendBtn;
	
	ArrayList<RomData> romValuesArray = new ArrayList<RomData>();
	RomValListAdapter adapter;
	
	String xmlData=null;
	String url= null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
             
        url = getResources().getString(R.string.link);
        romValuesList = (ListView)findViewById(R.id.unique_values_list);
        sendBtn = (Button)findViewById(R.id.send_btn);
        
        new ReadData().execute();
        
        sendBtn.setOnClickListener(new SendClick());
    }
    
    private class ReadData extends AsyncTask<String, Void, Void>
    {    	
    	ProgressDialog pDialog;
    	@Override
    	protected void onPreExecute() 
    	{    		
    		pDialog = new ProgressDialog(MainActivity.this);
    		pDialog.setMessage("Reading values...");
    		pDialog.setIndeterminate(true);
    		pDialog.show();
    	}
		@Override
		protected Void doInBackground(String... params) 
		{
			romValuesArray = new FileHandler().readFile();
			return null;			
		}
		@Override
		protected void onPostExecute(Void result) 
		{
			pDialog.dismiss();
			
			adapter = new RomValListAdapter(MainActivity.this, romValuesArray);
			romValuesList.setAdapter(adapter);
			
			XMLGenerator xmlgen = new XMLGenerator(romValuesArray);
			xmlData = xmlgen.createXML();
		}    	
    }
    
    private class SendClick implements OnClickListener
    {    	       
    	boolean b ;
    	ProgressDialog pDialog;
    	Handler handler;
		@Override
		public void onClick(View v) 
		{
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Sending data to server...");
			pDialog.setIndeterminate(true);
			pDialog.show();
			handler = new Handler(){
	        	@Override
	        	public void handleMessage(Message msg) 
	        	{	        	
	        		pDialog.dismiss();
	        		if(b)	        
	    	        	Toast.makeText(MainActivity.this, "Successfully submitted stats", Toast.LENGTH_SHORT).show();
	    	        else
	    	        	Toast.makeText(MainActivity.this, "Internet Connection Problem", Toast.LENGTH_SHORT).show();
	        	}
	        };
			Thread thread = new Thread() {
	            @Override
	            public void run() {
	        		WebRequest wr = new WebRequest(Constants.SERVER_URL);
	        		b = wr.postData(MainActivity.this, xmlData);
	        		handler.sendMessage(handler.obtainMessage());
	            }
	        };	        
	        
	        thread.start();	        
		}
    }    	
}