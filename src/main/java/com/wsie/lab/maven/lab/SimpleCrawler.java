package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.nodes.Document;

public class SimpleCrawler implements Crawler {
	
	public static void main(String[] args) throws Exception
    {
		System.out.println("INITIAL INFOS");
		/**
		 * Components declaration
		 */
		SimpleURLManager urlManager = new SimpleURLManager();
        SimpleFetcher fetcher = new SimpleFetcher();
        SimpleActionManager actionManager = new SimpleActionManager();
        SimpleStorage storage = new SimpleStorage();
        
        
        
        
        /**
		 * Seeds declaration
		 */
        ArrayList<String> seeds = new ArrayList<String>(
        		Arrays.asList(
        				"https://twiki.di.uniroma1.it/twiki/view/Estrinfo", 
        				"http://groups.di.unipi.it/~francesc/lc.html"
        				));  
        
        // check if there is at least one seed in the list
        if(seeds.size() < 1) {
        	throw new Exception("No seeds given");
        }
        
        // adding seeds to the toVisit list
        for (String seed : seeds) { 		      
	        URL url = urlManager.toURL(seed);
	        if(url != null)	
	        	storage.addToVisit(url);
        }  
        
        // printing the seeds
        System.out.println("\t...seeds:");
        for (URL seed : storage.getToVisitURLs()) 
        	System.out.println("\t\t" + seed);
        
        
        
        
        /**
		 * Other parameters declaration
		 */
        int maxIters = 50;
        System.out.println("\t...max iterations until early stop: " + maxIters);
        
        
        
        
        System.out.println("\nCRAWLING PHASE");
        /**
		 * Main loop
		 */
        // loops
        int currIter = 0;
        while(!storage.getToVisitURLs().isEmpty() && (currIter < maxIters)) {
        	currIter++;
        	
        	// selects next website to crawl according to a policy
        	URL currUrl = fetcher.selectNextURLToFetch(storage.getToVisitURLs());
        	storage.markAsVisited(currUrl);
        	
        	// fetches the website
        	Document site = fetcher.fetch(currUrl);
        	
        	// if the fetch has succeeded, add all the hyperlinks to the list
        	if (site != null) {
        		// retrieves all the hyperlinks in the page
        		ArrayList<URL> links = actionManager.getLinksInPage(site);
        		int newLinks = 0;
        		
        		// eventually adds the hyperlinks to the storage
        		for(URL link : links) {
        			if(!storage.getVisitedURLs().contains(link) && !storage.getToVisitURLs().contains(link)) {
        				storage.addToVisit(link);
        				newLinks++;
        			}
        		}
        		
        		System.out.println("\t...scraped " + currUrl + " (" + newLinks + " new links in page)");
        	}
        }

        
        
        
        System.out.println("SUMMARY");
        /**
		 * Summary
		 */
        storage.printURLs();
    }
	
}