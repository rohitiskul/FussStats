package com.fuss.stats;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class WebRequest 
{	
	String url = null;
		
	HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    InputStream inputStream;
    List<NameValuePair> nameValuePairs;    
	
	public WebRequest(String urlServer)
	{		
		this.url = urlServer;
	}

	
	public boolean postData(Context c ,String xmlString) 
	{
		try 
		{
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(url);
			// Add your data
			nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("senddata", xmlString));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			inputStream = response.getEntity().getContent();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String str = null;	StringBuilder sb= new StringBuilder();
			while((str=br.readLine())!=null)
			{
				sb.append(str);
				sb.append("\r\n");
			}			
			inputStream.close();
			Log.i("POSTDATA", "Successfully returned true");
			Toast.makeText(c, c.getResources().getString(R.string.sending), Toast.LENGTH_SHORT).show();
			return true;
		}	
	
		catch (Exception e)
		{			
			Log.e("POSTDATA", "error:"+e.toString());
			return false;
		}             
	}
}
