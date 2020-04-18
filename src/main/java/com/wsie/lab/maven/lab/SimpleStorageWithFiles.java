package com.wsie.lab.maven.lab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SimpleStorageWithFiles implements StorageWithFiles {

	private ArrayList<URL> toVisit = new ArrayList<URL>();
	private ArrayList<URL> visited = new ArrayList<URL>();

	@Override
	public void addToVisit(URL url) {
		if (!this.getVisitedURLs().contains(url) && !this.getToVisitURLs().contains(url)) {
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

		if (visited.size() < 1) {
			System.out.print(visited.size() + " pages visited");
		} else {
			System.out.print(visited.size() + " pages visited:\n");
			for (URL url : visited) {
				System.out.println("\t" + url);
			}
		}
	}

	public void printSeeds() {
		for (URL url : toVisit) {
			System.out.println("\t\t" + url);
		}

		System.out.println("\n\tTotal amount of initial seed: " + toVisit.size());
	}

	@Override
	public void saveToFile(String filepath, String text) {
		try {
			File file = new File(filepath);
			file.createNewFile();

			FileWriter myWriter = new FileWriter(filepath);
			myWriter.write(text);
			myWriter.close();

		  } catch (IOException e) {
			e.printStackTrace();
		  }
	}

	@Override
	public String readFile(String filepath) {
		return "OK";
	}

	@Override
	public void cleanDirectory(String dirpath) {
		File directory = new File(dirpath);

		// get all files in directory
		File[] files = directory.listFiles();
		for (File file : files)
		{
			file.delete();
		} 
	}

}
