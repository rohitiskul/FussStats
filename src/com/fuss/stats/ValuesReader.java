package com.fuss.stats;

import java.util.ArrayList;

import android.content.Context;


public class ValuesReader 
{
	ArrayList<RomData> listArray = new ArrayList<RomData>();
	
	public ArrayList<RomData> readValues(Context c)
	{
		addToList(Constants.ISITROM_HEAD, "DeviceId", Utilities.getUniqueID(c));
		addToList(Constants.MODNAME_HEAD, "ModName", Utilities.getModName());
		addToList(Constants.MODVERSION_HEAD, "ModVersion", Utilities.getModVersion());
		addToList(Constants.DEVICE_HEAD, "Device", Utilities.getDevice());
		addToList(Constants.BUILTDATE_HEAD, "BuiltDate", Utilities.getBuiltDate());
		addToList(Constants.COUNTRY_HEAD, "Country", Utilities.getCountryCode(c));
		return listArray;
	}
	
	private void addToList(String xmlHeader,String key,String value)
	{
		RomData rd = new RomData();
		rd.setKey(key);
		rd.setValue(value);
		rd.setXmlHeader(xmlHeader);
		listArray.add(rd);
	}
}
