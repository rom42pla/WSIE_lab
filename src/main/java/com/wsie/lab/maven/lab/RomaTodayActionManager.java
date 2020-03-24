package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher; 
import java.util.regex.Pattern;

import javax.management.Descriptor; 

public class RomaTodayActionManager implements ActionManager {

	@Override
	public ArrayList<URL> getLinksInPage(Document site) {
		ArrayList<URL> links = new ArrayList<URL>();
		SimpleURLManager urlManager = new SimpleURLManager();
		
		Elements linksElements = site.select("a[href]");
        for(Element element : linksElements){
        	String rawUrl = element.attr("abs:href").toString();
            URL url = urlManager.toURL(rawUrl);
            links.add(url);
        }
        return links;
    }
    
	public ArrayList<URL> getArticlesLinks(Document site) {
		ArrayList<URL> links = new ArrayList<URL>();
		SimpleURLManager urlManager = new SimpleURLManager();
		
		Elements linksElements = site.select("article");
        for(Element element : linksElements){
            String rawUrl = "http://www.romatoday.it" + element.select(".block__heading").select("a").attr("href").toString();
            URL url = urlManager.toURL(rawUrl);
            links.add(url);
        }
        return links;
    }

    public Map<String, String> getContentOfArticle(Document site) {
        Map<String, String> content = new HashMap<String, String>();

        String title = site.select(".entry-title").text();
        String description = site.select(".entry-content-body").select("p, h2").text();
        String date = null;
        String location = null;
        String price = null;
        String HTML = site.toString();

        for(Element element : site.select(".details-list__item")){
            String text = element.text().trim();

            // date
            if(text.toLowerCase().contains("quando")){
                for(Element element_sub : element.select("i, dt")){
                    String text_quando = element_sub.text().trim().toLowerCase();
                    if(text_quando != "quando" && text_quando != "orario non disponibile"){
                        if(date == null && date != "null")
                            date = text_quando;
                        else
                            date += ", " + text_quando;
                    }
                }
            }

            // location
            if(text.toLowerCase().contains("dove")){
                for(Element element_sub : element.select("i, dt")){
                    String text_location = element_sub.text().trim();
                    if(text_location.toLowerCase() != "dove" && text_location != "luogo non disponibile"){
                        if(location == null)
                            location = text_location;
                        else
                            location += ", " + text_location;
                    }
                }
            }

            // price
            if(text.toLowerCase().contains("prezzo")){
                for(Element element_sub : element.select("i, dt")){
                    String text_price = element_sub.text().trim().toLowerCase();
                    if(text_price.toLowerCase() != "prezzo" && text_price != "prezzo non disponibile"){
                        if(price == null)
                            price = text_price;
                        else
                            price += ", " + text_price;
                    }
                }
            }
        }

        if(title != null)   content.put("title", title);
        if(description != null)   content.put("description", description);
        if(date != null)    content.put("date", date);
        if(location != null)   content.put("location", location);
        if(price != null)   content.put("price", price);
        if(HTML != null)   content.put("HTML", HTML);

		return content;
    }

    public String getContentOfArticleAsString(Document site){
        Map<String, String> content = this.getContentOfArticle(site);
        String contentAsString = "";

        // for each key/value pair
        for (Map.Entry<String, String> entry : content.entrySet())
            contentAsString += entry.getKey().toUpperCase() + "\n" + entry.getValue() + "\n\n";

        return contentAsString;
    }

    public boolean isArticle(URL url){
        String pattern = ".*www.romatoday.it\\/eventi\\/.*\\.html"; 
  
        // Search above pattern in "geeksforgeeks.org" 
        if (Pattern.compile(pattern).matcher(url.toString()).matches()){
            return true;
        }
        else{
            return false;
        }     
    }

}
