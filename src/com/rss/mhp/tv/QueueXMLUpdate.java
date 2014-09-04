/**
 * 
 */
package com.rss.mhp.tv;

import java.util.ArrayList;

/**
 * @author Dany
 *
 */
public class QueueXMLUpdate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void getRSSFeedForQueues()
	{
		ArrayList<String> lQueueNames;
		lQueueNames=DMOperations.getXMlFileName();
		for(String queueName : lQueueNames)
		{
			
		}
	}

}
