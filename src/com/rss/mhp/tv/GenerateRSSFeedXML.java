/**
 * 
 */
package com.rss.mhp.tv;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author b030513
 *
 */
public class GenerateRSSFeedXML {

	/**
	 * @param args
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, TransformerException {
		
		ArrayList<Item> itemList=new ArrayList<Item>();
		itemList.add(new Item("Call Stats for July 3, 2014", "http://bnsfweb.bnsf.com/ACDStats/CP", "Call Stats Answered Calls: 237 Calls Waiting: 2 Longest Call 1:04", "Tue, 26 Oct 2004 14:06:44 -0500"));
		itemList.add(new Item("2014 Year-to-date Call Stats", "http://bnsfweb.bnsf.com/ACDStats/CP", "2014 YTD Answered Calls: 237 Calls Waiting: 2 Longest Call 1:04", "Tue, 26 Oct 2004 14:06:44 -0500"));
		
		String xml=GenerateRSSFeedXML.generateXMLforRSSFeed(new RSSFeed("ACD Call Stats for Consumer Products", "http://bnsfweb.bnsf.com/ACDStats/CP", "Call Stats for Consumer Products", "Tue, 26 Oct 2004 14:06:44 -0500", "Mon, 1 Nov 2004 13:17:17 -0500", itemList));

		System.out.println(xml);
	}
	
	/**
	 * Method to generate RSS feed XML the stat server message
	 * @param rssFeed
	 * @return
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static String generateXMLforRSSFeed(RSSFeed rssFeed) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		Element rssRoot = document.createElement("rss");		
		document.appendChild(rssRoot);
		
		Attr attr = document.createAttribute("version");
		attr.setValue("2.0");
		rssRoot.setAttributeNode(attr);
		
		Element root = document.createElement("channel");		
		rssRoot.appendChild(root);

		Element titleNode=document.createElement("title");
		root.appendChild(titleNode);
		titleNode.setTextContent(rssFeed.getTitle());
		
		Element linkNode=document.createElement("link");
		root.appendChild(linkNode);
		linkNode.setTextContent(rssFeed.getLink());
		
		Element descNode=document.createElement("description");
		root.appendChild(descNode);
		descNode.setTextContent(rssFeed.getDescription());
		
		Element pubDateNode=document.createElement("pubDate");
		root.appendChild(pubDateNode);
		pubDateNode.setTextContent(rssFeed.getPubDate());
		
		Element lastBuildDateNode=document.createElement("lastBuildDate");
		root.appendChild(lastBuildDateNode);
		lastBuildDateNode.setTextContent(rssFeed.getLastBuildDate());
				
		for(Item itemNode : rssFeed.itemList)
		{
			Element item = document.createElement("item");	
			root.appendChild(item);

			Element itemTitleNode=document.createElement("title");
			item.appendChild(itemTitleNode);
			itemTitleNode.setTextContent(itemNode.getTitle());
			
			Element itemLinkNode=document.createElement("link");
			item.appendChild(itemLinkNode);
			itemLinkNode.setTextContent(itemNode.getLink());
			
			Element itemDescNode=document.createElement("description");
			item.appendChild(itemDescNode);
			itemDescNode.setTextContent(itemNode.getDescription());
			
			Element itemPubDateNode=document.createElement("pubDate");
			item.appendChild(itemPubDateNode);
			itemPubDateNode.setTextContent(itemNode.getPubDate());		
			
		}


		//Create transformer factory
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		//Prepare the DOM source
		DOMSource domSource = new DOMSource(document);
		
		//Prepare String writer source
		StringWriter result=new StringWriter();
		StreamResult streamResult = new StreamResult(new File("D:\\XMLFILE.xml"));
		StreamResult stringStreamResult = new StreamResult(result);


		//Transform to XML
		transformer.transform(domSource, streamResult);
		
		//Transform to XML String
		transformer.transform(domSource, stringStreamResult);
		
		return result.toString();

	}

}
