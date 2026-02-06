package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//this class is used for accessing key values from config.properties files located in sr/test/resources/config folder. 

public class ConfigManager {
	
//We will utilize the properties class available in java.util package.
//The Properties class represents a persistent set of properties. 
//The Properties can be saved to a stream or loaded from a stream.
//Each key and its corresponding value in the property list is a string.
	
	private static Properties properties = new Properties();
	
//below is static block,it will be executed the moment class is loaded in the class loader.
//we are using Reflection concept below.
	static {
		
		InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties");
		if (input != null) {
			try {
				properties.load(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static String get(String key) {
		
		return properties.getProperty(key).trim();
	}
	
	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}
	
	
	

}
