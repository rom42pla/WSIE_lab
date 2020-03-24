package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Map;

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
        SimpleStorageWithFiles storage = new SimpleStorageWithFiles();
        
        RomaTodayActionManager romaTodayActionManager = new RomaTodayActionManager();
        
        
        /**
		 * Seeds declaration
		 */
        ArrayList<String> seeds = new ArrayList<String>(
        		Arrays.asList(
        				"http://www.romatoday.it/eventi/"
        				));  
        
        // check if there is at least one seed in the list
        if(seeds.isEmpty()) {
        	throw new Exception("No seeds given");
        }
        
        // adding seeds to the toVisit list
		storage.addToVisit(seeds);
        
        // printing the seeds
        System.out.println("\t...seeds:");
        for (URL seed : storage.getToVisitURLs()) 
        	System.out.println("\t\t" + seed);
        
        
        
        
        /**
		 * Other parameters declaration
		 */
		String filespath = "./pages";
		storage.cleanDirectory(filespath);
        final int MAX_ITERS = 50;
        System.out.println("\t...max iterations until early stop: " + MAX_ITERS);
        
        
        
        
		System.out.println("\nCRAWLING PHASE");
		
        /**
		 * Main loop
		 */
        // loops
        int currIter = 0;
        while(!storage.getToVisitURLs().isEmpty() && (currIter < MAX_ITERS)) {
        	currIter++;
        	
        	// selects next website to crawl according to a policy
        	URL currUrl = fetcher.selectNextURLToFetch(storage.getToVisitURLs());
			storage.markAsVisited(currUrl);
        	
        	// fetches the website
			Document site = fetcher.fetch(currUrl);
        	
        	// if the fetch has succeeded, add all the hyperlinks to the list
        	if (site != null) {
        		// retrieves all the hyperlinks in the page				
				ArrayList<URL> articles = romaTodayActionManager.getArticlesLinks(site);
				int newLinks = 0;
        		
				// eventually adds the hyperlinks to the storage
        		for(URL link : articles) {
        			if(!storage.getVisitedURLs().contains(link) && !storage.getToVisitURLs().contains(link)) {
        				storage.addToVisit(link);
        				newLinks++;
        			}
				}

				String filepath = null;
				String filecontent = null;
				// if the page is an article of RomaToday
				if(romaTodayActionManager.isArticle(currUrl)){
					// saves the article to a file
					Map<String, String> content = romaTodayActionManager.getContentOfArticle(site);
					filepath = filespath + "/RomaToday_" + content.get("title").toString().replace(" ", "-").replace("/", "") + ".txt";
					filecontent = romaTodayActionManager.getContentOfArticleAsString(site);
					
				}
				else {
					// saves website's HTML to a file
					filepath = filespath + "/page_" + storage.getVisitedURLs().size() + ".txt";
					filecontent = currUrl.toString() + "\n\n" + site.toString();
				}
				storage.saveToFile(filepath, filecontent);
				
				
        		
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
