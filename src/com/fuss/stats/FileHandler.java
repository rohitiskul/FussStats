package com.fuss.stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

public class FileHandler 
{
	private static String mainPath ;
	private static String filename;
	ArrayList<RomData> listArray = new ArrayList<RomData>();

	public FileHandler()
	{
		mainPath = Environment.getRootDirectory().getPath();
		filename = "fuss.prop"; //this must be changed to build.prop while delivering
	}
	
	public ArrayList<RomData> readFile(Context c)
	{
		addToList(Constants.ISITROM_HEAD, "DeviceId", Utilities.getUniqueID(c));
		addToList(Constants.MODNAME_HEAD, "ModName", Utilities.getModName());
		addToList(Constants.MODVERSION_HEAD, "ModVersion", Utilities.getModVersion());
		addToList(Constants.DEVICE_HEAD, "Device", Utilities.getDevice());
		addToList(Constants.BUILTDATE_HEAD, "BuiltDate", Utilities.getBuiltDate());
		addToList(Constants.COUNTRY_HEAD, "Country", Utilities.getCountryCode(c));
		return listArray;
	}
//	public ArrayList<RomData> readFile()
//	{
//		File mainFile = new File(mainPath+File.separator+filename);
//		String readerStr = null;
//		try 
//		{
//			BufferedReader br = new BufferedReader(new FileReader(mainFile));
//			String[] spliter;
//			while( (readerStr=br.readLine()) !=null)
//			{
//				spliter = null;
//				if(readerStr.startsWith(Constants.ISITROM))
//				{					
//					spliter = readerStr.split(Constants.SPLIT_EXPRESSION);
//					addToList(spliter,Constants.ISITROM_HEAD);
//				}
//				else if(readerStr.startsWith(Constants.MODNAME))
//				{
//					spliter = readerStr.split(Constants.SPLIT_EXPRESSION);
//					addToList(spliter,Constants.MODNAME_HEAD);
//				}
//				else if(readerStr.startsWith(Constants.MODVERSION))
//				{
//					spliter = readerStr.split(Constants.SPLIT_EXPRESSION);
//					addToList(spliter,Constants.MODVERSION_HEAD);
//				}
//				else if(readerStr.startsWith(Constants.DEVICE))
//				{
//					spliter = readerStr.split(Constants.SPLIT_EXPRESSION);
//					addToList(spliter,Constants.DEVICE_HEAD);
//				}
//				else if(readerStr.startsWith(Constants.BUILTDATE))
//				{
//					spliter = readerStr.split(Constants.SPLIT_EXPRESSION);
//					addToList(spliter,Constants.BUILTDATE_HEAD);
//				}
//			}
//		} 
//		catch (FileNotFoundException e) 
//		{		
//			e.printStackTrace();
//			return new ArrayList<RomData>();
//		}
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//			return new ArrayList<RomData>();
//		}
//		
//		return listArray;
//	}
	
//	private void addToList(String[] spliter,String xmlHeader)
//	{
//		RomData rd = new RomData();
//		rd.setHeader(spliter[0]);
//		rd.setValue(spliter[1]);
//		rd.setXmlHeader(xmlHeader);
//		listArray.add(rd);
//	}
	
	private void addToList(String xmlHeader,String key,String value)
	{
		RomData rd = new RomData();
		rd.setKey(key);
		rd.setValue(value);
		rd.setXmlHeader(xmlHeader);
		listArray.add(rd);
	}
}
