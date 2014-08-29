/*=====================================================================
 * Any authorized distribution of any copy of this code (including any
 * related documentation) must reproduce the following restrictions,
 * disclaimer and copyright notice:
 *
 * The Genesys name, the trademarks and/or logo(s) of Genesys shall
 * not be used to name (even as a part of another name), endorse and/or
 * promote products derived from this code without prior written
 * permission from Genesys Telecommunications Laboratories, Inc.
 *
 * The use, copy, and/or distribution of this code is subject to the
 * terms of the Genesys Developer License Agreement.  This code shall
 * not be used, copied, and/or distributed under any other license
 * agreement.
 *
 * THIS CODE IS PROVIDED BY GENESYS TELECOMMUNICATIONS LABORATORIES,
 * INC. ("GENESYS") "AS IS" WITHOUT ANY WARRANTY OF ANY KIND. GENESYS
 * HEREBY DISCLAIMS ALL EXPRESS, IMPLIED, OR STATUTORY CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES WITH RESPECT TO THIS CODE (OR ANY
 * PART THEREOF), INCLUDING, BUT NOT LIMITED TO, IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT. GENESYS AND ITS SUPPLIERS SHALL NOT BE LIABLE FOR
 * ANY DAMAGE SUFFERED AS A RESULT OF USING THIS CODE. IN NO EVENT
 * SHALL GENESYS AND ITS SUPPLIERS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * CONSEQUENTIAL, ECONOMIC, INCIDENTAL, OR SPECIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, ANY LOST REVENUES OR PROFITS).
 *
 * Copyright (C) 2008 Genesys Telecommunications Laboratories,
 * Inc. All rights reserved.
 =====================================================================*/

package com.rss.mhp.tv;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.commons.protocol.ProtocolException;

import com.genesyslab.platform.applicationblocks.commons.Action;
import com.genesyslab.platform.applicationblocks.commons.broker.EventBrokerService;
import com.genesyslab.platform.applicationblocks.commons.broker.BrokerServiceFactory;
import com.genesyslab.platform.applicationblocks.commons.broker.MessageIdFilter;
import com.genesyslab.platform.applicationblocks.commons.protocols.StatServerConfiguration;
import com.genesyslab.platform.applicationblocks.commons.protocols.ProtocolManagementServiceImpl;

import com.genesyslab.platform.reporting.protocol.statserver.Statistic;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticMetric;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticObject;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticObjectType;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticsCollection;
import com.genesyslab.platform.reporting.protocol.statserver.Notification;
import com.genesyslab.platform.reporting.protocol.statserver.NotificationMode;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticType;

import com.genesyslab.platform.reporting.protocol.statserver.events.EventPackageError;
import com.genesyslab.platform.reporting.protocol.statserver.events.EventPackageInfo;
import com.genesyslab.platform.reporting.protocol.statserver.events.EventPackageOpened;
import com.genesyslab.platform.reporting.protocol.statserver.events.EventPackageClosed;
import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestOpenPackage;
import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestClosePackage;

public class StatisticsPlatformSDKExample {
	// Beginning of Statistics Server information:
    private String protocolName = "Stat_Server_750"; //"<server name>";
    private String statServerHost = "techpubs4"; //"<host>";
    private int statServerPort = 3545; //"<port>";
    // End of Statistics Server information.

    // Beginning of Statistic information:
    private int statKey = 1234; // unique key for this Statistic request
    private int refreshTime = 10; // notification refresh time (in seconds)
    private String statObjTenantId = "TechPubs75"; //"<tenant ID>";
	private String statObjId = "4001@75_G3_1"; //"<object ID>";
	private String statMetricName = "TotalNumberInboundCalls"; //"<metric name>";
	private String statMetricTimeProfile = "CollectorDefault"; //"<metric time profile>";
	private String statMetricTimeRange = "Range0-120"; //"<metric time range>";
	private String statMetricFilter = "VoiceCall"; //"<metric filter>";
	// End of Statistic information.
	
	// Declare constants that will be used in our sample
    private JTextArea logTextArea = null;
	private JPanel contentPane = null;
	private JButton openButton = null;
	private JButton closeButton = null;
	
