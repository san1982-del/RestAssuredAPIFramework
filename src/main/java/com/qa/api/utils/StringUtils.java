package com.qa.api.utils;

import java.util.Random;


public class StringUtils {
	
	
	public static String getRandomEmailID() {
		
		Random random = new Random();
		String emailID = "APITest"+random.nextInt()+"@darr.com";
		return emailID; 
		
	}

}
