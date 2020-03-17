package com.wsie.lab.maven.lab;

import java.awt.List;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SimpleURLManager implements URLManager {

	@Override
	public URL toURL(String rawUrl) {
		try {
			URL newUrl = new URL(rawUrl);
			return newUrl;
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
