package com.fuss.stats;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PreviewActivity extends Activity 
{

	private ListView previewListview = null;
	private ArrayList<RomData> romValuesArray = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        
        previewListview = (ListView) findViewById(R.id.previewList);        
        romValuesArray = new ArrayList<RomData>();
		romValuesArray = new ValuesReader().readValues(getApplicationContext());
		previewListview.setAdapter(new PreviewListAdapter());
    }
    
    private class PreviewListAdapter extends BaseAdapter
    {

		@Override
		public int getCount() 
		{
			return romValuesArray.size();
		}

		@Override
		public Object getItem(int position) 
		{		
			return romValuesArray.get(position);
		}

		@Override
		public long getItemId(int position) 
		{		
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			convertView = getLayoutInflater().inflate(R.layout.fuss_list_item, null);			
			((TextView)((LinearLayout)convertView).getChildAt(0)).setText(romValuesArray.get(position).getKey());
			((TextView)((LinearLayout)convertView).getChildAt(1)).setText(romValuesArray.get(position).getValue());
			return convertView;
		}
    	
    }
}
