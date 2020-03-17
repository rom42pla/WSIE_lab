package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SimpleStorage implements Storage {

	private ArrayList<URL> toVisit = new ArrayList<URL>();
	private ArrayList<URL> visited = new ArrayList<URL>();
	
	@Override
	public void addToVisit(URL url) {
		if (!this.getVisitedURLs().contains(url)) {
			this.toVisit.add(url);
        }
	}
	
	public void addToVisit(String rawUrl) {
		SimpleURLManager urlManager = new SimpleURLManager();
		URL url = urlManager.toURL(rawUrl);
		addToVisit(url);
	}
	
	public void addToVisit(List<String> rawUrls) {
		SimpleURLManager urlManager = new SimpleURLManager();
		for (String rawUrl : rawUrls) {
			URL url = urlManager.toURL(rawUrl);
			addToVisit(url);
		}
	}

	@Override
	public void markAsVisited(URL url) {
		this.toVisit.remove(url);
		this.visited.add(url);
	}

	@Override
	public ArrayList<URL> getToVisitURLs() {
		return this.toVisit;
	}

	@Override
	public ArrayList<URL> getVisitedURLs() {
		return this.visited;
	}

	@Override
	public void printURLs() {
		ArrayList<URL> toVisit = this.getToVisitURLs();
		ArrayList<URL> visited = this.getVisitedURLs();
		
		System.out.print(toVisit.size() + " pages in visit's queue, ");
		
		if(visited.size() < 1) {
			System.out.print(visited.size() + " pages visited");
		}
		else {
			System.out.print(visited.size() + " pages visited:\n");
			for (URL url : visited) { 		      
		          System.out.println("\t" + url);
			}  
		}
	}

}
