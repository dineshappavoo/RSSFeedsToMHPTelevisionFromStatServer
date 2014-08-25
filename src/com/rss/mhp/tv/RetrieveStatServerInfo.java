package com.rss.mhp.tv;

public class RetrieveStatServerInfo {
	
	public static void main(String args[])
	{
		new RetrieveStatServerInfo().sendStatServerInfoRequest();
	}
	
   public void sendStatServerInfoRequest()
   {
	   try {

			Endpoint ep = new Endpoint("stat_server_pulse_pri","10.55.11.61",3211);
			StatServerProtocol scp = new StatServerProtocol(ep);
			scp.setClientId(101);
			scp.setClientName("c828850");

			scp.open();
			
			ResponseReceiver respReceiver=new ResponseReceiver(scp);			
			
			RequestOpenStatistic ros = RequestOpenStatistic.create();

			StatisticObject so = StatisticObject.create();
			so.setObjectId("NJNonCRMAgent2");
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
	
}

class ResponseReceiver{
	StatServerProtocol tp = null;

	ResponseReceiver(StatServerProtocol serverProtocol) {
		tp = serverProtocol;
		System.out.println("|ResponseReceiver() Initializing ResponseReceiver");
		receiveMessage();
	}
	
	public void receiveMessage() {
		System.out.println("Thread|Dashboard::MessageReceiver:run() Starting MessageReceiver Thread...");
		while (true) {
			try {
				Message message = tp.receive();
				if (message != null)
					handleMessage(message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void handleMessage(Message message) {
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
					StatResponseMessageHandler.handleStatEventInfoMessage(messageString);
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
