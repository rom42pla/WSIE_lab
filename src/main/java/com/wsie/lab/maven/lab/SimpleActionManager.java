package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimpleActionManager implements ActionManager {

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

}
