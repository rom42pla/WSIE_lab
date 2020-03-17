package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.List;

public interface Storage {
	
	public void addToVisit(URL url);
	public void markAsVisited(URL url);
	
	public List<URL> getToVisitURLs();
	public List<URL> getVisitedURLs();
	public void printURLs();
	
}
