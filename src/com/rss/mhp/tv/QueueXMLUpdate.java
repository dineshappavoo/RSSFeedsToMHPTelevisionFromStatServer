/**
 * 
 */
package com.rss.mhp.tv;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Dany
 *
 */
public class QueueXMLUpdate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new QueueXMLUpdate().getRSSFeedForQueues();
	}
	
	public void getRSSFeedForQueues()
	{
		HashMap<String,String> hQueueXMLFileMap=null;
		String sXMLFileName="";
		hQueueXMLFileMap=DMOperations.getXMlFileName();
		
		for(String queueName : hQueueXMLFileMap.keySet())
		{
			sXMLFileName=hQueueXMLFileMap.get(queueName);
				StatMain.initilizeRequestProcessing(queueName, sXMLFileName);
		}
	}

}
