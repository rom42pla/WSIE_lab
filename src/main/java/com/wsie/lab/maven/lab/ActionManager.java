package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.List;
import org.jsoup.nodes.Document;

public interface ActionManager {
	
	List<URL> getLinksInPage(Document site);
	
}
