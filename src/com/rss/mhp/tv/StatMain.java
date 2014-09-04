package com.rss.mhp.tv;
import java.net.URI;
import java.util.ArrayList;

import com.genesyslab.platform.applicationblocks.commons.Action;
import com.genesyslab.platform.commons.*;
import com.genesyslab.platform.commons.collections.KeyValueCollection;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.commons.protocol.ProtocolException;
import com.genesyslab.platform.commons.protocol.RegistrationException;
import com.genesyslab.platform.management.protocol.*;
import com.genesyslab.platform.management.protocol.solutioncontrolserver.requests.applications.RequestGetApplicationInfo;
import com.genesyslab.platform.reporting.protocol.StatServerProtocol;
import com.genesyslab.platform.reporting.protocol.statserver.Notification;
import com.genesyslab.platform.reporting.protocol.statserver.NotificationMode;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticMetric;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticObject;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticObjectType;
import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestOpenStatistic;
import com.genesyslab.platform.voice.protocol.ConnectionId;
import com.genesyslab.platform.voice.protocol.TServerProtocol;
import com.genesyslab.platform.voice.protocol.tserver.TimeStamp;
import com.genesyslab.platform.voice.protocol.tserver.events.EventACK;
import com.genesyslab.platform.voice.protocol.tserver.events.EventAbandoned;
import com.genesyslab.platform.voice.protocol.tserver.events.EventAddressInfo;
import com.genesyslab.platform.voice.protocol.tserver.events.EventDialing;
import com.genesyslab.platform.voice.protocol.tserver.events.EventDiverted;
import com.genesyslab.platform.voice.protocol.tserver.events.EventError;
import com.genesyslab.platform.voice.protocol.tserver.events.EventEstablished;
import com.genesyslab.platform.voice.protocol.tserver.events.EventHeld;
import com.genesyslab.platform.voice.protocol.tserver.events.EventLinkConnected;
import com.genesyslab.platform.voice.protocol.tserver.events.EventLinkDisconnected;
import com.genesyslab.platform.voice.protocol.tserver.events.EventReleased;
import com.genesyslab.platform.voice.protocol.tserver.events.EventRetrieved;
import com.genesyslab.platform.voice.protocol.tserver.events.EventRinging;
import com.genesyslab.platform.voice.protocol.tserver.events.EventRouteRequest;
import com.genesyslab.platform.voice.protocol.tserver.events.EventRouteUsed;
import com.genesyslab.platform.voice.protocol.tserver.events.EventServerInfo;
import com.genesyslab.platform.voice.protocol.tserver.events.EventSwitchInfo;




public  class StatMain {

	public StatMain()
	{}

	public static void main(String[] args) {

		initilizeRequestProcessing("NJNonCRMAgent2","NJNonCRMAgent2_XML");
	}

	public static void initilizeRequestProcessing(String sQueueName,String sXMLName)
	{
		StatMain dmain = new StatMain();
		dmain.init(sQueueName, sXMLName);
	}
	
