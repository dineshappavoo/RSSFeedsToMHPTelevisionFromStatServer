/**
 * 
 */
package com.rss.mhp.tv;

import java.util.ArrayList;

/**
 * @author Dinesh Appavoo
 *
 */
public class RSSFeed {

	 String title;
	 String link;
	 String description;
	 String pubDate;
	 String lastBuildDate;
	 ArrayList<Item> itemList=null;
	 
	 
	 public RSSFeed()
	 {
		 this.itemList=new ArrayList<Item>();
	 }
	 
	 public RSSFeed(String title, String link, String description, String pubDate, String lastBuildDate, ArrayList<Item> itemList)
	 {
		 this.title=title;
		 this.link=link;
		 this.description=description;
		 this.pubDate=pubDate;
		 this.lastBuildDate=lastBuildDate;
		 this.itemList=itemList;
	 }
	 
	 public RSSFeed(String title, String link, String description, String pubDate, String lastBuildDate)
	 {
		 this.title=title;
		 this.link=link;
		 this.description=description;
		 this.pubDate=pubDate;
		 this.lastBuildDate=lastBuildDate;
		 this.itemList=new ArrayList<Item>();
	 }
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the pubDate
	 */
	public String getPubDate() {
		return pubDate;
	}
	/**
	 * @param pubDate the pubDate to set
	 */
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	/**
	 * @return the lastBuildDate
	 */
	public String getLastBuildDate() {
		return lastBuildDate;
	}
	/**
	 * @param lastBuildDate the lastBuildDate to set
	 */
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	/**
	 * @return the itemList
	 */
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
}
