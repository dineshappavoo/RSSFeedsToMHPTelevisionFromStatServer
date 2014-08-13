/**
 * 
 */
package com.rss.mhp.tv;

/**
 * @author Dinesh Appavoo
 *
 */
public class Item {


	 String title;
	 String link;
	 String description;
	 String pubDate;
	 
	 public Item(String title, String link, String description, String pubDate)
	 {
		 this.title=title;
		 this.link=link;
		 this.description=description;
		 this.pubDate=pubDate;
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
	 
}
