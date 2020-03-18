package com.wsie.lab.maven.lab;

import java.net.URL;
import java.util.List;

public interface StorageWithFiles extends Storage {
	
    public void saveToFile(String filepath, String text);
    public String readFile(String filepath);
    public void cleanDirectory(String dirpath);
	
}