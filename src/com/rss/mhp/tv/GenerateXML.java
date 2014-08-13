/**
 * 
 */
package com.rss.mhp.tv;

import java.io.File;

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
 * @author Dinesh Appavoo
 *
 */
public class GenerateXML {

	/**
	 * @param args
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, TransformerException {
		// TODO Auto-generated method stub
		
		new GenerateXML().generateXML();

	}
	
	public void generateXML() throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		Element root = document.createElement("rss");		
		document.appendChild(root);
		
		Attr attr = document.createAttribute("version");
		attr.setValue("2.0");
		root.setAttributeNode(attr);

		root.appendChild(document.createElement("title"));
		root.appendChild(document.createElement("link"));
		root.appendChild(document.createElement("description"));
		root.appendChild(document.createElement("pubDate"));
		root.appendChild(document.createElement("lastBuildDate"));
		
		for(int i=0;i<2;i++)
		{
			Element item = document.createElement("item");	
			root.appendChild(item);

			item.appendChild(document.createElement("title"));
			item.appendChild(document.createElement("link"));
			item.appendChild(document.createElement("description"));
			item.appendChild(document.createElement("pubDate"));
			
		}


		//Create transformer factory
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		//Prepare the DOM source
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File("D:\\BNSF\\BNSF\\XMLFILE.xml"));

		//Transform to XML
		transformer.transform(domSource, streamResult);
		
	}

}
