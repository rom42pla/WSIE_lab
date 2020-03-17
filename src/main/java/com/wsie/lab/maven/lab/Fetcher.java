package com.wsie.lab.maven.lab;

import java.net.*;
import java.util.List;

import org.jsoup.nodes.Document;

public interface Fetcher {

	public Document fetch(URL url);
	public URL selectNextURLToFetch(List<URL> urls);
	
	public Document getLastDocument();
	
}
