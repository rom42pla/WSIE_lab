package com.wsie.lab.maven.lab;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SimpleFetcher implements Fetcher {

	Document lastDocument = null;
	
	@Override
	public Document getLastDocument() {
		return this.lastDocument;
	}

	@Override
	public Document fetch(URL url) {
		try {
			// fetches the page
			Document page = Jsoup.connect(url.toString()).get();
			this.lastDocument = page;

			return page;
		} catch (IOException e) {
			return null;
		}
		
	}

	@Override
	public URL selectNextURLToFetch(List<URL> urls) {
		if (new Random().nextInt(2) == 0) {
			// gets a random url from the list
        	return urls.get(new Random().nextInt(urls.size())); 
		}
		else {
			// gets the first element of the list
			return urls.get(0); 
		}
	}

}
