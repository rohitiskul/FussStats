package com.fuss.stats;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SendFussData extends AsyncTask<String, Void, Boolean>
{

	private ProgressDialog pDialog;
	private Context mContext;
	private String xmlData;
	
	public SendFussData(Context c,String xmlData)
	{
		this.mContext = c;
		this.xmlData = xmlData;
	}
	@Override
	protected void onPreExecute() 
	{
		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Sending data to server...");
		pDialog.setIndeterminate(true);
		pDialog.show();
	}
	
	@Override
	protected Boolean doInBackground(String... urls) 
	{
		WebRequest wr = new WebRequest(urls[0]);
		return wr.postData(mContext, xmlData);		
	}

	@Override
	protected void onPostExecute(Boolean result) 
	{
		pDialog.dismiss();
		if(!result)
		{
			Toast.makeText(mContext, "Internet Connection Problem", Toast.LENGTH_SHORT).show();
			return;
		}
		else 
		{
			Toast.makeText(mContext, "Successfully submitted stats", Toast.LENGTH_SHORT).show();
			return;
		}
	}
}