	public static void init(String sQueueName, String sXMLFileName)
	{
		try {

			Endpoint ep = new Endpoint("stat_server_pulse_pri","10.55.11.61",3211);
			StatServerProtocol scp = new StatServerProtocol(ep);
			scp.setClientId(101);
			scp.setClientName("c828850");

			scp.open();

			MessageReceivers msgReceiver = new MessageReceivers(scp, sXMLFileName);

			Thread.sleep(2000);
/////////// REQUEST-1

			RequestOpenStatistic ros = RequestOpenStatistic.create();

			StatisticObject so = StatisticObject.create();
			so.setObjectId(sQueueName);
			so.setObjectType(StatisticObjectType.Agent);
			so.setTenantName("Resources");
			so.setTenantPassword("");
			ros.setStatisticObject(so);

			StatisticMetric sm = StatisticMetric.create("AverHandleStatusTime");
			sm.setTimeProfile("Default");
			sm.setTimeRange("");
			sm.setFilter("");
			ros.setStatisticMetric(sm);

			Notification not = Notification.create();
			not.setMode(NotificationMode.Periodical);
			not.setFrequency(60);
			ros.setNotification(not);

			ros.setReferenceId(99);

			scp.send(ros);

	///////// REQUEST-2

            RequestOpenStatistic ros2 = RequestOpenStatistic.create();

			StatisticObject so2 = StatisticObject.create();
			so2.setObjectId(sQueueName);
			so2.setObjectType(StatisticObjectType.Agent);
			so2.setTenantName("Resources");
			so2.setTenantPassword("");
			ros2.setStatisticObject(so2);

			StatisticMetric sm2 = StatisticMetric.create("TotalLoginTime");
			sm2.setTimeProfile("Default");
			sm2.setTimeRange("");
			sm2.setFilter("");
			ros2.setStatisticMetric(sm2);

			Notification not2 = Notification.create();
			not2.setMode(NotificationMode.Periodical);
			not2.setFrequency(60);
			ros2.setNotification(not2);

			ros2.setReferenceId(98);

			scp.send(ros2);


///////// REQUEST-3

			RequestOpenStatistic ros3 = RequestOpenStatistic.create();

						StatisticObject so3 = StatisticObject.create();
						so3.setObjectId(sQueueName);
						so3.setObjectType(StatisticObjectType.Agent);
						so3.setTenantName("Resources");
						so3.setTenantPassword("");
						ros3.setStatisticObject(so3);

						StatisticMetric sm3 = StatisticMetric.create("CurrentAgentState");
						sm3.setTimeProfile("Default");
						sm3.setTimeRange("");
						sm3.setFilter("");
						ros3.setStatisticMetric(sm3);

						Notification not3 = Notification.create();
						not3.setMode(NotificationMode.Periodical);
						not3.setFrequency(60);
						ros3.setNotification(not3);

						ros3.setReferenceId(97);

						scp.send(ros3);

///////// REQUEST-4

	RequestOpenStatistic ros4 = RequestOpenStatistic.create();

				StatisticObject so4 = StatisticObject.create();
				so4.setObjectId(sQueueName);
				so4.setObjectType(StatisticObjectType.Agent);
				so4.setTenantName("Resources");
				so4.setTenantPassword("");
				ros4.setStatisticObject(so4);

				StatisticMetric sm4 = StatisticMetric.create("TotalNumberConsultCalls");
				sm4.setTimeProfile("Default");
				sm4.setTimeRange("");
				sm4.setFilter("");
				ros4.setStatisticMetric(sm4);

				Notification not4 = Notification.create();
				not4.setMode(NotificationMode.Periodical);
				not4.setFrequency(60);
				ros4.setNotification(not4);

				ros4.setReferenceId(96);

				scp.send(ros4);

		} catch (RegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handle(final Message message) {
		// Some user-specific work...
		System.out.println( "DashBoard::handleResponse() message = "+message.messageName());
		System.out.println( "DashBoard::handleResponse() message Content = "+message.toString());
	}

}

class MessageReceivers extends Thread {

	Thread tRecv = null;
	StatServerProtocol tp = null;
	String sXMLFileName="";


	MessageReceivers(StatServerProtocol serverProtocol, String sXMLFileName) {
		tp = serverProtocol;
		System.out.println("Thread|Dashboard:MessageReceiver() Initializing MessageReceiver Thread...");
		tRecv = new Thread(this, "RECEIVER");
		this.sXMLFileName=sXMLFileName;
		tRecv.start();
	}

	// This method will receive all messages:
	public void run() {
		System.out.println("Thread|Dashboard::MessageReceiver:run() Starting MessageReceiver Thread...");
		while (true) {
			try {
				Message message = tp.receive();
				if (message != null)
					handleMessage(message, sXMLFileName);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handleMessage(Message message, String sXMLFileName) {
		// Some user-specific work...
		try {

			ConnectionId cid = null;// = ea.getConnID();
			KeyValueCollection kve = null;// = ea.getExtensions();
			KeyValueCollection kvu = null;// = ea.getUserData();
			String messageString="";
			String callStat="";

			switch (message.messageId()) {
			case EventACK.ID:
				//System.out.println( "Thread|Dashboard::MessageReceiver:handleMessage() MsgRecv ACK...");

				break;

			default:
				if(message.messageName().equalsIgnoreCase("EventInfo"))
				{
				messageString=message.toString();
				StatResponseMessageHandler.handleStatEventInfoMessage(messageString, sXMLFileName);
				break;
				
				}else {
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}