package com.qa.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JsonUtils {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Generic method for Deserialize the Json Response
	 * @param <T>
	 * @param response
	 * @param targetClass
	 * @return POJO
	 */
	public static <T> T desrialize(Response response, Class<T> targetClass) {
		
		try {
			
		return mapper.readValue(response.getBody().asString(), targetClass);
		
		}catch(Exception e) {
			
			throw new RuntimeException("deserialize failed.. "+ targetClass.getName());
			
		}
		
		
	}

}
