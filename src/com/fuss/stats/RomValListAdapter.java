package com.fuss.stats;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RomValListAdapter extends BaseAdapter
{
	
	private ArrayList<RomData> fussArray;
	private LayoutInflater mInflater;
	
	
	public RomValListAdapter(Context c,ArrayList<RomData> fussArray)
	{
		mInflater = LayoutInflater.from(c);
		this.fussArray = fussArray;
	}
	
	@Override
	public int getCount() {
		return fussArray.size();
	}

	@Override
	public Object getItem(int position) {
		return fussArray.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder = new ViewHolder();
		convertView = mInflater.inflate(R.layout.fuss_list_item, null);
		
		holder.headingTv = (TextView)convertView.findViewById(R.id.headingTv);
		holder.valueTv = (TextView)convertView.findViewById(R.id.valueTv);
		
		holder.headingTv.setText(fussArray.get(position).getKey());
		holder.valueTv.setText(fussArray.get(position).getValue());
		
		return convertView;
	}
	
	static class ViewHolder
	{
		TextView headingTv,valueTv;
	}
}
