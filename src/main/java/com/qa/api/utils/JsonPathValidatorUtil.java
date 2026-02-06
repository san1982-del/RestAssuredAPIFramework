package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.response.Response;

public class JsonPathValidatorUtil {
	
	public static <T> T read(Response response, String JsonQuery) {
		
		String jsonResponse = response.getBody().asString();
		ReadContext ctx = JsonPath.parse(jsonResponse);
		return ctx.read(JsonQuery);
		
	}
	
	public static <T> List<T> readList(Response response, String JsonQuery) {
			
			String jsonResponse = response.getBody().asString();
			ReadContext ctx = JsonPath.parse(jsonResponse);
			return ctx.read(JsonQuery);
			
		}
	
	public static <T> List<Map<String, T>> readListOfMaps(Response response, String JsonQuery) {
		
		String jsonResponse = response.getBody().asString();
		ReadContext ctx = JsonPath.parse(jsonResponse);
		return ctx.read(JsonQuery);
		
	}
	
	
	

}
