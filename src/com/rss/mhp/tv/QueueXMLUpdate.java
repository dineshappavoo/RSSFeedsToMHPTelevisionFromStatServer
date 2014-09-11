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
public class QueueXMLUpdate extends Thread{

	/**
	 * @param args
	 */
	Thread receiverThread = null;

	public static void main(String[] args) {

		new QueueXMLUpdate().getRSSFeedForQueues();
	}
	
	public QueueXMLUpdate()
	{
		System.out.println("Thread|Dashboard:MessageReceiver() Initializing MessageReceiver Thread...");
		receiverThread = new Thread(this, "RECEIVER");
		receiverThread.start();
	}
	
	// This method will receive all messages:
		public void run() {
			System.out.println("Thread|Dashboard::MessageReceiver:run() Starting MessageReceiver Thread...");
			while (true) {
					getRSSFeedForQueues();
			}
		}
	
	public void getRSSFeedForQueues()
	{
		try {
			HashMap<String,String> hQueueXMLFileMap=null;
			String sXMLFileName="";
			hQueueXMLFileMap=DMOperations.getXMlFileName();
			
			for(String queueName : hQueueXMLFileMap.keySet())
			{
				sXMLFileName=hQueueXMLFileMap.get(queueName);
					StatMain.initilizeRequestProcessing(queueName, sXMLFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
