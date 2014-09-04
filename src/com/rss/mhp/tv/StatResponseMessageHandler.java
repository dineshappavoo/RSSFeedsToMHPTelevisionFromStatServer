package com.rss.mhp.tv;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author b030513
 *
 */
public class StatResponseMessageHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Method to handle EventInfo messages
	 * @param message
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void handleStatEventInfoMessage(String message, String sXMLFileName) throws ParserConfigurationException, TransformerException
	{
		String callStat="";
		if((message.indexOf("STRING_VALUE [str] = \"")>0)&&(message.indexOf("TM_SERVER [int] =")>0))
		{
			callStat=RSSFeedStatUtil.getCallStat(message);
		}
		
		System.out.println("Call Stat Value : "+callStat);
		
		ArrayList<Item> itemList=new ArrayList<Item>();
		itemList.add(new Item("Call Stats for July 3, 2014", "http://bnsfweb.bnsf.com/ACDStats/CP", callStat, "Tue, 26 Oct 2004 14:06:44 -0500"));
		//itemList.add(new Item("2014 Year-to-date Call Stats", "http://bnsfweb.bnsf.com/ACDStats/CP", callStat, "Tue, 26 Oct 2004 14:06:44 -0500"));
		
		String xml= GenerateRSSFeedXML.generateXMLforRSSFeed(new RSSFeed("ACD Call Stats for Consumer Products", "http://bnsfweb.bnsf.com/ACDStats/CP", "Call Stats for Consumer Products", "Tue, 26 Oct 2004 14:06:44 -0500", "Mon, 1 Nov 2004 13:17:17 -0500", itemList), sXMLFileName);
		System.out.println(xml);
	}

}
