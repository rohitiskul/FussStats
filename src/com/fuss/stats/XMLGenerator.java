package com.fuss.stats;

import java.util.ArrayList;

import android.util.Log;

public class XMLGenerator 
{
	private static final String NEWLINE = "\r\n";
	private ArrayList<RomData> romvalueslist = new ArrayList<RomData>();
	public XMLGenerator(ArrayList<RomData> romvalueslist)
	{
		this.romvalueslist = romvalueslist;
	}
	
	public String createXML()
	{
		StringBuilder sb = new StringBuilder();		
		sb.append("<variablen>");
		sb.append(NEWLINE);
		for (RomData rd : romvalueslist) 
		{
			sb.append("<"+rd.getXmlHeader()+">");
			sb.append(NEWLINE);
			sb.append("<DATA>");
			sb.append(rd.getValue());
			sb.append("</DATA>");
			sb.append(NEWLINE);
			sb.append("</"+rd.getXmlHeader()+">");
			sb.append(NEWLINE);
		}
		sb.append("</variablen>");
		Log.d("XMLGEN", sb.toString());
		return sb.toString();
	}
}
