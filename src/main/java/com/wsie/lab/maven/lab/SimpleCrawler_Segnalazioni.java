package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.jsoup.nodes.Document;

public class SimpleCrawler_Segnalazioni implements Crawler {
    public static void main(String[] args) throws Exception {


        System.out.println("INITIAL INFOS:");
		/**
		 * Components declaration
		 */
		
        Fetcher fetcher = new SimpleFetcher();
        SimpleStorageWithFiles storage = new SimpleStorageWithFiles();
        
        RomaTodayActionManager romaTodayActionManager = new RomaTodayActionManager(); // il metodo che mi serve non è nell'Interface -> Dichiaro anche a sx la classe


        /**
         * Seeds declaration
         */
        String firstSeed = "http://www.romatoday.it/social/segnalazioni/";

        ArrayList<String> seeds = new ArrayList<>();
        seeds.add(firstSeed);

        // Check the emptiness of seed list
        if (seeds.isEmpty()) {
            throw new Exception("No seeds given!");
        }

        // add seed to toVisit 
        storage.addToVisit(seeds);

        // print the seeds
        System.out.println("\n\tseeds:");
        storage.printSeeds();


        /**
         * Other parameters declaration
         */
        String path_reports = "./reports";
        String path_authors = "./authors";
        storage.cleanDirectory(path_reports);
        storage.cleanDirectory(path_authors);
        final int MAX_ITERS = 10;
        System.out.println("\n\tMAX iTERATIONS until early stop: " + MAX_ITERS);

        System.out.println("\n\tCRAWLING PHASE");

        /**
         * Main Loop
         */
        int currIter = 0;

        while (!storage.getToVisitURLs().isEmpty() && currIter < MAX_ITERS) {
            currIter++;

            // select next websites to crawl according to a policy
            URL currUrl = fetcher.selectNextURLToFetch(storage.getToVisitURLs());
            storage.markAsVisited(currUrl);

            // fetch the website
            Document page = fetcher.fetch(currUrl);

            // if the fetch has succeeded, add all the hyperlinks to the list
            if (page != null) {
                ArrayList<URL> articles = romaTodayActionManager.getReportsLinks(page);
                int newLinks = 0;

                for (URL url : articles) {
                    storage.addToVisit(url);
                    newLinks++;
                }

                String filepath = null;
                String filecontent = null;

                // if the page is a report posted on Romatoday
                if (romaTodayActionManager.isReport(currUrl)) {

                    // save report in a file
                    Map<String, String> content = romaTodayActionManager.getContentOfReport(page);
                    filepath = path_reports + "/RomaToday_" + content.get("title").toString().replace(" ","-").replace("/","") + ".txt";
                    filecontent = romaTodayActionManager.getContentOfReportAsString(page);
                }
                else {
                    // saves website's HTML to a file
                    // ACTUALLY DON'T CARE
                }

                if ( (filepath != null) && (filecontent!= null) )
                    {
                        System.out.println("\t...scraped " + currUrl + " (" + newLinks + " new links in page)");
                        storage.saveToFile(filepath, filecontent);

                    }
                

            }
        }


        System.out.println("SUMMARY");
        /**
		 * Summary
		 */
        storage.printURLs();

    }

}