	// Application block objects used in this sample
	private StatServerConfiguration statServerConfiguration;
	private ProtocolManagementServiceImpl mProtocolManager;
	private EventBrokerService mEventBroker;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StatisticsPlatformSDKExample reportingExample = new StatisticsPlatformSDKExample();
		// initialize the Protocol Manager application block object
		reportingExample.initializeProtocol();
		// initialize the Event Broker application block object
		reportingExample.initializeEventBroker();
		// create and display the GUI
		reportingExample.initializeGui();
	}

	private void initializeProtocol(){
		// create the configuration object used to connect to Stat Server
		statServerConfiguration = new StatServerConfiguration(protocolName);
    	statServerConfiguration.setClientName("default");
    	try{
    		statServerConfiguration.setUri(new URI("tcp://" + statServerHost + ":" + statServerPort));
    	}
    	catch(URISyntaxException e){
    		// add error handling
    		e.printStackTrace();
    	}
    	// register the configuration object with Protocol Manager, and open the protocol  
    	mProtocolManager = new ProtocolManagementServiceImpl();
    	mProtocolManager.register(statServerConfiguration);
    	try{
    		mProtocolManager.getProtocol(protocolName).open();
    	}
    	catch(Exception e){
    		// add error handling
    		e.printStackTrace();
    	}
	}
	
	private void initializeEventBroker(){
        // set up event broker service
    	mEventBroker = BrokerServiceFactory.CreateEventBroker(mProtocolManager.getReceiver());
        // register event handlers with the event broker
        mEventBroker.register(new OnEventPackageError(), new MessageIdFilter(EventPackageError.ID));
        mEventBroker.register(new OnEventPackageInfo(), new MessageIdFilter(EventPackageInfo.ID));
        mEventBroker.register(new OnEventPackageOpened(), new MessageIdFilter(EventPackageOpened.ID));
        mEventBroker.register(new OnEventPackageClosed(), new MessageIdFilter(EventPackageClosed.ID));
	}
    
    class OnEventPackageError implements Action<Message> {
    	public void handle(Message obj) {
    		if (obj == null)
    			System.out.println("nothing returned");
    		else
    			// handle EventPackageError
    			appendLogMessage("=== Event: Package Error ===\n"
					+ obj.toString()
					+ "\n\n" + createTimeStamp() 
					+ "\n===========================");
    	}
    }

    class OnEventPackageInfo implements Action<Message> {
    	public void handle(Message obj) {
    		if (obj == null)
    			System.out.println("nothing returned");
    		else
    			// handle EventPackageInfo
    			appendLogMessage("=== Event: Package Info ===\n"
    					+ formatStatistics(obj)
    					+ "\n\n" + createTimeStamp() 
    					+ "\n===========================");
    	}
    }
    
    class OnEventPackageOpened implements Action<Message> {
    	public void handle(Message obj) {
    		if (obj == null)
    			System.out.println("nothing returned");
    		else 
    			// handle EventPackageOpened
				appendLogMessage("=== Event: Package Open ===\n"
					+ obj.toString()
					+ "\n\n" + createTimeStamp() 
					+ "\n=============================");
    	}
    }
    
    class OnEventPackageClosed implements Action<Message> {
    	public void handle(Message obj) {
    		if (obj == null)
    			System.out.println("nothing returned");
    		else 
    			// handle EventPackageClosed
				appendLogMessage("=== Event: Package Close ===\n"
					+ obj.toString()
					+ "\n\n" + createTimeStamp() 
					+ "\n=============================");
    	}
    }
    
    private void checkStatistic() {
    	// set notification options (in this case, we use periodical notification) 
		Notification notification = Notification.create(NotificationMode.Periodical, refreshTime);
		// create the request object
		RequestOpenPackage requestOpenPackage = RequestOpenPackage.create(statKey,
				StatisticType.Historical, createStatisticsCollection(), notification);
		try {
			// try sending the request to the protocol object (via protocol manager) 
			mProtocolManager.getProtocol(protocolName).send(requestOpenPackage);
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
    }
	
    private StatisticsCollection createStatisticsCollection(){
    	// build a new StatisticMetric
		StatisticMetric statisticMetric = new StatisticMetric(statMetricName);
		statisticMetric.setTimeProfile(statMetricTimeProfile);
		statisticMetric.setTimeRange(statMetricTimeRange);
		statisticMetric.setFilter(statMetricFilter);
		// build a new StatisticObject
		StatisticObject objectDescription = new StatisticObject(statObjTenantId,
				statObjId, StatisticObjectType.RegularDN);
		// build a new Statistic object using the just created StatisticObject and StatisticMetric
		Statistic statistic = new Statistic(objectDescription, statisticMetric);
		// create a new StatisticCollection
		StatisticsCollection statisticsCollection = new StatisticsCollection();
		statisticsCollection.addStatistic(statistic);
		return statisticsCollection;
    }
    
    private void stopChecking(){
    	// create the request object
		RequestClosePackage requestClosePackage = RequestClosePackage.create(statKey);
		try{
			// try sending the request to the protocol object (via protocol manager)
			mProtocolManager.getProtocol(protocolName).send(requestClosePackage);
		}
		catch(ProtocolException e){
			e.printStackTrace();
		}
    }
    
    protected String createTimeStamp() {
        // Create a time stamp for application log window messages
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss:SSS").format(new GregorianCalendar().getTime());
    }
    
	private String formatStatistics(Message response)
	{
        // cast the Message object to the expected type
		EventPackageInfo eventPackageInfo = (EventPackageInfo) response;
    	String strStatistic = "";
        if (eventPackageInfo != null)
        {
        	int statisticsCount = eventPackageInfo.getStatistics().getCount();
        	if (statisticsCount==0)
        		// if no matching statistics were returned
        		strStatistic = "No statistics found.";
        	else
        	{
        		// extract the collection of statistic objects that was returned
        		StatisticsCollection statisticsCollection = eventPackageInfo.getStatistics();
        		Statistic statistic = null;
        		strStatistic = "Number of matching statistics found:" + statisticsCount;
        		for (int i = 0; i < statisticsCount; )
	            {
	            	// format each statistic for display
        			statistic = statisticsCollection.getStatistic(i);
	            	strStatistic = strStatistic + "\n\n--- Statistic #" + ++i + " ---" + 
	            		"\nStatistic Metric is: \n" + statistic.getMetric().toString() + 
	            		"\nStatistic Object is: \n" + statistic.getObject() + 
	            		"\nStatistic IntValue is: " + statistic.getIntValue() + 
	            		"\nStatistic StringValue is: " + statistic.getStringValue() + 
	            		"\nStatistic ObjectValue is: " + statistic.getObjectValue() + 
	            		"\nStatistic ExtendedValue is: " + statistic.getExtendedValue() + 
	            		"\nStatistic Tenant is: " + statistic.getObject().getTenant() + 
	            		"\nStatistic Type is: " + statistic.getObject().getType() + 
	            		"\nStatistic Id is: " + statistic.getObject().getId() + 
	            		"\nStatistic TimeProfile is: " + statistic.getMetric().getTimeProfile() + 
	            		"\nStatistic StatisticType is: " + statistic.getMetric().getStatisticType() +
	            		"\nStatistic TimeRange is: " + statistic.getMetric().getTimeRange();
	            }
	        }
        }
        else
        	// if the Message didn't match expected type
        	strStatistic = "Invalid response message.";
        // return the string
        return strStatistic;
	}

	
	
//=================================================
//					GUI Code    
//=================================================
	
	/**
     * This method initializes mainGuiWindow
     *
     * @return javax.swing.JFrame
     */
    private void initializeGui() {
        JFrame mainGuiWindow = new JFrame();
        mainGuiWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGuiWindow.setTitle("Statistics Platform SDK Example");
        mainGuiWindow.setSize(new Dimension(444, 436));
        mainGuiWindow.setContentPane(getContentPane());
        mainGuiWindow.setVisible(true);
    }

    /**
     * This method initializes contentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getContentPane() {
        //declare variables used to create GUI
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
    	
        //add the first (Open Statistic) button
        GridBagConstraints mGridBagConstraints = new GridBagConstraints();
        mGridBagConstraints.fill = GridBagConstraints.BOTH;
        mGridBagConstraints.gridx = 0;
        mGridBagConstraints.gridy = 0;
        mGridBagConstraints.weightx = 1.0D;
        openButton = newButton(140, 25, "Open Statistic");
        openButton.addActionListener(
    		// Create the ActionListener object for the Open Statistic button
        	 new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				// application logic for the Open Statistic button
    				openButton.setEnabled(false);
    				checkStatistic();
    				closeButton.setEnabled(true);
    			}
    		});
        contentPane.add(openButton, mGridBagConstraints);
 
        //add the second (Close Statistic) button
        mGridBagConstraints = new GridBagConstraints();
        mGridBagConstraints.fill = GridBagConstraints.BOTH;
        mGridBagConstraints.gridx = 1;
        mGridBagConstraints.gridy = 0;
        mGridBagConstraints.weightx = 1.0D;
        closeButton = newButton(140, 25, "Close Statistic");
        closeButton.setEnabled(false);
        closeButton.addActionListener(
    		// Create the ActionListener object for the Close Statistic button
        	 new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				// application logic for the Close Statistic button
    				closeButton.setEnabled(false);
    				stopChecking();
    				openButton.setEnabled(true);
    			}
    		});
        contentPane.add(closeButton, mGridBagConstraints);
 
    	//add the third (Clear Log) button
        mGridBagConstraints = new GridBagConstraints();
        mGridBagConstraints.fill = GridBagConstraints.BOTH;
        mGridBagConstraints.gridx = 2;
        mGridBagConstraints.gridy = 0;
        mGridBagConstraints.weightx = 1.0D;
        JButton mButton = newButton(140, 25, "Clear Log");
        mButton.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				//add action here for this button
    				clearLogMessage();
    			}
    		});
        contentPane.add(mButton, mGridBagConstraints);

        //add a new panel to display status information
        JPanel mPanel = newPanel("Status Info");
        mGridBagConstraints = new GridBagConstraints();
        mGridBagConstraints.anchor = GridBagConstraints.SOUTH;
        mGridBagConstraints.fill = GridBagConstraints.BOTH;
        mGridBagConstraints.gridheight = 1;
        mGridBagConstraints.gridwidth = 3;
        mGridBagConstraints.gridx = 0;
        mGridBagConstraints.gridy = 1;
        mGridBagConstraints.weightx = 0.0D;
        mGridBagConstraints.weighty = 1.0D;
        contentPane.add(mPanel, mGridBagConstraints);
        
        //add a text pane to the panel
        mGridBagConstraints = new GridBagConstraints();
        mGridBagConstraints.anchor = GridBagConstraints.SOUTH;
        mGridBagConstraints.fill = GridBagConstraints.BOTH;
        mGridBagConstraints.gridheight = 1;
        mGridBagConstraints.gridx = 0;
        mGridBagConstraints.gridy = 0;
        mGridBagConstraints.weightx = 0.0D;
        mGridBagConstraints.weighty = 1.0D;
        mPanel.add(newScrollPane(), mGridBagConstraints);
        //return the JPanel object we've created
        return contentPane;
    }

    private JPanel newPanel(String jpName){
    	JPanel mPanel = new JPanel();
    	//set up the panel
    	mPanel.setBorder(BorderFactory.createTitledBorder(
    			BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), jpName));
    	return mPanel;
    }
    
    private JButton newButton(int jbWidth, int jbHeight, String jbText){
    	JButton mButton = new JButton();
    	mButton.setPreferredSize(new Dimension(jbWidth, jbHeight));
    	mButton.setText(jbText);
    	return mButton;
    }
    
	/**
     * This method initializes the JTextArea control displayed on a scroll pane.
     *
     * @param isEditable Indicates whether the text area can be edited.
     * @return javax.swing.JTextArea
     */
    private JTextArea newTextArea(Boolean isEditable) {
    	logTextArea = new JTextArea(7, 20);
    	logTextArea.setEditable(isEditable);
		return logTextArea;
    }
	
    /**
     * This method initializes a new JScrollPane with a text area, and allows you to specify
     * whether or not the included text area can be edited. 
     * 
     * @param isEditable Indicates whether text area can be edited.
     * @return javax.swing.JScrollPane
     */
    private JScrollPane newScrollPane(Boolean isEditable) {
    	JScrollPane mScrollPane = new JScrollPane(newTextArea(isEditable));
        mScrollPane.setPreferredSize(new Dimension(420, 330));
        mScrollPane.setAutoscrolls(true);
        mScrollPane.setBackground(new Color(255, 255, 227));
        return mScrollPane;
    }
    
    /**
     * This method initializes a new JScrollPane that contains a non-editable text area.
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane newScrollPane() {
    	return newScrollPane(false);
    }

    private void appendLogMessage(String eventString){
    	try {
    		logTextArea.append(eventString + "\n\n\n");
    		logTextArea.setCaretPosition(logTextArea.getText().length()-3);
    	}
    	catch (Exception e){
    		System.err.println("Couldn't insert text into text area."
    				+ "\n" + e.toString());
    	}
    }

    private void clearLogMessage(){
    	try {
    		logTextArea.setText("");
    	}
    	catch (Exception e){
    		System.err.println("Couldn't clear the text area.\n" + e.toString() 
    				+ "\n\n" + e.getCause().toString());
    	}
    }
}
