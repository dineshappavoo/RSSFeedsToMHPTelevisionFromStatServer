/**
 * 
 */
package com.rss.mhp.tv;

/**
 * @author b030513
 *
 */
public class RSSFeedStatUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Method to parse the message string and get the call statistics
	 * @param message
	 * @return
	 */
	public static String getCallStat(String message)
	{
		String callStat="";
		
		message=message.trim();
		callStat=message.substring(message.indexOf("STRING_VALUE [str] = \"")+22, message.indexOf("\" TM_SERVER [int] "));
		return callStat;
	}

}
